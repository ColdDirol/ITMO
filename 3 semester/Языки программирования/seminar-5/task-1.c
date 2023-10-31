#include <stdio.h>

#define print_var(x) printf(#x " is %d\n", x)
#define MY_CONSTANT 42

int main() {
    int x = 100;

    print_var(x);
    print_var(123);
    print_var(MY_CONSTANT);

    return 0;
}


// gcc -E your_program.c
