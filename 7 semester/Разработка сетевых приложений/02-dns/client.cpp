#include <arpa/inet.h>
#include <netdb.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <unistd.h>

#include "dns_protocol.h"

void convert_to_dns_format(unsigned char *dns, const unsigned char *host) {
  int lock = 0;
  unsigned char hostname[256];
  strcpy((char *)hostname, (char *)host);
  strcat((char *)hostname, ".");

  for (unsigned int i = 0; i < strlen((char *)hostname); i++) {
    if (hostname[i] == '.') {
      *dns++ = i - lock;
      for (; lock < (int)i; lock++) {
        *dns++ = hostname[lock];
      }
      lock++;
    }
  }
  *dns++ = '\0';
}

int read_domain_name(unsigned char *reader, unsigned char *buffer, int *count,
                     unsigned char *name) {
  unsigned int p = 0, jumped = 0, offset;
  *count = 1;

  name[0] = '\0';

  while (*reader != 0) {
    if (*reader >= 192) {
      offset = (*reader) * 256 + *(reader + 1) - 49152;
      reader = buffer + offset - 1;
      jumped = 1;
    } else {
      name[p++] = *reader;
    }

    reader++;

    if (jumped == 0) {
      (*count)++;
    }
  }

  name[p] = '\0';

  if (jumped == 1) {
    (*count)++;
  }

  return 0;
}

void dns_format_to_name(unsigned char *dns_name, unsigned char *name) {
  int p = 0;
  int i = 0;

  while (dns_name[i] != 0) {
    int len = dns_name[i];
    i++;
    for (int j = 0; j < len; j++) {
      name[p++] = dns_name[i++];
    }
    if (dns_name[i] != 0) {
      name[p++] = '.';
    }
  }
  name[p] = '\0';
}

int main(int argc, char *argv[]) {
  if (argc < 2) {
    printf("Usage: %s dns_name [server] [port]\n", argv[0]);
    return 1;
  }

  const char *dns_name = argv[1];
  const char *server = (argc >= 3) ? argv[2] : "127.0.0.1";
  int port = (argc >= 4) ? atoi(argv[3]) : 53;

  int sockfd = socket(AF_INET, SOCK_DGRAM, 0);
  if (sockfd < 0) {
    perror("Socket creation failed");
    return 1;
  }

  struct timeval tv;
  tv.tv_sec = 5;
  tv.tv_usec = 0;
  setsockopt(sockfd, SOL_SOCKET, SO_RCVTIMEO, &tv, sizeof(tv));

  struct sockaddr_in server_addr;
  memset(&server_addr, 0, sizeof(server_addr));

  server_addr.sin_family = AF_INET;
  server_addr.sin_port = htons(port);

  if (inet_pton(AF_INET, server, &server_addr.sin_addr) <= 0) {
    struct hostent *he = gethostbyname(server);
    if (he == NULL) {
      printf("Failed to resolve server address\n");
      close(sockfd);
      return 1;
    }
    memcpy(&server_addr.sin_addr, he->h_addr_list[0], he->h_length);
  }

  unsigned char buffer[65536];
  struct DNS_HEADER *dns = (struct DNS_HEADER *)buffer;

  dns->id = htons(getpid());
  dns->qr = 0;
  dns->opcode = 0;
  dns->aa = 0;
  dns->tc = 0;
  dns->rd = 1;
  dns->ra = 0;
  dns->z = 0;
  dns->ad = 0;
  dns->cd = 0;
  dns->rcode = 0;
  dns->q_count = htons(1);
  dns->ans_count = 0;
  dns->auth_count = 0;
  dns->add_count = 0;

  unsigned char *qname = &buffer[sizeof(struct DNS_HEADER)];
  convert_to_dns_format(qname, (unsigned char *)dns_name);

  int qname_len = strlen((char *)qname) + 1;
  struct QUESTION *qinfo =
      (struct QUESTION *)&buffer[sizeof(struct DNS_HEADER) + qname_len];

  qinfo->qtype = htons(T_A);
  qinfo->qclass = htons(1);

  int query_len =
      sizeof(struct DNS_HEADER) + qname_len + sizeof(struct QUESTION);

  if (sendto(sockfd, buffer, query_len, 0, (struct sockaddr *)&server_addr,
             sizeof(server_addr)) < 0) {
    perror("Send failed");
    close(sockfd);
    return 1;
  }

  socklen_t addr_len = sizeof(server_addr);
  int n = recvfrom(sockfd, buffer, 65536, 0, (struct sockaddr *)&server_addr,
                   &addr_len);

  if (n < 0) {
    perror("Receive failed");
    close(sockfd);
    return 1;
  }

  dns = (struct DNS_HEADER *)buffer;

  if (ntohs(dns->ans_count) == 0) {
    printf("No answers received\n");
    close(sockfd);
    return 1;
  }

  unsigned char *reader = &buffer[sizeof(struct DNS_HEADER)];
  unsigned char name[256];
  int stop = 0;

  read_domain_name(reader, buffer, &stop, name);
  reader += stop;

  reader += sizeof(struct QUESTION);

  for (int i = 0; i < ntohs(dns->ans_count); i++) {
    read_domain_name(reader, buffer, &stop, name);
    reader += stop;

    struct R_DATA *rdata = (struct R_DATA *)reader;
    reader += sizeof(struct R_DATA);

    if (ntohs(rdata->type) == T_A) {
      unsigned char ip[4];
      for (int j = 0; j < 4; j++) {
        ip[j] = reader[j];
      }

      unsigned char printable_name[256];
      dns_format_to_name(name, printable_name);

      printf("%s has IPv4 address %d.%d.%d.%d\n", printable_name, ip[0], ip[1],
             ip[2], ip[3]);
    }

    reader += ntohs(rdata->data_len);
  }

  close(sockfd);
  return 0;
}
