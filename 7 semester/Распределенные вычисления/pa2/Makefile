CC = clang
CFLAGS = -std=c99 -Wall -pedantic
LDFLAGS = -L. -lruntime
TARGET = pa2
SOURCES = pa2.c bank_robbery.c ipc.c
OBJECTS = $(SOURCES:.c=.o)

ARCH := $(shell uname -m)
ifeq ($(ARCH),x86_64)
    LIBDIR = lib64
else
    LIBDIR = lib32
endif

all: $(TARGET)

$(TARGET): $(OBJECTS)
	cp $(LIBDIR)/libruntime.so .
	$(CC) $(CFLAGS) -o $(TARGET) $(OBJECTS) $(LDFLAGS)

%.o: %.c
	$(CC) $(CFLAGS) -c $< -o $@

clean:
	rm -f $(OBJECTS) $(TARGET) libruntime.so events.log pipes.log

run: $(TARGET)
	export LD_LIBRARY_PATH=".:$$LD_LIBRARY_PATH" && LD_PRELOAD=./libruntime.so ./$(TARGET) -p 3 10 20 30

.PHONY: all clean run
