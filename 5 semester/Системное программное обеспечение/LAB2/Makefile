# DO NOT TOUCH IT FILE !!!

SRC_TEST_FILES = $(filter-out main.cpp, $(wildcard *.cpp)) tests/*.cpp
SHELL := /bin/bash

all:
	$(CXX) *.cpp -std=c++17 -g -O3 -Werror -Wall -Wextra -pthread -pedantic -o posix

run_tests:
	./tests/tests -s -d

build_tests:
	$(CXX) $(SRC_TEST_FILES) -I tests -I . -std=c++17 -g -O3 -Werror -Wall -Wextra -pthread -pedantic -o tests/tests

clang_format:
	diff -u <(cat *.cpp *.h tests/*.cpp) <(clang-format *.cpp *.h tests/*.cpp)

clang_format_fix:
	clang-format -i *.cpp *.h tests/*.cpp

clang_tidy:
	clang-tidy *.cpp tests/*.cpp -checks=-*,clang-analyzer-*,-clang-analyzer-cplusplus* -- -I tests -I . -std=c++17 -g -O3 -Werror -Wall -Wextra -pthread -pedantic

clean:
	rm -f posix
	rm -f tests/tests
