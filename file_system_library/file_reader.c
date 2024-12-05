#include "file_reader.h"

struct disk_t* disk_open_from_file(const char* volume_file_name) {
    if (volume_file_name == NULL) {
        return NULL;
    }

    struct disk_t* disk = (struct disk_t*)calloc(1, sizeof(struct disk_t));
    if (disk == NULL) {
        return NULL;
    }

    disk->file = fopen(volume_file_name, "r");
    if (disk->file == NULL) {
        errno = ENOENT;
        free(disk);
        return NULL;
    }

    fseek(disk->file, 0, SEEK_END);
    disk->len = ftell(disk->file);
    disk->num_sectors = disk->len / BYTES_PER_SECTOR;
    fseek(disk->file, 0, SEEK_SET);
    return disk;
}

int disk_read(struct disk_t* pdisk, int32_t first_sector, void* buffer, int32_t sectors_to_read) {
    if (pdisk == NULL || buffer == NULL || first_sector < 0 || sectors_to_read < 0) {
        printf("disk_read1\n");
        errno = EINVAL;
        return -1;
    }

    if (first_sector + sectors_to_read > (int32_t)pdisk->num_sectors) {
        printf("disk_read2\n");
        errno = ERANGE;
        return -1;
    }

    fseek(pdisk->file, first_sector * BYTES_PER_SECTOR, SEEK_SET);
    fread(buffer, BYTES_PER_SECTOR, sectors_to_read, pdisk->file);
    fseek(pdisk->file, 0, SEEK_SET);
    return sectors_to_read;
}

int disk_close(struct disk_t* pdisk) {
    if (pdisk == NULL) {
        return -1;
    }
    fclose(pdisk->file);
    free(pdisk);
    return 0;
}

struct volume_t* fat_open(struct disk_t* pdisk, uint32_t first_sector) {
    unsigned char buffer[BYTES_PER_SECTOR];
    if (disk_read(pdisk, first_sector, buffer, 1) == -1) {
        printf("disk0\n");
        return NULL;
    }

    struct boot_record_t* boot_record = (struct boot_record_t*)buffer;
    if (boot_record->boot_signature != 0x29 &&
        boot_record->boot_sector_signature[0] != 0xAA &&
        boot_record->boot_sector_signature[1] != 0x55)
    {
        printf("boot0\n");
        errno = EINVAL;
        return NULL;
    }

    struct volume_t* volume = (struct volume_t*)calloc(1, sizeof(struct volume_t));
    if (volume == NULL) {
        errno = ENOMEM;
        return NULL;
    }

    uint16_t* fat = (uint16_t*)calloc(boot_record->sectors_per_fat, BYTES_PER_SECTOR);
    if (fat == NULL) {
        printf("fat_open fat\n");
        errno = ENOMEM;
        free(volume);
        return NULL;
    }

    if (disk_read(pdisk, 
                    boot_record->reserved1, 
                    fat, 
                    boot_record->sectors_per_fat) == -1) 
    {
        printf("fat_open disk_read1\n");
        errno = ENOMEM;
        free(volume);
        free(fat);
        return NULL;
    }

    uint8_t* root_dir = (uint8_t*)calloc(boot_record->root_dir_entries, sizeof(struct root_entry_t));
    if (root_dir == NULL) {
        errno = ENOMEM;
        free(volume);
        free(fat);
        return NULL;
    }

    if (disk_read(pdisk, 
                    boot_record->reserved1 + boot_record->fat_count * boot_record->sectors_per_fat, 
                    root_dir, 
                    boot_record->root_dir_entries * sizeof(struct root_entry_t) / BYTES_PER_SECTOR) == -1)
    {
        printf("fat_open disk_read2\n");
        errno = ENOMEM;
        free(volume);
        free(fat);
        free(root_dir);
        return NULL;
    }

