#include <linux/init.h>
#include <linux/module.h>
#include <linux/printk.h>
#include <linux/fs.h>
#include <linux/mount.h>
#include <linux/idr.h>
#include <linux/list.h>
#include <linux/slab.h>
#include <linux/mutex.h>


#define MODULE_NAME "vtfs"

MODULE_LICENSE("GPL");
MODULE_AUTHOR("secs-dev");
MODULE_DESCRIPTION("A simple FS kernel module with RAM storage");

#define LOG(fmt, ...) pr_info("[" MODULE_NAME "]: " fmt, ##__VA_ARGS__)
#define VTFS_ROOT_INO 101

struct vtfs_file_content {
    char *data;
    size_t size;
    size_t allocated;
};

struct vtfs_file_info {
    char name[256];
    ino_t ino;
    ino_t parent_ino;
    bool is_dir;
    struct list_head list;
    struct vtfs_file_content content;
    struct mutex lock;
};

// Глобальный список файлов
static LIST_HEAD(vtfs_files);
static int next_ino = 103; // Начинаем с 103, так как 101 и 102 уже используются
static DEFINE_MUTEX(vtfs_files_lock);

// Прототипы функций
struct dentry* vtfs_mount(struct file_system_type* fs_type, int flags, const char* token, void* data);
void vtfs_kill_sb(struct super_block* sb);
int vtfs_fill_super(struct super_block *sb, void *data, int silent);
struct inode* vtfs_get_inode(struct super_block* sb, const struct inode* dir, umode_t mode, int i_ino);
int vtfs_iterate(struct file* filp, struct dir_context* ctx);
struct dentry* vtfs_lookup(struct inode* parent_inode, struct dentry* child_dentry, unsigned int flag);
int vtfs_create(struct mnt_idmap *idmap, struct inode *parent_inode, struct dentry *child_dentry, umode_t mode, bool b);
int vtfs_unlink(struct inode *parent_inode, struct dentry *child_dentry);
ssize_t vtfs_read(struct file *filp, char __user *buffer, size_t length, loff_t *offset);
ssize_t vtfs_write(struct file *filp, const char __user *buffer, size_t length, loff_t *offset);
int vtfs_mkdir(struct mnt_idmap *idmap, struct inode *parent_inode, struct dentry *child_dentry, umode_t mode);
int vtfs_rmdir(struct inode *parent_inode, struct dentry *child_dentry);
int vtfs_link(struct dentry *old_dentry, struct inode *parent_dir, struct dentry *new_dentry);


struct vtfs_file_info *find_file_info(ino_t ino);
struct vtfs_file_info *find_file_in_dir(const char *name, ino_t parent_ino);


struct inode_operations vtfs_inode_ops = {
    .lookup = vtfs_lookup,
    .create = vtfs_create,
    .unlink = vtfs_unlink,
    .mkdir = vtfs_mkdir,
    .rmdir = vtfs_rmdir,
    .link = vtfs_link,
};

struct file_operations vtfs_dir_ops = {
    .iterate_shared = vtfs_iterate,
};

struct file_operations vtfs_file_ops = {
    .read = vtfs_read,
    .write = vtfs_write,
};

// статичные переменные
// static int mask = 0;

struct file_system_type vtfs_fs_type = {
    .name = "vtfs",
    .mount = vtfs_mount,
    .kill_sb = vtfs_kill_sb,
};

ssize_t vtfs_read(struct file *filp, char __user *buffer, size_t length, loff_t *offset) {
    struct inode *inode = filp->f_inode;
    struct vtfs_file_info *file_info = find_file_info(inode->i_ino);
    ssize_t ret = -ENOENT;

    if (!file_info) {
        return ret;
    }

    mutex_lock(&file_info->lock);
    
    // Проверяем, не вышли ли мы за пределы файла
    if (*offset >= file_info->content.size) {
        mutex_unlock(&file_info->lock);
        return 0; // EOF
    }

    // Вычисляем, сколько байт мы можем прочитать
    length = min(length, (size_t)(file_info->content.size - *offset));
    
    // Копируем данные в буфер пользователя
    if (copy_to_user(buffer, file_info->content.data + *offset, length)) {
        mutex_unlock(&file_info->lock);
        return -EFAULT;
    }

    *offset += length;
    ret = length;
    
    mutex_unlock(&file_info->lock);
    return ret;
}

