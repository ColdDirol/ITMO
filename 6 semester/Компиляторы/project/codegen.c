#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "ast.h"
#include "codegen.h"

/* Таблица символов для переменных */
typedef struct Symbol {
    char* name;
    char* type;
    int offset;  /* Смещение от fp */
    struct Symbol* next;
} Symbol;

typedef struct {
    Symbol* head;
    int current_offset;
    int label_count;
    FILE* output;
} CodeGenContext;

/* Создание контекста генерации кода */
CodeGenContext* create_context(FILE* output) {
    CodeGenContext* ctx = (CodeGenContext*)malloc(sizeof(CodeGenContext));
    ctx->head = NULL;
    ctx->current_offset = -8;  /* Начинаем с -8 от fp */
    ctx->label_count = 0;
    ctx->output = output;
    return ctx;
}

/* Поиск символа в таблице */
Symbol* find_symbol(CodeGenContext* ctx, const char* name) {
    Symbol* sym = ctx->head;
    while (sym) {
        if (strcmp(sym->name, name) == 0) {
            return sym;
        }
        sym = sym->next;
    }
    return NULL;
}

/* Добавление символа в таблицу */
void add_symbol(CodeGenContext* ctx, const char* name, const char* type) {
    Symbol* sym = (Symbol*)malloc(sizeof(Symbol));
    sym->name = strdup(name);
    sym->type = strdup(type);
    sym->offset = ctx->current_offset;
    sym->next = ctx->head;
    ctx->head = sym;
    
    ctx->current_offset -= 8;  /* Каждая переменная занимает 8 байт */
}

/* Генерация метки */
char* gen_label(CodeGenContext* ctx) {
    char* label = (char*)malloc(32);
    sprintf(label, ".L%d", ctx->label_count++);
    return label;
}

