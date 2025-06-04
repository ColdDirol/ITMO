# Makefile для компилятора MiniLang

CC = gcc
CFLAGS = -Wall -g -O2
LEX = flex
YACC = bison

# Исполняемый файл
TARGET = minilang

# Исходные файлы
SRCS = main.c ast.c codegen.c lex.yy.c parser.tab.c
OBJS = $(SRCS:.c=.o)

# Правила сборки
all: $(TARGET)

$(TARGET): $(OBJS)
	$(CC) $(CFLAGS) -o $@ $^

# Генерация файлов лексического анализатора
lex.yy.c: lexer.l parser.tab.h
	$(LEX) lexer.l

# Генерация файлов синтаксического анализатора
parser.tab.c parser.tab.h: parser.y
	$(YACC) -d parser.y

# Компиляция C файлов
%.o: %.c
	$(CC) $(CFLAGS) -c $< -o $@

# Зависимости
main.o: main.c ast.h codegen.h
ast.o: ast.c ast.h
codegen.o: codegen.c codegen.h ast.h
lex.yy.o: lex.yy.c parser.tab.h
parser.tab.o: parser.tab.c ast.h

# Тестирование
test: $(TARGET)
	@echo "=== Тест 1: Простая программа ==="
	./$(TARGET) -ast -dot ast.dot tests/test1.ml
	@echo ""
	@echo "=== Тест 2: Условный оператор ==="
	./$(TARGET) -ast tests/test2.ml
	@echo ""
	@echo "=== Тест 3: Цикл ==="
	./$(TARGET) -ast tests/test3.ml

# Создание тестовых файлов
test-files:
	mkdir -p tests
	@echo "Creating test files..."
	@echo "int x = 10;" > tests/test1.ml
	@echo "int y = 20;" >> tests/test1.ml
	@echo "int z = x + y;" >> tests/test1.ml
	@echo "print(z);" >> tests/test1.ml
	
	@echo "int a = 5;" > tests/test2.ml
	@echo "if (a > 3) {" >> tests/test2.ml
	@echo "    print(1);" >> tests/test2.ml
	@echo "} else {" >> tests/test2.ml
	@echo "    print(0);" >> tests/test2.ml
	@echo "}" >> tests/test2.ml
	
	@echo "int i = 0;" > tests/test3.ml
	@echo "while (i < 5) {" >> tests/test3.ml
	@echo "    print(i);" >> tests/test3.ml
	@echo "    i = i + 1;" >> tests/test3.ml
	@echo "}" >> tests/test3.ml

# Очистка
clean:
	rm -f $(OBJS) $(TARGET) lex.yy.c parser.tab.c parser.tab.h
	rm -f *.dot *.png *.s
	rm -rf tests

# Полная пересборка
rebuild: clean all

.PHONY: all clean test test-files rebuild