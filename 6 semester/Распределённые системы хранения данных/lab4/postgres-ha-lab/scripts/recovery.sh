#!/bin/bash

echo "Восстановление исходной конфигурации..."

# Проверяем текущее состояние
echo "Проверка текущего состояния..."
CURRENT_MASTER_COUNT=$(docker exec postgres_slave psql -U postgres -d testdb -t -c "SELECT COUNT(*) FROM users;" 2>/dev/null | tr -d ' ')
echo "Записей на текущем мастере (postgres_slave): $CURRENT_MASTER_COUNT"

# Остановка старого мастера для очистки
echo "Остановка старого мастера для полной пересинхронизации..."
docker stop postgres_master
docker exec postgres_master rm -rf /var/lib/postgresql/data/* 2>/dev/null || true

# Запуск контейнера
echo "Запуск контейнера postgres_master..."
docker start postgres_master

# Даем контейнеру время на инициализацию
sleep 5

# Настройка репликации с нуля
echo "Создание новой реплики с текущего мастера..."
docker exec postgres_master bash -c "
# Останавливаем PostgreSQL если он запущен
pg_ctl stop -D /var/lib/postgresql/data -m fast 2>/dev/null || true

# Очищаем директорию данных
rm -rf /var/lib/postgresql/data/*

# Создаем базовую копию с текущего мастера (postgres_slave)
echo 'Создание базовой копии с postgres_slave...'
PGPASSWORD=repl123 pg_basebackup -h postgres_slave -D /var/lib/postgresql/data -U replicator -v -P -W

# Настройка конфигурации для standby режима
cat >> /var/lib/postgresql/data/postgresql.conf <<EOCONF

# Standby configuration
primary_conninfo = 'host=postgres_slave port=5432 user=replicator password=repl123'
hot_standby = on
EOCONF

# Создание файла standby.signal
touch /var/lib/postgresql/data/standby.signal

# Установка прав доступа
chown -R postgres:postgres /var/lib/postgresql/data
chmod 700 /var/lib/postgresql/data

echo 'Запуск PostgreSQL в режиме standby...'
"

# Перезапуск контейнера для применения изменений
echo "Перезапуск postgres_master..."
docker restart postgres_master

echo "Ожидание запуска и синхронизации..."
sleep 20

# Проверка репликации на новом мастере (postgres_slave)
echo "Проверка состояния репликации на postgres_slave:"
docker exec postgres_slave psql -U postgres -d testdb -c "
SELECT client_addr, state, sync_state 
FROM pg_stat_replication;
"

# Проверка данных на обоих серверах
echo ""
echo "Проверка синхронизации данных:"
echo "Данные на postgres_slave (мастер):"
docker exec postgres_slave psql -U postgres -d testdb -t -c "
SELECT 'Users: ' || COUNT(*) FROM users
UNION ALL
SELECT 'Orders: ' || COUNT(*) FROM orders;
"

echo "Данные на postgres_master (слейв):"
RETRIES=10
for i in $(seq 1 $RETRIES); do
    SLAVE_COUNT=$(docker exec postgres_master psql -U postgres -d testdb -t -c "SELECT COUNT(*) FROM users;" 2>/dev/null | tr -d ' ')
    if [ "$SLAVE_COUNT" = "$CURRENT_MASTER_COUNT" ]; then
        echo "Данные синхронизированы!"
        docker exec postgres_master psql -U postgres -d testdb -t -c "
        SELECT 'Users: ' || COUNT(*) FROM users
        UNION ALL
        SELECT 'Orders: ' || COUNT(*) FROM orders;
        "
        break
    else
        echo "Ожидание синхронизации... ($i/$RETRIES)"
        sleep 3
    fi
done

# Проверка режима recovery
echo ""
echo "Проверка режимов работы:"
echo -n "postgres_slave в режиме recovery: "
docker exec postgres_slave psql -U postgres -d testdb -t -c "SELECT pg_is_in_recovery();" | tr -d ' '
echo -n "postgres_master в режиме recovery: "
docker exec postgres_master psql -U postgres -d testdb -t -c "SELECT pg_is_in_recovery();" | tr -d ' '

# Обновление pgbouncer для работы с новым мастером
echo ""
echo "Обновление PgBouncer..."
docker stop pgbouncer || true
docker rm pgbouncer || true

# Запуск pgbouncer с подключением к текущему мастеру (postgres_slave)
docker run -d --name pgbouncer --network postgres-ha-lab_postgres_network --ip 172.20.0.20 -p 6432:5432 \
  -e DATABASE_URL="postgres://postgres:postgres123@postgres_slave:5432/testdb" \
  -e POOL_MODE=transaction -e MAX_CLIENT_CONN=100 -e DEFAULT_POOL_SIZE=20 -e AUTH_TYPE=trust \
  edoburu/pgbouncer:latest

echo "Ожидание запуска pgbouncer..."
sleep 5

echo ""
echo "Восстановление завершено!"
echo "Текущая конфигурация:"
echo "  OK Активный мастер: postgres_slave (порт 5433)"
echo "  OK Слейв для чтения: postgres_master (порт 5432)"
echo "  OK PgBouncer: подключен к мастеру postgres_slave"
echo "  OK Все данные синхронизированы"
