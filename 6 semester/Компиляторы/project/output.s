.text
.globl _start
_start:
    # Инициализация стека
    la sp, stack_top
    mv fp, sp
    addi sp, sp, -256  # Резервируем место для локальных переменных

    li a0, 10
    sd a0, -8(fp)
    li a0, 20
    sd a0, -16(fp)
    ld a0, -8(fp)
    sd a0, 0(sp)
    addi sp, sp, -8
    ld a0, -16(fp)
    addi sp, sp, 8
    ld t0, 0(sp)
    add a0, t0, a0
    sd a0, -24(fp)
    ld a0, -24(fp)
    # Печать значения в a0
    li a7, 1    # Системный вызов print_int
    ecall
    li a0, 10   # Символ новой строки
    li a7, 11   # Системный вызов print_char
    ecall
    li a0, 15
    sd a0, -32(fp)
    ld a0, -32(fp)
    sd a0, 0(sp)
    addi sp, sp, -8
    li a0, 10
    addi sp, sp, 8
    ld t0, 0(sp)
    slt a0, a0, t0
    beqz a0, .L0
    li a0, 1
    # Печать значения в a0
    li a7, 1    # Системный вызов print_int
    ecall
    li a0, 10   # Символ новой строки
    li a7, 11   # Системный вызов print_char
    ecall
    j .L1
.L0:
    li a0, 0
    # Печать значения в a0
    li a7, 1    # Системный вызов print_int
    ecall
    li a0, 10   # Символ новой строки
    li a7, 11   # Системный вызов print_char
    ecall
.L1:
    li a0, 85
    sd a0, -40(fp)
    ld a0, -40(fp)
    sd a0, 0(sp)
    addi sp, sp, -8
    li a0, 90
    addi sp, sp, 8
    ld t0, 0(sp)
    slt a0, t0, a0
    xori a0, a0, 1
    beqz a0, .L2
    li a0, 5
    # Печать значения в a0
    li a7, 1    # Системный вызов print_int
    ecall
    li a0, 10   # Символ новой строки
    li a7, 11   # Системный вызов print_char
    ecall
    j .L3
.L2:
    ld a0, -40(fp)
    sd a0, 0(sp)
    addi sp, sp, -8
    li a0, 80
    addi sp, sp, 8
    ld t0, 0(sp)
    slt a0, t0, a0
    xori a0, a0, 1
    beqz a0, .L4
    li a0, 4
    # Печать значения в a0
    li a7, 1    # Системный вызов print_int
    ecall
    li a0, 10   # Символ новой строки
    li a7, 11   # Системный вызов print_char
    ecall
    j .L5
.L4:
    ld a0, -40(fp)
    sd a0, 0(sp)
    addi sp, sp, -8
    li a0, 70
    addi sp, sp, 8
    ld t0, 0(sp)
    slt a0, t0, a0
    xori a0, a0, 1
    beqz a0, .L6
    li a0, 3
    # Печать значения в a0
    li a7, 1    # Системный вызов print_int
    ecall
    li a0, 10   # Символ новой строки
    li a7, 11   # Системный вызов print_char
    ecall
    j .L7
.L6:
    li a0, 2
    # Печать значения в a0
    li a7, 1    # Системный вызов print_int
    ecall
    li a0, 10   # Символ новой строки
    li a7, 11   # Системный вызов print_char
    ecall
.L7:
.L5:
.L3:
    li a0, 1
    sd a0, -48(fp)
.L8:
    ld a0, -48(fp)
    sd a0, 0(sp)
    addi sp, sp, -8
    li a0, 5
    addi sp, sp, 8
    ld t0, 0(sp)
    slt a0, a0, t0
    xori a0, a0, 1
    beqz a0, .L9
    ld a0, -48(fp)
    # Печать значения в a0
    li a7, 1    # Системный вызов print_int
    ecall
    li a0, 10   # Символ новой строки
    li a7, 11   # Системный вызов print_char
    ecall
    ld a0, -48(fp)
    sd a0, 0(sp)
    addi sp, sp, -8
    li a0, 1
    addi sp, sp, 8
    ld t0, 0(sp)
    add a0, t0, a0
    sd a0, -48(fp)
    j .L8
.L9:
    li a0, 5
    sd a0, -56(fp)
    li a0, 1
    sd a0, -64(fp)
    li a0, 1
    sd a0, -72(fp)
.L10:
    ld a0, -72(fp)
    sd a0, 0(sp)
    addi sp, sp, -8
    ld a0, -56(fp)
    addi sp, sp, 8
    ld t0, 0(sp)
    slt a0, a0, t0
    xori a0, a0, 1
    beqz a0, .L11
    ld a0, -64(fp)
    sd a0, 0(sp)
    addi sp, sp, -8
    ld a0, -72(fp)
    addi sp, sp, 8
    ld t0, 0(sp)
    mul a0, t0, a0
    sd a0, -64(fp)
    ld a0, -72(fp)
    sd a0, 0(sp)
    addi sp, sp, -8
    li a0, 1
    addi sp, sp, 8
    ld t0, 0(sp)
    add a0, t0, a0
    sd a0, -72(fp)
    j .L10
.L11:
    ld a0, -64(fp)
    # Печать значения в a0
    li a7, 1    # Системный вызов print_int
    ecall
    li a0, 10   # Символ новой строки
    li a7, 11   # Системный вызов print_char
    ecall
    li a0, 1
    sd a0, -80(fp)
    li a0, 0
    sd a0, -88(fp)
    ld a0, -80(fp)
    beqz a0, .and_false14
    ld a0, -88(fp)
    seqz a0, a0
    j .and_end14
