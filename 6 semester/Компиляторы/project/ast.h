#ifndef AST_H
#define AST_H

/* Типы узлов AST */
typedef enum {
    NODE_PROGRAM,
    NODE_STATEMENT_LIST,
    NODE_DECLARATION,
    NODE_TYPE,
    NODE_ASSIGNMENT,
    NODE_IF,
    NODE_IF_ELSE,
    NODE_ELSE_PART,
    NODE_WHILE,
    NODE_PRINT,
    NODE_BLOCK,
    NODE_ADD,
    NODE_SUB,
    NODE_MUL,
    NODE_DIV,
    NODE_EQ,
    NODE_NE,
    NODE_LT,
    NODE_GT,
    NODE_LE,
    NODE_GE,
    NODE_AND,
    NODE_OR,
    NODE_NOT,
    NODE_NEGATE,
    NODE_NUMBER,
    NODE_STRING,
    NODE_IDENTIFIER
} NodeType;

/* Структура узла AST */
typedef struct ASTNode {
    NodeType type;
    char* string_value;
    int int_value;
    struct ASTNode* left;
    struct ASTNode* right;
} ASTNode;

/* Функции для работы с AST */
ASTNode* create_node(NodeType type, char* string_value, int int_value, 
                     ASTNode* left, ASTNode* right);
void print_ast(ASTNode* node, int indent);
void visualize_ast(ASTNode* node, const char* filename);
void free_ast(ASTNode* node);

#endif /* AST_H */