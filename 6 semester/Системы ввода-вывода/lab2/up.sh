qemu-system-riscv64 \
-machine virt \
-nographic \
-m 5G \
-smp 1 \
-kernel uboot.elf \
-device virtio-rng-pci \
-drive file=ubuntu-20.04.5-preinstalled-server-riscv64+unmatched.img,format=raw,if=virtio \
-device virtio-net-device,netdev=net \
-netdev user,id=net,hostfwd=tcp::2222-:22
