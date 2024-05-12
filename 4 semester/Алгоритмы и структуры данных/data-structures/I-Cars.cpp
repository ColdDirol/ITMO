#include <iostream>
#include <queue>
#include <unordered_set>
#include <vector>
using namespace std;

// на полу не осталось свободного места
void noSpaceOnTheFloor(unordered_set<int> &carsOnTheFloor, priority_queue<pair<int, int>> &queueOfCars) {
    carsOnTheFloor.erase(queueOfCars.top().second); // убираем машинку находящуюся на вершине очереди с приоритетом
    queueOfCars.pop(); // удаляем вершину
}

// ставим машинку на пол
void take_new_car(int &answer, int request, unordered_set<int> &active_cars) {
    answer++; // увеличиваем число ходов мамы
    active_cars.insert(request); // мама ставит машинку на пол
}

// проверка, что весь пол занят
void isFull(unordered_set<int> &carsOnTheFloor, priority_queue<pair<int, int>> &queueOfCars, int k) {
    if (carsOnTheFloor.size() >= k) { // число машинок на полу больше его вместивости
        noSpaceOnTheFloor(carsOnTheFloor, queueOfCars); // вызываем удаление машинки
    }
}

// проверка, что нужная машинка уже находится на полу
void isOnFloor(unordered_set<int> &carsOnTheFloor, priority_queue<pair<int, int>> &queueOfCars, int k, int &count, int current) {
    if (carsOnTheFloor.count(current) == 0){ // проверка, что нужных машинок на полу нет
        isFull(carsOnTheFloor, queueOfCars, k); // проверяем, можно ли поставить машинку
        take_new_car(count, current, carsOnTheFloor); // ставим машинку
    }
}

int main() {
    int n, k, p;
    scanf("%d%d%d", &n, &k, &p);
    vector<int> cars; // история запросов
    queue<int> timeOfEntrance[n]; // массив очередей, содержащий для каждой машинки времена её вхождений
    unordered_set<int> carsOnTheFloor; // множество машинок на полу
    priority_queue<pair<int, int>> queueOfCars; // (куча) очередь с приоритетом, содержащая время следующего вхождения и машинку
    for (int i = 0; i < p; i++) { // заполняем история запросов
        int car;
        scanf("%d", &car);
        car--;
        cars.push_back(car);
    }
    for (int i = 0; i < p; i++) {
        timeOfEntrance[cars[i]].push(i); // заполняем время вхождения машинок
    }
    int count = 0; // считаем ответ
    for (int i = 0; i < p; i++){
        int current = cars[i]; // выбираем машинку, которую нужно поставить
        int a = 500001; // если машинка больше не появится, время её следующего вхождения - "бесконечность" (так как мы каждый раз выбираем минимальное время вхождения)
        timeOfEntrance[current].pop(); // удаляем текущее вхождение
        if (!timeOfEntrance[current].empty()) {a = timeOfEntrance[current].front();} // если машинка ещё появится, выбираем время её следующего вхождения
        isOnFloor(carsOnTheFloor, queueOfCars, k, count, current); // запускаем помещение машинки на пол
        queueOfCars.push({a, current}); // добавляем машинку с временем её следующего вхождения
    }
    printf("%d", count); // выводим число ходов мамы
    return 0;
}