ssize_t vtfs_write(struct file *filp, const char __user *buffer, size_t length, loff_t *offset) {
    struct inode *inode = filp->f_inode;
    struct vtfs_file_info *file_info = find_file_info(inode->i_ino);
    ssize_t ret = -ENOENT;

    if (!file_info) {
        return ret;
    }

    mutex_lock(&file_info->lock);

    // Если пишем в начало файла (*offset == 0), очищаем существующее содержимое
    if (*offset == 0) {
        file_info->content.size = 0;
        if (file_info->content.data) {
            memset(file_info->content.data, 0, file_info->content.allocated);
        }
    }
    
    // Проверяем, нужно ли увеличить буфер
    if (*offset + length > file_info->content.allocated) {
        size_t new_size = max(*offset + length, file_info->content.allocated * 2);
        if (new_size == 0) new_size = PAGE_SIZE;
        
        char *new_data = krealloc(file_info->content.data, new_size, GFP_KERNEL);
        if (!new_data) {
            mutex_unlock(&file_info->lock);
            return -ENOMEM;
        }
        
        // Очищаем новую память
        if (new_size > file_info->content.allocated) {
            memset(new_data + file_info->content.allocated, 0, 
                   new_size - file_info->content.allocated);
        }
        
        file_info->content.data = new_data;
        file_info->content.allocated = new_size;
    }

    // Проверяем корректность ASCII символов
    char *tmp_buffer = kmalloc(length, GFP_KERNEL);
    if (!tmp_buffer) {
        mutex_unlock(&file_info->lock);
        return -ENOMEM;
    }

    if (copy_from_user(tmp_buffer, buffer, length)) {
        kfree(tmp_buffer);
        mutex_unlock(&file_info->lock);
        return -EFAULT;
    }

    // Проверяем, что все символы ASCII (0-127)
    for (size_t i = 0; i < length; i++) {
        if ((unsigned char)tmp_buffer[i] > 127) {
            kfree(tmp_buffer);
            mutex_unlock(&file_info->lock);
            return -EINVAL;
        }
    }

    // Копируем проверенные данные
    memcpy(file_info->content.data + *offset, tmp_buffer, length);
    kfree(tmp_buffer);

    if (*offset + length > file_info->content.size) {
        file_info->content.size = *offset + length;
    }

    *offset += length;
    ret = length;
    
    // Обновляем время модификации файла
    inode->__i_mtime = current_time(inode);
    
    mutex_unlock(&file_info->lock);
    return ret;
}

struct dentry* vtfs_mount(struct file_system_type* fs_type, int flags, const char* token, void* data) {
    struct dentry* ret = mount_nodev(fs_type, flags, data, vtfs_fill_super);
    if (ret == NULL) {
        printk(KERN_ERR "Can't mount file system");
    } else {
        printk(KERN_INFO "Mounted successfully");
    }
    return ret;
}

struct inode* vtfs_get_inode(struct super_block* sb, const struct inode* dir, umode_t mode, int i_ino) {
    struct inode *inode = new_inode(sb);
    if (inode != NULL) {
        inode->i_mode = mode;
        i_uid_write(inode, 0);
        i_gid_write(inode, 0);
        inode->i_ino = i_ino;
        
        inode->__i_atime = inode->__i_mtime = inode->__i_ctime = current_time(inode);
    }
    return inode;
}

struct dentry *vtfs_lookup(struct inode *parent_inode,
                           struct dentry *child_dentry,
                           unsigned int flag) {
    const char *name = child_dentry->d_name.name;
    struct vtfs_file_info *file_info;

    file_info = find_file_in_dir(name, parent_inode->i_ino);
    if (file_info) {
        struct inode *inode = vtfs_get_inode(
            parent_inode->i_sb,
            NULL,
            file_info->is_dir ? S_IFDIR : S_IFREG | S_IRWXU | S_IRWXG | S_IRWXO,
            file_info->ino
        );

        if (inode) {
            inode->i_op = &vtfs_inode_ops;
            inode->i_fop = file_info->is_dir ? &vtfs_dir_ops : &vtfs_file_ops;
            d_add(child_dentry, inode);
        }
    }
    return NULL;
}

struct vtfs_file_info *find_file_info(ino_t ino) {
    struct vtfs_file_info *file_info;
    
    list_for_each_entry(file_info, &vtfs_files, list) {
        if (file_info->ino == ino) {
            return file_info;
        }
    }
    return NULL;
}

struct vtfs_file_info *find_file_in_dir(const char *name, ino_t parent_ino) {
    struct vtfs_file_info *file_info;

    list_for_each_entry(file_info, &vtfs_files, list) {
        if (file_info->parent_ino == parent_ino && strcmp(file_info->name, name) == 0) {
            return file_info;
        }
    }
    return NULL;
}

