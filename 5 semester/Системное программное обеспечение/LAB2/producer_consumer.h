#pragma once

#include <pthread.h>
#include <vector>

struct SharedData {
  int value;
  bool is_updated;
  pthread_mutex_t mutex;
  pthread_cond_t cond;
};

int run_threads(int num_consumers, int max_sleep_ms);
int get_tid();