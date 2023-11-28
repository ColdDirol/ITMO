// #include <stdio.h>
// #include <stdlib.h>
//
// struct user {
//   const char *name, *password;
// } const users[] = {{"Cat", "Meowmeow"}, {"Skeletor", "Nyarr"}};
//
// void print_users() {
//   printf("Users:\n");
//   for (size_t i = 0; i < sizeof(users) / sizeof(struct user); i++) {
//     printf("%s: %s\n", users[i].name, users[i].password);
//   }
// }
//
// void vulnerable() {
//   char buffer[8];
//   if (fgets(buffer, sizeof(buffer), stdin) == NULL) {
//     // Handle error or exit
//     perror("fgets");
//     exit(EXIT_FAILURE);
//   }
// }
//
// int main(int argc, char **argv) {
//   vulnerable();
//
//   puts("nothing happened");
// }

#include <stdio.h>
#include <stdlib.h>

struct user {
  const char *name, *password;
} const users[] = {{"Cat", "Meowmeow"}, {"Skeletor", "Nyarr"}};

void print_users() {
  printf("Users:\n");
  for (size_t i = 0; i < sizeof(users) / sizeof(struct user); i++) {
    printf("%s: %s\n", users[i].name, users[i].password);
  }
}

// void fill(FILE *f, char *where) {
//   size_t read_total = 0;
//   for (;;) {
//     size_t read = fread(where + read_total, 1, 1, f);
//     if (!read)
//       break;
//     else
//       read_total += read;
//   }
// }

void fill(FILE *f, char *where, size_t size) {
  size_t read_total = 0;
  while (read_total < size) {
    size_t read = fread(where + read_total, 1, size - read_total, f);
    if (read == 0) {
      // Handle error or exit if end of file
      if (feof(f)) {
        break;
      } else {
        perror("fread");
        exit(EXIT_FAILURE);
      }
    } else {
      read_total += read;
    }
  }
}

void vulnerable(FILE *f) {
  char buffer[8];
  fill(f, buffer, sizeof(buffer));
}

int main(int argc, char **argv) {
  vulnerable(stdin);

  puts("nothing happened");
}

