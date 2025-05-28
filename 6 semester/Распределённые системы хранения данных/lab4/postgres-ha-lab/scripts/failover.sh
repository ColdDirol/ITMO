#!/bin/bash

echo "Выполнение переключения на слейв (используя pg_promote)..."

# Промоция слейва в мастер используя pg_promote
echo "Промоция слейва в мастер..."
docker exec -u postgres postgres_slave bash -c "
# Используем pg_promote для промоции (доступно в PostgreSQL 12+)
/usr/lib/postgresql/*/bin/pg_promote -D /var/lib/postgresql/data -w
"

# Проверка успешности промоции
if [ $? -eq 0 ]; then
    echo "OK---Команда pg_promote выполнена успешно"
else
    echo "ERROR-pg_promote завершилась с ошибкой, пробуем альтернативный метод..."
    
    # Альтернативный метод через pg_ctl promote
    docker exec -u postgres postgres_slave bash -c "
    pg_ctl promote -D /var/lib/postgresql/data -w
    "
fi

# Ожидание завершения промоции
echo "WAIT---Ожидание завершения промоции..."
for i in {1..30}; do
    if docker exec postgres_slave psql -U postgres -d testdb -c "SELECT pg_is_in_recovery();" 2>/dev/null | grep -q " f"; then
        echo "OK---Слейв успешно промотирован в мастер"
        break
    fi
    echo "Ожидание... ($i/30)"
    sleep 1
done

# Финальная проверка
IS_RECOVERY=$(docker exec postgres_slave psql -U postgres -d testdb -t -c "SELECT pg_is_in_recovery();" 2>/dev/null | tr -d ' ')
if [ "$IS_RECOVERY" != "f" ]; then
    echo "NO---ОШИБКА: postgres_slave все еще в режиме recovery"
    echo "Текущий статус recovery: $IS_RECOVERY"
    exit 1
fi

# Обновление конфигурации pgbouncer
echo "Переключение PgBouncer на новый мастер..."
docker stop pgbouncer || true
docker rm pgbouncer || true

docker run -d --name pgbouncer --network postgres-ha-lab_postgres_network --ip 172.20.0.20 -p 6432:5432 \
  -e DATABASE_URL="postgres://postgres:postgres123@postgres_slave:5432/testdb" \
  -e POOL_MODE=transaction -e MAX_CLIENT_CONN=100 -e DEFAULT_POOL_SIZE=20 -e AUTH_TYPE=trust \
  edoburu/pgbouncer:latest

echo "WAIT---Ожидание запуска pgbouncer..."
sleep 5

# Тестирование
echo "Тестирование операций записи на новом мастере:"
docker exec postgres_client bash -c "
PGPASSWORD=postgres123 psql -h pgbouncer -p 5432 -U postgres -d testdb <<EOSQL
-- Информация о сервере
SELECT 'Server info: ' || version();
SELECT 'Recovery status: ' || pg_is_in_recovery();

-- Тест записи
BEGIN;
INSERT INTO users (username, email) VALUES 
    ('failover_test1', 'failover1@test.com'),
    ('failover_test2', 'failover2@test.com');
    
INSERT INTO orders (user_id, product_name, quantity, price) 
    VALUES (currval('users_id_seq'), 'Failover Product', 1, 99.99);
    
SELECT 'Inserted ' || COUNT(*) || ' test records during failover' 
FROM users WHERE username LIKE 'failover_test%';
COMMIT;

-- Итоговая статистика
SELECT 'Total users: ' || COUNT(*) FROM users;
SELECT 'Total orders: ' || COUNT(*) FROM orders;
EOSQL
"

echo ""
echo "OK---Failover завершен успешно!"
echo "Теперь вы можете выполнять load_test.sh и другие операции записи"
