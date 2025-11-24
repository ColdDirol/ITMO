#define _POSIX_C_SOURCE 200809L
#include <time.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <errno.h>
#include <sys/wait.h>
#include <stdint.h>
#include <stdbool.h>
#include "ipc.h"
#include "pa2345.h"
#include "common.h"
#include "banking.h"

static timestamp_t lamport_time = 0;
static bool use_mutex = false;

typedef struct {
    timestamp_t request_time;
    local_id process_id;
} Request;

typedef struct {
    local_id local_id;
    int num_processes;
    int pipes[MAX_PROCESS_ID + 1][MAX_PROCESS_ID + 1][2];
    FILE *events_log;
    FILE *pipes_log;
} IpcData;

static IpcData *global_ipc = NULL;
static Request request_queue[MAX_PROCESS_ID + 1];
static int queue_size = 0;
static int reply_count = 0;
static bool done_received[MAX_PROCESS_ID + 1] = {false};

timestamp_t get_lamport_time(void) {
    return lamport_time;
}

static void increment_lamport_time(void) {
    lamport_time++;
}

static void update_lamport_time(timestamp_t received_time) {
    if (received_time > lamport_time) {
        lamport_time = received_time;
    }
    lamport_time++;
}

static void delay_ms(int ms) {
    struct timespec req = {0, ms * 1000000L};
    nanosleep(&req, NULL);
}

static int set_nonblocking(int fd) {
    int flags = fcntl(fd, F_GETFL, 0);
    if (flags == -1) {
        return -1;
    }
    return fcntl(fd, F_SETFL, flags | O_NONBLOCK);
}

static int compare_requests(const void *a, const void *b) {
    Request *ra = (Request *)a;
    Request *rb = (Request *)b;
    if (ra->request_time != rb->request_time) {
        return ra->request_time - rb->request_time;
    }
    return ra->process_id - rb->process_id;
}

static void add_to_queue(timestamp_t time, local_id id) {
    request_queue[queue_size].request_time = time;
    request_queue[queue_size].process_id = id;
    queue_size++;
    qsort(request_queue, queue_size, sizeof(Request), compare_requests);
}

static void remove_from_queue(local_id id) {
    for (int i = 0; i < queue_size; i++) {
        if (request_queue[i].process_id == id) {
            for (int j = i; j < queue_size - 1; j++) {
                request_queue[j] = request_queue[j + 1];
            }
            queue_size--;
            break;
        }
    }
}

static bool is_first_in_queue(local_id id) {
    return queue_size > 0 && request_queue[0].process_id == id;
}

static void process_cs_message(IpcData *ipc, Message *msg, local_id from) {
    update_lamport_time(msg->s_header.s_local_time);
    
    if (msg->s_header.s_type == CS_REQUEST) {
        add_to_queue(msg->s_header.s_local_time, from);
        
        increment_lamport_time();
        Message reply_msg;
        reply_msg.s_header.s_magic = MESSAGE_MAGIC;
        reply_msg.s_header.s_type = CS_REPLY;
        reply_msg.s_header.s_local_time = get_lamport_time();
        reply_msg.s_header.s_payload_len = 0;
        send(ipc, from, &reply_msg);
        
    } else if (msg->s_header.s_type == CS_REPLY) {
        reply_count++;
        
    } else if (msg->s_header.s_type == CS_RELEASE) {
        remove_from_queue(from);
    } else if (msg->s_header.s_type == DONE) {
        done_received[from] = true;
    }
}

int request_cs(const void *self) {
    if (!use_mutex) {
        return 0;
    }
    
    IpcData *ipc = (IpcData *)self;
    
    increment_lamport_time();
    timestamp_t request_time = get_lamport_time();
    
    add_to_queue(request_time, ipc->local_id);
    reply_count = 0;
    
    Message req_msg;
    req_msg.s_header.s_magic = MESSAGE_MAGIC;
    req_msg.s_header.s_type = CS_REQUEST;
    req_msg.s_header.s_local_time = request_time;
    req_msg.s_header.s_payload_len = 0;
    
    for (local_id i = 1; i < ipc->num_processes; i++) {
        if (i != ipc->local_id) {
            send(ipc, i, &req_msg);
        }
    }
    
    int expected_replies = ipc->num_processes - 2;
    
    while (reply_count < expected_replies || !is_first_in_queue(ipc->local_id)) {
        for (local_id i = 1; i < ipc->num_processes; i++) {
            if (i != ipc->local_id) {
                Message msg;
                if (receive(ipc, i, &msg) == 0) {
                    process_cs_message(ipc, &msg, i);
                }
            }
        }
        delay_ms(1);
    }
    
    return 0;
}

