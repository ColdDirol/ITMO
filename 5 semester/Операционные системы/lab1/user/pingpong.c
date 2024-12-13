//
// Created by vladimir on 11/26/24.
//

#include "kernel/types.h"
#include "user/user.h"

#define MESSAGE_SIZE 4
#define BUFFER_SIZE 5
#define MESSAGE_PING "ping"
#define MESSAGE_PONG "pong"
#define END_LINE '\0'

#define SUCCESSFUL 0
#define ERROR      1

int main() {
  int p[2];
  char message[BUFFER_SIZE];

  if (pipe(p) < 0) {
    printf("pipe error\n");
    exit(ERROR);
  }

  int child = fork();
  if (child < 0) {
    printf("fork error\n");
    exit(ERROR);
  }

  if (child > 0) {
    write(p[1], MESSAGE_PING, MESSAGE_SIZE);
    wait((int *) 0);

    int n = read(p[0], message, sizeof(message) - 1);
    message[n] = END_LINE;
    printf("%d: got %s\n", getpid(), message);

    close(p[1]);
    close(p[0]);
  } else if (child == 0) {
    int n = read(p[0], message, sizeof(message) - 1);
    message[n] = END_LINE;
    printf("%d: got %s\n", getpid(), message);

    write(p[1], MESSAGE_PONG, MESSAGE_SIZE);
  } else {
    printf("fork error\n");
  }

  exit(SUCCESSFUL);
}