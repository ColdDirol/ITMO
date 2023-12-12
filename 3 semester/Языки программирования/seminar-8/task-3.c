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

struct Data {
    int index;
    int value;
};

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
            struct Data data;
            // Читаем данные из родительского процесса
            read(pipe_parent_to_child[0], &data, sizeof(struct Data));
            if (data.index < 0) {
                break;  // Если получен отрицательный индекс, завершаем цикл
            }

            // Меняем соответствующее число в массиве
            shmem[data.index] = data.value;
            printf("Changed shmem[%d] to %d\n", data.index, data.value);

            printf("The final array is: ");
            for (int i = 0; i < 10; i++) {
                printf("%d ", shmem[i]);
            }
            printf("\n");

            // Отправляем сигнал родительскому процессу
            write(pipe_child_to_parent[1], "OK", 2);
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

        struct Data data;

        // Посылаем сообщения с индексами и значениями дочернему процессу
        for (int i = 0; i < 3; i++) {
            printf("Enter index and value: ");
            scanf("%d %d", &data.index, &data.value);
            if (data.index == -1) {
                break;
            }

            // Пишем данные в конвейер
            write(pipe_parent_to_child[1], &data, sizeof(struct Data));

            // Ждем сигнала от дочернего процесса
            char buf[2];
            read(pipe_child_to_parent[0], buf, 2);
        }

        // Завершаем передачу данных, отправляя отрицательный индекс
        data.index = -1;
        write(pipe_parent_to_child[1], &data, sizeof(struct Data));

        // Закрываем конвейер после завершения передачи данных
        close(pipe_parent_to_child[1]);

        wait(NULL);  // Ждем завершения дочернего процесса

        // Закрываем конвейер для чтения
        close(pipe_child_to_parent[0]);

        munmap(shmem, 10 * sizeof(int));
    }

    return 0;
}
