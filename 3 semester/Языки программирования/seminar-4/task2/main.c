#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <stdio.h>
#include <stddef.h>
#include <stdint.h>
#include <sys/mman.h>

void print_substring(char* address, size_t length) {
    for (size_t i = 0; i < length; i++) {
        printf("%c", address[i]);
    }
    printf("\n");
}

int main() {
    int file_descriptor;
    struct stat file_stat;

    file_descriptor = open("file.txt", O_RDONLY);
    if (file_descriptor == -1) {
        perror("Error opening file");
        return 1;
    }

    if (fstat(file_descriptor, &file_stat) == -1) {
        perror("Error getting file information");
        close(file_descriptor);
        return 1;
    }

    off_t file_size = (off_t)file_stat.st_size;
    printf("File size: %ld\n", (long)file_size);

    char* file_content = mmap(NULL, (size_t)file_size, PROT_READ, MAP_PRIVATE, file_descriptor, 0);
    if (file_content == MAP_FAILED) {
        perror("Error mapping file");
        close(file_descriptor);
        return 1;
    }

    print_substring(file_content, (size_t)file_size);

    munmap(file_content, (size_t)file_size);

    close(file_descriptor);

    return 0;
}
