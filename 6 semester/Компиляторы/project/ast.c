#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "ast.h"

/* Создание нового узла AST */
ASTNode* create_node(NodeType type, char* string_value, int int_value, 
                     ASTNode* left, ASTNode* right) {
    ASTNode* node = (ASTNode*)malloc(sizeof(ASTNode));
    if (!node) {
        fprintf(stderr, "Ошибка выделения памяти\n");
        exit(1);
    }
    
    node->type = type;
    node->string_value = string_value ? strdup(string_value) : NULL;
    node->int_value = int_value;
    node->left = left;
    node->right = right;
    
    return node;
}

/* Получение имени типа узла */
const char* get_node_type_name(NodeType type) {
    switch (type) {
        case NODE_PROGRAM: return "PROGRAM";
        case NODE_STATEMENT_LIST: return "STATEMENT_LIST";
        case NODE_DECLARATION: return "DECLARATION";
        case NODE_TYPE: return "TYPE";
        case NODE_ASSIGNMENT: return "ASSIGNMENT";
        case NODE_IF: return "IF";
        case NODE_IF_ELSE: return "IF_ELSE";
        case NODE_ELSE_PART: return "ELSE_PART";
        case NODE_WHILE: return "WHILE";
        case NODE_PRINT: return "PRINT";
        case NODE_BLOCK: return "BLOCK";
        case NODE_ADD: return "ADD";
        case NODE_SUB: return "SUB";
        case NODE_MUL: return "MUL";
        case NODE_DIV: return "DIV";
        case NODE_EQ: return "EQ";
        case NODE_NE: return "NE";
        case NODE_LT: return "LT";
        case NODE_GT: return "GT";
        case NODE_LE: return "LE";
        case NODE_GE: return "GE";
        case NODE_AND: return "AND";
        case NODE_OR: return "OR";
        case NODE_NOT: return "NOT";
        case NODE_NEGATE: return "NEGATE";
        case NODE_NUMBER: return "NUMBER";
        case NODE_STRING: return "STRING";
        case NODE_IDENTIFIER: return "IDENTIFIER";
        default: return "UNKNOWN";
    }
}

/* Печать AST в текстовом виде */
void print_ast(ASTNode* node, int indent) {
    if (!node) return;
    
    /* Печать отступа */
    for (int i = 0; i < indent; i++) {
        printf("  ");
    }
    
    /* Печать информации об узле */
    printf("%s", get_node_type_name(node->type));
    
    if (node->string_value) {
        printf(" [%s]", node->string_value);
    }
    if (node->type == NODE_NUMBER) {
        printf(" [%d]", node->int_value);
    }
    
    printf("\n");
    
    /* Рекурсивная печать дочерних узлов */
    if (node->left) {
        print_ast(node->left, indent + 1);
    }
    if (node->right) {
        print_ast(node->right, indent + 1);
    }
}

/* Вспомогательная функция для визуализации AST в формате DOT */
static void write_dot_node(FILE* fp, ASTNode* node, int* node_count) {
    if (!node) return;
    
    int current = (*node_count)++;
    
    /* Создаем узел */
    fprintf(fp, "  node%d [label=\"%s", current, get_node_type_name(node->type));
    
    if (node->string_value) {
        fprintf(fp, "\\n%s", node->string_value);
    }
    if (node->type == NODE_NUMBER) {
        fprintf(fp, "\\n%d", node->int_value);
    }
    
    fprintf(fp, "\"];\n");
    
    /* Создаем связи с дочерними узлами */
    if (node->left) {
        int left_id = *node_count;
        write_dot_node(fp, node->left, node_count);
        fprintf(fp, "  node%d -> node%d;\n", current, left_id);
    }
    
    if (node->right) {
        int right_id = *node_count;
        write_dot_node(fp, node->right, node_count);
        fprintf(fp, "  node%d -> node%d;\n", current, right_id);
    }
}

/* Визуализация AST в формате GraphViz DOT */
void visualize_ast(ASTNode* node, const char* filename) {
    FILE* fp = fopen(filename, "w");
    if (!fp) {
        fprintf(stderr, "Не удалось открыть файл %s для записи\n", filename);
        return;
    }
    
    fprintf(fp, "digraph AST {\n");
    fprintf(fp, "  node [shape=box];\n");
    
    int node_count = 0;
    write_dot_node(fp, node, &node_count);
    
    fprintf(fp, "}\n");
    fclose(fp);
    
    printf("AST сохранен в файл %s\n", filename);
    printf("Для визуализации используйте: dot -Tpng %s -o ast.png\n", filename);
}

/* Освобождение памяти AST */
void free_ast(ASTNode* node) {
    if (!node) return;
    
    free_ast(node->left);
    free_ast(node->right);
    
    if (node->string_value) {
        free(node->string_value);
    }
    
    free(node);
}