CC = clang
CFLAGS = -std=c99 -Wall -pedantic
LIBS = -L./lib64 -lruntime

all: pa2

pa2: pa23.c ipc.c bank_robbery.c
	$(CC) $(CFLAGS) -o pa2 pa23.c ipc.c bank_robbery.c $(LIBS)

clean:
	rm -f pa2 events.log pipes.log

.PHONY: all clean