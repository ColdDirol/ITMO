//
// Created by vladimir on 01/22/25.
//

#include "kernel/types.h"
#include "user/user.h"
#include "kernel/fcntl.h"

// Функция потока
void thread_func(void *arg) {
    int id = *(int *)arg; // Получаем идентификатор потока из указателя
    sleep(10);
    printf("Thread %d: started\n", id);
    // Имитация работы потока
    for (int i = 0; i < 5; i++) {
        sleep(10);
        printf("Thread %d: working %d\n", id, i);
    }
    sleep(10);
    printf("Thread %d: finished\n", id);
    exit(0);
}

int main() {
    const int NUM_THREADS = 3;      // Количество потоков
    int thread_ids[NUM_THREADS];    // Идентификаторы потоков
    void *stacks[NUM_THREADS];      // Стек для каждого потока
    int pids[NUM_THREADS];          // PID потоков
    for (int i = 0; i < NUM_THREADS; i++) {
        thread_ids[i] = i + 1;
        // Выделяем память для стека
        stacks[i] = malloc(4096);
        if (stacks[i] == 0) {
            printf("Error: unable to allocate memory for stack\n");
            exit(1);
        }
        // Создаем поток
        pids[i] = clone(thread_func, &thread_ids[i], stacks[i]);
        // printf("%d thread created!\n", i);
        if (pids[i] < 0) {
            printf("Error: unable to create thread %d\n", i + 1);
            free(stacks[i]);
            exit(1);
        }
    }
    // Ожидаем завершения потоков
    for (int i = 0; i < NUM_THREADS; i++) {
        void *stack;
        int pid = join(pids[i], &stack);
        if (pid < 0) {
            printf("Error: unable to join thread %d\n", i + 1);
        } 
        free(stack);
    }
    printf("All threads finished.\n");
    exit(0);
}