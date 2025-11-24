#define _POSIX_C_SOURCE 200809L
#include "ipc.h"
#include "common.h"
#include <time.h>
#include <unistd.h>
#include <fcntl.h>
#include <errno.h>
#include <string.h>
#include <stdio.h>

typedef struct {
    local_id local_id;
    int num_processes;
    int pipes[MAX_PROCESS_ID + 1][MAX_PROCESS_ID + 1][2];
    FILE *events_log;
    FILE *pipes_log;
} IpcData;

static void log_pipe_operation(FILE *pipes_log, const char *operation, local_id from, local_id to) {
    if (pipes_log) {
        fprintf(pipes_log, "%s: %d -> %d\n", operation, from, to);
        fflush(pipes_log);
    }
}

int send(void *self, local_id dst, const Message *msg) {
    if (!self || !msg) {
        return -1;
    }
    
    IpcData *ipc = (IpcData *)self;
    
    if (dst < 0 || dst > MAX_PROCESS_ID || dst == ipc->local_id) {
        return -1;
    }
    
    int write_fd = ipc->pipes[ipc->local_id][dst][1];
    
    if (write_fd < 0) {
        return -1;
    }
    
    log_pipe_operation(ipc->pipes_log, "SEND", ipc->local_id, dst);
    
    size_t total_size = sizeof(MessageHeader) + msg->s_header.s_payload_len;
    ssize_t written = 0;
    int total_attempts = 0;
    const int MAX_ATTEMPTS = 10;
    
    while (written < (ssize_t)total_size) {
        total_attempts++;
        if (total_attempts > MAX_ATTEMPTS) {
            return -1;
        }
        
        ssize_t n = write(write_fd, ((char *)msg) + written, total_size - written);
        if (n < 0) {
            if (errno == EAGAIN || errno == EWOULDBLOCK) {
                struct timespec req = {0, 100000L};
                nanosleep(&req, NULL);
                continue;
            }
            return -1;
        }
        if (n == 0) {
            return -1;
        }
        written += n;
    }
    
    return 0;
}

int send_multicast(void *self, const Message *msg) {
    if (!self || !msg) {
        return -1;
    }
    
    IpcData *ipc = (IpcData *)self;
    int success_count = 0;
    
    for (local_id i = 0; i < ipc->num_processes; i++) {
        if (i != ipc->local_id) {
            if (send(self, i, msg) == 0) {
                success_count++;
            }
        }
    }
    
    return (success_count > 0) ? 0 : -1;
}

int receive(void *self, local_id from, Message *msg) {
    if (!self || !msg) {
        return -1;
    }
    
    IpcData *ipc = (IpcData *)self;
    
    if (from < 0 || from > MAX_PROCESS_ID || from == ipc->local_id) {
        return -1;
    }
    
    int read_fd = ipc->pipes[from][ipc->local_id][0];
    
    if (read_fd < 0) {
        return -1;
    }
    
    log_pipe_operation(ipc->pipes_log, "RECEIVE", from, ipc->local_id);
    
    ssize_t n = 0;
    while (n < (ssize_t)sizeof(MessageHeader)) {
        ssize_t read_bytes = read(read_fd, ((char *)&msg->s_header) + n, sizeof(MessageHeader) - n);
        if (read_bytes < 0) {
            if (errno == EAGAIN || errno == EWOULDBLOCK) {
                if (n == 0) {
                    return -1;
                }
                struct timespec req = {0, 10000L};
                nanosleep(&req, NULL);
                continue;
            }
            return -1;
        }
        if (read_bytes == 0) {
            if (n == 0) {
                return -1;
            }
            return -1;
        }
        n += read_bytes;
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
            ssize_t m = read(read_fd, msg->s_payload + payload_read, 
                           msg->s_header.s_payload_len - payload_read);
            if (m < 0) {
                if (errno == EAGAIN || errno == EWOULDBLOCK) {
                    struct timespec req = {0, 10000L};
                    nanosleep(&req, NULL);
                    continue;
                }
                return -1;
            }
            if (m == 0) {
                return -1;
            }
            payload_read += m;
        }
    }
    
    return 0;
}

int receive_any(void *self, Message *msg) {
    if (!self || !msg) {
        return -1;
    }
    
    IpcData *ipc = (IpcData *)self;
    
    for (local_id i = 0; i < ipc->num_processes; i++) {
        if (i != ipc->local_id) {
            if (receive(self, i, msg) == 0) {
                return 0;
            }
        }
    }
    
    return -1;
}
