/* heap-1.c */

#include <stdbool.h>
#include <stddef.h>
#include <stdio.h>
#include <stdlib.h>

#define HEAP_BLOCKS 16
#define BLOCK_CAPACITY 1024

enum block_status {
    BLK_FREE = 0,
    BLK_ONE,
    BLK_FIRST,
    BLK_CONT,
    BLK_LAST,
};

struct heap {
    struct block {
        char contents[BLOCK_CAPACITY];
    } blocks[HEAP_BLOCKS];
    enum block_status status[HEAP_BLOCKS];
} global_heap = {0};

struct block_id {
    size_t value;
    bool valid;
    struct heap* heap;
};

struct block_id block_id_new(size_t value, struct heap* from) {
    return (struct block_id){.valid = true, .value = value, .heap = from};
}
struct block_id block_id_invalid() {
    return (struct block_id){.valid = false};
}

bool block_id_is_valid(struct block_id bid) {
    return bid.valid && bid.value < HEAP_BLOCKS;
}

/* Find block */
bool block_is_free(struct block_id bid) {
    if (!block_id_is_valid(bid))
        return false;
    return bid.heap->status[bid.value] == BLK_FREE;
}

/* Allocate */
struct block_id block_allocate(struct heap* heap, size_t size) {
    size_t start_block = 0;
    size_t end_block = 0;
    size_t consecutive_free_blocks = 0;

    // Find consecutive free blocks
    for (size_t i = 0; i < HEAP_BLOCKS; ++i) {
        if (heap->status[i] == BLK_FREE) {
            if (consecutive_free_blocks == 0) {
                start_block = i;
            }
            ++consecutive_free_blocks;
            end_block = i;

            if (consecutive_free_blocks == size) {
                // Mark the blocks as allocated
                for (size_t j = start_block; j <= end_block; ++j) {
                    if (j == start_block) {
                        heap->status[j] = BLK_FIRST;
                    } else if (j == end_block) {
                        heap->status[j] = BLK_LAST;
                    } else {
                        heap->status[j] = BLK_CONT;
                    }
                }
                return block_id_new(start_block, heap);
            }
        } else {
            consecutive_free_blocks = 0;
        }
    }

    return block_id_invalid(); // Not enough consecutive free blocks
}

/* Free */
/* Free */
void block_free(struct block_id b) {
    if (block_id_is_valid(b)) {
        // Mark the blocks as free
        size_t current_block = b.value;
        while (b.heap->status[current_block] != BLK_LAST) {
            b.heap->status[current_block] = BLK_FREE;
            ++current_block;
            if (current_block >= HEAP_BLOCKS) {
                break; // Avoid going out of bounds
            }
        }
        if (current_block < HEAP_BLOCKS) {
            b.heap->status[current_block] = BLK_FREE;
        }
    }
}

/* Printer */
const char* block_repr(struct block_id b) {
    static const char* const repr[] = {[BLK_FREE] = " .",
            [BLK_ONE] = " *",
            [BLK_FIRST] = "[=",
            [BLK_LAST] = "=]",
            [BLK_CONT] = " ="};
    if (b.valid)
        return repr[b.heap->status[b.value]];
    else
        return "INVALID";
}

void block_debug_info(struct block_id b, FILE* f) {
    fprintf(f, "%s", block_repr(b));
}

void block_foreach_printer(struct heap* h, size_t count,
                           void printer(struct block_id, FILE* f), FILE* f) {
    for (size_t c = 0; c < count; c++)
        printer(block_id_new(c, h), f);
}

void heap_debug_info(struct heap* h, FILE* f) {
    block_foreach_printer(h, HEAP_BLOCKS, block_debug_info, f);
    fprintf(f, "\n");
}

/*  -------- */

void print_error(char* message) {
    printf("%s \n", message);
}

int main() {
    printf("0: ");
    heap_debug_info(&global_heap, stdout);

    // Test 1: Allocate three contiguous blocks
    struct block_id bid1 = block_allocate(&global_heap, 3);
    printf("1: ");
    heap_debug_info(&global_heap, stdout);

    // Test 2: Allocate one block
    struct block_id bid2 = block_allocate(&global_heap, 1);
    printf("2: ");
    heap_debug_info(&global_heap, stdout);

    // Test 3: Free the first allocation
    if(!block_is_free(bid1)) {
        block_free(bid1);
    } else {
        print_error("Test 4: Block already is free.");
    }
    printf("3: ");
    heap_debug_info(&global_heap, stdout);

    // Test 4: Allocate two contiguous blocks
    struct block_id bid3 = block_allocate(&global_heap, 2);
    printf("4: ");
    heap_debug_info(&global_heap, stdout);

    // Test 5: Free the second allocation
    if(!block_is_free(bid2)) {
        block_free(bid2);
    } else {
        print_error("Test 5: Block already is free.");
    }
    printf("5: ");
    heap_debug_info(&global_heap, stdout);

    // Test 6: Try to allocate more blocks than available
    struct block_id bid4 = block_allocate(&global_heap, 5);
    if (!block_id_is_valid(bid4)) {
        print_error("Test 6: Allocation failed as expected.");
    }
    printf("6: ");
    heap_debug_info(&global_heap, stdout);

    // Test 7: Free all
    if(!block_is_free(bid3)) {
        block_free(bid3);
    } else {
        print_error("Test 7: Block already is free.");
    }

    if(!block_is_free(bid4)) {
        block_free(bid4);
    } else {
        print_error("Test 7: Block already is free.");
    }

    printf("7: ");
    heap_debug_info(&global_heap, stdout);

    return 0;
}
