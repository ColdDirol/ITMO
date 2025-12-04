#include <arpa/inet.h>
#include <netinet/in.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <time.h>
#include <unistd.h>
#include <vector>

struct ClientInfo {
  int socket_fd;
  pthread_mutex_t* clients_mutex;
  std::vector<ClientInfo*>* clients;
};

ssize_t recv_all(int sock, void* buffer, size_t length) {
  size_t total = 0;
  char* ptr = (char*)buffer;

  while (total < length) {
    ssize_t n = recv(sock, ptr + total, length - total, 0);
    if (n <= 0) {
      return n;
    }
    total += n;
  }
  return total;
}

ssize_t send_all(int sock, const void* buffer, size_t length) {
  size_t total = 0;
  const char* ptr = (const char*)buffer;

  while (total < length) {
    ssize_t n = send(sock, ptr + total, length - total, 0);
    if (n <= 0) {
      return n;
    }
    total += n;
  }
  return total;
}

void* handle_client(void* arg) {
  ClientInfo* info = (ClientInfo*)arg;
  int client_fd = info->socket_fd;

  while (true) {
    uint32_t nick_size_net;
    ssize_t bytes = recv_all(client_fd, &nick_size_net, 4);
    if (bytes <= 0) {
      break;
    }

    uint32_t nick_size = ntohl(nick_size_net);
    if (nick_size == 0 || nick_size > 1024) {
      break;
    }

    char* nickname = (char*)malloc(nick_size + 1);
    if (!nickname) {
      break;
    }

    bytes = recv_all(client_fd, nickname, nick_size);
    if (bytes <= 0) {
      free(nickname);
      break;
    }
    nickname[nick_size] = '\0';

    uint32_t body_size_net;
    bytes = recv_all(client_fd, &body_size_net, 4);
    if (bytes <= 0) {
      free(nickname);
      break;
    }

    uint32_t body_size = ntohl(body_size_net);
    if (body_size == 0 || body_size > 4096) {
      free(nickname);
      break;
    }

    char* message_body = (char*)malloc(body_size + 1);
    if (!message_body) {
      free(nickname);
      break;
    }

    bytes = recv_all(client_fd, message_body, body_size);
    if (bytes <= 0) {
      free(nickname);
      free(message_body);
      break;
    }
    message_body[body_size] = '\0';

    time_t now = time(NULL);
    struct tm* local = localtime(&now);
    char timestamp[64];
    snprintf(timestamp, sizeof(timestamp), "%02d:%02d:%02d", local->tm_hour,
             local->tm_min, local->tm_sec);

    uint32_t date_size = strlen(timestamp);
    uint32_t date_size_net = htonl(date_size);

    pthread_mutex_lock(info->clients_mutex);
    for (size_t i = 0; i < info->clients->size(); i++) {
      int target_fd = (*info->clients)[i]->socket_fd;

      if (send_all(target_fd, &nick_size_net, 4) <= 0) {
        continue;
      }
      if (send_all(target_fd, nickname, nick_size) <= 0) {
        continue;
      }
      if (send_all(target_fd, &body_size_net, 4) <= 0) {
        continue;
      }
      if (send_all(target_fd, message_body, body_size) <= 0) {
        continue;
      }
      if (send_all(target_fd, &date_size_net, 4) <= 0) {
        continue;
      }
      if (send_all(target_fd, timestamp, date_size) <= 0) {
        continue;
      }
    }
    pthread_mutex_unlock(info->clients_mutex);

    free(nickname);
    free(message_body);
  }

  pthread_mutex_lock(info->clients_mutex);
  for (auto it = info->clients->begin(); it != info->clients->end(); ++it) {
    if ((*it)->socket_fd == client_fd) {
      info->clients->erase(it);
      break;
    }
  }
  pthread_mutex_unlock(info->clients_mutex);

  close(client_fd);
  delete info;
  return NULL;
}

int main(int argc, char* argv[]) {
  if (argc != 2) {
    fprintf(stderr, "Usage: %s <port>\n", argv[0]);
    exit(1);
  }

  uint16_t port = (uint16_t)atoi(argv[1]);

  int server_fd = socket(AF_INET6, SOCK_STREAM, 0);
  if (server_fd < 0) {
    perror("Socket creation failed");
    exit(1);
  }

  int opt = 0;
  setsockopt(server_fd, IPPROTO_IPV6, IPV6_V6ONLY, &opt, sizeof(opt));

  int reuse = 1;
  setsockopt(server_fd, SOL_SOCKET, SO_REUSEADDR, &reuse, sizeof(reuse));

  struct sockaddr_in6 server_addr;
  memset(&server_addr, 0, sizeof(server_addr));
  server_addr.sin6_family = AF_INET6;
  server_addr.sin6_addr = in6addr_any;
  server_addr.sin6_port = htons(port);

  if (bind(server_fd, (struct sockaddr*)&server_addr, sizeof(server_addr)) <
      0) {
    perror("Bind failed");
    close(server_fd);
    exit(1);
  }

  if (listen(server_fd, 10) < 0) {
    perror("Listen failed");
    close(server_fd);
    exit(1);
  }

  printf("Server listening on port %d\n", port);

  std::vector<ClientInfo*> clients;
  pthread_mutex_t clients_mutex = PTHREAD_MUTEX_INITIALIZER;

  while (true) {
    struct sockaddr_in6 client_addr;
    socklen_t client_len = sizeof(client_addr);
    int client_fd =
        accept(server_fd, (struct sockaddr*)&client_addr, &client_len);

    if (client_fd < 0) {
      perror("Accept failed");
      continue;
    }

    ClientInfo* info = new ClientInfo;
    info->socket_fd = client_fd;
    info->clients_mutex = &clients_mutex;
    info->clients = &clients;

    pthread_mutex_lock(&clients_mutex);
    clients.push_back(info);
    pthread_mutex_unlock(&clients_mutex);

    pthread_t thread_id;
    pthread_create(&thread_id, NULL, handle_client, info);
    pthread_detach(thread_id);
  }

  close(server_fd);
  return 0;
}
