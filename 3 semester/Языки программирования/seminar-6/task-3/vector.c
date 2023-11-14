#include "vector.h"
#include <stdlib.h>
#include <string.h>

struct Vector {
    int64_t* data;
    size_t length;
    size_t capacity;
};

Vector* vector_create() {
    Vector* vec = malloc(sizeof(Vector));
    if (!vec) {
        // Handle memory allocation failure
        exit(EXIT_FAILURE);
    }

    vec->data = NULL;
    vec->length = 0;
    vec->capacity = 0;

    return vec;
}

void vector_destroy(Vector* vec) {
    free(vec->data);
    free(vec);
}

int64_t vector_get(const Vector* vec, size_t index) {
    if (index < vec->length) {
        return vec->data[index];
    } else {
        // Handle index out of bounds (for simplicity, return 0)
        return 0;
    }
}

void vector_set(Vector* vec, size_t index, int64_t value) {
    if (index < vec->length) {
        vec->data[index] = value;
    } else {
        // Handle index out of bounds (for simplicity, do nothing)
    }
}

size_t vector_length(const Vector* vec) {
    return vec->length;
}

size_t vector_capacity(const Vector* vec) {
    return vec->capacity;
}

void vector_push_back(Vector* vec, int64_t value) {
    if (vec->length == vec->capacity) {
        // Double the capacity when needed
        size_t new_capacity = (vec->capacity == 0) ? 1 : vec->capacity * 2;
        vec->data = realloc(vec->data, new_capacity * sizeof(int64_t));
        if (!vec->data) {
            // Handle memory allocation failure
            exit(EXIT_FAILURE);
        }
        vec->capacity = new_capacity;
    }

    // Add the value to the end
    vec->data[vec->length++] = value;
}

void vector_append(Vector* dest, const Vector* src) {
    size_t new_length = dest->length + src->length;
    if (new_length > dest->capacity) {
        // Resize the destination vector if needed
        size_t new_capacity = (new_length > dest->capacity * 2) ? new_length : dest->capacity * 2;
        dest->data = realloc(dest->data, new_capacity * sizeof(int64_t));
        if (!dest->data) {
            // Handle memory allocation failure
            exit(EXIT_FAILURE);
        }
        dest->capacity = new_capacity;
    }

    // Copy the elements from the source vector to the end of the destination vector
    memcpy(dest->data + dest->length, src->data, src->length * sizeof(int64_t));
    dest->length = new_length;
}

void vector_resize(Vector* vec, size_t new_size) {
    if (new_size < vec->length) {
        // If new size is smaller, truncate the vector
        vec->length = new_size;
    } else if (new_size > vec->length && new_size > vec->capacity) {
        // If new size is larger and exceeds capacity, reallocate memory
        vec->data = realloc(vec->data, new_size * sizeof(int64_t));
        if (!vec->data) {
            // Handle memory allocation failure
            exit(EXIT_FAILURE);
        }
        vec->capacity = new_size;
    }

    // If new size is larger, keep the existing elements uninitialized
    vec->length = new_size;
}

void vector_shrink_to_fit(Vector* vec) {
    if (vec->length < vec->capacity) {
        vec->data = realloc(vec->data, vec->length * sizeof(int64_t));
        if (!vec->data) {
            // Handle memory allocation failure
            exit(EXIT_FAILURE);
        }
        vec->capacity = vec->length;
    }
}

void vector_print(FILE* stream, const Vector* vec) {
    for (size_t i = 0; i < vec->length; ++i) {
        fprintf(stream, "%" PRId64 " ", vec->data[i]);
    }
    fprintf(stream, "\n");
}