/* Генерация кода для выражения */
void gen_expression(CodeGenContext* ctx, ASTNode* node) {
    if (!node) return;
    
    switch (node->type) {
        case NODE_NUMBER:
            fprintf(ctx->output, "    li a0, %d\n", node->int_value);
            break;
            
        case NODE_STRING:
            /* Для строк создаем метку в секции данных */
            fprintf(ctx->output, "    la a0, .str%d\n", ctx->label_count);
            /* Добавляем строку в секцию данных позже */
            break;
            
        case NODE_IDENTIFIER: {
            Symbol* sym = find_symbol(ctx, node->string_value);
            if (sym) {
                fprintf(ctx->output, "    ld a0, %d(fp)\n", sym->offset);
            } else {
                fprintf(stderr, "Ошибка: неопределенная переменная %s\n", 
                        node->string_value);
            }
            break;
        }
            
        case NODE_ADD:
            gen_expression(ctx, node->left);
            fprintf(ctx->output, "    sd a0, 0(sp)\n");
            fprintf(ctx->output, "    addi sp, sp, -8\n");
            gen_expression(ctx, node->right);
            fprintf(ctx->output, "    addi sp, sp, 8\n");
            fprintf(ctx->output, "    ld t0, 0(sp)\n");
            fprintf(ctx->output, "    add a0, t0, a0\n");
            break;
            
        case NODE_SUB:
            gen_expression(ctx, node->left);
            fprintf(ctx->output, "    sd a0, 0(sp)\n");
            fprintf(ctx->output, "    addi sp, sp, -8\n");
            gen_expression(ctx, node->right);
            fprintf(ctx->output, "    addi sp, sp, 8\n");
            fprintf(ctx->output, "    ld t0, 0(sp)\n");
            fprintf(ctx->output, "    sub a0, t0, a0\n");
            break;
            
        case NODE_MUL:
            gen_expression(ctx, node->left);
            fprintf(ctx->output, "    sd a0, 0(sp)\n");
            fprintf(ctx->output, "    addi sp, sp, -8\n");
            gen_expression(ctx, node->right);
            fprintf(ctx->output, "    addi sp, sp, 8\n");
            fprintf(ctx->output, "    ld t0, 0(sp)\n");
            fprintf(ctx->output, "    mul a0, t0, a0\n");
            break;
            
        case NODE_DIV:
            gen_expression(ctx, node->left);
            fprintf(ctx->output, "    sd a0, 0(sp)\n");
            fprintf(ctx->output, "    addi sp, sp, -8\n");
            gen_expression(ctx, node->right);
            fprintf(ctx->output, "    addi sp, sp, 8\n");
            fprintf(ctx->output, "    ld t0, 0(sp)\n");
            fprintf(ctx->output, "    div a0, t0, a0\n");
            break;
            
        case NODE_LT:
            gen_expression(ctx, node->left);
            fprintf(ctx->output, "    sd a0, 0(sp)\n");
            fprintf(ctx->output, "    addi sp, sp, -8\n");
            gen_expression(ctx, node->right);
            fprintf(ctx->output, "    addi sp, sp, 8\n");
            fprintf(ctx->output, "    ld t0, 0(sp)\n");
            fprintf(ctx->output, "    slt a0, t0, a0\n");
            break;
            
        case NODE_GT:
            gen_expression(ctx, node->left);
            fprintf(ctx->output, "    sd a0, 0(sp)\n");
            fprintf(ctx->output, "    addi sp, sp, -8\n");
            gen_expression(ctx, node->right);
            fprintf(ctx->output, "    addi sp, sp, 8\n");
            fprintf(ctx->output, "    ld t0, 0(sp)\n");
            fprintf(ctx->output, "    slt a0, a0, t0\n");
            break;
            
        case NODE_EQ:
            gen_expression(ctx, node->left);
            fprintf(ctx->output, "    sd a0, 0(sp)\n");
            fprintf(ctx->output, "    addi sp, sp, -8\n");
            gen_expression(ctx, node->right);
            fprintf(ctx->output, "    addi sp, sp, 8\n");
            fprintf(ctx->output, "    ld t0, 0(sp)\n");
            fprintf(ctx->output, "    sub a0, t0, a0\n");
            fprintf(ctx->output, "    seqz a0, a0\n");
            break;
            
        case NODE_NE:
            gen_expression(ctx, node->left);
            fprintf(ctx->output, "    sd a0, 0(sp)\n");
            fprintf(ctx->output, "    addi sp, sp, -8\n");
            gen_expression(ctx, node->right);
            fprintf(ctx->output, "    addi sp, sp, 8\n");
            fprintf(ctx->output, "    ld t0, 0(sp)\n");
            fprintf(ctx->output, "    sub a0, t0, a0\n");
            fprintf(ctx->output, "    snez a0, a0\n");
            break;
            
        case NODE_LE:
            gen_expression(ctx, node->left);
            fprintf(ctx->output, "    sd a0, 0(sp)\n");
            fprintf(ctx->output, "    addi sp, sp, -8\n");
            gen_expression(ctx, node->right);
            fprintf(ctx->output, "    addi sp, sp, 8\n");
            fprintf(ctx->output, "    ld t0, 0(sp)\n");
            fprintf(ctx->output, "    slt a0, a0, t0\n");
            fprintf(ctx->output, "    xori a0, a0, 1\n");
            break;
            
        case NODE_GE:
            gen_expression(ctx, node->left);
            fprintf(ctx->output, "    sd a0, 0(sp)\n");
            fprintf(ctx->output, "    addi sp, sp, -8\n");
            gen_expression(ctx, node->right);
            fprintf(ctx->output, "    addi sp, sp, 8\n");
            fprintf(ctx->output, "    ld t0, 0(sp)\n");
            fprintf(ctx->output, "    slt a0, t0, a0\n");
            fprintf(ctx->output, "    xori a0, a0, 1\n");
            break;
            
        case NODE_AND:
            gen_expression(ctx, node->left);
            fprintf(ctx->output, "    beqz a0, .and_false%d\n", ctx->label_count);
            gen_expression(ctx, node->right);
            fprintf(ctx->output, "    j .and_end%d\n", ctx->label_count);
            fprintf(ctx->output, ".and_false%d:\n", ctx->label_count);
            fprintf(ctx->output, "    li a0, 0\n");
            fprintf(ctx->output, ".and_end%d:\n", ctx->label_count);
            ctx->label_count++;
            break;
            
        case NODE_OR:
            gen_expression(ctx, node->left);
            fprintf(ctx->output, "    bnez a0, .or_true%d\n", ctx->label_count);
            gen_expression(ctx, node->right);
            fprintf(ctx->output, "    j .or_end%d\n", ctx->label_count);
            fprintf(ctx->output, ".or_true%d:\n", ctx->label_count);
            fprintf(ctx->output, "    li a0, 1\n");
            fprintf(ctx->output, ".or_end%d:\n", ctx->label_count);
            ctx->label_count++;
            break;
            
        case NODE_NOT:
            gen_expression(ctx, node->left);
            fprintf(ctx->output, "    seqz a0, a0\n");
            break;
            
        case NODE_NEGATE:
            gen_expression(ctx, node->left);
            fprintf(ctx->output, "    neg a0, a0\n");
            break;
            
        default:
            /* Для других типов узлов ничего не делаем */
            break;
    }
}

/* Генерация кода для оператора */
void gen_statement(CodeGenContext* ctx, ASTNode* node);

/* Генерация кода для списка операторов */
void gen_statement_list(CodeGenContext* ctx, ASTNode* node) {
    if (node->type == NODE_STATEMENT_LIST) {
        gen_statement_list(ctx, node->left);
        gen_statement(ctx, node->right);
    } else {
        gen_statement(ctx, node);
    }
}

