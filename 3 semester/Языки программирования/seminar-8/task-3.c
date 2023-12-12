#define ARR_SIZE 10
#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <string.h>
#include <unistd.h>
#include <semaphore.h>
#include <fcntl.h>

void* create_shared_memory(size_t size) {
    return mmap(NULL,
                size,
                PROT_READ | PROT_WRITE,
                MAP_SHARED | MAP_ANONYMOUS,
                -1, 0);
}

int main(void) {
    sem_t *child_ready, *parent_ready;
    child_ready = sem_open("/child_ready", O_CREAT, 0644, 0);
    parent_ready = sem_open("/parent_ready", O_CREAT, 0644, 0);

    int* shmem = create_shared_memory(sizeof(int)*10);
    for (size_t i = 0; i < 10; i++) {
        shmem[i] = i + 1;
    }
    printf("Shared memory at: %p\n" , (void*)shmem);
    int pid = fork();
    if (pid == 0) {
        // Дочерний процесс
        while (1) {
            printf("[c] Enter index and value: ");
            size_t index;
            int value;
            scanf("%zu %d", &index, &value);
            if (index < 0) {
                printf("[c] Error! Error! Error!");
                break;
            }
            if (index < 10) {
                shmem[index] = value;
            }
            printf("[c] Finishing child process...\n");
            sem_post(child_ready);
            sem_wait(parent_ready);
        }
    } else {
        // Родительский процесс
        while (1) {
            sem_wait(child_ready);
            printf("[p] The final array is: ");
            for (size_t i = 0; i < 10; i++) {
                printf("%d ", shmem[i]);
            }
            printf("\n");
            sem_post(parent_ready);
        }
    }

    return 0;
}
