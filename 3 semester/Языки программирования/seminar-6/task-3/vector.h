#ifndef VECTOR_H
#define VECTOR_H

#include <inttypes.h>
#include <stddef.h>
#include <stdio.h>

// Opaque structure for vector
typedef struct Vector Vector;

// Function to create a new vector
Vector* vector_create();

// Function to destroy a vector
void vector_destroy(Vector* vec);

// Getter function to access an element at a specific index
int64_t vector_get(const Vector* vec, size_t index);

// Setter function to set the value of an element at a specific index
void vector_set(Vector* vec, size_t index, int64_t value);

// Function to get the current length of the vector
size_t vector_length(const Vector* vec);

// Function to get the capacity of the vector
size_t vector_capacity(const Vector* vec);

// Function to add an element to the end of the vector
void vector_push_back(Vector* vec, int64_t value);

// Function to append the contents of another vector to the end of the vector
void vector_append(Vector* dest, const Vector* src);

// Function to resize the vector to a specified size
void vector_resize(Vector* vec, size_t new_size);

// Function to shrink the vector's capacity to match its length
void vector_shrink_to_fit(Vector* vec);

// Function to print the vector to a specified file stream
void vector_print(FILE* stream, const Vector* vec);

#endif // VECTOR_H
