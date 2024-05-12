#include <iostream>
#include <algorithm>

bool compare(int a, int b) {
    return a > b;
}

int main() {
    int n;
    scanf("%d", &n);
    int k;
    scanf("%d", &k);

    int check[n];
    for (int i = 0; i < n; i++){
        scanf("%d", &check[i]);
    }

    std::sort(check, check + n, compare);

    int sum = 0;
    for (int i = 1; i <= n; i++) {
        if (i % k != 0) sum += check[i-1];
    }
    printf("%d", sum);
}