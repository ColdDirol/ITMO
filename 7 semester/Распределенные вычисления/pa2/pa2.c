#define _GNU_SOURCE
#include "banking.h"
#include "pa2345.h"
#include "common.h"
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <fcntl.h>
#include <string.h>

typedef struct {
    local_id id;
    int num_processes;
    int read_fds[MAX_PROCESS_ID + 1];
    int write_fds[MAX_PROCESS_ID + 1];
    balance_t balance;
    BalanceHistory history;
} IOData;

void update_balance_history(IOData * data, timestamp_t current_time) {
    if (data->history.s_history_len == 0) {
        data->history.s_history[0].s_balance = data->balance;
        data->history.s_history[0].s_time = 0;
        data->history.s_history[0].s_balance_pending_in = 0;
        data->history.s_history_len = 1;
    }

    timestamp_t last_time = data->history.s_history_len - 1;
    balance_t last_balance = data->history.s_history[last_time].s_balance;

    for (timestamp_t t = last_time + 1; t <= current_time; t++) {
        data->history.s_history[t].s_balance = last_balance;
        data->history.s_history[t].s_time = t;
        data->history.s_history[t].s_balance_pending_in = 0;
    }
    
    data->history.s_history[current_time].s_balance = data->balance;
    data->history.s_history[current_time].s_time = current_time;
    data->history.s_history[current_time].s_balance_pending_in = 0;

    if (current_time >= data->history.s_history_len) {
        data->history.s_history_len = current_time + 1;
    }
}

void child_process(IOData * data) {
    FILE* log = fopen(events_log, "a");
    timestamp_t t = get_physical_time();

    update_balance_history(data, t);

    fprintf(log, log_started_fmt, t, data->id, getpid(), getppid(), data->balance);
    fflush(log);

    Message msg;
    msg.s_header.s_magic = MESSAGE_MAGIC;
    msg.s_header.s_type = STARTED;
    msg.s_header.s_payload_len = 0;
    msg.s_header.s_local_time = t;
    send_multicast(data, &msg);

    int started_received = 0;
    while (started_received < data->num_processes - 1) {
        receive_any(data, &msg);
        if (msg.s_header.s_type == STARTED) {
            started_received++;
        }
    }

    t = get_physical_time();
    fprintf(log, log_received_all_started_fmt, t, data->id);
    fflush(log);

    int stop_received = 0;
    while (!stop_received) {
        receive_any(data, &msg);
        t = get_physical_time();

        if (msg.s_header.s_type == TRANSFER) {
            TransferOrder * order = (TransferOrder*)msg.s_payload;

            if (order->s_src == data->id) {
                data->balance -= order->s_amount;
                update_balance_history(data, t);
                fprintf(log, log_transfer_out_fmt, t, data->id, order->s_amount, order->s_dst);
                fflush(log);
                send(data, order->s_dst, &msg);
            } else if (order->s_dst == data->id) {
                data->balance += order->s_amount;
                update_balance_history(data, t);
                fprintf(log, log_transfer_in_fmt, t, data->id, order->s_amount, order->s_src);
                fflush(log);
                
                Message ack;
                ack.s_header.s_magic = MESSAGE_MAGIC;
                ack.s_header.s_type = ACK;
                ack.s_header.s_payload_len = 0;
                ack.s_header.s_local_time = t;
                send(data, PARENT_ID, &ack);
            }
        } else if (msg.s_header.s_type == STOP) {
            stop_received = 1;
        }
    }

    t = get_physical_time();
    update_balance_history(data, t);
    fprintf(log, log_done_fmt, t, data->id, data->balance);
    fflush(log);

    msg.s_header.s_magic = MESSAGE_MAGIC;
    msg.s_header.s_type = DONE;
    msg.s_header.s_payload_len = 0;
    msg.s_header.s_local_time = t;
    send_multicast(data, &msg);

    int done_received = 0;
    // In the final phase, we may still receive messages from other processes
    while (done_received < data->num_processes - 1) {
        receive_any(data, &msg);
        
        if (msg.s_header.s_type == DONE) {
            done_received++;
        } else if (msg.s_header.s_type == TRANSFER) {
            TransferOrder * order = (TransferOrder*)msg.s_payload;
            t = get_physical_time();

            if (order->s_dst == data->id) {
                data->balance += order->s_amount;
                update_balance_history(data, t);
                fprintf(log, log_transfer_in_fmt, t, data->id, order->s_amount, order->s_src);
                fflush(log);
                
                Message ack;
                ack.s_header.s_magic = MESSAGE_MAGIC;
                ack.s_header.s_type = ACK;
                ack.s_header.s_payload_len = 0;
                ack.s_header.s_local_time = t;
                send(data, PARENT_ID, &ack);
            }
        }
    }

    t = get_physical_time();
    fprintf(log, log_received_all_done_fmt, t, data->id);
    fflush(log);

    msg.s_header.s_magic = MESSAGE_MAGIC;
    msg.s_header.s_type = BALANCE_HISTORY;
    msg.s_header.s_local_time = t;
    msg.s_header.s_payload_len = sizeof(local_id) + sizeof(uint8_t) + 
                                  data->history.s_history_len * sizeof(BalanceState);
    memcpy(msg.s_payload, &data->history, msg.s_header.s_payload_len);
    send(data, PARENT_ID, &msg);

    fclose(log);
}

