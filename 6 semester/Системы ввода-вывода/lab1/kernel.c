#include <stdarg.h> // переменное число аргументов
#include <stddef.h> // NULL

extern char __bss[], __bss_end[], __stack_top[];

// Прототипы функций
void sbi_console_putchar(int ch);
int sbi_console_getchar(void);

void putchar(char ch);
int getchar(void);

void printf(const char *format, ...);
void scanf(const char *format, ...);

// sbi specials
long sbi_get_spec_version(void);
long sbi_get_counter_num(void);
long sbi_get_counter_info(int counter_id);
void sbi_system_shutdown(void);




void *memset(void *s, int c, size_t n) {
    unsigned char *p = s;
    while (n--) {
        *p++ = (unsigned char)c;
    }
    return s;
}



void itoa(long num, char *str) {
    long i = 0;
    long isNegative = 0;

    if (num == 0) {
        str[i++] = '0';
        str[i] = '\0';
        return;
    }

    if (num < 0) {
        isNegative = 1;
        num = -num;
    }

    while (num != 0) {
        int rem = num % 10;
        str[i++] = (rem > 9) ? (rem - 10) + 'a' : rem + '0';
        num = num / 10;
    }

    if (isNegative) {
        str[i++] = '-';
    }

    str[i] = '\0';

    // Reverse the string
    long start = 0;
    long end = i - 1;
    while (start < end) {
        char temp = str[start];
        str[start] = str[end];
        str[end] = temp;
        start++;
        end--;
    }
}

long atoi(const char *str) {
    long res = 0;
    int sign = 1;
    int i = 0;

    if (str[0] == '-') {
        sign = -1;
        i++;
    }

    for (; str[i] != '\0'; ++i) {
        res = res * 10 + str[i] - '0';
    }

    return sign * res;
}

void print_counter_info(long counter_info) {
    char numStr[20];
    itoa(counter_info, numStr);
    printf("%s\n", numStr);
    long csr = counter_info & 0xFFF;
    long width = (counter_info >> 12) & 0x3F;
    long type = (counter_info >> (sizeof(long) * 8 - 1)) & 0x1;

    itoa(csr, numStr);
    printf("Counter Info:\n");
    printf("  CSR: %s\n", numStr);

    itoa(width, numStr);
    printf("  Width: %s\n", numStr);

    printf("  Type: %s\n", type == 0 ? "Hardware" : "Firmware");
}

void kernel_main(void) {
    char input[100];
    int choice;
    int counter_id;
    long counter_info;
    char numStr[20];

    while (1) {
        printf("\nOS Menu System:\n");
        printf("1. Get SBI specification version\n");
        printf("2. Get number of counters\n");
        printf("3. Get details of a counter\n");
        printf("4. System Shutdown\n");
        printf("Enter your choice (1-4): ");

        scanf("%s", input);
        choice = atoi(input);

        switch (choice) {
            case 1:
                itoa(sbi_get_spec_version(), numStr);
                printf("\nSBI Specification Version: %s\n", numStr);
                break;

            case 2:
                itoa(sbi_get_counter_num(), numStr);
                printf("\nNumber of Counters: %s\n", numStr);
                break;

            case 3:
                printf("\nEnter counter ID: ");
                scanf("%s", input);
                counter_id = atoi(input);
                counter_info = sbi_get_counter_info(counter_id);
                print_counter_info(counter_info);
                break;

            case 4:
                printf("\nShutting down system...\n");
                sbi_system_shutdown();
                break;

            default:
                printf("\nInvalid choice! Please enter a number between 1 and 4.\n");
        }
    }
}

__attribute__((section(".text.boot")))
__attribute__((naked))
void boot(void) {
	__asm__ __volatile__(
		"mv sp, %[stack_top]\n" // Устанавливаем указатель стека
		"j kernel_main\n"		// Переходим к функции main ядра
		:
		: [stack_top] "r" (__stack_top) // Передаем верхний адрес стека в виде %[stack_top]
	);
}




#define SBI_CONSOLE_PUTCHAR 1
#define SBI_CONSOLE_GETCHAR 2

void sbi_console_putchar(int ch) {
    __asm__ __volatile__ (
        "mv a0, %[ch]\n"
        "li a7, %[call_num]\n"
        "ecall\n"
        :
        : [ch] "r" (ch), [call_num] "i" (SBI_CONSOLE_PUTCHAR)
        : "a0", "a7"
    );
}