    printf("root_dir_entries: %d\n", boot_record->root_dir_entries);
    for (uint16_t i = 0; i < boot_record->root_dir_entries; i++) {
        uint32_t offset = i * sizeof(struct root_entry_t);
        struct root_entry_t* root_entry = (struct root_entry_t*)&root_dir[offset];
        // printf("root_entry->file_name: %s\n", root_entry->filename);

        if (root_entry->filename[0] == 0) {
            break;
        }
        if (root_entry->filename[0] == 0xE5 || root_entry->attributes == 0x0F) {
            continue;
        }

        volume->num_root_entries++;

        struct root_entry_t** new_root_entry = (struct root_entry_t**)realloc(volume->root_entries, 
                                                                            volume->num_root_entries * sizeof(struct root_entry_t*));
        if (new_root_entry == NULL) {
            errno = ENOMEM;
            free(volume);
            free(fat);
            free(root_dir);
            return NULL;
        }
        volume->root_entries = new_root_entry;
        volume->root_entries[volume->num_root_entries - 1] = root_entry;
    }

    volume->fat = fat;
    volume->pdisk = pdisk;
    volume->root = root_dir;
    volume->boot_record = boot_record;
    volume->fat_start_sector = boot_record->reserved1 + boot_record->fat_count * boot_record->sectors_per_fat;
    volume->bytes_per_cluster = boot_record->sectors_per_cluster * BYTES_PER_SECTOR;
    volume->sectors_per_cluster = boot_record->sectors_per_cluster;
    volume->data_start_sector = boot_record->reserved1 + boot_record->hidden_sectors 
                                    + boot_record->fat_count * boot_record->sectors_per_fat 
                                    + boot_record->root_dir_entries / 16;

    return volume;
}

int fat_close(struct volume_t* pvolume) {
    if (pvolume == NULL) {
        errno = EFAULT;
        return -1;
    }

    free(pvolume->fat);
    free(pvolume->root);
    free(pvolume->root_entries);
    free(pvolume);
    return 0;
}

struct file_t* file_open(struct volume_t* pvolume, const char* file_name) {
    if (pvolume == NULL || file_name == NULL) {
        errno = EFAULT;
        return NULL;
    }

    struct root_entry_t* root_entry = NULL;
    for (uint16_t i = 0; i < pvolume->num_root_entries; i++) {
        char* filename = make_filename(pvolume->root_entries[i]);
        if (strcmp(filename, file_name) == 0) {
            printf("found file\n");
            root_entry = (struct root_entry_t*)calloc(1, sizeof(struct root_entry_t));
            if (root_entry == NULL) {
                free((void*)filename);
                errno = ENOMEM;
                return NULL;
            }

            memcpy(root_entry, pvolume->root_entries[i], sizeof(struct root_entry_t));
            free((void*)filename);
            break;
        }
        free((void*)filename);
    }


    if (root_entry == NULL) {
        printf("file_open: file not found\n");
        errno = ENOENT;
        return NULL;
    }

    if ((root_entry->attributes >> 3) & 1 || 
        (root_entry->attributes >> 4) & 1) 
    {
        printf("file_open: file is a directory\n");
        errno = EISDIR;
        free(root_entry);
        return NULL;
    }

    printf("file_open: file found\n");


    struct cluster_t* cluster = (struct cluster_t*)calloc(1, sizeof(struct cluster_t));
    if (cluster == NULL) {
        errno = ENOMEM;
        free(root_entry);
        return NULL;
    }
    cluster->cluster_number = root_entry->first_cluster_low;
    cluster->cluster_size = pvolume->data_start_sector + (root_entry->first_cluster_low - 2) * pvolume->sectors_per_cluster;

    struct file_t* file = (struct file_t*)calloc(1, sizeof(struct file_t));
    if (file == NULL) {
        errno = ENOMEM;
        free(root_entry);
        return NULL;
    }

    memcpy(file->filename, root_entry->filename, sizeof(file->filename));
    file->attributes = root_entry->attributes;
    file->file_size = root_entry->file_size;
    file->head = 0;
    file->first_volume = pvolume;
    file->first_cluster = cluster;

