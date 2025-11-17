#define _XOPEN_SOURCE 500
#include "banking.h"
#include "ipc.h"
#include "pa2345.h"
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include <sys/wait.h>

typedef struct {
    local_id id;
    int num_processes;
    int (*pipes)[2];
    balance_t balance;
    BalanceHistory history;
} ProcessData;

void update_balance_history(ProcessData *data) {
    timestamp_t current_time = get_physical_time();
    if (current_time > MAX_T) {
        current_time = MAX_T;
    }
    
    timestamp_t end_time = current_time;
    if (end_time > MAX_T) {
        end_time = MAX_T;
    }
    
    for (timestamp_t t = data->history.s_history_len; t < end_time; t++) {
        if (t <= MAX_T) {
            data->history.s_history[t].s_balance = data->balance;
            data->history.s_history[t].s_time = t;
            data->history.s_history[t].s_balance_pending_in = 0;
        }
    }

    if (current_time <= MAX_T) {
        data->history.s_history[current_time].s_balance = data->balance;
        data->history.s_history[current_time].s_time = current_time;
        data->history.s_history[current_time].s_balance_pending_in = 0;
        if (current_time < 255) {
            data->history.s_history_len = (uint8_t)(current_time + 1);
        } else {
            data->history.s_history_len = 255;
        }
    } else {
        data->history.s_history_len = 255;
    }
}

void transfer(void *parent_data, local_id src, local_id dst, balance_t amount) {
    TransferOrder order;
    order.s_src = src;
    order.s_dst = dst;
    order.s_amount = amount;
    
    Message msg;
    msg.s_header.s_magic = MESSAGE_MAGIC;
    msg.s_header.s_type = TRANSFER;
    msg.s_header.s_payload_len = sizeof(TransferOrder);
    msg.s_header.s_local_time = get_physical_time();
    memcpy(msg.s_payload, &order, sizeof(TransferOrder));
    
    send(parent_data, src, &msg);
    
    Message ack_msg;
    while (1) {
        if (receive(parent_data, dst, &ack_msg) == 0) {
            if (ack_msg.s_header.s_type == ACK) {
                break;
            }
        }
        usleep(1000);
    }
}

