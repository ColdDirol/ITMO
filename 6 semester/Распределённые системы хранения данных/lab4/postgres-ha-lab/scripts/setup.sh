#!/bin/bash

echo "Запуск PostgreSQL HA кластера..."

# Очистка предыдущих данных
echo "Очистка предыдущих данных..."
docker-compose down -v 2>/dev/null || true

# Создание логов директории
mkdir -p logs

# Запуск сервисов
docker-compose up -d

echo "Ожидание готовности сервисов..."
sleep 45

# Проверка состояния
echo "Проверка состояния сервисов:"
docker-compose ps

# Проверка репликации
echo "Проверка репликации:"
docker exec postgres_master psql -U postgres -d testdb -c "SELECT client_addr, state FROM pg_stat_replication;" || echo "Проверка репликации не удалась"

echo "OK---Кластер запущен и готов к работе!"
echo ""
echo "Подключения:"
echo "  Master:    localhost:5432"
echo "  Slave:     localhost:5433"
echo "  PgBouncer: localhost:6432"
echo ""
echo "Для тестирования выполните:"
echo "  cd client && bash test_connections.sh"
echo "  cd client && bash load_test.sh"
