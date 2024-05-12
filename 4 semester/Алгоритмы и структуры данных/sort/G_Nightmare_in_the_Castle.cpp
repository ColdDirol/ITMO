#include <iostream>
#include <algorithm>
using namespace std;

// Докажем, что буквы максимального веса должны стоять как можно ближе к краям строки:
// Пусть вес буквы y = p, а вес буквы x = p + 1, n – длина строки.
// Рассмотрим строки “xy…yx”, “yx…xy”, “yx…yx”:

// Вес строки “xy…yx” = (n - 2) * (p + 1) + (n - 4) * p = np – 2p + n – 2 + np – 4p = 2np – 6p + n – 2
// Вес строки “yx…xy” = (n - 4) * (p + 1) + (n - 2) * p = np – 4p + n – 4 + np – 2p = 2np – 6p + n – 4
// Вес строки “yx…yx” = (n - 3) * (2p + 1) = 2pn – 6p + n – 3

// Заметим, что 2np – 6p + n – 2 > 2pn – 6p + n – 3 > 2np – 6p + n – 4 => Вес строки “xy…yx” > веса строки “yx…xy” > веса строки “yx…yx”.
// Что и требовалось доказать.

void change(int &a, int &b) {
    int temp = a;
    a = b;
    b = temp;
}

void change(char &a, char &b) {
    char temp = a;
    a = b;
    b = temp;
}

void bubbleSort(vector<char> &pairLetters, int weights[]) { // сортируем массив парных букв пузырьком по убыванию
    for (int i = 0; i < pairLetters.size(); i++) {
        for (int j = 0; j < pairLetters.size() - 1; j++) {
            if (weights[pairLetters[j] - 97] < weights[pairLetters[j + 1] - 97]) { // 97 - 'a' в юникоде, поэтому 'a' - 97 = 0, и индекс 0 в массиве, 'b' - 97 = 1 и т.д.
                change(pairLetters[j], pairLetters[j + 1]);
            }
        }
    }
}

int main() {
    string s;
    cin >> s; // читаем строку
    int weights[26];
    int numberOfLetters[26];
    for (int i = 0; i < 26; i++) {
        cin >> weights[i]; // читаем вес каждой буквы алфавита
        numberOfLetters[i] = 0; // количество каждой буквы при равниваем к нулю
    }
    for (int i = 0; i < s.size(); i++) {
        numberOfLetters[s[i] - 97]++; // считаем количество каждой буквы в строке
    }
    vector<char> pairLetters; // массив букв, имеющих пару
    vector<char> unpairLetters; // массив букв, не имеющих пару
    for (int i = 0; i < 26; i++) {
        if (numberOfLetters[i] >= 2) { // ищем буквы, имеющие пары
            pairLetters.push_back(97 + i);
            numberOfLetters[i] -= 2;
        }
    }
    for (int i = 0; i < 26; i++) {
        while (numberOfLetters[i] > 0) { // ищем оставшиеся буквы, и записываем их в массив букв, не имеющих пару
            numberOfLetters[i]--;
            unpairLetters.push_back(i + 97);
        }
    }
    bubbleSort(pairLetters, weights); // сортируем парные буквы по их весу в порядке убывания
    for (int i = 0; i < pairLetters.size(); i++) { // выводим парные буквы
        cout << pairLetters[i];
    }
    for (int i = 0; i < unpairLetters.size(); i++) { // выводим не парные буквы
        cout << unpairLetters[i];
    }
    reverse(pairLetters.begin(), pairLetters.end());
    for (int i = 0; i < pairLetters.size(); i++) { // снова выводим парные буквы, только уже в обратном порядке
        cout << pairLetters[i];
    }
    return 0;
}