    while (1) {
        size_t next_cluster = pvolume->fat[cluster->cluster_number];
        if (next_cluster >= 0x0FFF8){
            break;
        }

        struct cluster_t* new_cluster = (struct cluster_t*)calloc(1, sizeof(struct cluster_t));
        if (new_cluster == NULL) {
            errno = ENOMEM;
            struct cluster_t* temp;
            while (file->first_cluster != NULL) {
                temp = file->first_cluster;
                file->first_cluster = file->first_cluster->next_cluster;
                free(temp);
            }
            free(root_entry);
            free(file);
            return NULL;
        }

        new_cluster->cluster_number = next_cluster;
        new_cluster->cluster_size = pvolume->data_start_sector + (next_cluster - 2) * pvolume->sectors_per_cluster;

        cluster->next_cluster = new_cluster;
        cluster = new_cluster;
    }

    free(root_entry);
    return file;
}

char* make_filename(struct root_entry_t* root_entry) {
    if (root_entry->filename == NULL || root_entry->extension == NULL) {
        printf("make_filename: root_entry is NULL\n");
        errno = EFAULT;
        return NULL;
    }

    char* filename = (char*)calloc(15, sizeof(char));
    memcpy(filename, root_entry->filename, 8);

    normalize_name(filename);

    if (root_entry->extension[0] != ' ') {
        strcat(filename, ".");

        char* extension = (char*)calloc(3, sizeof(char));
        int j = 0;
        for (int i = 0; i < 3; i++) {
            if (root_entry->extension[i] != ' ') {
                extension[j++] = root_entry->extension[i];
            } else {
                extension[j] = '\0';
            }
        }
        printf("%ld %ld len\n", strlen(filename), strlen(extension));
        strncat(filename, extension, 3);
        int idx = strlen(filename);
        filename[idx] = '\0';
        free((void*)extension);
    }

    return filename;
}

void normalize_name(char* name) {
    if (name == NULL) {
        printf("normalize_name: name is NULL\n");
        errno = EFAULT;
        return;
    }
    size_t len = strlen(name);
    for (size_t i = 0; i < len; i++) {
        if (name[i] == ' ') {
            name[i] = '\0';
        }
    }
}

int file_close(struct file_t* stream) {
    if (stream == NULL) {
        errno = EFAULT;
        return -1;
    }

    struct cluster_t* cluster = stream->first_cluster;
    while (cluster != NULL) {
        struct cluster_t* next_cluster = cluster->next_cluster;
        free(cluster);
        cluster = next_cluster;
    }
    free(stream);
    return 0;
}

size_t file_read(void* ptr, size_t size, size_t nmemb, struct file_t* stream) {
    if (ptr == NULL || stream == NULL) {
        errno = EFAULT;
        return -1;
    } else if (stream->head >= stream->file_size) {
        return 0;
    }

    uint8_t* buf = calloc(stream->first_volume->bytes_per_cluster, sizeof(uint8_t));
    if (buf == NULL) {
        errno = ENOMEM;
        return -1;
    }

    uint32_t bytes_left = size * nmemb;
    uint32_t bytes_to_read = 0;
    uint32_t pos = 0;
    uint32_t pos_cluster = 0;
    struct cluster_t* curr = stream->first_cluster;
    while (curr != NULL && bytes_left > 0) {
        if (disk_read(stream->first_volume->pdisk, curr->cluster_size, buf,
                      stream->first_volume->sectors_per_cluster) == -1) {
            free(buf);
            return -1;
        }

        pos_cluster = 0;
        while (bytes_left > 0 && pos_cluster < stream->first_volume->bytes_per_cluster &&
               pos < stream->file_size) {
            if (pos >= stream->head) {
                ((uint8_t*)ptr)[bytes_to_read] = buf[pos_cluster];

                stream->head++;

                bytes_to_read++;
                bytes_left--;
            }
            pos_cluster++;
            pos++;
        }

        curr = curr->next_cluster;
    }
    free(buf);
    return (size_t)bytes_to_read / size;
}