void transfer(void * parent_data, local_id src, local_id dst, balance_t amount) {
    IOData * data = (IOData*)parent_data;

    TransferOrder order = {src, dst, amount};
    Message msg;
    msg.s_header.s_magic = MESSAGE_MAGIC;
    msg.s_header.s_type = TRANSFER;
    msg.s_header.s_local_time = get_physical_time();
    msg.s_header.s_payload_len = sizeof(TransferOrder);
    memcpy(msg.s_payload, &order, sizeof(TransferOrder));

    send(data, src, &msg);

    Message ack;
    receive(data, dst, &ack);
}

int main(int argc, char * argv[]) {
    if (argc < 4) return 1;

    int num_processes = atoi(argv[2]);
    balance_t balances[MAX_PROCESS_ID + 1];
    for (int i = 0; i < num_processes; i++) {
        balances[i + 1] = atoi(argv[3 + i]);
    }

    int pipes[MAX_PROCESS_ID + 1][MAX_PROCESS_ID + 1][2];

    for (int i = 0; i <= num_processes; i++) {
        for (int j = 0; j <= num_processes; j++) {
            if (i != j) {
                pipe(pipes[i][j]);
                fcntl(pipes[i][j][0], F_SETFL, O_NONBLOCK);
            }
        }
    }

    IOData parent_data;
    parent_data.id = PARENT_ID;
    parent_data.num_processes = num_processes;

    for (int i = 1; i <= num_processes; i++) {
        parent_data.read_fds[i] = pipes[i][PARENT_ID][0];
        parent_data.write_fds[i] = pipes[PARENT_ID][i][1];
    }

    for (int i = 1; i <= num_processes; i++) {
        pid_t pid = fork();
        if (pid == 0) {
            IOData child_data;
            child_data.id = i;
            child_data.num_processes = num_processes;
            child_data.balance = balances[i];
            child_data.history.s_id = i;
            child_data.history.s_history_len = 0;

            for (int j = 0; j <= num_processes; j++) {
                if (j != i) {
                    child_data.read_fds[j] = pipes[j][i][0];
                    child_data.write_fds[j] = pipes[i][j][1];
                }
            }

            for (int x = 0; x <= num_processes; x++) {
                for (int y = 0; y <= num_processes; y++) {
                    if (x != y && !((x == i) || (y == i))) {
                        close(pipes[x][y][0]);
                        close(pipes[x][y][1]);
                    }
                }
            }

            child_process(&child_data);
            exit(0);
        }
    }

    for (int i = 1; i <= num_processes; i++) {
        for (int j = 1; j <= num_processes; j++) {
            if (i != j) {
                close(pipes[i][j][0]);
                close(pipes[i][j][1]);
            }
        }
    }

    Message msg;
    int started_count = 0;
    while (started_count < num_processes) {
        receive_any(&parent_data, &msg);
        if (msg.s_header.s_type == STARTED) {
            started_count++;
        }
    }

    bank_robbery(&parent_data, num_processes);

    msg.s_header.s_magic = MESSAGE_MAGIC;
    msg.s_header.s_type = STOP;
    msg.s_header.s_payload_len = 0;
    msg.s_header.s_local_time = get_physical_time();
    send_multicast(&parent_data, &msg);

    int done_count = 0;
    while (done_count < num_processes) {
        receive_any(&parent_data, &msg);
        if (msg.s_header.s_type == DONE) {
            done_count++;
        }
    }

    AllHistory all_history;
    all_history.s_history_len = num_processes;

    for (int i = 1; i <= num_processes; i++) {
        receive(&parent_data, i, &msg);
        BalanceHistory * history = (BalanceHistory*)msg.s_payload;
        all_history.s_history[history->s_id - 1] = *history;
    }

    print_history(&all_history);

    for (int i = 0; i < num_processes; i++) {
        wait(NULL);
    }

    return 0;
}