int release_cs(const void *self) {
    if (!use_mutex) {
        return 0;
    }
    
    IpcData *ipc = (IpcData *)self;
    
    remove_from_queue(ipc->local_id);
    
    increment_lamport_time();
    Message release_msg;
    release_msg.s_header.s_magic = MESSAGE_MAGIC;
    release_msg.s_header.s_type = CS_RELEASE;
    release_msg.s_header.s_local_time = get_lamport_time();
    release_msg.s_header.s_payload_len = 0;
    
    for (local_id i = 1; i < ipc->num_processes; i++) {
        if (i != ipc->local_id) {
            send(ipc, i, &release_msg);
        }
    }
    
    return 0;
}

void transfer(void *parent_data, local_id src, local_id dst, balance_t amount) {
    (void)parent_data;
    (void)src;
    (void)dst;
    (void)amount;
}

static int create_pipes(IpcData *ipc) {
    for (int i = 0; i < ipc->num_processes; i++) {
        for (int j = 0; j < ipc->num_processes; j++) {
            if (i != j) {
                if (pipe(ipc->pipes[i][j]) == -1) {
                    return -1;
                }
                set_nonblocking(ipc->pipes[i][j][0]);
                set_nonblocking(ipc->pipes[i][j][1]);
            } else {
                ipc->pipes[i][j][0] = -1;
                ipc->pipes[i][j][1] = -1;
            }
        }
    }
    return 0;
}

static void close_unused_pipes(IpcData *ipc) {
    for (int i = 0; i < ipc->num_processes; i++) {
        for (int j = 0; j < ipc->num_processes; j++) {
            if (i != j) {
                if (i != ipc->local_id && j != ipc->local_id) {
                    close(ipc->pipes[i][j][0]);
                    close(ipc->pipes[i][j][1]);
                } else if (i == ipc->local_id && j != ipc->local_id) {
                    close(ipc->pipes[i][j][0]);
                } else if (i != ipc->local_id && j == ipc->local_id) {
                    close(ipc->pipes[i][j][1]);
                }
            }
        }
    }
}

static void child_process(IpcData *ipc) {
    lamport_time = 0;
    queue_size = 0;
    reply_count = 0;
    global_ipc = ipc;
    for (int i = 0; i <= MAX_PROCESS_ID; i++) {
        done_received[i] = false;
    }
    
    increment_lamport_time();
    timestamp_t time = get_lamport_time();
    
    Message msg;
    msg.s_header.s_magic = MESSAGE_MAGIC;
    msg.s_header.s_type = STARTED;
    msg.s_header.s_local_time = time;
    
    char payload[256];
    snprintf(payload, sizeof(payload), log_started_fmt, time, ipc->local_id, getpid(), getppid(), 0);
    strncpy(msg.s_payload, payload, strlen(payload));
    msg.s_header.s_payload_len = strlen(payload);
    
    send_multicast(ipc, &msg);
    
    fprintf(ipc->events_log, "%s", payload);
    fflush(ipc->events_log);
    printf("%s", payload);
    fflush(stdout);
    
    int num_children = ipc->num_processes - 1;
    int started_count = 0;
    
    while (started_count < num_children - 1) {
        for (local_id i = 1; i < ipc->num_processes; i++) {
            if (i != ipc->local_id) {
                Message recv_msg;
                if (receive(ipc, i, &recv_msg) == 0) {
                    update_lamport_time(recv_msg.s_header.s_local_time);
                    if (recv_msg.s_header.s_type == STARTED) {
                        started_count++;
                    } else {
                        process_cs_message(ipc, &recv_msg, i);
                    }
                }
            }
        }
        delay_ms(1);
    }
    
    increment_lamport_time();
    time = get_lamport_time();
    fprintf(ipc->events_log, log_received_all_started_fmt, time, ipc->local_id);
    fflush(ipc->events_log);
    printf(log_received_all_started_fmt, time, ipc->local_id);
    fflush(stdout);
    
    int iterations = ipc->local_id * 5;
    for (int i = 1; i <= iterations; i++) {
        if (use_mutex) {
            request_cs(ipc);
        }
        
        char loop_msg[256];
        snprintf(loop_msg, sizeof(loop_msg), log_loop_operation_fmt, ipc->local_id, i, iterations);
        print(loop_msg);
        
        if (use_mutex) {
            release_cs(ipc);
        }
    }
    
    if (use_mutex) {
        delay_ms(100);
    }
    
    increment_lamport_time();
    time = get_lamport_time();
    Message done_msg;
    done_msg.s_header.s_magic = MESSAGE_MAGIC;
    done_msg.s_header.s_type = DONE;
    done_msg.s_header.s_local_time = time;
    
    char done_payload[256];
    snprintf(done_payload, sizeof(done_payload), log_done_fmt, time, ipc->local_id, 0);
    strncpy(done_msg.s_payload, done_payload, strlen(done_payload));
    done_msg.s_header.s_payload_len = strlen(done_payload);
    
    send_multicast(ipc, &done_msg);
    
    fprintf(ipc->events_log, "%s", done_payload);
    fflush(ipc->events_log);
    printf("%s", done_payload);
    fflush(stdout);
    
    int done_count = 0;

    for (local_id i = 1; i < ipc->num_processes; i++) {
        if (i != ipc->local_id && done_received[i]) {
            done_count++;
        }
    }

    while (done_count < num_children - 1) {
        for (local_id i = 1; i < ipc->num_processes; i++) {
            if (i != ipc->local_id && !done_received[i]) {
                Message recv_msg;
                if (receive(ipc, i, &recv_msg) == 0) {
                    update_lamport_time(recv_msg.s_header.s_local_time);

                    if (recv_msg.s_header.s_type == DONE) {
                        done_received[i] = true;
                        done_count++;
                    } else if (recv_msg.s_header.s_type == CS_REQUEST) {
                        increment_lamport_time();
                        Message reply_msg;
                        reply_msg.s_header.s_magic = MESSAGE_MAGIC;
                        reply_msg.s_header.s_type = CS_REPLY;
                        reply_msg.s_header.s_local_time = get_lamport_time();
                        reply_msg.s_header.s_payload_len = 0;
                        send(ipc, i, &reply_msg);
                    } else if (recv_msg.s_header.s_type == CS_REPLY) {
                        reply_count++;
                    } else if (recv_msg.s_header.s_type == CS_RELEASE) {
                        remove_from_queue(i);
                    }
                }
            }
        }
        delay_ms(1);
    }
    
    increment_lamport_time();
    time = get_lamport_time();
    
    fprintf(ipc->events_log, log_received_all_done_fmt, time, ipc->local_id);
    fflush(ipc->events_log);
    printf(log_received_all_done_fmt, time, ipc->local_id);
    fflush(stdout);
    
    fclose(ipc->events_log);
    fclose(ipc->pipes_log);
    exit(0);
}

