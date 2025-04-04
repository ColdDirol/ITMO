#include <stdio.h>
#include <stdlib.h>

#define DEFINE_LIST(type, format)                                                   \
    struct list_##type##_node {                                                     \
        type data;                                                                  \
        struct list_##type##_node *next;                                            \
    };                                                                              \
    struct list_##type {                                                            \
        struct list_##type##_node *head;                                            \
    };                                                                              \
                                                                                    \
    void list_##type##_push(struct list_##type *list, type data) {                  \
        struct list_##type##_node *new_node =                                       \
            (struct list_##type##_node *)malloc(sizeof(struct list_##type##_node)); \
        if (!new_node) {                                                            \
            fprintf(stderr, "Memory allocation failed.\n");                         \
            exit(1);                                                                \
        }                                                                           \
        new_node->data = data;                                                      \
        new_node->next = NULL;                                                      \
        if (list->head == NULL) {                                                   \
            list->head = new_node;                                                  \
        } else {                                                                    \
            struct list_##type##_node *current = list->head;                        \
            while (current->next != NULL) {                                         \
                current = current->next;                                            \
            }                                                                       \
            current->next = new_node;                                               \
        }                                                                           \
    }                                                                               \
                                                                                    \
    void list_##type##_print(struct list_##type *list) {                            \
        struct list_##type##_node *current = list->head;                            \
        while (current != NULL) {                                                   \
            printf(format, current->data);                                          \
            current = current->next;                                       \
        }                                                                  \
        printf("\n");                                                      \
    }

// Define the int, float, and char lists
DEFINE_LIST(int, "%d ")
DEFINE_LIST(float, "%f ")
DEFINE_LIST(char, "%c ")

int main() {
    // Create and populate int list
    struct list_int intList = {NULL};
    list_int_push(&intList, 1);
    list_int_push(&intList, 2);
    list_int_push(&intList, 3);
    list_int_push(&intList, 4);
    printf("Int List: ");
    list_int_print(&intList);

    // Create and populate float list
    struct list_float floatList = {NULL};
    list_float_push(&floatList, 1.1);
    list_float_push(&floatList, 2.2);
    list_float_push(&floatList, 3.3);
    printf("Float List: ");
    list_float_print(&floatList);

    // Create and populate char list
    struct list_char charList = {NULL};
    list_char_push(&charList, 'A');
    list_char_push(&charList, 'B');
    list_char_push(&charList, 'C');
    printf("Char List: ");
    list_char_print(&charList);


    // Lets to free memory!
    struct list_int_node *current_int = intList.head;
    while (current_int != NULL) {
        struct list_int_node *temp_int = current_int;
        current_int = current_int->next;
        free(temp_int);
    }
    printf("\n* int list memory has been free!");

    struct list_float_node *current_float = floatList.head;
    while (current_float != NULL) {
        struct list_float_node *temp_float = current_float;
        current_float = current_float->next;
        free(temp_float);
    }
    printf("\n* float list memory has been free!");

    struct list_char_node *current_char = charList.head;
    while (current_char != NULL) {
        struct list_char_node *temp_char = current_char;
        current_char = current_char->next;
        free(temp_char);
    }
    printf("\n* char list memory has been free!");


    return 0;
}
