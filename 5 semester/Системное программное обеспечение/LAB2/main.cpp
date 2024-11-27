#include <cstdlib>
#include <iostream>
#include "producer_consumer.h"

int main(int argc, char* argv[]) {
  if (argc < 3) {
    std::cerr << "Usage: " << argv[0] << " <num_consumers> <max_sleep_ms>"
              << std::endl;
    return -1;
  }

  int num_consumers = std::stoi(argv[1]);
  int max_sleep_ms = std::stoi(argv[2]);

  int result = run_threads(num_consumers, max_sleep_ms);
  std::cout << result << std::endl;

  return 0;
}
