#include "parse.h"
#include <linux/kernel.h>
#include <linux/string.h>
#include <linux/module.h>


#define MODULE_NAME "parse"

MODULE_LICENSE("GPL");
MODULE_AUTHOR("secs-dev");
MODULE_DESCRIPTION("A simple FS kernel module with RAM storage");

char* skip_whitespace(char* p) {
    while (*p == ' ' || *p == '\n' || *p == '\r' || *p == '\t')
        p++;
    return p;
}

char* find_char(char* p, char c) {
    while (*p && *p != c)
        p++;
    return p;
}

char* parse_string(char* p, char* out, size_t max_len) {
    if (*p != '"')
        return NULL;
    p++;
    
    char* end = find_char(p, '"');
    if (!*end)
        return NULL;
    
    size_t len = end - p;
    if (len >= max_len)
        return NULL;
    
    memcpy(out, p, len);
    out[len] = '\0';
    
    return end + 1;
}

char* parse_number(char* p, unsigned long* num) {
    char* start = p;
    while (*p >= '0' && *p <= '9')
        p++;
    
    char tmp = *p;
    *p = '\0';
    if (kstrtoul(start, 10, num) != 0)
        return NULL;
    *p = tmp;
    
    return p;
}

char* parse_bool(char* p, bool* value) {
    if (strncmp(p, "true", 4) == 0) {
        *value = true;
        return p + 4;
    }
    if (strncmp(p, "false", 5) == 0) {
        *value = false;
        return p + 5;
    }
    return NULL;
}

char* parse_file_entry(char* p, struct file_entry* entry) {
    if (*p != '{')
        return NULL;
    p++;
    
    bool got_name = false;
    bool got_ino = false;
    bool got_is_dir = false;
    
    while (*p) {
        p = skip_whitespace(p);
        
        // Проверяем завершение объекта
        if (*p == '}') {
            if (!got_name || !got_ino || !got_is_dir)
                return NULL;
            return p + 1;
        }
        
        // Ожидаем строку-ключ
        char key[32];
        p = parse_string(p, key, sizeof(key));
        if (!p)
            return NULL;
        
        // Пропускаем двоеточие
        p = skip_whitespace(p);
        if (*p != ':')
            return NULL;
        p++;
        p = skip_whitespace(p);
        
        if (strcmp(key, "name") == 0) {
            p = parse_string(p, entry->name, sizeof(entry->name));
            got_name = true;
        }
        else if (strcmp(key, "id") == 0) {
            p = parse_number(p, &entry->ino);
            got_ino = true;
        }
        else if (strcmp(key, "isDir") == 0) {
            p = parse_bool(p, &entry->is_dir);
            got_is_dir = true;
        }
        else {
            // Пропускаем неизвестные поля
            while (*p && *p != ',' && *p != '}')
                p++;
        }
        
        if (!p)
            return NULL;
        
        p = skip_whitespace(p);
        if (*p == ',')
            p++;
    }
    
    return NULL;
}