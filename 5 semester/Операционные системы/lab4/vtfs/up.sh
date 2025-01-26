make
echo "MAKE done"
sudo insmod vtfs.ko
echo "INSMOD done"
sudo mount -t vtfs "TODO" /mnt/vt
echo "MOUNT done"
sudo dmesg | tail
