#include <cstdio>

int simulate(int bacteria, int b, int c, int d, long k) {
    if (bacteria <= 0) {
        return 0;
    }

    if (bacteria > d) {
        return d;
    }

    if (k <= 0) {
        return bacteria;
    }
    int new_bacteria = bacteria * b - c;
    if (bacteria == new_bacteria) {
        return bacteria;
    }
    if (new_bacteria <= 0) {
        return 0;
    }

    return simulate(new_bacteria, b, c, d, k - 1);
}

// a - количество бактерий в начале дня
// b - кол-во образовавшихся бактерий вместо каждой бактерии из a
// c - кол-во используемых бактерий из бактерий из инкубатора
// d - вместимость бактерий
// k - кол-во дней эксперимента

int main() {
    int a;
    short b, c, d;
    long k;

    scanf("%d %hd %hd %hd %ld", &a, &b, &c, &d, &k);

    int remaining_bacteria = simulate(a, b, c, d, k);

    printf("%ld", remaining_bacteria);

    return 0;
}