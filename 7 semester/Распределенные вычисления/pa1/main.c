#define _POSIX_C_SOURCE 200809L

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <string.h>
#include <fcntl.h>
#include <stdarg.h>

#include "ipc.h"
#include "pa1.h"
#include "common.h"

typedef struct {
    local_id local_id;
    int process_count;
    int pipes[MAX_PROCESS_ID + 1][MAX_PROCESS_ID + 1][2];
} ProcessInfo;

static ProcessInfo proc_info;
static FILE *events_log_file = NULL;
static FILE *pipes_log_file = NULL;

void log_event(const char *format, ...) {
    va_list args;
    
    va_start(args, format);
    vprintf(format, args);
    va_end(args);
    
    if (events_log_file) {
        va_start(args, format);
        vfprintf(events_log_file, format, args);
        va_end(args);
        fflush(events_log_file);
    }
}

void create_pipes(void) {
    pipes_log_file = fopen(pipes_log, "w");
    
    for (int i = 0; i < proc_info.process_count; i++) {
        for (int j = 0; j < proc_info.process_count; j++) {
            if (i != j) {
                if (pipe(proc_info.pipes[i][j]) == -1) {
                    perror("pipe");
                    exit(1);
                }
                
                if (pipes_log_file) {
                    fprintf(pipes_log_file, "Pipe from %d to %d: read=%d, write=%d\n",
                            i, j, proc_info.pipes[i][j][0], proc_info.pipes[i][j][1]);
                }
            }
        }
    }
    
    if (pipes_log_file) {
        fclose(pipes_log_file);
    }
}

void close_unused_pipes(void) {
    for (int i = 0; i < proc_info.process_count; i++) {
        for (int j = 0; j < proc_info.process_count; j++) {
            if (i != j) {
                if (i != proc_info.local_id) {
                    close(proc_info.pipes[i][j][0]);
                }
                if (j != proc_info.local_id) {
                    close(proc_info.pipes[i][j][1]);
                }
            }
        }
    }
}

int send(void *self, local_id dst, const Message *msg) {
    ProcessInfo *info = (ProcessInfo *)self;
    int fd = info->pipes[info->local_id][dst][1];
    
    size_t total_size = sizeof(MessageHeader) + msg->s_header.s_payload_len;
    ssize_t written = write(fd, msg, total_size);
    
    if (written != (ssize_t)total_size) {
        return -1;
    }
    
    return 0;
}

int send_multicast(void *self, const Message *msg) {
    ProcessInfo *info = (ProcessInfo *)self;
    
    for (int i = 0; i < info->process_count; i++) {
        if (i != info->local_id) {
            if (send(self, i, msg) != 0) {
                return -1;
            }
        }
    }
    
    return 0;
}

int receive(void *self, local_id from, Message *msg) {
    ProcessInfo *info = (ProcessInfo *)self;
    int fd = info->pipes[from][info->local_id][0];
    
    ssize_t bytes_read = read(fd, &msg->s_header, sizeof(MessageHeader));
    
    if (bytes_read == sizeof(MessageHeader)) {
        if (msg->s_header.s_payload_len > 0) {
            read(fd, msg->s_payload, msg->s_header.s_payload_len);
        }
        return 0;
    }
    
    return -1;
}

int receive_any(void *self, Message *msg) {
    ProcessInfo *info = (ProcessInfo *)self;
    
    for (int i = 0; i < info->process_count; i++) {
        if (i != info->local_id) {
            int fd = info->pipes[i][info->local_id][0];
            ssize_t bytes_read = read(fd, &msg->s_header, sizeof(MessageHeader));
            
            if (bytes_read == sizeof(MessageHeader)) {
                if (msg->s_header.s_payload_len > 0) {
                    read(fd, msg->s_payload, msg->s_header.s_payload_len);
                }
                return 0;
            }
        }
    }
    
    return -1;
}

void child_work(void) {
    Message msg;
    
    sprintf(msg.s_payload, log_started_fmt, 
            proc_info.local_id, getpid(), getppid());
    
    msg.s_header.s_magic = MESSAGE_MAGIC;
    msg.s_header.s_payload_len = strlen(msg.s_payload);
    msg.s_header.s_type = STARTED;
    msg.s_header.s_local_time = 0;
    
    log_event(msg.s_payload);
    send_multicast(&proc_info, &msg);
    
    for (int i = 1; i < proc_info.process_count; i++) {
        if (i != proc_info.local_id) {
            receive(&proc_info, i, &msg);
        }
    }
    
    log_event(log_received_all_started_fmt, proc_info.local_id);
    
    sprintf(msg.s_payload, log_done_fmt, proc_info.local_id);
    
    msg.s_header.s_magic = MESSAGE_MAGIC;
    msg.s_header.s_payload_len = strlen(msg.s_payload);
    msg.s_header.s_type = DONE;
    msg.s_header.s_local_time = 0;
    
    log_event(msg.s_payload);
    send_multicast(&proc_info, &msg);
    
    for (int i = 1; i < proc_info.process_count; i++) {
        if (i != proc_info.local_id) {
            receive(&proc_info, i, &msg);
        }
    }
    
    log_event(log_received_all_done_fmt, proc_info.local_id);
}

void parent_work(void) {
    Message msg;
    
    for (int i = 1; i < proc_info.process_count; i++) {
        receive(&proc_info, i, &msg);
    }
    
    log_event(log_received_all_started_fmt, PARENT_ID);
    
    for (int i = 1; i < proc_info.process_count; i++) {
        receive(&proc_info, i, &msg);
    }
    
    log_event(log_received_all_done_fmt, PARENT_ID);
    
    for (int i = 1; i < proc_info.process_count; i++) {
        wait(NULL);
    }
}

int main(int argc, char *argv[]) {
    int child_count = 0;
    
    if (argc != 3 || strcmp(argv[1], "-p") != 0) {
        fprintf(stderr, "Usage: %s -p X\n", argv[0]);
        return 1;
    }
    
    child_count = atoi(argv[2]);
    
    if (child_count < 1 || child_count > MAX_PROCESS_ID - 1) {
        fprintf(stderr, "Invalid process count: %d\n", child_count);
        return 1;
    }
    
    proc_info.process_count = child_count + 1;
    events_log_file = fopen(events_log, "w");
    
    create_pipes();
    
    for (int i = 1; i <= child_count; i++) {
        pid_t pid = fork();
        
        if (pid == -1) {
            perror("fork");
            return 1;
        }
        
        if (pid == 0) {
            proc_info.local_id = i;
            close_unused_pipes();
            child_work();
            
            if (events_log_file) {
                fclose(events_log_file);
            }
            
            return 0;
        }
    }
    
    proc_info.local_id = PARENT_ID;
    close_unused_pipes();
    parent_work();
    
    if (events_log_file) {
        fclose(events_log_file);
    }
    
    return 0;
}
