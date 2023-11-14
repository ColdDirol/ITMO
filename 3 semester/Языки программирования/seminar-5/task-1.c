#include <stdio.h>

#define print_var(x) printf(int is %d\n", x)
#define MY_CONSTANT 42

int main() {
    int x = 100;

    print_var(x);
    print_var(123);
    print_var(MY_CONSTANT);

    return 0;
}


// gcc -E task-1.c
