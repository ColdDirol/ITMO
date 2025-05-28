#!/bin/bash

echo "=== Тестирование подключений ==="

# Подключение к мастеру через pgbouncer
echo "1. Подключение к мастеру через pgbouncer:"
docker exec postgres_client bash -c "PGPASSWORD=postgres123 psql -h pgbouncer -p 5432 -U postgres -d testdb -c \"SELECT 'Connected to PgBouncer -> ' || version();\""

# Подключение к мастеру напрямую
echo "2. Подключение к мастеру напрямую:"
docker exec postgres_client bash -c "PGPASSWORD=postgres123 psql -h postgres_master -p 5432 -U postgres -d testdb -c \"SELECT 'Master direct: ' || version();\""

# Подключение к слейву для чтения
echo "3. Подключение к слейву (только чтение):"
docker exec postgres_client bash -c "PGPASSWORD=postgres123 psql -h postgres_slave -p 5432 -U postgres -d testdb -c \"SELECT 'Slave connection: ' || version();\""

# Проверка репликации
echo "4. Проверка данных на мастере:"
docker exec postgres_client bash -c "PGPASSWORD=postgres123 psql -h postgres_master -p 5432 -U postgres -d testdb -c \"SELECT COUNT(*) as users_count FROM users;\""

echo "5. Проверка данных на слейве:"
docker exec postgres_client bash -c "PGPASSWORD=postgres123 psql -h postgres_slave -p 5432 -U postgres -d testdb -c \"SELECT COUNT(*) as users_count FROM users;\""
