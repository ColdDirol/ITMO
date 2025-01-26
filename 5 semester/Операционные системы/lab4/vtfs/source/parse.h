// parse.h
#ifndef _VTFS_PARSE_H
#define _VTFS_PARSE_H

#include <linux/types.h>
#include <linux/string.h>

struct file_entry {
    char name[256];
    unsigned long ino;
    bool is_dir;
};

// Функции для парсинга JSON
char* skip_whitespace(char* p);
char* find_char(char* p, char c);
char* parse_string(char* p, char* out, size_t max_len);
char* parse_number(char* p, unsigned long* num);
char* parse_bool(char* p, bool* value);
char* parse_file_entry(char* p, struct file_entry* entry);

#endif /* _VTFS_PARSE_H */