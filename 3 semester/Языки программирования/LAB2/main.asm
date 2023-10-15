section .data

%include "lib.inc"
%include "dict.inc"
%include "words.inc"

%define WORD_MAX_LENGTH 256

section .rodata
	word_not_found_error: db 'Word not found', 0
	max_length_error: db 'Word is too long. Max avalliable length 256 chars!', 0

section .bss
	buffer: resb WORD_MAX_LENGTH

section .text
	global _start

_start:
	mov rdi, buffer
	mov rsi, WORD_MAX_LENGTH
	call read_word

	test rax, rax
	je .length_error
	mov rdi, rax
	mov rsi, first_word
	call find_word
	test rax, rax
	jz .not_found

	mov rdi, rax
	push rdi
	call string_length
	pop rdi
	add rdi, rax
	inc rdi
	call print_string
	jmp .exit

.not_found:
	mov rdi, word_not_found_error
    call print_error

.exit:
	call exit

.length_error:
	mov rdi, max_length_error
	call print_error
	jmp .exit
