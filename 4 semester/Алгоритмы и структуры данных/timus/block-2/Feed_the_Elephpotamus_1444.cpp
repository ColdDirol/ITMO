#include <iostream>
#include <cmath>
using namespace std;

// По условию задачи элефпотам не должен пересекать свой путь.
// Чтобы такого не случилось, можно, чтобы элефпотам шёл по “кругу”
// (кругом это было бы, если бы расстояния от первой точки до остальных были бы одинаковыми,
// но представим что это круг, но радиус он может менять). Чтобы выстроить круг,
// для каждой точки найдем угол между прямой соединяющей её и начало координат, и осью x.
// Полученные углы отсортируем, и в таком порядке начнём движение.
//
// Чтобы элефпотам не пересекал свой путь, надо чтобы он шел по кругу в одну сторону.
// Таким образом он не будет возвращаться, и не пересечет свои следы.
// Чтобы выстроить круг, для каждой точки найдем угол, между прямой,
// соединяющей центр координат и заданную точку и осью ox. Полученные углы отсортируем, и будем идти по порядку.
// Таким образом элефпотам не пересекает свой след и сможет съесть все тыквы.

struct point { // создаём структуру - точка
    int index;
    int distance;
    double angle;
};

int returnDeltaX(int x, int x0) {
    return (x - x0);
}

int returnDeltaY(int y, int y0) {
    return (y - y0);
}

int returnDistance(int x0, int y0, int x, int y) { // расстояние между точками (в квадрате) по теореме пифагора, используя координаты
    int deltaX = returnDeltaX(x, x0);
    int deltaY = returnDeltaY(y, y0);
    return deltaX * deltaX + deltaY * deltaY;
}

int comparator(const void *first_variable, const void *second_variable) {
    const point*first_point = (point*) first_variable;
    const point*second_point = (point*) second_variable;
    double epsilon = 1e-8; // из-за округления, сравниваем не точно, а приблизительно
    if (first_point->angle - second_point->angle > epsilon) {return 1;} // сортируем по углу
    else if (first_point->angle - second_point->angle < (epsilon * -1)) {return -1;}
    else {if (first_point->distance < second_point->distance) {return -1;} else {return 1;}} // если углы примерно равны, то по расстоянию
}

int main() {
    int n;
    int x0, y0; // координаты первой точки
    scanf("%d", &n);
    scanf("%d%d", &x0, &y0);
    point coord[n]; // массив точек
    coord[0].index = 1; // индекс первой точки
    coord[0].distance = 0; // от первой точки до неё же - 0
    coord[0].angle = -100000; // берём маленький угол, чтоб точка оказалась в конце массива после сортировки
    for (int i = 1; i < n; i ++) {
        int x, y;
        scanf("%d%d", &x, &y);
        coord[i].index = i + 1; // индекс новой точки
        coord[i].distance = returnDistance(x0, y0, x, y); // расстояние от первой точки до текущей
        coord[i].angle = atan2(returnDeltaY(y, y0), returnDeltaX(x, x0)); // угол между прямой соединяющей текущую точку и первую, которую мы взями за начало координат и осью ox
        // угол считается через тангенс, на основе координат точки
        if (returnDeltaY(y, y0) < 0) { // если угол от pi до 0, прибывляем 2pi
            coord[i].angle += 3.14159265 * 2;
        };
    }
    qsort(coord, n, sizeof(point), comparator); // сортируем углы
    int first = 1;
    double maxAngle = coord[1].angle - coord[n-1].angle + 3.14159265 * 2; // ищем максимальный угол между двумя точками, чтобы решить, с какой точки начинать обход круга
    for (int i = 1; i < n - 1; i++) {
        double deltaAngle = coord[i + 1].angle - coord[i].angle;
        if (deltaAngle > maxAngle) {
            maxAngle = deltaAngle;
            first = i + 1;
        }
    }
    printf("%d", n);
    cout << '\n';
    printf("%d", 1);
    cout << '\n';
    for (int i = first; i < n; i++) { // начинаем обход с выбранной точки
        printf("%d", coord[i].index);
        cout << '\n';
    }
    for (int i = 1; i < first; i++) { // продолжаем обход
        printf("%d", coord[i].index);
        cout << '\n';
    }
    return 0;
}