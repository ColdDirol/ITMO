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
#include "ipc.h"
#include "banking.h"
#include "pa2345.h"
#include "common.h"

static timestamp_t lamport_time = 0;

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

typedef struct {
    local_id local_id;
    int num_processes;
    int pipes[MAX_PROCESS_ID + 1][MAX_PROCESS_ID + 1][2];
    FILE *events_log;
    FILE *pipes_log;
} IpcData;

static int set_nonblocking(int fd) {
    int flags = fcntl(fd, F_GETFL, 0);
    if (flags == -1) {
        return -1;
    }
    return fcntl(fd, F_SETFL, flags | O_NONBLOCK);
}

typedef struct {
    balance_t balance;
    BalanceHistory history;
    int history_size;
} BalanceInfo;

static BalanceInfo *balance_info = NULL;

static void update_balance_history_to(BalanceInfo *info, timestamp_t time) {
    if (time > MAX_T) {
        time = MAX_T;
    }
    
    while (info->history_size < time) {
        BalanceState *state = &info->history.s_history[info->history_size];
        state->s_balance = info->balance;
        state->s_time = info->history_size;
        if (info->history_size > 0) {
            state->s_balance_pending_in = info->history.s_history[info->history_size - 1].s_balance_pending_in;
        } else {
            state->s_balance_pending_in = 0;
        }
        info->history_size++;
    }
}

static void update_balance_history(BalanceInfo *info, timestamp_t time) {
    if (time > MAX_T) {
        time = MAX_T;
    }
    
    while (info->history_size <= time) {
        BalanceState *state = &info->history.s_history[info->history_size];
        state->s_balance = info->balance;
        state->s_time = info->history_size;
        if (info->history_size > 0) {
            state->s_balance_pending_in = info->history.s_history[info->history_size - 1].s_balance_pending_in;
        } else {
            state->s_balance_pending_in = 0;
        }
        info->history_size++;
    }
}

