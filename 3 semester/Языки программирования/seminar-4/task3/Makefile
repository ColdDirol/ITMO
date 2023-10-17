all: program

program: main.o assembler.o
	gcc main.o assembler.o -o program

main.o: main.c
	gcc -c main.c -o main.o

assembler.o: assembler.asm
	nasm -f elf64 assembler.asm -o assembler.o

clean:
	rm -f *.o program
