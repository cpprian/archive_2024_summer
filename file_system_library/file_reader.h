#ifndef FILE_READER_H
#define FILE_READER_H

#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <string.h>
#include <errno.h>

#define BITS_PER_FAT_ENTRY  16
#define BYTES_PER_SECTOR    512


// ========= DISK API =========
struct disk_t {
    FILE* file;
    size_t len;
    size_t num_sectors;
};

struct disk_t* disk_open_from_file(const char* volume_file_name);
int disk_read(struct disk_t* pdisk, int32_t first_sector, void* buffer, int32_t sectors_to_read);
int disk_close(struct disk_t* pdisk);


// ========= FAT API =========
struct boot_record_t {
    uint8_t jmp[3];
    uint8_t oem_name[8];
    uint16_t sector_count;
    uint8_t sectors_per_cluster;
    uint16_t reserved1;
    uint8_t fat_count;
    uint16_t root_dir_entries;
    uint16_t total_sectors;
    uint8_t media_descriptor;
    uint16_t sectors_per_fat;
    uint16_t sectors_per_track;
    uint16_t heads;
    uint32_t hidden_sectors;
    uint32_t total_sectors_large;
    uint8_t drive_number;
    uint8_t reserved2;
    uint8_t boot_signature;
    uint32_t volume_id;
    uint8_t volume_label[11];
    uint8_t fs_type[8];
    uint8_t boot_code[448];
    uint8_t boot_sector_signature[2];
} __attribute__((packed));

struct root_entry_t {
    uint8_t filename[8];
    uint8_t extension[3];
    uint8_t attributes;
    uint8_t reserved1;
    uint8_t creation_time_tenths;
    uint16_t creation_time;
    uint16_t creation_date;
    uint16_t last_access_date;
    uint16_t reserved2;
    uint16_t last_modification_time;
    uint16_t last_modification_date;
    uint16_t first_cluster_low;
    uint32_t file_size;
} __attribute__((packed));

struct volume_t {
    uint16_t* fat;
    uint8_t* root;
    size_t num_root_entries;
    uint32_t fat_start_sector;
    uint32_t bytes_per_cluster;
    uint32_t sectors_per_cluster;
    uint32_t data_start_sector;
    struct disk_t* pdisk;
    struct boot_record_t* boot_record;
    struct root_entry_t** root_entries;
};

struct volume_t* fat_open(struct disk_t* pdisk, uint32_t first_sector);
int fat_close(struct volume_t* pvolume);


// ========= FILE API =========
struct file_t {
    struct volume_t* first_volume;
    struct cluster_t* first_cluster;
    char filename[12];
    uint16_t attributes;
    uint16_t file_size;
    uint16_t head; 
};

struct cluster_t {
    uint32_t cluster_number;
    struct cluster_t* next_cluster;
    uint32_t cluster_size;
};

struct file_t* file_open(struct volume_t* pvolume, const char* file_name);
int file_close(struct file_t* stream);
size_t file_read(void *ptr, size_t size, size_t nmemb, struct file_t *stream);
int32_t file_seek(struct file_t* stream, int32_t offset, int whence);
char* make_filename(struct root_entry_t* root_entry);
void normalize_name(char* name);


// ========= DISK API =========
struct dir_t {
    struct root_entry_t** root_entry;
    struct volume_t* pvolume;
    uint16_t entry_index;
    uint16_t head_cluster;
};

struct dir_entry_t {
    char name[12];
    uint32_t size;
    int is_archive;
    int is_directory;
    int is_readonly;
    int is_hidden;
    int is_system;
} __attribute__((packed));

struct dir_t* dir_open(struct volume_t* pvolume, const char* dir_path);
int dir_read(struct dir_t* pdir, struct dir_entry_t* pentry);
int dir_close(struct dir_t* pdir);

#endif // FILE_READER_H