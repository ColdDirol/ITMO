#ifndef VTFS_HTTP_H
#define VTFS_HTTP_H

#include <linux/inet.h>

int64_t vtfs_http_call(const char *token, const char *method,
                            char *response_buffer, size_t buffer_size,
                            size_t arg_size, ...);

void encode(const char *, char *);

int fill_request(struct kvec *vec, const char *token, const char *method,
                 size_t arg_size, va_list args);

int receive_all(struct socket *sock, char *buffer, size_t buffer_size);

int64_t parse_http_response(char *raw_response, size_t raw_response_size,
                            char *response, size_t response_size);


#endif // VTFS_HTTP_H