/* Генерация кода для оператора */
void gen_statement(CodeGenContext* ctx, ASTNode* node) {
    if (!node) return;
    
    switch (node->type) {
        case NODE_DECLARATION: {
            /* Получаем имя переменной */
            char* var_name = node->string_value;
            char* var_type = node->left->string_value;
            
            /* Добавляем в таблицу символов */
            add_symbol(ctx, var_name, var_type);
            
            /* Если есть инициализация */
            if (node->right) {
                gen_expression(ctx, node->right);
                Symbol* sym = find_symbol(ctx, var_name);
                fprintf(ctx->output, "    sd a0, %d(fp)\n", sym->offset);
            }
            break;
        }
        
        case NODE_ASSIGNMENT: {
            char* var_name = node->string_value;
            Symbol* sym = find_symbol(ctx, var_name);
            
            if (sym) {
                gen_expression(ctx, node->left);
                fprintf(ctx->output, "    sd a0, %d(fp)\n", sym->offset);
            } else {
                fprintf(stderr, "Ошибка: неопределенная переменная %s\n", var_name);
            }
            break;
        }
        
        case NODE_IF: {
            char* else_label = gen_label(ctx);
            char* end_label = gen_label(ctx);
            
            /* Вычисляем условие */
            gen_expression(ctx, node->left);
            fprintf(ctx->output, "    beqz a0, %s\n", else_label);
            
            /* Генерируем код для then-части */
            gen_statement(ctx, node->right);
            fprintf(ctx->output, "    j %s\n", end_label);
            
            fprintf(ctx->output, "%s:\n", else_label);
            fprintf(ctx->output, "%s:\n", end_label);
            
            free(else_label);
            free(end_label);
            break;
        }
        
        case NODE_IF_ELSE: {
            char* else_label = gen_label(ctx);
            char* end_label = gen_label(ctx);
            
            /* Вычисляем условие */
            gen_expression(ctx, node->left);
            fprintf(ctx->output, "    beqz a0, %s\n", else_label);
            
            /* Генерируем код для then-части */
            gen_statement(ctx, node->right->left);
            fprintf(ctx->output, "    j %s\n", end_label);
            
            /* Генерируем код для else-части */
            fprintf(ctx->output, "%s:\n", else_label);
            gen_statement(ctx, node->right->right);
            
            fprintf(ctx->output, "%s:\n", end_label);
            
            free(else_label);
            free(end_label);
            break;
        }
        
        case NODE_WHILE: {
            char* loop_label = gen_label(ctx);
            char* end_label = gen_label(ctx);
            
            fprintf(ctx->output, "%s:\n", loop_label);
            
            /* Вычисляем условие */
            gen_expression(ctx, node->left);
            fprintf(ctx->output, "    beqz a0, %s\n", end_label);
            
            /* Генерируем код тела цикла */
            gen_statement(ctx, node->right);
            fprintf(ctx->output, "    j %s\n", loop_label);
            
            fprintf(ctx->output, "%s:\n", end_label);
            
            free(loop_label);
            free(end_label);
            break;
        }
        
        case NODE_PRINT: {
            gen_expression(ctx, node->left);
            
            /* Вызов системной функции для печати */
            /* Для RISC-V эмулятора используем ecall */
            fprintf(ctx->output, "    # Печать значения в a0\n");
            fprintf(ctx->output, "    li a7, 1    # Системный вызов print_int\n");
            fprintf(ctx->output, "    ecall\n");
            
            /* Печать новой строки */
            fprintf(ctx->output, "    li a0, 10   # Символ новой строки\n");
            fprintf(ctx->output, "    li a7, 11   # Системный вызов print_char\n");
            fprintf(ctx->output, "    ecall\n");
            break;
        }
        
        case NODE_BLOCK:
            if (node->left) {
                gen_statement_list(ctx, node->left);
            }
            break;
            
        case NODE_STATEMENT_LIST:
            gen_statement_list(ctx, node);
            break;
            
        default:
            /* Это может быть выражение-оператор */
            gen_expression(ctx, node);
            break;
    }
}

/* Генерация кода для всей программы */
void generate_code(ASTNode* root, FILE* output) {
    CodeGenContext* ctx = create_context(output);
    
    /* Заголовок программы */
    fprintf(output, ".text\n");
    fprintf(output, ".globl _start\n");
    fprintf(output, "_start:\n");
    
    /* Настройка стека и frame pointer */
    fprintf(output, "    # Инициализация стека\n");
    fprintf(output, "    la sp, stack_top\n");
    fprintf(output, "    mv fp, sp\n");
    fprintf(output, "    addi sp, sp, -256  # Резервируем место для локальных переменных\n");
    fprintf(output, "\n");
    
    /* Генерация кода для программы */
    if (root) {
        gen_statement_list(ctx, root);
    }
    
    /* Завершение программы */
    fprintf(output, "\n");
    fprintf(output, "    # Завершение программы\n");
    fprintf(output, "    li a0, 0\n");
    fprintf(output, "    li a7, 93   # Системный вызов exit\n");
    fprintf(output, "    ecall\n");
    fprintf(output, "\n");
    
    /* Секция данных */
    fprintf(output, ".data\n");
    fprintf(output, "    .align 3\n");
    fprintf(output, "stack_bottom:\n");
    fprintf(output, "    .space 4096\n");
    fprintf(output, "stack_top:\n");
    
    /* TODO: Добавить строковые литералы */
    
    /* Освобождение памяти */
    Symbol* sym = ctx->head;
    while (sym) {
        Symbol* next = sym->next;
        free(sym->name);
        free(sym->type);
        free(sym);
        sym = next;
    }
    free(ctx);
}