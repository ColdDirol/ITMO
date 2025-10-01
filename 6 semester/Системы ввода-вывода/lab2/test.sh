#!/bin/bash

echo "=== Тестирование драйвера var1 ==="
echo

if lsmod | grep -q ch_drv; then
    echo "Модуль уже загружен, выгружаем..."
    sudo rmmod ch_drv
fi

echo "Компиляция модуля..."
make clean
make

if [ $? -ne 0 ]; then
    echo "Ошибка компиляции!"
    exit 1
fi

echo "Загрузка модуля..."
sudo insmod ch_drv.ko

if [ $? -ne 0 ]; then
    echo "Ошибка загрузки модуля!"
    exit 1
fi

echo "Проверка создания устройства /dev/var1..."
ls -l /dev/var1

echo "Тест 1: Запись 'Hello' (5 символов)"
echo -n "Hello" | sudo tee /dev/var1 > /dev/null

echo "Тест 2: Запись 'World!' (6 символов)"
echo -n "World!" | sudo tee /dev/var1 > /dev/null

echo "Тест 3: Запись 'This is a test' (14 символов)"
echo -n "This is a test" | sudo tee /dev/var1 > /dev/null

echo
echo "Чтение последовательности подсчитанных значений:"
sudo cat /dev/var1

echo
echo "Последние сообщения ядра:"
dmesg | tail -20 | grep var1

echo
read -p "Выгрузить модуль? (y/n): " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    sudo rmmod ch_drv
    echo "Модуль выгружен"
fi