int vtfs_iterate(struct file *filp, struct dir_context *ctx) {
    struct dentry *dentry = filp->f_path.dentry;
    struct inode *inode = dentry->d_inode;
    ino_t current_ino = inode->i_ino;
    int pos = ctx->pos;
    
    if (pos < 0)
        return 0;

    if (pos == 0) {
        if (!dir_emit(ctx, ".", 1, current_ino, DT_DIR))
            return 0;
        ctx->pos++;
        return 1;
    }

    if (pos == 1) {
        struct vtfs_file_info *current_dir = find_file_info(current_ino);
        ino_t parent_ino = current_dir ? current_dir->parent_ino : VTFS_ROOT_INO;
        if (!dir_emit(ctx, "..", 2, parent_ino, DT_DIR))
            return 0;
        ctx->pos++;
        return 1;
    }

    struct vtfs_file_info *file_info;
    int count = 2;
    
    list_for_each_entry(file_info, &vtfs_files, list) {
        if (file_info->parent_ino == current_ino) {
            if (count == pos) {
                unsigned char type = file_info->is_dir ? DT_DIR : DT_REG;
                if (!dir_emit(ctx, file_info->name, strlen(file_info->name),
                            file_info->ino, type))
                    return 0;
                ctx->pos++;
                return 1;
            }
            count++;
        }
    }
    
    return 0;
}



int vtfs_fill_super(struct super_block *sb, void *data, int silent) {
    struct inode* inode = vtfs_get_inode(sb, NULL, S_IFDIR | S_IRWXU | S_IRWXG | S_IRWXO, 100);
    if (inode == NULL) {
        return -ENOMEM;
    }

    inode->i_op = &vtfs_inode_ops;
    inode->i_fop = &vtfs_dir_ops;
    sb->s_root = d_make_root(inode);
    if (sb->s_root == NULL) {
        return -ENOMEM;
    }
    printk(KERN_INFO "return 0\n");
    return 0;
}

void vtfs_kill_sb(struct super_block* sb) {
    struct vtfs_file_info *file_info, *tmp;
    
    list_for_each_entry_safe(file_info, tmp, &vtfs_files, list) {
        if (file_info->content.data) {
            kfree(file_info->content.data);
        }
        list_del(&file_info->list);
        kfree(file_info);
    }
    
    kill_litter_super(sb);
    printk(KERN_INFO "vtfs super block is destroyed. Unmount successfully.\n");
}

int vtfs_create(struct mnt_idmap *idmap, struct inode *parent_inode, 
                struct dentry *child_dentry, umode_t mode, bool b) {
    struct vtfs_file_info *new_file_info = kmalloc(sizeof(*new_file_info), GFP_KERNEL);
    if (!new_file_info) {
        return -ENOMEM;
    }

    new_file_info->ino = next_ino++;
    new_file_info->content.data = NULL;
    new_file_info->content.size = 0;
    new_file_info->content.allocated = 0;
    new_file_info->is_dir = false;
    new_file_info->parent_ino = parent_inode->i_ino;
    mutex_init(&new_file_info->lock);
    
    snprintf(new_file_info->name, sizeof(new_file_info->name), "%s", child_dentry->d_name.name);
    
    list_add(&new_file_info->list, &vtfs_files);
    
    struct inode *inode = vtfs_get_inode(parent_inode->i_sb, NULL, 
                                       S_IFREG | S_IRWXU | S_IRWXG | S_IRWXO,
                                       new_file_info->ino);
    if (!inode) {
        list_del(&new_file_info->list);
        kfree(new_file_info);
        return -ENOMEM;
    }

    inode->i_op = &vtfs_inode_ops;
    inode->i_fop = &vtfs_file_ops;
    d_add(child_dentry, inode);
    
    return 0;
}


int vtfs_unlink(struct inode *parent_inode, struct dentry *child_dentry) {
    const char *name = child_dentry->d_name.name;
    struct vtfs_file_info *file_info, *tmp;
    
    list_for_each_entry_safe(file_info, tmp, &vtfs_files, list) {
        if (!strcmp(name, file_info->name)) {
            if (file_info->content.data) {
                kfree(file_info->content.data);
            }
            list_del(&file_info->list);
            kfree(file_info);
            break;
        }
    }
    
    return simple_unlink(parent_inode, child_dentry);
}

