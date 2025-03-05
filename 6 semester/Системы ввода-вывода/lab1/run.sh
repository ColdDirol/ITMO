#!/bin/bash
set -xue

QEMU=qemu-system-riscv32

# Путь к cland и его флагам
CC=clang
CFLAGS="-std=c11 -o2 -g3 -Wall -Wextra --target=riscv32 -ffreestanding -nostdlib"

# Сборка ядра
$CC $CFLAGS -Wl,-Tkernel.ld -Wl,-Map=kernel.map -o kernel.elf kernel.c

$QEMU -machine virt -bios default -nographic -serial mon:stdio --no-reboot -kernel kernel.elf
