// Пример 1: Простые арифметические операции
int a = 10;
int b = 20;
int c = a + b;
print(c);  // Выведет 30

// Пример 2: Использование условных операторов
int x = 15;
if (x > 10) {
    print(1);  // Это выполнится
} else {
    print(0);
}

// Пример 3: Вложенные условия
int score = 85;
if (score >= 90) {
    print(5);  // Отлично
} else {
    if (score >= 80) {
        print(4);  // Хорошо
    } else {
        if (score >= 70) {
            print(3);  // Удовлетворительно
        } else {
            print(2);  // Неудовлетворительно
        }
    }
}

// Пример 4: Цикл while
int i = 1;
while (i <= 5) {
    print(i);
    i = i + 1;
}

// Пример 5: Факториал числа
int n = 5;
int factorial = 1;
int counter = 1;

while (counter <= n) {
    factorial = factorial * counter;
    counter = counter + 1;
}
print(factorial);  // Выведет 120

// Пример 6: Логические операции
int p = 1;
int q = 0;

if (p and not q) {
    print(1);  // Это выполнится
}

if (p or q) {
    print(2);  // Это тоже выполнится
}

// Пример 7: Операции сравнения
int num1 = 10;
int num2 = 20;

if (num1 < num2) {
    print(num1);  // Выведет меньшее число
} else {
    print(num2);
}

// Пример 8: Вычисление суммы чисел от 1 до n
int n = 10;
int sum = 0;
int j = 1;

while (j <= n) {
    sum = sum + j;
    j = j + 1;
}
print(sum);  // Выведет 55

// Пример 9: Проверка четности числа
int number = 42;
int remainder = number - (number / 2) * 2;

if (remainder == 0) {
    print(1);  // Четное
} else {
    print(0);  // Нечетное
}

// Пример 10: Нахождение максимума из трех чисел
int a1 = 15;
int b1 = 25;
int c1 = 20;
int max = a1;

if (b1 > max) {
    max = b1;
}
if (c1 > max) {
    max = c1;
}
print(max);  // Выведет 25