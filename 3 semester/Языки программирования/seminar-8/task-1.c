#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <string.h>
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
    // Выделяем память для 10 целых чисел
    int* shmem = (int*) create_shared_memory(10 * sizeof(int));

    // Заполняем массив числами от 1 до 10
    for (int i = 0; i < 10; i++) {
        shmem[i] = i + 1;
    }

    printf("Shared memory at: %p\n" , shmem);
    int pid = fork();

    if (pid == 0) {
        // Дочерний процесс
        int index, value;
        // Считываем индекс и новое значение
        printf("Enter index and value: ");
        scanf("%d %d", &index, &value);
        // Проверяем, что индекс в диапазоне от 0 до 9
        if (index >= 0 && index < 10) {
            // Меняем соответствующее число в массиве
            shmem[index] = value;
            printf("Changed shmem[%d] to %d\n", index, value);
        } else {
            printf("Invalid index\n");
        }
        // Завершаем работу дочернего процесса
        exit(0);
    } else {
        // Родительский процесс
        // Ждем завершения дочернего процесса
        wait(NULL);
        // Выводим все 10 чисел на экран
        printf("The final array is: ");
        for (int i = 0; i < 10; i++) {
            printf("%d ", shmem[i]);
        }
        printf("\n");
        // Освобождаем память
        munmap(shmem, 10 * sizeof(int));
    }
}
