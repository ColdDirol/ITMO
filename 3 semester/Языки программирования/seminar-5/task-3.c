#include <stdio.h>

// Обобщённый макрос для добавления элемента в конец списка
#define list_push(list, item) _Generic((list), \
    int*: list_push_int, \
    float*: list_push_float, \
    char*: list_push_char \
)(list, item)

// Обобщённый макрос для вывода списка на экран
#define list_print(list) _Generic((list), \
    int*: list_print_int, \
    float*: list_print_float, \
    char*: list_print_char \
)(list)

// Функция для добавления int в конец списка int
void list_push_int(int* list, int item) {
    // Реализация добавления элемента в конец списка
    // Для простоты просто увеличим длину списка и присвоим новое значение
    list[list[0] + 1] = item;
    list[0]++;
}

// Функция для вывода списка int на экран
void list_print_int(int* list) {
    int length = list[0];
    for (int i = 1; i <= length; i++) {
        printf("%d ", list[i]);
    }
    printf("\n");
}

// Функция для добавления float в конец списка float
void list_push_float(float* list, float item) {
    // Реализация добавления элемента в конец списка
    // Для простоты просто увеличим длину списка и присвоим новое значение
    list[(int)list[0] + 1] = item;
    list[0]++;
}

// Функция для вывода списка float на экран
void list_print_float(float* list) {
    int length = (int)list[0];
    for (int i = 1; i <= length; i++) {
        printf("%.2f ", list[i]);
    }
    printf("\n");
}

// Функция для добавления char в конец списка char
void list_push_char(char* list, char item) {
    // Реализация добавления элемента в конец списка
    // Для простоты просто увеличим длину списка и присвоим новое значение
    list[list[0] + 1] = item;
    list[0]++;
}

// Функция для вывода списка char на экран
void list_print_char(char* list) {
    int length = list[0];
    for (int i = 1; i <= length; i++) {
        printf("%c ", list[i]);
    }
    printf("\n");
}

int main() {
    // Создание и вывод трех разных списков
    int intList[100] = {0};
    list_push(intList, 10);
    list_push(intList, 20);
    list_push(intList, 30);
    list_print(intList);

    float floatList[100] = {0};
    list_push(floatList, 3.14);
    list_push(floatList, 2.718);
    list_print(floatList);

    char charList[100] = {0};
    list_push(charList, 'A');
    list_push(charList, 'B');
    list_push(charList, 'C');
    list_print(charList);

    return 0;
}
