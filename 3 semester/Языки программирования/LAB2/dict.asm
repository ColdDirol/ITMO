%include "lib.inc"

section .text
global find_word

find_word:
	mov r8, rsi
.loop:
	lea rsi, [r8 + 8]
	call string_equals
	test rax, rax
	jnz .found

	mov r8, [r8]
	test r8, r8
	jnz .loop

	xor rax, rax
	jmp .exit

.found:
	mov rax, rsi

.exit:
	ret
