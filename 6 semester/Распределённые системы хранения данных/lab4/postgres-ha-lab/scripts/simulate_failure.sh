#!/bin/bash

echo "Симуляция отказа мастера..."

# Остановка мастера
echo "Остановка postgres_master..."
docker stop postgres_master

echo "WAIT---Ожидание обнаружения отказа..."
sleep 10

# Проверка логов pgbouncer
echo "Логи PgBouncer:"
docker logs pgbouncer | tail -20

# Попытка подключения через pgbouncer
echo "Попытка подключения через pgbouncer (должна завершиться ошибкой):"
timeout 10 docker exec postgres_client bash -c "PGPASSWORD=postgres123 psql -h pgbouncer -p 5432 -U postgres -d testdb -c \"SELECT NOW();\"" || echo "Ожидаемая ошибка подключения"

echo "Для восстановления выполните: bash scripts/failover.sh"
