#!/bin/bash

echo "=== Тест нагрузки и транзакций ==="

# Установка bc если его нет
command -v bc >/dev/null 2>&1 || { echo "bc не установлен, используем простые вычисления"; BC_AVAILABLE=false; } || BC_AVAILABLE=true

# Функция для выполнения транзакций
run_transaction() {
    local session_id=$1
    local iterations=$2
    
    for ((i=1; i<=iterations; i++)); do
        # Простое вычисление цены без bc
        local price=$(( i * 11 ))
        
        docker exec postgres_client bash -c "PGPASSWORD=postgres123 psql -h pgbouncer -p 5432 -U postgres -d testdb <<EOSQL
BEGIN;
INSERT INTO users (username, email) VALUES ('user_${session_id}_${i}', 'user${session_id}_${i}@test.com');
INSERT INTO orders (user_id, product_name, quantity, price) 
    VALUES (currval('users_id_seq'), 'Product_${session_id}_${i}', ${i}, ${price}.99);
COMMIT;
EOSQL"
        echo "Session $session_id: Transaction $i completed"
        sleep 1
    done
}

# Запуск параллельных сессий
echo "Запуск 3 параллельных клиентских сессий..."
run_transaction 1 5 &
run_transaction 2 5 &
run_transaction 3 5 &

wait
echo "Все транзакции завершены"

# Проверка результатов
echo "=== Результаты после нагрузочного теста ==="
docker exec postgres_client bash -c "PGPASSWORD=postgres123 psql -h pgbouncer -p 5432 -U postgres -d testdb -c \"
SELECT 'Master - Users:' as info, COUNT(*) as count FROM users
UNION ALL
SELECT 'Master - Orders:', COUNT(*) FROM orders;
\""

sleep 3

docker exec postgres_client bash -c "PGPASSWORD=postgres123 psql -h postgres_slave -p 5432 -U postgres -d testdb -c \"
SELECT 'Slave - Users:' as info, COUNT(*) as count FROM users
UNION ALL
SELECT 'Slave - Orders:', COUNT(*) FROM orders;
\""
