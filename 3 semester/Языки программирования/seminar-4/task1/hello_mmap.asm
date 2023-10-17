%define O_RDONLY 0
%define PROT_READ 0x1
%define MAP_PRIVATE 0x2
%define MAP_FAILED -1
%define SYS_WRITE 1
%define SYS_OPEN 2
%define SYS_MMAP 9
%define SYS_MUNMAP 11
%define SYS_CLOSE 3
%define FD_STDOUT 1
%define PAGE_SIZE 4096  ; one page

section .data
    fname: db 'hello.txt', 0

section .text
global _start

exit:
    mov  rax, 60
    xor  rdi, rdi
    syscall

print_string:
    push rdi
    call string_length
    pop  rsi
    mov  rdx, rax
    mov  rax, SYS_WRITE
    mov  rdi, FD_STDOUT
    syscall
    ret

string_length:
    xor  rax, rax
.loop:
    cmp  byte [rdi+rax], 0
    je   .end
    inc  rax
    jmp .loop
.end:
    ret


_start:
    mov  rax, SYS_OPEN
    mov  rdi, fname
    xor  rsi, rsi                 ; Open file read only
    xor  rdx, rdx                 ; We aren't creating a file
    syscall                       ; rax now hold the file descriptor

    mov  rdi, 0                   ; Let the OS choose where to map the file
    mov  rsi, PAGE_SIZE           ; Mapping one page
    mov  rdx, PROT_READ           ; Memory protect read only
    mov  r10, MAP_PRIVATE         ; Private memory mapping
    mov  r8, rax                  ; Here rax had the file descriptor
    xor  r9, r9                   ; Offset to the file
    mov  rax, SYS_MMAP
    syscall

    cmp  rax, MAP_FAILED
    je   exit                     ; Error - exit

    ; Now rax has the pointer to the memory mapped file
    mov  rdi, rax
    call print_string

    ; Unmap the memory
    mov  rdi, rax                 ; Address to unmap
    mov  rsi, PAGE_SIZE           ; Length (one page)
    mov  rax, SYS_MUNMAP
    syscall

    ; Close the file
    mov  rdi, r8                  ; File descriptor
    mov  rax, SYS_CLOSE
    syscall

    jmp  exit

    ; SYS_OPEN - exit, если не удалось
