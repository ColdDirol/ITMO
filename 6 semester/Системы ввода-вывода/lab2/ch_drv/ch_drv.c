#include <linux/module.h>
#include <linux/version.h>
#include <linux/kernel.h>
#include <linux/types.h>
#include <linux/kdev_t.h>
#include <linux/fs.h>
#include <linux/device.h>
#include <linux/cdev.h>
#include <linux/slab.h>
#include <linux/uaccess.h>
#include <linux/string.h>

static dev_t first;
static struct cdev c_dev; 
static struct class *cl;

struct count_node {
    size_t count;
    struct count_node *next;
};

static struct count_node *head = NULL;
static struct count_node *tail = NULL;

static void add_count(size_t count)
{
    struct count_node *new_node = kmalloc(sizeof(struct count_node), GFP_KERNEL);
    if (!new_node) {
        printk(KERN_ERR "Failed to allocate memory for count node\n");
        return;
    }
    
    new_node->count = count;
    new_node->next = NULL;
    
    if (!head) {
        head = tail = new_node;
    } else {
        tail->next = new_node;
        tail = new_node;
    }
}

static int my_open(struct inode *i, struct file *f)
{
    printk(KERN_INFO "var1: open()\n");
    return 0;
}

static int my_close(struct inode *i, struct file *f)
{
    printk(KERN_INFO "var1: close()\n");
    return 0;
}

static ssize_t my_read(struct file *f, char __user *buf, size_t len, loff_t *off)
{
    struct count_node *node;
    char *output;
    char temp[32];
    size_t output_len = 0;
    size_t total_len = 0;
    ssize_t ret;
    
    printk(KERN_INFO "var1: read()\n");
    
    if (*off != 0)
        return 0;
    
    node = head;
    while (node) {
        snprintf(temp, sizeof(temp), "%zu\n", node->count);
        total_len += strlen(temp);
        node = node->next;
    }
    
    if (total_len == 0) {
        const char *msg = "No data written yet\n";
        size_t msg_len = strlen(msg);
        
        if (msg_len > len)
            msg_len = len;
            
        if (copy_to_user(buf, msg, msg_len))
            return -EFAULT;
            
        *off = msg_len;
        return msg_len;
    }
    
    output = kmalloc(total_len + 1, GFP_KERNEL);
    if (!output)
        return -ENOMEM;
    
    output[0] = '\0';
    
    node = head;
    while (node) {
        snprintf(temp, sizeof(temp), "%zu\n", node->count);
        strcat(output, temp);
        node = node->next;
    }
    
    output_len = strlen(output);
    if (output_len > len)
        output_len = len;
    
    if (copy_to_user(buf, output, output_len)) {
        kfree(output);
        return -EFAULT;
    }
    
    *off = output_len;
    ret = output_len;
    
    kfree(output);
    return ret;
}

static ssize_t my_write(struct file *f, const char __user *buf, size_t len, loff_t *off)
{
    printk(KERN_INFO "var1: write() - %zu characters\n", len);
    
    add_count(len);
    
    return len;
}

static struct file_operations mychdev_fops =
{
    .owner = THIS_MODULE,
    .open = my_open,
    .release = my_close,
    .read = my_read,
    .write = my_write
};
 
static int __init ch_drv_init(void)
{
    printk(KERN_INFO "var1: Loading module\n");
    
    if (alloc_chrdev_region(&first, 0, 1, "var1_dev") < 0)
    {
        return -1;
    }
    
    if ((cl = class_create(THIS_MODULE, "chardrv")) == NULL)
    {
        unregister_chrdev_region(first, 1);
        return -1;
    }
    
    if (device_create(cl, NULL, first, NULL, "var1") == NULL)
    {
        class_destroy(cl);
        unregister_chrdev_region(first, 1);
        return -1;
    }
    
    cdev_init(&c_dev, &mychdev_fops);
    if (cdev_add(&c_dev, first, 1) == -1)
    {
        device_destroy(cl, first);
        class_destroy(cl);
        unregister_chrdev_region(first, 1);
        return -1;
    }
    
    printk(KERN_INFO "var1: Device created successfully\n");
    return 0;
}
 
static void __exit ch_drv_exit(void)
{
    struct count_node *node = head;
    struct count_node *next;
    
    while (node) {
        next = node->next;
        kfree(node);
        node = next;
    }
    
    cdev_del(&c_dev);
    device_destroy(cl, first);
    class_destroy(cl);
    unregister_chrdev_region(first, 1);
    printk(KERN_INFO "var1: Module unloaded\n");
}
 
module_init(ch_drv_init);
module_exit(ch_drv_exit);
 
MODULE_LICENSE("GPL");
MODULE_AUTHOR("Student");
MODULE_DESCRIPTION("Character device driver for variant 1");