.and_false14:
    li a0, 0
.and_end14:
    beqz a0, .L12
    li a0, 1
    # Печать значения в a0
    li a7, 1    # Системный вызов print_int
    ecall
    li a0, 10   # Символ новой строки
    li a7, 11   # Системный вызов print_char
    ecall
    j .L13
.L12:
.L13:
    ld a0, -80(fp)
    bnez a0, .or_true17
    ld a0, -88(fp)
    j .or_end17
.or_true17:
    li a0, 1
.or_end17:
    beqz a0, .L15
    li a0, 2
    # Печать значения в a0
    li a7, 1    # Системный вызов print_int
    ecall
    li a0, 10   # Символ новой строки
    li a7, 11   # Системный вызов print_char
    ecall
    j .L16
.L15:
.L16:
    li a0, 10
    sd a0, -96(fp)
    li a0, 20
    sd a0, -104(fp)
    ld a0, -96(fp)
    sd a0, 0(sp)
    addi sp, sp, -8
    ld a0, -104(fp)
    addi sp, sp, 8
    ld t0, 0(sp)
    slt a0, t0, a0
    beqz a0, .L18
    ld a0, -96(fp)
    # Печать значения в a0
    li a7, 1    # Системный вызов print_int
    ecall
    li a0, 10   # Символ новой строки
    li a7, 11   # Системный вызов print_char
    ecall
    j .L19
.L18:
    ld a0, -104(fp)
    # Печать значения в a0
    li a7, 1    # Системный вызов print_int
    ecall
    li a0, 10   # Символ новой строки
    li a7, 11   # Системный вызов print_char
    ecall
.L19:
    li a0, 10
    sd a0, -112(fp)
    li a0, 0
    sd a0, -120(fp)
    li a0, 1
    sd a0, -128(fp)
.L20:
    ld a0, -128(fp)
    sd a0, 0(sp)
    addi sp, sp, -8
    ld a0, -112(fp)
    addi sp, sp, 8
    ld t0, 0(sp)
    slt a0, a0, t0
    xori a0, a0, 1
    beqz a0, .L21
    ld a0, -120(fp)
    sd a0, 0(sp)
    addi sp, sp, -8
    ld a0, -128(fp)
    addi sp, sp, 8
    ld t0, 0(sp)
    add a0, t0, a0
    sd a0, -120(fp)
    ld a0, -128(fp)
    sd a0, 0(sp)
    addi sp, sp, -8
    li a0, 1
    addi sp, sp, 8
    ld t0, 0(sp)
    add a0, t0, a0
    sd a0, -128(fp)
    j .L20
.L21:
    ld a0, -120(fp)
    # Печать значения в a0
    li a7, 1    # Системный вызов print_int
    ecall
    li a0, 10   # Символ новой строки
    li a7, 11   # Системный вызов print_char
    ecall
    li a0, 42
    sd a0, -136(fp)
    ld a0, -136(fp)
    sd a0, 0(sp)
    addi sp, sp, -8
    ld a0, -136(fp)
    sd a0, 0(sp)
    addi sp, sp, -8
    li a0, 2
    addi sp, sp, 8
    ld t0, 0(sp)
    div a0, t0, a0
    sd a0, 0(sp)
    addi sp, sp, -8
    li a0, 2
    addi sp, sp, 8
    ld t0, 0(sp)
    mul a0, t0, a0
    addi sp, sp, 8
    ld t0, 0(sp)
    sub a0, t0, a0
    sd a0, -144(fp)
    ld a0, -144(fp)
    sd a0, 0(sp)
    addi sp, sp, -8
    li a0, 0
    addi sp, sp, 8
    ld t0, 0(sp)
    sub a0, t0, a0
    seqz a0, a0
    beqz a0, .L22
    li a0, 1
    # Печать значения в a0
    li a7, 1    # Системный вызов print_int
    ecall
    li a0, 10   # Символ новой строки
    li a7, 11   # Системный вызов print_char
    ecall
    j .L23
.L22:
    li a0, 0
    # Печать значения в a0
    li a7, 1    # Системный вызов print_int
    ecall
    li a0, 10   # Символ новой строки
    li a7, 11   # Системный вызов print_char
    ecall
.L23:
    li a0, 15
    sd a0, -152(fp)
    li a0, 25
    sd a0, -160(fp)
    li a0, 20
    sd a0, -168(fp)
    ld a0, -152(fp)
    sd a0, -176(fp)
    ld a0, -160(fp)
    sd a0, 0(sp)
    addi sp, sp, -8
    ld a0, -176(fp)
    addi sp, sp, 8
    ld t0, 0(sp)
    slt a0, a0, t0
    beqz a0, .L24
    ld a0, -160(fp)
    sd a0, -176(fp)
    j .L25
.L24:
.L25:
    ld a0, -168(fp)
    sd a0, 0(sp)
    addi sp, sp, -8
    ld a0, -176(fp)
    addi sp, sp, 8
    ld t0, 0(sp)
    slt a0, a0, t0
    beqz a0, .L26
    ld a0, -168(fp)
    sd a0, -176(fp)
    j .L27
.L26:
.L27:
    ld a0, -176(fp)
    # Печать значения в a0
    li a7, 1    # Системный вызов print_int
    ecall
    li a0, 10   # Символ новой строки
    li a7, 11   # Системный вызов print_char
    ecall

    # Завершение программы
    li a0, 0
    li a7, 93   # Системный вызов exit
    ecall

.data
    .align 3
stack_bottom:
    .space 4096
stack_top:
