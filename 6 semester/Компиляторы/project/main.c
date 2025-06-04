#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "ast.h"
#include "codegen.h"

/* Внешние переменные и функции от Bison/Flex */
extern int yyparse();
extern FILE* yyin;
extern ASTNode* root;
extern int line_number;

void print_usage(const char* program_name) {
    printf("Использование: %s [опции] <входной_файл>\n", program_name);
    printf("Опции:\n");
    printf("  -o <файл>    Выходной файл (по умолчанию: output.s)\n");
    printf("  -ast         Вывести AST\n");
    printf("  -dot <файл>  Сохранить AST в формате DOT\n");
    printf("  -h           Показать эту справку\n");
}

int main(int argc, char* argv[]) {
    char* input_file = NULL;
    char* output_file = "output.s";
    char* dot_file = NULL;
    int show_ast = 0;
    
    /* Разбор аргументов командной строки */
    for (int i = 1; i < argc; i++) {
        if (strcmp(argv[i], "-o") == 0 && i + 1 < argc) {
            output_file = argv[++i];
        }
        else if (strcmp(argv[i], "-ast") == 0) {
            show_ast = 1;
        }
        else if (strcmp(argv[i], "-dot") == 0 && i + 1 < argc) {
            dot_file = argv[++i];
        }
        else if (strcmp(argv[i], "-h") == 0) {
            print_usage(argv[0]);
            return 0;
        }
        else if (argv[i][0] != '-') {
            input_file = argv[i];
        }
        else {
            fprintf(stderr, "Неизвестная опция: %s\n", argv[i]);
            print_usage(argv[0]);
            return 1;
        }
    }
    
    /* Проверка наличия входного файла */
    if (!input_file) {
        fprintf(stderr, "Ошибка: не указан входной файл\n");
        print_usage(argv[0]);
        return 1;
    }
    
    /* Открытие входного файла */
    yyin = fopen(input_file, "r");
    if (!yyin) {
        fprintf(stderr, "Ошибка: не удалось открыть файл %s\n", input_file);
        return 1;
    }
    
    printf("Компиляция файла: %s\n", input_file);
    
    /* Синтаксический анализ */
    if (yyparse() != 0) {
        fprintf(stderr, "Ошибка компиляции\n");
        fclose(yyin);
        return 1;
    }
    
    fclose(yyin);
    
    /* Проверка, что AST был построен */
    if (!root) {
        fprintf(stderr, "Ошибка: AST не был построен\n");
        return 1;
    }
    
    /* Вывод AST, если требуется */
    if (show_ast) {
        printf("\n=== Abstract Syntax Tree ===\n");
        print_ast(root, 0);
        printf("\n");
    }
    
    /* Сохранение AST в формате DOT */
    if (dot_file) {
        visualize_ast(root, dot_file);
    }
    
    /* Генерация кода */
    FILE* output = fopen(output_file, "w");
    if (!output) {
        fprintf(stderr, "Ошибка: не удалось создать файл %s\n", output_file);
        free_ast(root);
        return 1;
    }
    
    printf("Генерация кода...\n");
    generate_code(root, output);
    fclose(output);
    
    printf("Компиляция завершена успешно!\n");
    printf("Выходной файл: %s\n", output_file);
    
    /* Освобождение памяти */
    free_ast(root);
    
    return 0;
}