int vtfs_mkdir(struct mnt_idmap *idmap, struct inode *parent_inode,
               struct dentry *child_dentry, umode_t mode) {
    struct inode *inode;
    ino_t parent_ino = parent_inode->i_ino;
    const char *name = child_dentry->d_name.name;
    
    // Проверяем, не существует ли уже директория с таким именем
    if (find_file_in_dir(name, parent_ino))
        return -EEXIST;
    
    mutex_lock(&vtfs_files_lock);
    
    inode = vtfs_get_inode(parent_inode->i_sb, NULL,
                          S_IFDIR | mode, next_ino++);
    
    if (!inode) {
        mutex_unlock(&vtfs_files_lock);
        return -ENOMEM;
    }
    
    struct vtfs_file_info *dir_info = kmalloc(sizeof(*dir_info), GFP_KERNEL);
    if (!dir_info) {
        iput(inode);
        mutex_unlock(&vtfs_files_lock);
        return -ENOMEM;
    }
    
    // Инициализация информации о директории
    memset(dir_info, 0, sizeof(*dir_info));
    strncpy(dir_info->name, name, 255);
    dir_info->name[255] = '\0';
    dir_info->ino = inode->i_ino;
    dir_info->parent_ino = parent_ino;
    dir_info->is_dir = true;
    mutex_init(&dir_info->lock);
    
    list_add(&dir_info->list, &vtfs_files);
    
    inode->i_op = &vtfs_inode_ops;
    inode->i_fop = &vtfs_dir_ops;
    d_add(child_dentry, inode);
    
    mutex_unlock(&vtfs_files_lock);
    return 0;
}

int vtfs_rmdir(struct inode *parent_inode, struct dentry *child_dentry) {
    const char *name = child_dentry->d_name.name;
    struct vtfs_file_info *dir_info, *tmp;
    struct inode *dir_inode = d_inode(child_dentry);
    
    // Проверяем, что директория пуста
    if (!simple_empty(child_dentry))
        return -ENOTEMPTY;
    
    // Находим и удаляем информацию о директории
    list_for_each_entry_safe(dir_info, tmp, &vtfs_files, list) {
        if (!strcmp(name, dir_info->name) && dir_info->ino == dir_inode->i_ino) {
            list_del(&dir_info->list);
            kfree(dir_info);
            break;
        }
    }
    
    // Удаляем саму директорию
    return simple_rmdir(parent_inode, child_dentry);
}

int vtfs_link(struct dentry *old_dentry, struct inode *parent_dir, struct dentry *new_dentry) {
    struct inode *old_inode = old_dentry->d_inode;
    struct vtfs_file_info *old_file_info;
    struct vtfs_file_info *new_file_info;

    if (!S_ISREG(old_inode->i_mode))
        return -EPERM;

    mutex_lock(&vtfs_files_lock);

    // Ищем информацию о старом файле
    old_file_info = find_file_info(old_inode->i_ino);
    if (!old_file_info) {
        mutex_unlock(&vtfs_files_lock);
        return -ENOENT;
    }

    // Проверяем, нет ли уже файла с таким именем в целевой директории
    if (find_file_in_dir(new_dentry->d_name.name, parent_dir->i_ino)) {
        mutex_unlock(&vtfs_files_lock);
        return -EEXIST;
    }

    // Создаем новую запись для жесткой ссылки
    new_file_info = kzalloc(sizeof(*new_file_info), GFP_KERNEL);
    if (!new_file_info) {
        mutex_unlock(&vtfs_files_lock);
        return -ENOMEM;
    }

    // Копируем информацию
    strncpy(new_file_info->name, new_dentry->d_name.name, sizeof(new_file_info->name) - 1);
    new_file_info->ino = old_file_info->ino; // Используем тот же inode
    new_file_info->parent_ino = parent_dir->i_ino;
    new_file_info->is_dir = false; // Жесткие ссылки для директорий запрещены
    new_file_info->content = old_file_info->content; // Ссылаемся на тот же контент
    mutex_init(&new_file_info->lock);

    list_add(&new_file_info->list, &vtfs_files);

    // Увеличиваем счетчик ссылок у старого inode
    ihold(old_inode);

    mutex_unlock(&vtfs_files_lock);

    // Создаем новый inode
    struct inode *new_inode = vtfs_get_inode(parent_dir->i_sb, NULL, old_inode->i_mode, new_file_info->ino);
    if (!new_inode)
        return -ENOMEM;

    d_instantiate(new_dentry, new_inode);
    return 0;
}


static int __init vtfs_init(void) {
    int ret = register_filesystem(&vtfs_fs_type);
    if (ret == 0) {
        LOG("VTFS joined the kernel\n");
    } else {
        LOG("Failed to register filesystem\n");
    }
    return ret;
}

static void __exit vtfs_exit(void) {
    unregister_filesystem(&vtfs_fs_type);
    LOG("VTFS left the kernel\n");
}

module_init(vtfs_init);
module_exit(vtfs_exit);