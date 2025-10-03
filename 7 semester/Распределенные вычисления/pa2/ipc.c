#define _GNU_SOURCE
#include "ipc.h"
#include <unistd.h>
#include <fcntl.h>
#include <errno.h>
#include <string.h>

typedef struct {
    local_id id;
    int num_processes;
    int read_fds[MAX_PROCESS_ID + 1];
    int write_fds[MAX_PROCESS_ID + 1];
} IOData;

int send(void * self, local_id dst, const Message * msg) {
    IOData * data = (IOData*)self;
    if (dst > data->num_processes || dst == data->id) return -1;
    
    int fd = data->write_fds[dst];
    const char * buf = (const char*)msg;
    size_t total_len = sizeof(MessageHeader) + msg->s_header.s_payload_len;
    size_t written = 0;

    while (written < total_len) {
        ssize_t result = write(fd, buf + written, total_len - written);
        if (result > 0) {
            written += result;
        } else if (result == -1) {
            if (errno == EAGAIN || errno == EWOULDBLOCK) {
                usleep(1000);
            } else {
                return -1;
            }
        }
    }
    return 0;
}

int send_multicast(void * self, const Message * msg) {
    IOData * data = (IOData*)self;
    for (local_id i = 0; i <= data->num_processes; i++) {
        if (i != data->id) {
            if (send(self, i, msg) != 0) return -1;
        }
    }
    return 0;
}

int receive(void * self, local_id from, Message * msg) {
    IOData * data = (IOData*)self;
    if (from > data->num_processes || from == data->id) return -1;

    int fd = data->read_fds[from];
    char * buf = (char*)msg;
    size_t total_read = 0;

    while (total_read < sizeof(MessageHeader)) {
        ssize_t result = read(fd, buf + total_read, sizeof(MessageHeader) - total_read);
        if (result > 0) {
            total_read += result;
        } else if (result == 0) {
            return -1;
        } else if (result == -1) {
            if (errno == EAGAIN || errno == EWOULDBLOCK) {
                usleep(1000);
                continue;
            } else {
                return -1;
            }
        }
    }

    if (msg->s_header.s_payload_len > 0) {
        total_read = 0;
        while (total_read < msg->s_header.s_payload_len) {
            ssize_t result = read(fd, msg->s_payload + total_read, 
                                 msg->s_header.s_payload_len - total_read);
            if (result > 0) {
                total_read += result;
            } else if (result == 0) {
                return -1;
            } else if (result == -1) {
                if (errno == EAGAIN || errno == EWOULDBLOCK) {
                    usleep(1000);
                    continue;
                } else {
                    return -1;
                }
            }
        }
    }
    return 0;
}

int receive_any(void * self, Message * msg) {
    IOData * data = (IOData*)self;

    while (1) {
        for (local_id i = 0; i <= data->num_processes; i++) {
            if (i == data->id) continue;
            
            int fd = data->read_fds[i];
            
            // Try to read header
            ssize_t header_result = read(fd, &msg->s_header, sizeof(MessageHeader));
            
            if (header_result == sizeof(MessageHeader)) {
                // Successfully read the header
                if (msg->s_header.s_payload_len > 0) {
                    // Now read the payload
                    size_t total_read = 0;
                    while (total_read < msg->s_header.s_payload_len) {
                        ssize_t result = read(fd, msg->s_payload + total_read,
                                             msg->s_header.s_payload_len - total_read);
                        if (result > 0) {
                            total_read += result;
                        } else if (result == 0) {
                            // EOF before completely reading the payload
                            break;
                        } else if (result == -1) {
                            if (errno == EAGAIN || errno == EWOULDBLOCK) {
                                // Partial read, try again
                                usleep(1000);
                                continue;
                            } else {
                                // Error reading
                                break;
                            }
                        }
                    }
                    
                    if (total_read == msg->s_header.s_payload_len) {
                        return 0; // Success - both header and payload read
                    } else {
                        // Need to try again or handle error - continue to next fd
                        continue;
                    }
                } else {
                    return 0; // Success - header read, no payload needed
                }
            } else if (header_result == 0) {
                // EOF - try next fd
                continue;
            } else if (header_result == -1) {
                if (errno == EAGAIN || errno == EWOULDBLOCK) {
                    // No data available on this fd - continue to next
                    continue;
                } else {
                    // Actual error - return error
                    return -1;
                }
            }
        }
        
        // No messages available from any fd, sleep briefly and try again
        usleep(1000);
    }
}
