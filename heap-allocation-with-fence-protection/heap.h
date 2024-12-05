#ifndef HEAP_H
#define HEAP_H

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define ALIGNMENT 4
#define ALIGN(size) (((size) + (ALIGNMENT-1)) & ~(ALIGNMENT-1))

struct memory_manager_t {
    void* memory_start;
    size_t memory_size;
    struct memory_chunk_t* first_chunk;
};

struct memory_chunk_t {
    size_t size;
    int free;
    struct memory_chunk_t* next;
    struct memory_chunk_t* prev;
    size_t checksum;
} __attribute__((packed));

#define MEMORY_MANAGER_SIZE     sizeof(struct memory_manager_t)
#define MEMORY_CHUNK_SIZE       sizeof(struct memory_chunk_t)
#define FENCEPOST_SIZE          sizeof(size_t)
#define FENCEPOST_VALUE         0

enum pointer_type_t
{
    pointer_null=0,
    pointer_heap_corrupted,
    pointer_control_block,
    pointer_inside_fences,
    pointer_inside_data_block,
    pointer_unallocated,
    pointer_valid
};

enum pointer_type_t get_pointer_type(const void* const pointer);
int is_inside_data_block(const void* const pointer, struct memory_chunk_t* chunk);
int is_inside_fences(const void* const pointer, struct memory_chunk_t* chunk);
int is_inside_control_block(const void* const pointer, struct memory_chunk_t* chunk);
int is_unallocated(const void* const pointer, struct memory_chunk_t* chunk);
int is_allocated(const void* const pointer, struct memory_chunk_t* chunk);
size_t heap_get_largest_used_block_size(void);  

int heap_setup(void);
void heap_clean(void);
int heap_validate(void); 

void* heap_malloc(size_t size);                             
void* heap_calloc(size_t number, size_t size);              
void* heap_realloc(void* memblock, size_t count);               
void  heap_free(void* memblock);

long check_is_space_between_two_chunks(struct memory_chunk_t* chunk);
void merge_chunks(struct memory_chunk_t* chunk);
void* find_free_chunk(size_t size);
void add_fenceposts(struct memory_chunk_t* chunk);
void fill_control_block(struct memory_chunk_t* chunk);
size_t sum_control_block(struct memory_chunk_t* chunk);

void checksum_all_chunks();
#endif // HEAP_H