void transfer(void *parent_data, local_id src, local_id dst, balance_t amount) {
    IpcData *ipc = (IpcData *)parent_data;

    Message msg;
    msg.s_header.s_magic = MESSAGE_MAGIC;
    msg.s_header.s_type = TRANSFER;

    TransferOrder *order = (TransferOrder *)msg.s_payload;
    order->s_src = src;
    order->s_dst = dst;
    order->s_amount = amount;

    msg.s_header.s_payload_len = sizeof(TransferOrder);

    increment_lamport_time();
    msg.s_header.s_local_time = get_lamport_time();

    if (send(ipc, src, &msg) != 0) {
        return;
    }
    
    delay_ms(50);
    
    Message ack_msg;
    int attempts = 0;
    while (1) {
        if (receive(ipc, dst, &ack_msg) == 0) {
            if (ack_msg.s_header.s_type == ACK) {
                update_lamport_time(ack_msg.s_header.s_local_time);
                break;
            }
        }
        Message other_msg;
        if (receive_any(ipc, &other_msg) == 0) {
            update_lamport_time(other_msg.s_header.s_local_time);
        }
        delay_ms(1);
        attempts++;
        if (attempts > 100000) {
            return;
        }
    }
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

static void child_process(IpcData *ipc, balance_t initial_balance) {
    lamport_time = 0;
    
    balance_info = malloc(sizeof(BalanceInfo));
    balance_info->balance = initial_balance;
    balance_info->history.s_id = ipc->local_id;
    balance_info->history_size = 0;
    
    increment_lamport_time();
    timestamp_t time = get_lamport_time();
    update_balance_history(balance_info, time);
    
    Message msg;
    msg.s_header.s_magic = MESSAGE_MAGIC;
    msg.s_header.s_type = STARTED;
    msg.s_header.s_local_time = time;
    
    char payload[256];
    snprintf(payload, sizeof(payload), "process %d started", ipc->local_id);
    strncpy(msg.s_payload, payload, strlen(payload));
    msg.s_header.s_payload_len = strlen(payload);
    
    increment_lamport_time();
    send(ipc, PARENT_ID, &msg);
    
    fprintf(ipc->events_log, log_started_fmt, time, ipc->local_id, getpid(), getppid(), initial_balance);
    fflush(ipc->events_log);
    printf(log_started_fmt, time, ipc->local_id, getpid(), getppid(), initial_balance);
    fflush(stdout);
    
    int num_children = ipc->num_processes - 1;
    
    increment_lamport_time();
    time = get_lamport_time();
    fprintf(ipc->events_log, log_received_all_started_fmt, time, ipc->local_id);
    fflush(ipc->events_log);
    printf(log_received_all_started_fmt, time, ipc->local_id);
    fflush(stdout);
    
    int stop_received = 0;
    
    while (!stop_received) {
        Message recv_msg;
        if (receive_any(ipc, &recv_msg) == 0) {
            update_lamport_time(recv_msg.s_header.s_local_time);
            time = get_lamport_time();
            
            if (recv_msg.s_header.s_type == TRANSFER) {
                TransferOrder *order = (TransferOrder *)recv_msg.s_payload;
                
                if (ipc->local_id == order->s_src) {
                    timestamp_t event_time = time;
                    update_balance_history_to(balance_info, event_time);
                    balance_info->balance -= order->s_amount;
                    update_balance_history(balance_info, event_time);

                    for (timestamp_t t = event_time; t < balance_info->history_size; t++) {
                        balance_info->history.s_history[t].s_balance_pending_in += order->s_amount;
                    }

                    fprintf(ipc->events_log, log_transfer_out_fmt, event_time, ipc->local_id, order->s_amount, order->s_dst);
                    fflush(ipc->events_log);
                    printf(log_transfer_out_fmt, event_time, ipc->local_id, order->s_amount, order->s_dst);
                    fflush(stdout);

                    increment_lamport_time();
                    recv_msg.s_header.s_local_time = get_lamport_time();
                    send(ipc, order->s_dst, &recv_msg);
                } else if (ipc->local_id == order->s_dst) {
                    timestamp_t receive_time = time;

                    update_balance_history_to(balance_info, receive_time);
                    balance_info->balance += order->s_amount;
                    update_balance_history(balance_info, receive_time);

                    for (timestamp_t t = receive_time; t < balance_info->history_size; t++) {
                        balance_info->history.s_history[t].s_balance_pending_in -= order->s_amount;
                    }
                    
                    fprintf(ipc->events_log, log_transfer_in_fmt, receive_time, ipc->local_id, order->s_amount, order->s_src);
                    fflush(ipc->events_log);
                    printf(log_transfer_in_fmt, receive_time, ipc->local_id, order->s_amount, order->s_src);
                    fflush(stdout);
                    
                    increment_lamport_time();
                    Message ack_msg;
                    ack_msg.s_header.s_magic = MESSAGE_MAGIC;
                    ack_msg.s_header.s_type = ACK;
                    ack_msg.s_header.s_local_time = get_lamport_time();
                    ack_msg.s_header.s_payload_len = 0;
                    send(ipc, PARENT_ID, &ack_msg);
                }
            } else if (recv_msg.s_header.s_type == STOP) {
                stop_received = 1;
            }
        }
        delay_ms(1);
    }
    
    int done_sent = 0;
    int done_count = 0;
    
    while (done_count < num_children - 1) {
        Message recv_msg;
        if (receive_any(ipc, &recv_msg) == 0) {
            update_lamport_time(recv_msg.s_header.s_local_time);
            time = get_lamport_time();
            
            if (recv_msg.s_header.s_type == TRANSFER) {
                TransferOrder *order = (TransferOrder *)recv_msg.s_payload;
                
                if (ipc->local_id == order->s_src) {
                    timestamp_t event_time = time;
                    update_balance_history_to(balance_info, event_time);
                    balance_info->balance -= order->s_amount;
                    update_balance_history(balance_info, event_time);

                    for (timestamp_t t = event_time; t < balance_info->history_size; t++) {
                        balance_info->history.s_history[t].s_balance_pending_in += order->s_amount;
                    }

                    fprintf(ipc->events_log, log_transfer_out_fmt, event_time, ipc->local_id, order->s_amount, order->s_dst);
                    fflush(ipc->events_log);
                    printf(log_transfer_out_fmt, event_time, ipc->local_id, order->s_amount, order->s_dst);
                    fflush(stdout);

                    increment_lamport_time();
                    recv_msg.s_header.s_local_time = get_lamport_time();
                    send(ipc, order->s_dst, &recv_msg);
                } else if (ipc->local_id == order->s_dst) {
                    timestamp_t receive_time = time;

                    update_balance_history_to(balance_info, receive_time);
                    balance_info->balance += order->s_amount;
                    update_balance_history(balance_info, receive_time);

                    for (timestamp_t t = receive_time; t < balance_info->history_size; t++) {
                        balance_info->history.s_history[t].s_balance_pending_in -= order->s_amount;
                    }
                    
                    fprintf(ipc->events_log, log_transfer_in_fmt, receive_time, ipc->local_id, order->s_amount, order->s_src);
                    fflush(ipc->events_log);
                    printf(log_transfer_in_fmt, receive_time, ipc->local_id, order->s_amount, order->s_src);
                    fflush(stdout);
                    
                    increment_lamport_time();
                    Message ack_msg;
                    ack_msg.s_header.s_magic = MESSAGE_MAGIC;
                    ack_msg.s_header.s_type = ACK;
                    ack_msg.s_header.s_local_time = get_lamport_time();
                    ack_msg.s_header.s_payload_len = 0;
                    send(ipc, PARENT_ID, &ack_msg);
                }
            } else if (recv_msg.s_header.s_type == DONE) {
                done_count++;
            }
        }
        
        if (!done_sent) {
            increment_lamport_time();
            time = get_lamport_time();
            update_balance_history(balance_info, time);
            Message done_msg;
            done_msg.s_header.s_magic = MESSAGE_MAGIC;
            done_msg.s_header.s_type = DONE;
            done_msg.s_header.s_local_time = time;
            
            char done_payload[256];
            snprintf(done_payload, sizeof(done_payload), "process %d done", ipc->local_id);
            strncpy(done_msg.s_payload, done_payload, strlen(done_payload));
            done_msg.s_header.s_payload_len = strlen(done_payload);
            
            increment_lamport_time();
            send(ipc, PARENT_ID, &done_msg);
            for (local_id i = 1; i <= num_children; i++) {
                if (i != ipc->local_id) {
                    increment_lamport_time();
                    send(ipc, i, &done_msg);
                }
            }
            
            fprintf(ipc->events_log, log_done_fmt, time, ipc->local_id, balance_info->balance);
            fflush(ipc->events_log);
            printf(log_done_fmt, time, ipc->local_id, balance_info->balance);
            fflush(stdout);
            
            done_sent = 1;
        }
        
        delay_ms(1);
    }
    
    increment_lamport_time();
    time = get_lamport_time();
    update_balance_history(balance_info, time);
    balance_info->history.s_history_len = balance_info->history_size;
    
    fprintf(ipc->events_log, log_received_all_done_fmt, time, ipc->local_id);
    fflush(ipc->events_log);
    printf(log_received_all_done_fmt, time, ipc->local_id);
    fflush(stdout);
    
    increment_lamport_time();
    Message history_msg;
    history_msg.s_header.s_magic = MESSAGE_MAGIC;
    history_msg.s_header.s_type = BALANCE_HISTORY;
    history_msg.s_header.s_local_time = get_lamport_time();
    
    memcpy(history_msg.s_payload, &balance_info->history, sizeof(BalanceHistory));
    history_msg.s_header.s_payload_len = sizeof(BalanceHistory);
    
    increment_lamport_time();
    send(ipc, PARENT_ID, &history_msg);
    
    free(balance_info);
    fclose(ipc->events_log);
    fclose(ipc->pipes_log);
    exit(0);
}

static void parent_process(IpcData *ipc, int num_children, balance_t *initial_balances) {
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
    
    bank_robbery(ipc, num_children);
    
    increment_lamport_time();
    Message stop_msg;
    stop_msg.s_header.s_magic = MESSAGE_MAGIC;
    stop_msg.s_header.s_type = STOP;
    stop_msg.s_header.s_local_time = get_lamport_time();
    stop_msg.s_header.s_payload_len = 0;
    
    for (local_id i = 1; i <= num_children; i++) {
        increment_lamport_time();
        send(ipc, i, &stop_msg);
    }
    
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
    
    AllHistory all_history;
    memset(&all_history, 0, sizeof(AllHistory));
    all_history.s_history_len = num_children;
    
    int history_count = 0;
    while (history_count < num_children) {
        Message msg;
        if (receive_any(ipc, &msg) == 0) {
            update_lamport_time(msg.s_header.s_local_time);
            if (msg.s_header.s_type == BALANCE_HISTORY) {
                BalanceHistory *hist = (BalanceHistory *)msg.s_payload;
                memcpy(&all_history.s_history[history_count], hist, sizeof(BalanceHistory));
                history_count++;
            }
        }
        delay_ms(1);
    }
    
    print_history(&all_history);
    
    for (int i = 0; i < num_children; i++) {
        wait(NULL);
    }
    
    fclose(ipc->events_log);
    fclose(ipc->pipes_log);
}

int main(int argc, char *argv[]) {
    if (argc < 3) {
        return 1;
    }
    
    int num_children = 0;
    balance_t *initial_balances = NULL;
    
    if (strcmp(argv[1], "-p") == 0) {
        num_children = atoi(argv[2]);
        if (num_children < 1 || num_children > MAX_PROCESS_ID) {
            return 1;
        }
        
        initial_balances = malloc(num_children * sizeof(balance_t));
        for (int i = 0; i < num_children; i++) {
            initial_balances[i] = atoi(argv[3 + i]);
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
            child_process(&ipc, initial_balances[i - 1]);
            return 0;
        } else if (pid < 0) {
            return 1;
        }
    }
    
    close_unused_pipes(&ipc);
    parent_process(&ipc, num_children, initial_balances);
    
    free(initial_balances);
    return 0;
}
