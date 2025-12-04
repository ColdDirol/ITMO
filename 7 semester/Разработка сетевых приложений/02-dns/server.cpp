#include <arpa/inet.h>
#include <netinet/in.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <unistd.h>

#include "dns_protocol.h"

int get_domain_text_length(unsigned char *domain) {
  int length = 0;
  unsigned char *p = domain;
  int is_first = 1;
  unsigned char full_domain[256];
  int fd_pos = 0;

  while (*p != 0) {
    int segment_len = *p;
    p++;
    for (int i = 0; i < segment_len; i++) {
      full_domain[fd_pos++] = *p++;
    }
    if (*p != 0) {
      full_domain[fd_pos++] = '.';
    }
  }
  full_domain[fd_pos] = '\0';

  if (fd_pos == 0) {
    return 1;
  }

  if (strstr((char *)full_domain, ".internal") != NULL ||
      strstr((char *)full_domain, ".local") != NULL) {
    unsigned char *first_dot =
        (unsigned char *)strchr((char *)full_domain, '.');
    if (first_dot != NULL) {
      return first_dot - full_domain;
    }
  }

  p = domain;
  is_first = 1;
  length = 0;

  while (*p != 0) {
    int segment_len = *p;
    if (!is_first) {
      length++;
    }
    length += segment_len;
    p += segment_len + 1;
    is_first = 0;
  }

  return length;
}

int get_domain_wire_length(unsigned char *domain) {
  int length = 0;
  unsigned char *p = domain;

  while (*p != 0) {
    length += *p + 1;
    p += *p + 1;
  }
  length++;

  return length;
}

void read_domain_name(unsigned char *reader, unsigned char *buffer,
                      unsigned char *name) {
  unsigned int p = 0, jumped = 0, offset;
  int count = 1;

  *name = '\0';

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
      count++;
    }
  }

  name[p] = '\0';

  if (jumped == 1) {
    count++;
  }
}

void convert_to_dns_format(unsigned char *dns, const unsigned char *host) {
  int lock = 0;
  strcat((char *)host, ".");

  for (unsigned int i = 0; i < strlen((char *)host); i++) {
    if (host[i] == '.') {
      *dns++ = i - lock;
      for (; lock < (int)i; lock++) {
        *dns++ = host[lock];
      }
      lock++;
    }
  }
  *dns++ = '\0';
}

int main(int argc, char *argv[]) {
  int port = 53;

  if (argc == 2) {
    port = atoi(argv[1]);
  }

  int sockfd = socket(AF_INET, SOCK_DGRAM, 0);
  if (sockfd < 0) {
    perror("Socket creation failed");
    return 1;
  }

  struct sockaddr_in server_addr, client_addr;
  memset(&server_addr, 0, sizeof(server_addr));
  memset(&client_addr, 0, sizeof(client_addr));

  server_addr.sin_family = AF_INET;
  server_addr.sin_addr.s_addr = INADDR_ANY;
  server_addr.sin_port = htons(port);

  if (bind(sockfd, (const struct sockaddr *)&server_addr, sizeof(server_addr)) <
      0) {
    perror("Bind failed");
    close(sockfd);
    return 1;
  }

  unsigned char buffer[65536];

  while (1) {
    socklen_t len = sizeof(client_addr);
    int n = recvfrom(sockfd, buffer, 65536, 0, (struct sockaddr *)&client_addr,
                     &len);

    if (n < 0) {
      continue;
    }

    struct DNS_HEADER *dns = (struct DNS_HEADER *)buffer;
    unsigned char *qname = &buffer[sizeof(struct DNS_HEADER)];

    int text_length = get_domain_text_length(qname);
    int wire_length = get_domain_wire_length(qname);

    dns->qr = 1;
    dns->aa = 1;
    dns->ra = 0;
    dns->rcode = 0;
    dns->ans_count = htons(1);
    dns->auth_count = 0;
    dns->add_count = 0;

    unsigned char *response_ptr = qname + wire_length + sizeof(struct QUESTION);

    *response_ptr++ = 0xc0;
    *response_ptr++ = 0x0c;

    struct R_DATA rdata;
    rdata.type = htons(T_A);
    rdata._class = htons(1);
    rdata.ttl = htonl(300);
    rdata.data_len = htons(4);

    memcpy(response_ptr, &rdata, sizeof(struct R_DATA));
    response_ptr += sizeof(struct R_DATA);

    response_ptr[0] = 0;
    response_ptr[1] = 0;
    response_ptr[2] = 0;
    response_ptr[3] = text_length;
    response_ptr += 4;

    int response_len = response_ptr - buffer;

    sendto(sockfd, buffer, response_len, 0, (struct sockaddr *)&client_addr,
           len);
  }

  close(sockfd);
  return 0;
}
