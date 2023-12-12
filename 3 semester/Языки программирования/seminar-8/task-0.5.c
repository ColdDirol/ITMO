#include <pthread.h>
#include <stdio.h>
#include <unistd.h>

// Мьютекс для синхронизации доступа к выводу
pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;

void bad_print(char *s)
{
    // Заблокировать мьютекс перед входом в критическую секцию
    pthread_mutex_lock(&mutex);

    for (; *s != '\0'; s++)
    {
        fprintf(stdout, "%c", *s);
        fflush(stdout);
        usleep(100);
    }

    // Разблокировать мьютекс после выхода из критической секции
    pthread_mutex_unlock(&mutex);
}

void* my_thread(void* arg)
{
    for(int i = 0; i < 10; i++ )
    {
        bad_print((char *)arg);
        sleep(1);
    }
    return NULL;
}

int main()
{
    pthread_t t1, t2;

    // Инициализировать мьютекс
    pthread_mutex_init(&mutex, NULL);

    pthread_create(&t1, NULL, my_thread, "Hello\n");
    pthread_create(&t2, NULL, my_thread, "world\n");

    // Ожидать завершения потоков
    pthread_join(t1, NULL);
    pthread_join(t2, NULL);

    // Уничтожить мьютекс после использования
    pthread_mutex_destroy(&mutex);

    return 0;
}
