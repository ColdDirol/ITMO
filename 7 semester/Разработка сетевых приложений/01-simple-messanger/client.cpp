#include <arpa/inet.h>
#include <netdb.h>
#include <netinet/in.h>
#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <termios.h>
#include <unistd.h>
#include <string>

struct ThreadData {
  int socket_fd;
  std::string nickname;
  bool* input_mode;
  pthread_mutex_t* mode_mutex;
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

void* receive_messages(void* arg) {
  ThreadData* data = (ThreadData*)arg;
  int sock = data->socket_fd;

  while (true) {
    uint32_t nick_size_net;
    ssize_t bytes = recv_all(sock, &nick_size_net, 4);
    if (bytes <= 0) {
      break;
    }

    uint32_t nick_size = ntohl(nick_size_net);
    if (nick_size == 0 || nick_size > 1024) {
      continue;
    }

    char* nickname = (char*)malloc(nick_size + 1);
    if (!nickname) {
      continue;
    }

    bytes = recv_all(sock, nickname, nick_size);
    if (bytes <= 0) {
      free(nickname);
      break;
    }
    nickname[nick_size] = '\0';

    uint32_t body_size_net;
    bytes = recv_all(sock, &body_size_net, 4);
    if (bytes <= 0) {
      free(nickname);
      break;
    }

    uint32_t body_size = ntohl(body_size_net);
    if (body_size == 0 || body_size > 4096) {
      free(nickname);
      continue;
    }

    char* message = (char*)malloc(body_size + 1);
    if (!message) {
      free(nickname);
      continue;
    }

    bytes = recv_all(sock, message, body_size);
    if (bytes <= 0) {
      free(nickname);
      free(message);
      break;
    }
    message[body_size] = '\0';

    uint32_t date_size_net;
    bytes = recv_all(sock, &date_size_net, 4);
    if (bytes <= 0) {
      free(nickname);
      free(message);
      break;
    }

    uint32_t date_size = ntohl(date_size_net);
    if (date_size == 0 || date_size > 128) {
      free(nickname);
      free(message);
      continue;
    }

    char* timestamp = (char*)malloc(date_size + 1);
    if (!timestamp) {
      free(nickname);
      free(message);
      continue;
    }

    bytes = recv_all(sock, timestamp, date_size);
    if (bytes <= 0) {
      free(nickname);
      free(message);
      free(timestamp);
      break;
    }
    timestamp[date_size] = '\0';

    pthread_mutex_lock(data->mode_mutex);
    if (!*(data->input_mode)) {
      printf("{%s} [%s] %s\n", timestamp, nickname, message);
      fflush(stdout);
    }
    pthread_mutex_unlock(data->mode_mutex);

    free(nickname);
    free(message);
    free(timestamp);
  }

  return NULL;
}

void send_message(int sock, const char* nickname, const char* message) {
  uint32_t nick_size = strlen(nickname);
  uint32_t body_size = strlen(message);

  uint32_t nick_size_net = htonl(nick_size);
  uint32_t body_size_net = htonl(body_size);

  if (send_all(sock, &nick_size_net, 4) <= 0) {
    return;
  }
  if (send_all(sock, nickname, nick_size) <= 0) {
    return;
  }
  if (send_all(sock, &body_size_net, 4) <= 0) {
    return;
  }
  if (send_all(sock, message, body_size) <= 0) {
    return;
  }
}

int main(int argc, char* argv[]) {
  if (argc != 4) {
    fprintf(stderr, "Usage: %s <server_address> <port> <nickname>\n", argv[0]);
    exit(1);
  }

  const char* server_address = argv[1];
  uint16_t port = (uint16_t)atoi(argv[2]);
  const char* nickname = argv[3];

  struct addrinfo hints, *result, *rp;
  memset(&hints, 0, sizeof(hints));
  hints.ai_family = AF_UNSPEC;
  hints.ai_socktype = SOCK_STREAM;

  char port_str[16];
  snprintf(port_str, sizeof(port_str), "%d", port);

  if (getaddrinfo(server_address, port_str, &hints, &result) != 0) {
    fprintf(stderr, "Failed to resolve server address\n");
    exit(1);
  }

  int sock = -1;
  for (rp = result; rp != NULL; rp = rp->ai_next) {
    sock = socket(rp->ai_family, rp->ai_socktype, rp->ai_protocol);
    if (sock == -1) {
      continue;
    }

    if (connect(sock, rp->ai_addr, rp->ai_addrlen) == 0) {
      break;
    }

    close(sock);
    sock = -1;
  }

  freeaddrinfo(result);

  if (sock == -1) {
    fprintf(stderr, "Failed to connect to server\n");
    exit(1);
  }

  bool input_mode = false;
  pthread_mutex_t mode_mutex = PTHREAD_MUTEX_INITIALIZER;

  ThreadData thread_data;
  thread_data.socket_fd = sock;
  thread_data.nickname = nickname;
  thread_data.input_mode = &input_mode;
  thread_data.mode_mutex = &mode_mutex;

  pthread_t recv_thread;
  pthread_create(&recv_thread, NULL, receive_messages, &thread_data);

  bool is_tty = isatty(STDIN_FILENO);
  struct termios old_term, new_term;

  if (is_tty) {
    printf("Connected to server. Press 'm' to send message, 'q' to quit.\n");
    tcgetattr(STDIN_FILENO, &old_term);
    new_term = old_term;
    new_term.c_lflag &= ~(ICANON | ECHO);
    tcsetattr(STDIN_FILENO, TCSANOW, &new_term);

    char buffer[4096];
    while (true) {
      char ch = getchar();

      if (ch == 'q' || ch == 'Q') {
        break;
      }

      if (ch == 'm' || ch == 'M') {
        pthread_mutex_lock(&mode_mutex);
        input_mode = true;
        pthread_mutex_unlock(&mode_mutex);

        tcsetattr(STDIN_FILENO, TCSANOW, &old_term);

        printf("\nEnter message: ");
        fflush(stdout);

        if (fgets(buffer, sizeof(buffer), stdin) != NULL) {
          size_t len = strlen(buffer);
          if (len > 0 && buffer[len - 1] == '\n') {
            buffer[len - 1] = '\0';
          }

          if (strlen(buffer) > 0) {
            send_message(sock, nickname, buffer);
          }
        }

        tcsetattr(STDIN_FILENO, TCSANOW, &new_term);

        pthread_mutex_lock(&mode_mutex);
        input_mode = false;
        pthread_mutex_unlock(&mode_mutex);
      }
    }

    tcsetattr(STDIN_FILENO, TCSANOW, &old_term);
    printf("\nDisconnected\n");
  } else {
    char buffer[4096];
    while (fgets(buffer, sizeof(buffer), stdin) != NULL) {
      size_t len = strlen(buffer);
      if (len > 0 && buffer[len - 1] == '\n') {
        buffer[len - 1] = '\0';
      }

      if (strlen(buffer) > 0) {
        if (buffer[0] == 'm' || buffer[0] == 'M') {
          if (fgets(buffer, sizeof(buffer), stdin) != NULL) {
            len = strlen(buffer);
            if (len > 0 && buffer[len - 1] == '\n') {
              buffer[len - 1] = '\0';
            }
            if (strlen(buffer) > 0) {
              send_message(sock, nickname, buffer);
            }
          }
        } else {
          send_message(sock, nickname, buffer);
        }
      }
    }
  }

  close(sock);
  pthread_mutex_destroy(&mode_mutex);
  return 0;
}
