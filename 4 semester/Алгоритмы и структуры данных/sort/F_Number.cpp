#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

// Для получения максимального числа - нам надо отсортировать листочки.
// В качестве компаратора используем функцию isMore(), которая сравнивает, при какой из перестановок, число получается больше.

bool isMore(string k, string iAnswer) { // для чисел vec[j] и vec[j+1] проверяем, какая строка больше, после склеивания, строка "vec[j] + vec[j + 1]" или "vec[j + 1] + vec[j]"
    if (k + iAnswer > iAnswer + k) {
        return true;
    }
    return false;
}

void bubbleSort(vector<string> &vec) {
    for (int i = 0; i < vec.size(); i++) { // сортировка пузырьком
        for (int j = 0; j < vec.size() - 1; j++) {
            if (!(isMore(vec[j], vec[j + 1]))) { // сортируем с помощью функции isMore
                string t = vec[j];
                vec[j] = vec[j + 1];
                vec[j + 1] = t;
            }
        }
    }
}

int main() {
    string k;
    vector<string> answer;
    int count = 0;
    while (cin >> k) {
        answer.push_back(k); // читаем числа в массив
    }
    bubbleSort(answer); // сортируем массив с использованием функции isMore
    for (int i = 0; i < answer.size(); i++) {
        cout << answer[i]; // выводим массив
    }
    return 0;
}