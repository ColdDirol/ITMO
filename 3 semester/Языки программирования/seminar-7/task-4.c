// #include <stdio.h>
// #include <stdlib.h>
// #include <string.h>
//
//
// int check_password(FILE *f, const char *password) {
//   char buffer[10];
//   int okay = 0;
//   fscanf(f, "%s", buffer);
//   if (strcmp(buffer, password) == 0)
//     okay = 1;
//
//   return okay;
// }
//
// int main(int argc, char **argv) {
//   if (check_password(stdin, "password"))
//     puts("Access granted.");
//   else
//     puts("Wrong password.");
// }

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define BUFFER_SIZE 20  // Максимальная длина пароля, которую вы ожидаете

int check_password(FILE *f, const char *password) {
  char buffer[BUFFER_SIZE];
  int okay = 0;

  // Используйте ограничивающую ширину для чтения пароля
  if (fscanf(f, "%19s", buffer) == 1) {
    // Проверка, что считанный пароль не превышает размер буфера
    if (strcmp(buffer, password) == 0)
      okay = 1;
  }

  return okay;
}

int main(int argc, char **argv) {
  if (check_password(stdin, "password"))
    puts("Access granted.");
  else
    puts("Wrong password.");
}
