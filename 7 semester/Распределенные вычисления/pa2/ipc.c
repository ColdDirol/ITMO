#define _XOPEN_SOURCE 500
#include "ipc.h"
#include <unistd.h>
#include <fcntl.h>
#include <errno.h>
#include <string.h>

typedef struct {
    local_id id;
    int num_processes;
    int (*pipes)[2];
} IOLocal;

static int get_pipe_index(int from, int to, int num_processes) {
    return from * num_processes + to;
}

int send(void *self, local_id dst, const Message *msg) {
    IOLocal *io = (IOLocal *)self;
    if (dst == io->id) return 0;
    
    int pipe_idx = get_pipe_index(io->id, dst, io->num_processes);
    int write_fd = io->pipes[pipe_idx][1];
    
    if (write_fd <= 0) return -1;
    
    size_t total_size = sizeof(MessageHeader) + msg->s_header.s_payload_len;
    const char *buffer = (const char*)msg;
    size_t written = 0;
    
    while (written < total_size) {
        ssize_t result = write(write_fd, buffer + written, total_size - written);
        if (result <= 0) {
            if (errno == EAGAIN || errno == EWOULDBLOCK) {
                continue;
            }
            return -1;
        }
        written += result;
    }
    return 0;
}

int receive(void *self, local_id from, Message *msg) {
    IOLocal *io = (IOLocal *)self;
    if (from == io->id) return -1;
    
    int pipe_idx = get_pipe_index(from, io->id, io->num_processes);
    int read_fd = io->pipes[pipe_idx][0];
    
    if (read_fd <= 0) return -1;
    
    ssize_t result = read(read_fd, msg, sizeof(MessageHeader));
    if (result <= 0) {
        if (result == 0) {
            return -1;
        }
        if (errno != EAGAIN && errno != EWOULDBLOCK) {
            return -1;
        }
        return -1;
    }
    
    if (result < (ssize_t)sizeof(MessageHeader)) {
        size_t header_read = result;
        while (header_read < sizeof(MessageHeader)) {
            result = read(read_fd, ((char*)msg) + header_read, 
                         sizeof(MessageHeader) - header_read);
            if (result <= 0) {
                if (result == 0) {
                    return -1;
                }
                if (errno != EAGAIN && errno != EWOULDBLOCK) {
                    return -1;
                }
                usleep(1000);
                continue;
            }
            header_read += result;
        }
    }
    
    if (msg->s_header.s_magic != MESSAGE_MAGIC) {
        return -1;
    }
    
    if (msg->s_header.s_payload_len > 0) {
        if (msg->s_header.s_payload_len > MAX_PAYLOAD_LEN) {
            return -1;
        }
        size_t payload_read = 0;
        while (payload_read < msg->s_header.s_payload_len) {
            ssize_t result = read(read_fd, msg->s_payload + payload_read, 
                                 msg->s_header.s_payload_len - payload_read);
            if (result <= 0) {
                if (result == 0) {
                    return -1;
                }
                if (errno != EAGAIN && errno != EWOULDBLOCK) {
                    return -1;
                }
                usleep(1000);
                continue;
            }
            payload_read += result;
        }
    }
    
    return 0;
}

int send_multicast(void *self, const Message *msg) {
    IOLocal *io = (IOLocal *)self;
    for (local_id i = 0; i < io->num_processes; i++) {
        if (i != io->id) {
            if (send(self, i, msg) != 0) {
                return -1;
            }
        }
    }
    return 0;
}

int receive_any(void *self, Message *msg) {
    IOLocal *io = (IOLocal *)self;
    int attempts = 0;
    const int max_attempts = 5000;
    
    while (attempts < max_attempts) {
        for (local_id i = 0; i < io->num_processes; i++) {
            if (i != io->id) {
                int result = receive(self, i, msg);
                if (result == 0) {
                    return 0;
                }
            }
        }
        usleep(10000);
        attempts++;
    }
    return -1;
}
