NASM=nasm -f elf64 -o
LD=ld -o
PYTHON=python3

ASM_FILES=$(wildcard *.asm)
O_FILES=$(ASM_FILES:%.asm=%.o)

main: $(O_FILES)
	$(LD) $@ $^

%.o: %.asm
	$(NASM) $@ $<

clean:
	rm *.o

test:
	make clean
	make main
	$(PYTHON) test.py
