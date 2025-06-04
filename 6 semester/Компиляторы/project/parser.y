%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "ast.h"

extern int yylex();
extern int yyparse();
extern FILE* yyin;
extern int line_number;

void yyerror(const char* s);

ASTNode* root = NULL;
%}

/* Объединение для хранения различных типов значений */
%union {
    int integer;
    char* string;
    struct ASTNode* node;
}

/* Токены */
%token <integer> NUMBER
%token <string> IDENTIFIER STRING_LITERAL
%token INT STRING_TYPE
%token IF ELSE WHILE PRINT
%token PLUS MINUS MULTIPLY DIVIDE
%token ASSIGN EQ NE LT GT LE GE
%token AND OR NOT
%token LPAREN RPAREN LBRACE RBRACE SEMICOLON

/* Типы нетерминалов */
%type <node> program statement_list statement
%type <node> declaration assignment if_statement while_statement
%type <node> print_statement block expression
%type <node> logical_or logical_and equality relational
%type <node> additive multiplicative unary primary
%type <string> type

/* Приоритеты операторов */
%left OR
%left AND
%left EQ NE
%left LT GT LE GE
%left PLUS MINUS
%left MULTIPLY DIVIDE
%right NOT

/* Начальный символ грамматики */
%start program

%%

program:
    statement_list { 
        root = $1; 
        $$ = $1;
    }
    ;

statement_list:
    statement_list statement {
        $$ = create_node(NODE_STATEMENT_LIST, NULL, 0, $1, $2);
    }
    | statement {
        $$ = $1;
    }
    ;

statement:
    declaration SEMICOLON { $$ = $1; }
    | assignment SEMICOLON { $$ = $1; }
    | if_statement { $$ = $1; }
    | while_statement { $$ = $1; }
    | print_statement SEMICOLON { $$ = $1; }
    | block { $$ = $1; }
    ;

declaration:
    type IDENTIFIER {
        $$ = create_node(NODE_DECLARATION, $2, 0, 
                        create_node(NODE_TYPE, $1, 0, NULL, NULL), NULL);
    }
    | type IDENTIFIER ASSIGN expression {
        $$ = create_node(NODE_DECLARATION, $2, 0,
                        create_node(NODE_TYPE, $1, 0, NULL, NULL), $4);
    }
    ;

type:
    INT { $$ = strdup("int"); }
    | STRING_TYPE { $$ = strdup("string"); }
    ;

assignment:
    IDENTIFIER ASSIGN expression {
        $$ = create_node(NODE_ASSIGNMENT, $1, 0, $3, NULL);
    }
    ;

if_statement:
    IF LPAREN expression RPAREN statement {
        $$ = create_node(NODE_IF, NULL, 0, $3, $5);
    }
    | IF LPAREN expression RPAREN statement ELSE statement {
        $$ = create_node(NODE_IF_ELSE, NULL, 0, $3, 
                        create_node(NODE_ELSE_PART, NULL, 0, $5, $7));
    }
    ;

while_statement:
    WHILE LPAREN expression RPAREN statement {
        $$ = create_node(NODE_WHILE, NULL, 0, $3, $5);
    }
    ;

print_statement:
    PRINT LPAREN expression RPAREN {
        $$ = create_node(NODE_PRINT, NULL, 0, $3, NULL);
    }
    ;

block:
    LBRACE statement_list RBRACE {
        $$ = create_node(NODE_BLOCK, NULL, 0, $2, NULL);
    }
    | LBRACE RBRACE {
        $$ = create_node(NODE_BLOCK, NULL, 0, NULL, NULL);
    }
    ;

expression:
    logical_or { $$ = $1; }
    ;

logical_or:
    logical_or OR logical_and {
        $$ = create_node(NODE_OR, NULL, 0, $1, $3);
    }
    | logical_and { $$ = $1; }
    ;

logical_and:
    logical_and AND equality {
        $$ = create_node(NODE_AND, NULL, 0, $1, $3);
    }
    | equality { $$ = $1; }
    ;

equality:
    equality EQ relational {
        $$ = create_node(NODE_EQ, NULL, 0, $1, $3);
    }
    | equality NE relational {
        $$ = create_node(NODE_NE, NULL, 0, $1, $3);
    }
    | relational { $$ = $1; }
    ;

relational:
    relational LT additive {
        $$ = create_node(NODE_LT, NULL, 0, $1, $3);
    }
    | relational GT additive {
        $$ = create_node(NODE_GT, NULL, 0, $1, $3);
    }
    | relational LE additive {
        $$ = create_node(NODE_LE, NULL, 0, $1, $3);
    }
    | relational GE additive {
        $$ = create_node(NODE_GE, NULL, 0, $1, $3);
    }
    | additive { $$ = $1; }
    ;

additive:
    additive PLUS multiplicative {
        $$ = create_node(NODE_ADD, NULL, 0, $1, $3);
    }
    | additive MINUS multiplicative {
        $$ = create_node(NODE_SUB, NULL, 0, $1, $3);
    }
    | multiplicative { $$ = $1; }
    ;

multiplicative:
    multiplicative MULTIPLY unary {
        $$ = create_node(NODE_MUL, NULL, 0, $1, $3);
    }
    | multiplicative DIVIDE unary {
        $$ = create_node(NODE_DIV, NULL, 0, $1, $3);
    }
    | unary { $$ = $1; }
    ;

unary:
    NOT unary {
        $$ = create_node(NODE_NOT, NULL, 0, $2, NULL);
    }
    | MINUS unary {
        $$ = create_node(NODE_NEGATE, NULL, 0, $2, NULL);
    }
    | primary { $$ = $1; }
    ;

primary:
    NUMBER {
        $$ = create_node(NODE_NUMBER, NULL, $1, NULL, NULL);
    }
    | STRING_LITERAL {
        $$ = create_node(NODE_STRING, $1, 0, NULL, NULL);
    }
    | IDENTIFIER {
        $$ = create_node(NODE_IDENTIFIER, $1, 0, NULL, NULL);
    }
    | LPAREN expression RPAREN {
        $$ = $2;
    }
    ;

%%

void yyerror(const char* s) {
    fprintf(stderr, "Ошибка: %s в строке %d\n", s, line_number);
}