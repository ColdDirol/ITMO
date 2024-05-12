#include <iostream>
#include <vector>

using namespace std;

void addGoblin(int number, vector<int> &goblins, int &count) { // добавление гоблина в конец очереди
    count++;
    goblins.push_back(number);
}

void addPrivilegedGoblin(int number, vector<int> &goblins, int &count) { // добавление гоблина в середину очереди
    int half = (count + 1) / 2;
    goblins.insert(goblins.cbegin() + half, number);
    count++;
}

void goblinLeft(vector<int> &goblins, int &count) { // удаление гоблина из начала очереди
    cout << goblins[0] << '\n'; // вывод первого гоблина
    count--;
    goblins.erase(goblins.cbegin());
}

void choiseFunction(string sign, vector<int> &goblins, int &count) { // выбираем действие
    if (sign == "-") { // запрос на удаление
        goblinLeft(goblins, count);
    } else {
        int number; // читаем номер гоблина
        cin >> number;
        if (sign == "+") { // запрос на добавление в конец очереди
            addGoblin(number, goblins, count);
        } else {addPrivilegedGoblin(number, goblins, count);} // запрос на добавление в середину очереди
    }
}

int main()
{
    int n;
    cin >> n;
    string sign;
    vector<int> goblins; // очередь
    int count = 0;
    for (int i = 0; i < n; i++) {
        cin >> sign; // считываем запрос
        choiseFunction(sign, goblins, count); // выбираем действие
    }
    return 0;
}