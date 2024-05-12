#include <algorithm>
#include <set>
#include <iostream>
#include <vector>
using namespace std;

bool compareByFirst(pair<int, int> a, pair<int, int> b) { // сортируем по x
    if (a.first != b.first) {
        return a.first < b.first;
    }
    return a.second < b.second;
}

bool compareBySecond(pair<int, int> a, pair<int, int> b) { // сортируем по y
    if (a.second != b.second) {
        return a.second < b.second;
    }
    return a.first < b.first;
}

void horizontalBorder(int m, vector<pair<int, int>> &black, int n, int &k) { // добавление горизонтальных границ
    k += m * 2;
    for (int i = 1; i <= m; i++) {
        black.push_back({0, i});
        black.push_back({n + 1, i});
    }
}

void verticalBorder(int m, vector<pair<int, int>> &black, int n, int &k){ // добавление вертикальных границ
    k += n * 2;
    for (int i = 1; i <= n; i++) {
        black.push_back({i, 0});
        black.push_back({i, m + 1});
    }
}

void read(int k, vector<pair<int, int>> &black){ // добавление чёрных прямоугольников
    int x, y;
    for (int i = 0; i < k; i++) {
        scanf("%d%d", &x, &y);
        black.push_back({x, y});
    }
}

// забыла удалить, есть в main
void countByY(int k, int &count, set<pair<int, int>> &onePixel, vector<pair<int, int>> &black){
    for (int i = 0; i < k; i++) {
        int line = black[i + 1].second - black[i].second;
        if (line == 2) {
            onePixel.insert({black[i].first, black[i].second + 1});
        } else if (black[i].first == black[i + 1].first) {
            if (line > 2) {
                count++;
            }
        }
    }
}

int main() {
    int m, n, k;
    scanf("%d%d%d", &m, &n, &k);
    int x, y;
    vector<pair<int, int>> black; // координаты чёрных квадратов
    set<pair<int, int>> onePixel; // множество прямоугольников со стороной 1 пиксель (возможно, только одной)
    int count;
    read(k, black); // читаем вводимые значения
    verticalBorder(m, black, n, k); // добавляем вертикальную границу
    horizontalBorder(m, black, n, k); // добавляем горизонтальную границу
    int one = 0; // число белых полос длины 1
    k = black.size(); // обновляем количество чёрных квадратов (мы ведь добавили границы)
    sort(black.begin(), black.begin() + k, compareByFirst); // сортировка по иксу
    for (int i = 0; i < k; i++) {
        int line = black[i + 1].second - black[i].second; // считаем длину белой полосы, как расстояние между соседними чырным квадратами
        if (line == 2) { // если расстояние равно 2, то длина полосы = 1, возможно, по другой координате она длинее, поэтому пока сохраним её в отдельное множество
            onePixel.insert(make_pair(black[i].first, black[i].second + 1));
        } else if (black[i].first == black[i + 1].first) { // если концы полосы находятся на одной линии
            if (line > 2) { // и длина линии больше единицы
                count++; // добавляем к линиям
            }
        }
    }
    sort(black.begin(), black.begin() + k, compareBySecond); // сортирока по y
    for (int i = 0; i < k; i++) {
        int line = black[i + 1].first - black[i].first; // считаем длину белой полосы, как расстояние между соседними чырным квадратами
        if (line == 2) { // если расстояние равно 2, то длина полосы = 1, если она равно 1 и по другой координате, тогда это квадрат 1x1
            if (onePixel.count(make_pair(black[i].first + 1, black[i].second)) != 0) { // для этого проверяем её наличие в множестве
                one++; // если она там есть, то добавляем её
            }
        } else if (black[i].second == black[i + 1].second) { // если концы полосы находятся на одной линии
            if (line > 2) { // и длина линии больше единицы
                count++; // добавляем к линиям
            }
        }
    }
    count = count + one; // добавляем одиночные полосы к остальным
    cout << count; // выводим результат
    return 0;
}