int sbi_console_getchar(void) {
    long ch;
    __asm__ __volatile__ (
        "li a7, %[call_num]\n"
        "ecall\n"
        "mv %[ch], a0\n"
        : [ch] "=r" (ch)
        : [call_num] "i" (SBI_CONSOLE_GETCHAR)
        : "a0", "a7"
    );
    return ch != -1 ? ch : 0;
}




void putchar(char ch) {
    sbi_console_putchar((int)ch);
}

int getchar(void) {
    int ch;
    while ((ch = sbi_console_getchar()) == 0) {
        for (volatile int i = 0; i < 1000; i++);
    }
    putchar(ch);
    return ch;
}




void printf(const char *format, ...) {
    va_list args;
    va_start(args, format);

    while (*format) {
        if (*format == '%') {
            format++;
            switch (*format) {
                case 'c': {
                    char ch = (char)va_arg(args, int);
                    putchar(ch);
                    break;
                }
                case 's': {
                    char *str = va_arg(args, char*);
                    while (*str) {
                        putchar(*str++);
                    }
                    break;
                }
                case '%':
                    putchar('%');
                    break;
                default:
                    break;
            }
        } else {
            putchar(*format);
        }
        format++;
    }

    va_end(args);
}



void scanf(const char *format, ...) {
    va_list args;
    va_start(args, format);
    
    while (*format) {
        if (*format == '%') {
            format++;
            switch (*format) {
                case 'c': {
                    char *c = va_arg(args, char*);
                    *c = (char)getchar();
                    break;
                }
                case 's': {
                    char *str = va_arg(args, char*);
                    int i = 0;
                    char ch;
                    
                    while ((ch = getchar()) == ' ' || ch == '\n' || ch == '\t');
                    
                    str[i++] = ch;
                    
                    while ((ch = getchar()) != '\n' && ch != '\r' && i < 99) {
                        str[i++] = ch;
                    }
                    
                    str[i] = '\0';
                    break;
                }
                default:
                    break;
            }
        }
        format++;
    }
    va_end(args);
}




struct sbiret {
    long error;   // a0 - код ошибки
    long value;   // a1 - значение
};


struct sbiret sbi_call(long arg0, long arg1, long arg2, long arg3, long arg4,
                       long arg5, long fid, long eid) {
    register long a0 __asm__("a0") = arg0;
    register long a1 __asm__("a1") = arg1;
    register long a2 __asm__("a2") = arg2;
    register long a3 __asm__("a3") = arg3;
    register long a4 __asm__("a4") = arg4;
    register long a5 __asm__("a5") = arg5;
    register long a6 __asm__("a6") = fid;
    register long a7 __asm__("a7") = eid;

    __asm__ __volatile__("ecall"
                         : "=r"(a0), "=r"(a1)
                         : "r"(a0), "r"(a1), "r"(a2), "r"(a3), "r"(a4), "r"(a5),
                           "r"(a6), "r"(a7)
                         : "memory");
    return (struct sbiret){.error = a0, .value = a1};
}


#define SBI_SPEC_EID 0x10
#define SBI_GET_SPEC_VERSION 0x00

#define SBI_COUNTER_EID 0x504D55
#define SBI_GET_COUNTER_NUM 0x00
#define SBI_GET_COUNTER_INFO 0x01

#define SBI_SYSTEM_SHUTDOWN 0x08

// long sbi_get_spec_version(void) {
// 	long res = sbi_call(0,0,0,0,0,0,SBI_GET_SPEC_VERSION,SBI_SPEC_EID).value;
// 	char ch = (char)(res);
// 	putchar(ch);
// 	return res;
// }

long sbi_get_spec_version(void) {
    return sbi_call(0, 0, 0, 0, 0, 0, SBI_GET_SPEC_VERSION, SBI_SPEC_EID).value;
}

long sbi_get_counter_num(void) {
    return sbi_call(0, 0, 0, 0, 0, 0, SBI_GET_COUNTER_NUM, SBI_COUNTER_EID).value;
}

long sbi_get_counter_info(int counter_id) {
    return sbi_call(counter_id, 0, 0, 0, 0, 0, SBI_GET_COUNTER_INFO, SBI_COUNTER_EID).value;
}

void sbi_system_shutdown(void) {
    __asm__ volatile (
        "li a7, %[call_num]\n"
        "ecall\n"
        :
        : [call_num] "i" (SBI_SYSTEM_SHUTDOWN)
        : "a7"
    );
}






// a0/a1 - возвращаемые значения (a0-a5 для передачи аргумента в функцию и возвращаемого значения функции)
// putchar когда выводится - нужно выводить строку