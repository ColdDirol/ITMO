#ifndef CODEGEN_H
#define CODEGEN_H

#include <stdio.h>
#include "ast.h"

/* Генерация кода RISC-V для AST */
void generate_code(ASTNode* root, FILE* output);

#endif /* CODEGEN_H */