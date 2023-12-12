#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <unistd.h>
#include <sys/wait.h>

void* create_shared_memory(size_t size) {
    return mmap(NULL,
                size,
                PROT_READ | PROT_WRITE,
                MAP_SHARED | MAP_ANONYMOUS,
                -1, 0);
}

int main() {
    int* shmem = (int*) create_shared_memory(10 * sizeof(int));
    int pipe_parent_to_child[2], pipe_child_to_parent[2];

    if (pipe(pipe_parent_to_child) == -1 || pipe(pipe_child_to_parent) == -1) {
        perror("Pipe creation failed");
        exit(EXIT_FAILURE);
    }

    for (int i = 0; i < 10; i++) {
        shmem[i] = i + 1;
    }

    printf("Shared memory at: %p\n", shmem);

    int pid = fork();

    if (pid == 0) {
        // Дочерний процесс
        close(pipe_parent_to_child[1]);  // Закрываем конец для записи
        close(pipe_child_to_parent[0]);  // Закрываем конец для чтения

        while (1) {
            int index, value;
            // Читаем индекс и новое значение из родительского процесса
            if (read(pipe_parent_to_child[0], &index, sizeof(int)) == 0 || read(pipe_parent_to_child[0], &value, sizeof(int)) == 0) {
                printf("Pipes have been closed!");
                break;
            }

            // Меняем соответствующее число в массиве
            shmem[index] = value;
            printf("Changed shmem[%d] to %d\n", index, value);

            printf("The final array is: ");
            for (int i = 0; i < 10; i++) {
                printf("%d ", shmem[i]);
            }
            printf("\n");
        }

        close(pipe_parent_to_child[0]);  // Закрываем конец для чтения
        close(pipe_child_to_parent[1]);  // Закрываем конец для записи
        exit(0);
    } else {
        // Родительский процесс
        close(pipe_parent_to_child[0]);  // Закрываем конец для чтения
        close(pipe_child_to_parent[1]);  // Закрываем конец для записи

        for (int i = 0; i < 10; i++) {
            printf("%d ", shmem[i]);
        }
        printf("\n");

        int index, value;

        // Посылаем сообщения с индексами и значениями дочернему процессу
        for (int i = 0; i < 3; i++) {
            printf("Enter index and value: ");
            scanf("%d %d", &index, &value);
            if (index == -1) {
                close(pipe_parent_to_child[1]);
                close(pipe_child_to_parent[0]);
                break;
            }

            // Пишем индекс и значение в конвейер
            write(pipe_parent_to_child[1], &index, sizeof(int));
            write(pipe_parent_to_child[1], &value, sizeof(int));
        }

        // Закрываем конвейер после завершения передачи данных
        close(pipe_parent_to_child[1]);

        wait(NULL);  // Ждем завершения дочернего процесса

        // Закрываем конвейер для чтения
        close(pipe_child_to_parent[0]);

        munmap(shmem, 10 * sizeof(int));
    }

    return 0;
}