static void parent_process(IpcData *ipc, int num_children) {
    lamport_time = 0;
    
    int started_count = 0;
    while (started_count < num_children) {
        Message msg;
        if (receive_any(ipc, &msg) == 0) {
            update_lamport_time(msg.s_header.s_local_time);
            if (msg.s_header.s_type == STARTED) {
                started_count++;
            }
        }
        delay_ms(1);
    }
    
    increment_lamport_time();
    timestamp_t time = get_lamport_time();
    fprintf(ipc->events_log, log_received_all_started_fmt, time, ipc->local_id);
    fflush(ipc->events_log);
    printf(log_received_all_started_fmt, time, ipc->local_id);
    fflush(stdout);
    
    int done_count = 0;
    while (done_count < num_children) {
        Message msg;
        if (receive_any(ipc, &msg) == 0) {
            update_lamport_time(msg.s_header.s_local_time);
            if (msg.s_header.s_type == DONE) {
                done_count++;
            }
        }
        delay_ms(1);
    }
    
    increment_lamport_time();
    time = get_lamport_time();
    fprintf(ipc->events_log, log_received_all_done_fmt, time, ipc->local_id);
    fflush(ipc->events_log);
    printf(log_received_all_done_fmt, time, ipc->local_id);
    fflush(stdout);
    
    for (int i = 0; i < num_children; i++) {
        wait(NULL);
    }
    
    fclose(ipc->events_log);
    fclose(ipc->pipes_log);
}

int main(int argc, char *argv[]) {
    int num_children = 0;
    int arg_offset = 1;
    
    if (argc > 1 && strcmp(argv[1], "--mutexl") == 0) {
        use_mutex = true;
        arg_offset = 2;
    }
    
    if (argc < arg_offset + 2) {
        return 1;
    }
    
    if (strcmp(argv[arg_offset], "-p") == 0) {
        num_children = atoi(argv[arg_offset + 1]);
        if (num_children < 1 || num_children > MAX_PROCESS_ID) {
            return 1;
        }
    } else {
        return 1;
    }
    
    IpcData ipc;
    ipc.local_id = PARENT_ID;
    ipc.num_processes = num_children + 1;
    
    int events_fd = open(events_log, O_WRONLY | O_CREAT | O_APPEND, 0644);
    if (events_fd < 0) {
        return 1;
    }
    ipc.events_log = fdopen(events_fd, "a");
    ipc.pipes_log = fopen(pipes_log, "w");
    
    if (!ipc.events_log || !ipc.pipes_log) {
        return 1;
    }
    
    if (create_pipes(&ipc) != 0) {
        return 1;
    }
    
    for (local_id i = 1; i <= num_children; i++) {
        pid_t pid = fork();
        if (pid == 0) {
            ipc.local_id = i;
            close_unused_pipes(&ipc);
            child_process(&ipc);
            return 0;
        } else if (pid < 0) {
            return 1;
        }
    }
    
    close_unused_pipes(&ipc);
    parent_process(&ipc, num_children);
    
    return 0;
}