int32_t file_seek(struct file_t* stream, int32_t offset, int whence) {
    if (stream == NULL) {
        errno = EFAULT;
        return -1;
    }

    if (whence == SEEK_SET) {
        if (offset < 0 || offset > stream->file_size) {
            errno = ENXIO;
            return -1;
        }
        stream->head = offset;
    } else if (whence == SEEK_CUR) {
        if (offset < 0 && stream->head < (uint32_t)abs(offset)) {
            errno = ENXIO;
            return -1;
        }
        stream->head += offset;
    } else if (whence == SEEK_END) {
        if (offset > 0 || stream->file_size < (uint32_t)abs(offset)) {
            errno = ENXIO;
            return -1;
        }
        stream->head = stream->file_size + offset;
    } else {
        errno = EINVAL;
        return -1;
    }

    return 0;
}

struct dir_t* dir_open(struct volume_t* pvolume, const char* dir_path) {
    if (pvolume == NULL || dir_path == NULL) {
        errno = EFAULT;
        return NULL;
    }

    struct dir_t* dir = (struct dir_t*)calloc(1, sizeof(struct dir_t));
    if (dir == NULL) {
        errno = ENOMEM;
        return NULL;
    }
    dir->pvolume = pvolume;
    dir->head_cluster = 0;

    if (strcmp(dir_path, "\\") == 0) {
        printf("dir_open: root dir\n");
        dir->root_entry = pvolume->root_entries;
        dir->entry_index = pvolume->num_root_entries;
        return dir;
    }

    printf("dir_open: dir_path: %s\n", dir_path);
    struct root_entry_t* root_entry = NULL;
    for (uint16_t i = 0; i < pvolume->num_root_entries; i++) {
        const char* filename = make_filename(pvolume->root_entries[i]);
        if (strcmp(filename, dir_path) == 0) {
            printf("found dir\n");
            root_entry = (struct root_entry_t*)calloc(1, sizeof(struct root_entry_t));
            if (root_entry == NULL) {
                free((void*)filename);
                errno = ENOMEM;
                return NULL;
            }
            free((void*)filename);
            memcpy(root_entry, pvolume->root_entries[i], sizeof(struct root_entry_t));
            break;
        }

        free((void*)filename);
    }

    if (root_entry == NULL) {
        printf("dir_open: dir not found\n");
        free(dir);
        errno = ENOENT;
        return NULL;
    }

    if (!((root_entry->attributes >> 4) & 1) ||
        !((root_entry->attributes >> 3) & 1)) 
    {
        printf("dir_open: %s is not a directory\n", dir_path);
        free(root_entry);
        free(dir);
        errno = ENOTDIR;
        return NULL;
    }

    return dir;
}

int dir_read(struct dir_t* pdir, struct dir_entry_t* pentry) {
    if (pdir == NULL || pentry == NULL) {
        errno = EFAULT;
        return -1;
    } else if (pdir->root_entry == NULL) {
        errno = EIO;
        return -1;
    } else if (pdir->head_cluster == pdir->entry_index) {
        return 1;
    }

    struct root_entry_t* root_entry = pdir->root_entry[pdir->head_cluster];
    if (root_entry->filename == NULL) {
        errno = EIO;
        return -1;
    }

    char* filename = make_filename(root_entry);
    memcpy(pentry->name, filename, strlen((const char*)root_entry->filename) + 1);
    free((void*)filename);

    printf("dir_read: name: %s\n", pentry->name);

    pentry->size = root_entry->file_size;
    pentry->is_directory = root_entry->file_size == 0;
    pdir->head_cluster++;
    pentry->is_archive = (root_entry->attributes >> 5) & 1;
    pentry->is_system = (root_entry->attributes >> 2) & 1;
    pentry->is_hidden = (root_entry->attributes >> 1) & 1;
    pentry->is_readonly = (root_entry->attributes >> 0) & 1;

    return 0;
}

int dir_close(struct dir_t* pdir) {
    if (pdir == NULL) {
        errno = EFAULT;
        return -1;
    }

    free(pdir);
    return 0;
}

