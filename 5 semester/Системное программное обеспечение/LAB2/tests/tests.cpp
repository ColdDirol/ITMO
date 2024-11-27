#define DOCTEST_CONFIG_IMPLEMENT_WITH_MAIN
#include <thread>
#include <vector>
#include "doctest.h"
#include "producer_consumer.h"

TEST_CASE("Single consumer test") {
  std::string input = "1 2 3 4 5";
  std::istringstream iss(input);
  std::cin.rdbuf(iss.rdbuf());

  int total_sum = run_threads(1, 0);
  CHECK(total_sum == 15);
}

TEST_CASE("Multiple consumers test") {
  std::string input = "1 2 3 4 5 6 7 8 9 10";
  std::istringstream iss(input);
  std::cin.rdbuf(iss.rdbuf());

  int total_sum = run_threads(3, 0);
  CHECK(total_sum == 55);
}

TEST_CASE("Consumer sleep time test") {
  std::string input = "1 2 3";
  std::istringstream iss(input);
  std::cin.rdbuf(iss.rdbuf());

  int total_sum = run_threads(2, 100);
  CHECK(total_sum == 6);
}

TEST_CASE("Invalid input test") {
  std::string input = "abc def ghi";
  std::istringstream iss(input);
  std::cin.rdbuf(iss.rdbuf());

  int total_sum = run_threads(2, 0);
  CHECK(total_sum == -1);
}

TEST_CASE("Mixed input test") {
  std::string input = "1 2 abc 4 5";
  std::istringstream iss(input);
  std::cin.rdbuf(iss.rdbuf());

  int total_sum = run_threads(2, 0);
  CHECK(total_sum == -1);
}

TEST_CASE("Special characters input test") {
  std::string input = "! @ # $ %";
  std::istringstream iss(input);
  std::cin.rdbuf(iss.rdbuf());

  int total_sum = run_threads(2, 0);
  CHECK(total_sum == -1);
}

TEST_CASE("Empty input with non-zero consumers test") {
  std::string input = "";
  std::istringstream iss(input);
  std::cin.rdbuf(iss.rdbuf());

  int total_sum = run_threads(2, 0);
  CHECK(total_sum == 0);
}

TEST_CASE("Single number input test") {
  std::string input = "42";
  std::istringstream iss(input);
  std::cin.rdbuf(iss.rdbuf());

  int total_sum = run_threads(1, 0);
  CHECK(total_sum == 42);
}

TEST_CASE("Debug mode test") {
  setenv("DEBUG", "1", 1);

  std::string input = "1 2 3";
  std::istringstream iss(input);
  std::cin.rdbuf(iss.rdbuf());

  int total_sum = run_threads(2, 0);
  CHECK(total_sum == 6);

  unsetenv("DEBUG");
}