void child_process(ProcessData *data) {
    data->history.s_id = data->id;
    data->history.s_history_len = 0;
    
    timestamp_t current_time = get_physical_time();
    timestamp_t max_time = current_time;
    if (max_time > MAX_T) {
        max_time = MAX_T;
    }
    for (timestamp_t t = 0; t <= max_time; t++) {
        data->history.s_history[t].s_balance = data->balance;
        data->history.s_history[t].s_time = t;
        data->history.s_history[t].s_balance_pending_in = 0;
    }
    if (current_time <= MAX_T) {
        if (current_time < 255) {
            data->history.s_history_len = (uint8_t)(current_time + 1);
        } else {
            data->history.s_history_len = 255;
        }
    } else {
        data->history.s_history_len = 255;
    }
    
    printf(log_started_fmt, current_time, data->id, getpid(), getppid(), data->balance);
    fflush(stdout);
    
    Message started_msg;
    started_msg.s_header.s_magic = MESSAGE_MAGIC;
    started_msg.s_header.s_type = STARTED;
    started_msg.s_header.s_local_time = get_physical_time();
    started_msg.s_header.s_payload_len = 0;
    
    send_multicast(data, &started_msg);
    
    int started_count = 0;
    int expected_started = data->num_processes - 2;
    
    while (started_count < expected_started) {
        Message msg;
        if (receive_any(data, &msg) == 0) {
            if (msg.s_header.s_type == STARTED) {
                started_count++;
            }
        }
    }
    
    printf(log_received_all_started_fmt, get_physical_time(), data->id);
    fflush(stdout);
    
    while (1) {
        Message msg;
        if (receive_any(data, &msg) == 0) {
            if (msg.s_header.s_type == TRANSFER) {
                TransferOrder *order = (TransferOrder *)msg.s_payload;
                
                if (data->id == order->s_src) {
                    timestamp_t transfer_time = get_physical_time();
                    if (transfer_time > MAX_T) {
                        transfer_time = MAX_T;
                    }
                    
                    timestamp_t end_time = transfer_time;
                    if (end_time > MAX_T) {
                        end_time = MAX_T;
                    }
                    for (timestamp_t t = data->history.s_history_len; t < end_time; t++) {
                        if (t <= MAX_T) {
                            data->history.s_history[t].s_balance = data->balance;
                            data->history.s_history[t].s_time = t;
                            data->history.s_history[t].s_balance_pending_in = 0;
                        }
                    }
                    
                    data->balance -= order->s_amount;
                    
                    if (transfer_time <= MAX_T) {
                        data->history.s_history[transfer_time].s_balance = data->balance;
                        data->history.s_history[transfer_time].s_time = transfer_time;
                        data->history.s_history[transfer_time].s_balance_pending_in = 0;
                        if (transfer_time < 255) {
                            data->history.s_history_len = (uint8_t)(transfer_time + 1);
                        } else {
                            data->history.s_history_len = 255;
                        }
                    }
                    
                    printf(log_transfer_out_fmt, transfer_time, data->id, order->s_amount, order->s_dst);
                    fflush(stdout);
                    
                    send(data, order->s_dst, &msg);
                    
                } else if (data->id == order->s_dst) {
                    timestamp_t transfer_time = get_physical_time();
                    if (transfer_time > MAX_T) {
                        transfer_time = MAX_T;
                    }
                    
                    timestamp_t end_time = transfer_time;
                    if (end_time > MAX_T) {
                        end_time = MAX_T;
                    }
                    for (timestamp_t t = data->history.s_history_len; t < end_time; t++) {
                        if (t <= MAX_T) {
                            data->history.s_history[t].s_balance = data->balance;
                            data->history.s_history[t].s_time = t;
                            data->history.s_history[t].s_balance_pending_in = 0;
                        }
                    }
                    
                    data->balance += order->s_amount;
                    
                    if (transfer_time <= MAX_T) {
                        data->history.s_history[transfer_time].s_balance = data->balance;
                        data->history.s_history[transfer_time].s_time = transfer_time;
                        data->history.s_history[transfer_time].s_balance_pending_in = 0;
                        if (transfer_time < 255) {
                            data->history.s_history_len = (uint8_t)(transfer_time + 1);
                        } else {
                            data->history.s_history_len = 255;
                        }
                    }
                    
                    printf(log_transfer_in_fmt, transfer_time, data->id, order->s_amount, order->s_src);
                    fflush(stdout);
                    
                    Message ack;
                    ack.s_header.s_magic = MESSAGE_MAGIC;
                    ack.s_header.s_type = ACK;
                    ack.s_header.s_payload_len = 0;
                    ack.s_header.s_local_time = get_physical_time();
                    send(data, 0, &ack);
                }
                
            } else if (msg.s_header.s_type == STOP) {
                break;
            }
        }
    }
    
    update_balance_history(data);
    
    Message done_msg;
    done_msg.s_header.s_magic = MESSAGE_MAGIC;
    done_msg.s_header.s_type = DONE;
    done_msg.s_header.s_payload_len = 0;
    done_msg.s_header.s_local_time = get_physical_time();
    send_multicast(data, &done_msg);
    
    printf(log_done_fmt, get_physical_time(), data->id, data->balance);
    fflush(stdout);
    
    int done_count = 0;
    int expected_done = data->num_processes - 2;
    
    while (done_count < expected_done) {
        Message msg;
        if (receive_any(data, &msg) == 0) {
            if (msg.s_header.s_type == DONE) {
                done_count++;
            } else if (msg.s_header.s_type == TRANSFER) {
                TransferOrder *order = (TransferOrder *)msg.s_payload;
                
                if (data->id == order->s_src) {
                    timestamp_t transfer_time = get_physical_time();
                    if (transfer_time > MAX_T) {
                        transfer_time = MAX_T;
                    }
                    timestamp_t end_time = transfer_time;
                    if (end_time > MAX_T) {
                        end_time = MAX_T;
                    }
                    for (timestamp_t t = data->history.s_history_len; t < end_time; t++) {
                        if (t <= MAX_T) {
                            data->history.s_history[t].s_balance = data->balance;
                            data->history.s_history[t].s_time = t;
                            data->history.s_history[t].s_balance_pending_in = 0;
                        }
                    }
                    data->balance -= order->s_amount;
                    if (transfer_time <= MAX_T) {
                        data->history.s_history[transfer_time].s_balance = data->balance;
                        data->history.s_history[transfer_time].s_time = transfer_time;
                        data->history.s_history[transfer_time].s_balance_pending_in = 0;
                        if (transfer_time < 255) {
                            data->history.s_history_len = (uint8_t)(transfer_time + 1);
                        } else {
                            data->history.s_history_len = 255;
                        }
                    }
                    printf(log_transfer_out_fmt, transfer_time, data->id, order->s_amount, order->s_dst);
                    fflush(stdout);
                    send(data, order->s_dst, &msg);
                } else if (data->id == order->s_dst) {
                    timestamp_t transfer_time = get_physical_time();
                    if (transfer_time > MAX_T) {
                        transfer_time = MAX_T;
                    }
                    timestamp_t end_time = transfer_time;
                    if (end_time > MAX_T) {
                        end_time = MAX_T;
                    }
                    for (timestamp_t t = data->history.s_history_len; t < end_time; t++) {
                        if (t <= MAX_T) {
                            data->history.s_history[t].s_balance = data->balance;
                            data->history.s_history[t].s_time = t;
                            data->history.s_history[t].s_balance_pending_in = 0;
                        }
                    }
                    data->balance += order->s_amount;
                    if (transfer_time <= MAX_T) {
                        data->history.s_history[transfer_time].s_balance = data->balance;
                        data->history.s_history[transfer_time].s_time = transfer_time;
                        data->history.s_history[transfer_time].s_balance_pending_in = 0;
                        if (transfer_time < 255) {
                            data->history.s_history_len = (uint8_t)(transfer_time + 1);
                        } else {
                            data->history.s_history_len = 255;
                        }
                    }
                    printf(log_transfer_in_fmt, transfer_time, data->id, order->s_amount, order->s_src);
                    fflush(stdout);
                    Message ack;
                    ack.s_header.s_magic = MESSAGE_MAGIC;
                    ack.s_header.s_type = ACK;
                    ack.s_header.s_payload_len = 0;
                    ack.s_header.s_local_time = get_physical_time();
                    send(data, 0, &ack);
                }
            }
        }
    }
    
    printf(log_received_all_done_fmt, get_physical_time(), data->id);
    fflush(stdout);
    
    update_balance_history(data);
    
    fflush(stdout);
    
    size_t actual_history_size = sizeof(local_id) 
        + sizeof(uint8_t) 
        + data->history.s_history_len * sizeof(BalanceState);
        
    Message history_msg;
    history_msg.s_header.s_magic = MESSAGE_MAGIC;
    history_msg.s_header.s_type = BALANCE_HISTORY;
    history_msg.s_header.s_payload_len = actual_history_size;
    history_msg.s_header.s_local_time = get_physical_time();
    memcpy(history_msg.s_payload, &data->history, actual_history_size); 
    send(data, 0, &history_msg);
    
    usleep(10000);
    
    exit(0);
}

