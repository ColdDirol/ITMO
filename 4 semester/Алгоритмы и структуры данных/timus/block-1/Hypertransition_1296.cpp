#include <cstdio>
#include <algorithm>

int main() {
    int n;
    scanf("%d", &n);
    int sum_p = 0;
    int min = 0;
    int max = 0;

    int p;
    for (int i = 0; i < n; i++) {
        scanf("%d", &p);
        sum_p += p;
        max = std::max(sum_p - min, max);
        min = std::min(min, sum_p);
    }
    printf("%d", std::max(max, 0));
    return 0;
}