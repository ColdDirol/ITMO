#include "producer_consumer.h"
#include <pthread.h>
#include <unistd.h>
#include <atomic>
#include <iostream>
#include <random>
#include <sstream>
#include <string>
#include <vector>

pthread_key_t tls_key;
pthread_once_t tls_once = PTHREAD_ONCE_INIT;

void tls_destructor(void* ptr) { delete static_cast<int*>(ptr); }

void create_tls_key() { pthread_key_create(&tls_key, tls_destructor); }

int get_tid() {
  pthread_once(&tls_once, create_tls_key);

  int* tid_ptr = static_cast<int*>(pthread_getspecific(tls_key));
  if (!tid_ptr) {
    tid_ptr = new int;
    static std::atomic<int> next_id(1);
    *tid_ptr = next_id.fetch_add(1);
    pthread_setspecific(tls_key, tid_ptr);
  }
  return *tid_ptr;
}

struct ThreadArgs {
  SharedData* shared_data;
  std::vector<int>* input_data;
  int num_consumers;
  int max_sleep_ms;
  std::atomic<bool>* is_complete;
  std::vector<long>* partial_sums;
  std::atomic<int>* current_index;
  bool debug_mode;
};

void* producer_routine(void* arg) {
  ThreadArgs* args = static_cast<ThreadArgs*>(arg);
  SharedData* shared_data = args->shared_data;
  std::vector<int>* input_data = args->input_data;

  for (int value : *input_data) {
    pthread_mutex_lock(&shared_data->mutex);
    while (shared_data->is_updated) {
      pthread_cond_wait(&shared_data->cond, &shared_data->mutex);
    }

    shared_data->value = value;
    shared_data->is_updated = true;
    pthread_cond_broadcast(&shared_data->cond);
    pthread_mutex_unlock(&shared_data->mutex);
  }

  pthread_mutex_lock(&shared_data->mutex);
  args->is_complete->store(true);
  pthread_cond_broadcast(&shared_data->cond);
  pthread_mutex_unlock(&shared_data->mutex);

  return nullptr;
}

void* consumer_routine(void* arg) {
  ThreadArgs* args = static_cast<ThreadArgs*>(arg);
  SharedData* shared_data = args->shared_data;
  long partial_sum = 0;

  while (true) {
    pthread_mutex_lock(&shared_data->mutex);

    while (!shared_data->is_updated && !args->is_complete->load()) {
      pthread_cond_wait(&shared_data->cond, &shared_data->mutex);
    }

    if (args->is_complete->load() && !shared_data->is_updated) {
      pthread_mutex_unlock(&shared_data->mutex);
      break;
    }

    partial_sum += shared_data->value;
    shared_data->is_updated = false;

    if (args->debug_mode) {
      int tid = get_tid();
      std::cout << "Consumer " << tid << ": psum = " << partial_sum
                << std::endl;
    }

    pthread_cond_signal(&shared_data->cond);
    pthread_mutex_unlock(&shared_data->mutex);

    if (args->max_sleep_ms > 0) {
      usleep(args->max_sleep_ms * 1000);
    }
  }

  int thread_index = (*args->current_index)++;
  (*args->partial_sums)[thread_index] = partial_sum;
  return nullptr;
}

int run_threads(int num_consumers, int max_sleep_ms) {
  if (num_consumers <= 0) {
    return -1;
  }

  std::string input_line;
  std::getline(std::cin, input_line);

  if (input_line.empty()) {
    return 0;
  }

  std::istringstream iss(input_line);
  std::vector<int> input_data;
  std::string token;

  while (iss >> token) {
    try {
      int num = std::stoi(token);
      input_data.push_back(num);
    } catch (const std::invalid_argument&) {
      return -1;
    } catch (const std::out_of_range&) {
      return -1;
    }
  }

  if (input_data.empty()) {
    return 0;
  }

  std::atomic<bool> is_complete(false);
  bool debug_mode = (getenv("DEBUG") != nullptr);

  SharedData shared_data = {0, false, PTHREAD_MUTEX_INITIALIZER,
                            PTHREAD_COND_INITIALIZER};
  std::vector<pthread_t> consumer_threads(num_consumers);
  std::vector<long> partial_sums(num_consumers, 0);
  std::atomic<int> current_index(0);

  ThreadArgs args = {&shared_data, &input_data,   num_consumers,  max_sleep_ms,
                     &is_complete, &partial_sums, &current_index, debug_mode};

  pthread_t producer_thread;
  pthread_create(&producer_thread, nullptr, producer_routine, &args);

  for (int i = 0; i < num_consumers; ++i) {
    pthread_create(&consumer_threads[i], nullptr, consumer_routine, &args);
  }

  pthread_join(producer_thread, nullptr);

  for (int i = 0; i < num_consumers; ++i) {
    pthread_join(consumer_threads[i], nullptr);
  }

  long total_sum = 0;
  for (long partial_sum : partial_sums) {
    total_sum += partial_sum;
  }

  return total_sum;
}