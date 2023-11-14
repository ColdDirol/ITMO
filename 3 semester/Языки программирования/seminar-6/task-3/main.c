#include "vector.h"

int main() {
    Vector* vec = vector_create();

    // Fill the vector with squares of numbers from 0 to 100
    for (size_t i = 0; i <= 100; ++i) {
        vector_push_back(vec, i);
    }

    // Print the vector
    vector_print(stdout, vec);

    // Destroy
    vector_destroy(vec);

    return 0;
}
