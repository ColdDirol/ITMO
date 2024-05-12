#include <iostream>
#include <map>
using namespace std;

multimap<int, int> blocks_by_size;  // Мультимапа для хранения блоков по размеру.
map<int, int> blocks;                // Мапа для хранения блоков.

// Функция для удаления элемента из памяти по итератору в multimap.
void delete_from_memory(const multimap<int, int>::iterator& it){
    blocks.erase(it->second);         // Удаление блока из мапы blocks по индексу.
    blocks_by_size.erase(it);         // Удаление блока из мультимапы blocks_by_size.
}

// Функция для удаления элемента по размеру из мапы blocks и multimap blocks_by_size.
void delete_by_size(const map<int, int>::iterator& it){
    auto it_d = blocks_by_size.find(it->second);  // Поиск элемента в multimap по индексу.
    while (it_d->second != it->first) it_d++;     // Поиск нужного индекса в multimap.
    blocks_by_size.erase(it_d);                   // Удаление элемента из multimap.
    blocks.erase(it);                             // Удаление элемента из мапы.
}

// Функция для добавления нового блока в память.
void add(const pair<int, int>& pair){
    blocks.insert(pair);                          // Добавление блока в мапу blocks.
    blocks_by_size.insert({pair.second, pair.first});  // Добавление блока в multimap blocks_by_size.
}

// Функция для добавления блока в память с учётом размера.
void toAdd(int key, int& index, int& size) {
    auto it = blocks_by_size.lower_bound(key);     // Поиск блока в multimap по размеру.
    if (it == blocks_by_size.end()) index = -1;    // Если блок не найден, установка index в -1.
    else {
        index = it->second;                         // Установка index в индекс блока.
        size = it->first - key;                     // Вычисление размера свободного пространства.
        delete_from_memory(it);                     // Удаление блока из памяти.
        if (size > 0) add({index + key, size});     // Если есть свободное место, добавление блока.
    }
}

// Функция для объединения двух блоков в памяти.
void merge(map<int, int>::iterator it_left, map<int, int>::iterator it_right, int& index, int& size, int size_x) {
    index = it_left->first;                          // Установка index в индекс первого блока.
    size = it_left->second + it_right->second;       // Вычисление общего размера объединённого блока.
    delete_by_size(it_left);                         // Удаление первого блока из памяти.
    delete_by_size(it_right);                        // Удаление второго блока из памяти.
    add({index, size + size_x});                     // Добавление объединённого блока в память.
}

// Функция для добавления блока в память, если он не смежен с другими.
void not_adjacent(map<int, int>::iterator it_right, int index_x, int size_x, int& index, int& size) {
    size = it_right->second;                         // Установка size в размер блока.
    delete_by_size(it_right);                        // Удаление блока из памяти.
    add({index_x, size + size_x});                   // Добавление нового блока в память.
}

// Функция для добавления блока в память, если он смежен с другим слева.
void adjacent(map<int, int>::iterator it_left, int index_x, int size_x, int& index, int& size) {
    if (it_left != blocks.end() && it_left->first + it_left->second == index_x) {  // Если слева есть блок.
        index = it_left->first;                          // Установка index в индекс блока слева.
        size = it_left->second;                          // Установка size в размер блока слева.
        delete_by_size(it_left);                         // Удаление блока слева из памяти.
        add({index, size + size_x});                     // Добавление объединённого блока в память.
    } else add({index_x, size_x});                       // Добавление блока в память.
}

// Функция для удаления блока из памяти
void to_remove_from_memory(int key, int historySize, const pair<int, int> history[], int& index, int& size) {
    int index_x = history[abs(key) - 1].second;          // Получение индекса блока из истории.
    int size_x = history[abs(key) - 1].first;            // Получение размера блока из истории.
    if (index_x == -1) return;                           // Если индекс блока некорректен, завершить функцию.

    auto it_right = blocks.lower_bound(index_x);         // Поиск блока по индексу в мапе blocks.
    auto it_left = (it_right != blocks.begin()) ? prev(it_right) : blocks.end();  // Поиск блока слева.

    if (it_right != blocks.end() && it_right->first == index_x + size_x){  // Если блок справа найден.
        if (it_left != blocks.end() && it_left->first + it_left->second == index_x) {  // Если блок слева найден.
            merge(it_left, it_right, index, size, size_x);  // Объединить блоки.
        } else {
            not_adjacent(it_right, index_x, size_x, index, size);  // Добавить блок в память.
        }
    } else {
        adjacent(it_left, index_x, size_x, index, size);  // Добавить блок в память.
    }

    index = 0;
}


int main() {
    int n, m;                                 // Переменные для хранения размера памяти и числа операций.
    cin >> n >> m;                             // Ввод размера памяти и числа операций.
    pair<int, int> history[m];                 // Массив для хранения истории операций.
    int key, index, size;                      // Переменные для хранения ключа, индекса и размера.
    add({1, n});                               // Добавление начального блока в память.
    for (int i = 0; i < m; history[i] = {key, index}, i++){  // Цикл по всем операциям.
        cin >> key;                            // Ввод ключа операции.
        if (key > 0) {                         // Если ключ положительный.
            toAdd(key, index, size);           // Добавление блока в память.
            cout << index << endl;             // Вывод индекса добавленного блока.
        } else {
            to_remove_from_memory(key, m, history, index, size);  // Удаление блока из памяти.
        }
    }
    return 0;
}