int main(int argc, char *argv[]) {
    if (argc < 4 || strcmp(argv[1], "-p") != 0) {
        fprintf(stderr, "Usage: %s -p <num_processes> <balance1> <balance2> ...\n", argv[0]);
        return 1;
    }
    
    int num_children = atoi(argv[2]);
    if (argc != 3 + num_children) {
        fprintf(stderr, "Wrong number of balances\n");
        return 1;
    }
    
    int num_processes = num_children + 1;
    int (*pipefds)[2] = malloc(num_processes * num_processes * sizeof(int[2]));
    
    for (int i = 0; i < num_processes; i++) {
        for (int j = 0; j < num_processes; j++) {
            if (i != j) {
                if (pipe(pipefds[i * num_processes + j]) == -1) {
                    perror("pipe");
                    return 1;
                }
                fcntl(pipefds[i * num_processes + j][0], F_SETFL, O_NONBLOCK);
                fcntl(pipefds[i * num_processes + j][1], F_SETFL, O_NONBLOCK);
            } else {
                pipefds[i * num_processes + j][0] = -1;
                pipefds[i * num_processes + j][1] = -1;
            }
        }
    }
    
    for (local_id i = 1; i <= num_children; i++) {
        pid_t pid = fork();
        if (pid == 0) {
            ProcessData data;
            data.id = i;
            data.num_processes = num_processes;
            data.pipes = pipefds;
            data.balance = atoi(argv[2 + i]);
            
            for (int from = 0; from < num_processes; from++) {
                for (int to = 0; to < num_processes; to++) {
                    if (from != i && to != i) {
                        if (pipefds[from * num_processes + to][0] != -1) {
                            close(pipefds[from * num_processes + to][0]);
                        }
                        if (pipefds[from * num_processes + to][1] != -1) {
                            close(pipefds[from * num_processes + to][1]);
                        }
                    } else if (from == i && to != i) {
                        if (pipefds[from * num_processes + to][0] != -1) {
                            close(pipefds[from * num_processes + to][0]);
                        }
                    } else if (from != i && to == i) {
                        if (pipefds[from * num_processes + to][1] != -1) {
                            close(pipefds[from * num_processes + to][1]);
                        }
                    }
                }
            }
            
            child_process(&data);
        } else if (pid < 0) {
            perror("fork");
            return 1;
        }
    }

    ProcessData parent_data;
    parent_data.id = 0;
    parent_data.num_processes = num_processes;
    parent_data.pipes = pipefds;
    
    for (int from = 0; from < num_processes; from++) {
        for (int to = 0; to < num_processes; to++) {
            if (from != 0 && to != 0) {
                if (pipefds[from * num_processes + to][0] != -1) {
                    close(pipefds[from * num_processes + to][0]);
                }
                if (pipefds[from * num_processes + to][1] != -1) {
                    close(pipefds[from * num_processes + to][1]);
                }
            } else if (from == 0 && to != 0) {
                if (pipefds[from * num_processes + to][0] != -1) {
                    close(pipefds[from * num_processes + to][0]);
                }
            } else if (from != 0 && to == 0) {
                if (pipefds[from * num_processes + to][1] != -1) {
                    close(pipefds[from * num_processes + to][1]);
                }
            }
        }
    }
    
    int started_count = 0;
    while (started_count < num_children) {
        Message msg;
        if (receive_any(&parent_data, &msg) == 0 && msg.s_header.s_type == STARTED) {
            started_count++;
        }
    }
    
    printf(log_received_all_started_fmt, get_physical_time(), PARENT_ID);
    fflush(stdout);
    bank_robbery(&parent_data, num_children);
    
    Message stop_msg;
    stop_msg.s_header.s_magic = MESSAGE_MAGIC;
    stop_msg.s_header.s_type = STOP;
    stop_msg.s_header.s_payload_len = 0;
    stop_msg.s_header.s_local_time = get_physical_time();
    send_multicast(&parent_data, &stop_msg);
    
    int done_count = 0;
    while (done_count < num_children) {
        Message msg;
        if (receive_any(&parent_data, &msg) == 0 && msg.s_header.s_type == DONE) {
            done_count++;
        }
    }
    
    printf(log_received_all_done_fmt, get_physical_time(), PARENT_ID);
    fflush(stdout);
    
    AllHistory *all_history_ptr = (AllHistory *)malloc(sizeof(AllHistory));
    if (all_history_ptr == NULL) {
        perror("malloc failed");
        exit(EXIT_FAILURE);
    }
    memset(all_history_ptr, 0, sizeof(AllHistory));
    all_history_ptr->s_history_len = num_children;
    
    int history_count = 0;
    while (history_count < num_children) {
        Message msg;
        if (receive_any(&parent_data, &msg) == 0 && msg.s_header.s_type == BALANCE_HISTORY) {
            
            BalanceHistory *history_src = (BalanceHistory *)msg.s_payload;
            local_id id = history_src->s_id; 
            
            if (id < 1 || id > num_children) {
                continue;
            }
            
            BalanceHistory *history_dst = &all_history_ptr->s_history[id - 1]; 
            
            size_t received_size = msg.s_header.s_payload_len;
            size_t max_size = sizeof(BalanceHistory);
            if (received_size > max_size) {
                received_size = max_size;
            }
            
            memcpy(history_dst, history_src, received_size); 

            fflush(stdout);
            
            history_count++;
        }
    }

    printf("\n");
    fflush(stdout);
    
    print_history(all_history_ptr);
    free(all_history_ptr);
    
    for (int i = 0; i < num_children; i++) {
        wait(NULL);
    }
    
    free(pipefds);
    
    return 0;
}
