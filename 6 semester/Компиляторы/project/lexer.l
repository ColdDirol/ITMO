%{
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "parser.tab.h"

int line_number = 1;
%}

DIGIT       [0-9]
LETTER      [a-zA-Z]
ID          {LETTER}({LETTER}|{DIGIT}|_)*
NUMBER      {DIGIT}+
STRING      \"([^\"\n]|\\.)*\"
WHITESPACE  [ \t]+
NEWLINE     \n

%x COMMENT
%x MULTILINE_COMMENT

%%

int             { return INT; }
string          { return STRING_TYPE; }
if              { return IF; }
else            { return ELSE; }
while           { return WHILE; }
print           { return PRINT; }
and             { return AND; }
or              { return OR; }
not             { return NOT; }

\+              { return PLUS; }
\-              { return MINUS; }
\*              { return MULTIPLY; }
\/              { return DIVIDE; }
=               { return ASSIGN; }
==              { return EQ; }
!=              { return NE; }
\<              { return LT; }
\>              { return GT; }
\<=             { return LE; }
\>=             { return GE; }

\(              { return LPAREN; }
\)              { return RPAREN; }
\{              { return LBRACE; }
\}              { return RBRACE; }
;               { return SEMICOLON; }

{ID}            { 
                  yylval.string = strdup(yytext);
                  return IDENTIFIER; 
                }

{NUMBER}        { 
                  yylval.integer = atoi(yytext);
                  return NUMBER; 
                }

{STRING}        { 
                  yylval.string = strdup(yytext + 1);
                  yylval.string[strlen(yylval.string) - 1] = '\0';
                  return STRING_LITERAL; 
                }

\/\/            { BEGIN(COMMENT); }
<COMMENT>\n     { line_number++; BEGIN(INITIAL); }
<COMMENT>.      { }

\/\*            { BEGIN(MULTILINE_COMMENT); }
<MULTILINE_COMMENT>\*\/ { BEGIN(INITIAL); }
<MULTILINE_COMMENT>\n   { line_number++; }
<MULTILINE_COMMENT>.    { }

{WHITESPACE}    { }
{NEWLINE}       { line_number++; }

.               { 
                  fprintf(stderr, "Неизвестный символ: %s в строке %d\n", 
                          yytext, line_number);
                }

%%

int yywrap() {
    return 1;
}