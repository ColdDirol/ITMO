#include <iostream>
#include <deque>
#include <vector>

using namespace std;

void addFirstWindow(int k, deque<int> &win, int line[]) { // добавляем первые K значений
    for (int i = 0; i < k; i++) {
        while (win.size() != 0 and line[i] <= line[win[win.size() - 1]]) { // в окне не должно быть значений, больше добавляемого, они всё равно не будут минимальны
            win.pop_back();
        }
        win.push_back(i); // добавляем индекс нового значения
    }
}

void deletePreviousNumbers(deque<int> &win, int i, int k) {
    while (win.size() != 0 and win[0] <= i - k) { // удаляем значения, вышедшие за пределы окна
        win.pop_front();
    }
}

void deleteBiggerNumbers(deque<int> &win, int line[], int i) { // удаляем значения, большие последнего ("сталинская сортировка")
    while (win.size() != 0 and line[i] <= line[win[win.size() - 1]]) {
        win.pop_back();
    }
}

int main() {
    int n, k;
    scanf("%d%d", &n, &k);
    int line[n];
    for (int i = 0; i < n; i++) {
        scanf("%d", &line[i]); // заполняем историю запросов
    }
    deque<int> win; // дек, хранящий индексы элементов, находящихся в окне
    addFirstWindow(k, win, line); // заполняем начальное окно
    printf("%d ", line[win[0]]);
    for (int i = k; i < n; i++) {
        deletePreviousNumbers(win, i, k); // удаляем значения, вышедшие за пределы окна
        deleteBiggerNumbers(win, line, i); // "сортируем"
        win.push_back(i); // добавляем новый индекс
        printf("%d ", line[win[0]]); // наименьшим элементом являемся первый (мы ведь сталинскую сортировку использовали)
    }
    return 0;
}
