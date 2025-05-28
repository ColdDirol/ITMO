#!/bin/bash

echo "Демонстрация PostgreSQL HA кластера"
echo "======================================"

echo "1---Запуск кластера..."
bash scripts/setup.sh

read -p "Нажмите Enter для продолжения к тестированию подключений..."

echo ""
echo "2---Тестирование подключений..."
cd client
bash test_connections.sh
cd ..

read -p "Нажмите Enter для продолжения к нагрузочному тестированию..."

echo ""
echo "3---Нагрузочное тестирование..."
cd client
bash load_test.sh
cd ..

read -p "Нажмите Enter для продолжения к проверке статуса..."

echo ""
echo "4---Проверка статуса кластера..."
bash scripts/status.sh

read -p "Нажмите Enter для симуляции отказа мастера..."

echo ""
echo "5---Симуляция отказа мастера..."
bash scripts/simulate_failure.sh

read -p "Нажмите Enter для переключения на резервный сервер..."

echo ""
echo "6---Переключение на резервный сервер..."
bash scripts/failover.sh

read -p "Нажмите Enter для проверки работы после failover..."

echo ""
echo "7---Проверка работы после failover..."
bash scripts/status.sh

read -p "Нажмите Enter для восстановления исходной конфигурации..."

echo ""
echo "8---Восстановление исходной конфигурации..."
bash scripts/recovery.sh

read -p "Нажмите Enter для финальной проверки..."

echo ""
echo "9---Финальная проверка..."
bash scripts/status.sh

echo ""
echo "OK---Демонстрация завершена!"
echo "Для просмотра логов: docker-compose logs [service_name]"
echo "Для остановки: docker-compose down"
