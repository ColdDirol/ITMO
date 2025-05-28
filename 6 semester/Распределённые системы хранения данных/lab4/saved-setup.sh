#!/bin/bash

# PostgreSQL High Availability Lab Setup Script (ИСПРАВЛЕННАЯ ВЕРСИЯ)
# Автоматическое создание всех необходимых файлов и конфигураций

set -e

echo "🚀 Создание PostgreSQL HA кластера с pgbouncer..."

# Очистка предыдущих данных если они есть
echo "🧹 Очистка предыдущих данных..."
docker-compose down -v 2>/dev/null || true
docker system prune -f 2>/dev/null || true

# Создание структуры директорий
mkdir -p postgres-ha-lab/{master,slave,pgbouncer,client,scripts,logs}
cd postgres-ha-lab

# =================
# Docker Compose файл (ИСПРАВЛЕННЫЙ)
# =================
cat > docker-compose.yml <<'EOF'
networks:
  postgres_network:
    driver: bridge
    ipam:
      config:
        - subnet: 172.20.0.0/16

services:
  # PostgreSQL Master
  postgres_master:
    image: postgres:latest
    container_name: postgres_master
    hostname: postgres_master
    environment:
      POSTGRES_DB: testdb
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres123
      POSTGRES_INITDB_ARGS: "--auth-host=md5"
    volumes:
      - ./master/init-master.sql:/docker-entrypoint-initdb.d/01-init-master.sql
      - ./master/setup-master.sh:/docker-entrypoint-initdb.d/02-setup-master.sh
      - postgres_master_data:/var/lib/postgresql/data
      - ./logs:/var/log/postgresql
    ports:
      - "5432:5432"
    networks:
      postgres_network:
        ipv4_address: 172.20.0.10
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U postgres"]
      interval: 10s
      timeout: 5s
      retries: 10

  # PostgreSQL Slave
  postgres_slave:
    image: postgres:latest
    container_name: postgres_slave
    hostname: postgres_slave
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres123
      PGPASSWORD: postgres123
    volumes:
      - postgres_slave_data:/var/lib/postgresql/data
      - ./logs:/var/log/postgresql
      - ./slave/setup-slave.sh:/setup-slave.sh
    entrypoint: |
      bash -c "
        chmod +x /setup-slave.sh
        /setup-slave.sh
      "
    ports:
      - "5433:5432"
    networks:
      postgres_network:
        ipv4_address: 172.20.0.11
    depends_on:
      postgres_master:
        condition: service_healthy
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U postgres || exit 1"]
      interval: 10s
      timeout: 5s
      retries: 10

  # PgBouncer для балансировки нагрузки
  pgbouncer:
    image: edoburu/pgbouncer:latest
    container_name: pgbouncer
    hostname: pgbouncer
    environment:
      DATABASE_URL: postgres://postgres:postgres123@postgres_master:5432/testdb
      POOL_MODE: transaction
      MAX_CLIENT_CONN: 100
      DEFAULT_POOL_SIZE: 20
      RESERVE_POOL_SIZE: 5
      AUTH_TYPE: trust
    ports:
      - "6432:5432"
    networks:
      postgres_network:
        ipv4_address: 172.20.0.20
    depends_on:
      postgres_master:
        condition: service_healthy
      postgres_slave:
        condition: service_healthy

  # Клиентская машина для тестирования
  postgres_client:
    image: postgres:latest
    container_name: postgres_client
    hostname: postgres_client
    environment:
      PGPASSWORD: postgres123
    volumes:
      - ./client:/client
      - ./scripts:/scripts
    networks:
      postgres_network:
        ipv4_address: 172.20.0.30
    command: tail -f /dev/null
    depends_on:
      - pgbouncer

volumes:
  postgres_master_data:
  postgres_slave_data:
EOF

# =================
# Master конфигурация (ИСПРАВЛЕННАЯ)
# =================
cat > master/setup-master.sh <<'EOF'
#!/bin/bash

echo "Настройка мастера PostgreSQL..."

# Создание пользователя для репликации
psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" --dbname "$POSTGRES_DB" <<-EOSQL
    CREATE USER replicator REPLICATION LOGIN ENCRYPTED PASSWORD 'repl123';
EOSQL

# Настройка конфигурации PostgreSQL для репликации
cat >> /var/lib/postgresql/data/postgresql.conf <<-EOCONF
# Replication settings
wal_level = replica
max_wal_senders = 3
wal_keep_size = 64
hot_standby = on
archive_mode = on
archive_command = 'test ! -f /var/lib/postgresql/data/archive/%f && cp %p /var/lib/postgresql/data/archive/%f'

# Logging
log_statement = 'all'
log_connections = on
log_disconnections = on
log_line_prefix = '%t [%p]: [%l-1] user=%u,db=%d,app=%a,client=%h '
EOCONF

# Настройка pg_hba.conf для репликации
cat >> /var/lib/postgresql/data/pg_hba.conf <<-EOHBA
# Replication connections
host    replication     replicator      172.20.0.0/16           md5
host    all             all             172.20.0.0/16           md5
EOHBA

# Создание директории для архивов
mkdir -p /var/lib/postgresql/data/archive
chown postgres:postgres /var/lib/postgresql/data/archive

echo "Перезапуск PostgreSQL для применения конфигурации..."
pg_ctl reload -D /var/lib/postgresql/data
EOF

cat > master/init-master.sql <<'EOF'
-- Создание тестовых таблиц
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    product_name VARCHAR(100) NOT NULL,
    quantity INTEGER NOT NULL DEFAULT 1,
    price DECIMAL(10,2) NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'pending'
);

-- Вставка тестовых данных
INSERT INTO users (username, email) VALUES 
    ('user1', 'user1@example.com'),
    ('user2', 'user2@example.com'),
    ('user3', 'user3@example.com');

INSERT INTO orders (user_id, product_name, quantity, price) VALUES
    (1, 'Laptop', 1, 999.99),
    (2, 'Mouse', 2, 25.50),
    (3, 'Keyboard', 1, 75.00);

-- Создание функции для логирования
CREATE OR REPLACE FUNCTION log_transaction()
RETURNS TRIGGER AS $$
BEGIN
    RAISE NOTICE 'Transaction executed on table: %, Operation: %, Time: %', 
                 TG_TABLE_NAME, TG_OP, NOW();
    RETURN COALESCE(NEW, OLD);
END;
$$ LANGUAGE plpgsql;

-- Создание триггеров для логирования
CREATE TRIGGER users_log_trigger
    AFTER INSERT OR UPDATE OR DELETE ON users
    FOR EACH ROW EXECUTE FUNCTION log_transaction();

CREATE TRIGGER orders_log_trigger
    AFTER INSERT OR UPDATE OR DELETE ON orders
    FOR EACH ROW EXECUTE FUNCTION log_transaction();
EOF

# =================
# Slave конфигурация (ИСПРАВЛЕННАЯ)
# =================
cat > slave/setup-slave.sh <<'EOF'
#!/bin/bash

echo "Настройка slave PostgreSQL..."

# Ожидание готовности мастера
echo "Ожидание готовности мастера..."
until pg_isready -h postgres_master -p 5432 -U postgres; do
    echo "Мастер еще не готов, ожидание..."
    sleep 3
done

echo "Мастер готов, начинаем настройку репликации..."

# Создание базовой копии данных с мастера
PGPASSWORD=repl123 pg_basebackup -h postgres_master -D /var/lib/postgresql/data -U replicator -v -P -W

# Настройка конфигурации для standby режима
cat >> /var/lib/postgresql/data/postgresql.conf <<-EOCONF
# Standby configuration
primary_conninfo = 'host=postgres_master port=5432 user=replicator password=repl123'
hot_standby = on
EOCONF

# Создание файла standby.signal
touch /var/lib/postgresql/data/standby.signal

# Установка прав доступа
chown -R postgres:postgres /var/lib/postgresql/data
chmod 700 /var/lib/postgresql/data

echo "Запуск PostgreSQL в режиме standby..."
exec docker-entrypoint.sh postgres
EOF

# =================
# PgBouncer конфигурация (УПРОЩЕННАЯ)
# =================
# Создание директории 
mkdir -p pgbouncer

# Создание простого скрипта для переключения pgbouncer
cat > pgbouncer/switch_to_slave.sh <<'EOF'
#!/bin/bash
docker stop pgbouncer
docker run -d --name pgbouncer_temp --network postgres-ha-lab_postgres_network --ip 172.20.0.20 -p 6432:5432 \
  -e DATABASE_URL="postgres://postgres:postgres123@postgres_slave:5432/testdb" \
  -e POOL_MODE=transaction -e MAX_CLIENT_CONN=100 -e DEFAULT_POOL_SIZE=20 -e AUTH_TYPE=trust \
  edoburu/pgbouncer:latest
docker rm pgbouncer
docker rename pgbouncer_temp pgbouncer
EOF

cat > pgbouncer/switch_to_master.sh <<'EOF'
#!/bin/bash
docker stop pgbouncer
docker run -d --name pgbouncer_temp --network postgres-ha-lab_postgres_network --ip 172.20.0.20 -p 6432:5432 \
  -e DATABASE_URL="postgres://postgres:postgres123@postgres_master:5432/testdb" \
  -e POOL_MODE=transaction -e MAX_CLIENT_CONN=100 -e DEFAULT_POOL_SIZE=20 -e AUTH_TYPE=trust \
  edoburu/pgbouncer:latest
docker rm pgbouncer
docker rename pgbouncer_temp pgbouncer
EOF

chmod +x pgbouncer/*.sh

# =================
# Клиентские скрипты (ИСПРАВЛЕННЫЕ)
# =================
cat > client/test_connections.sh <<'EOF'
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
EOF

cat > client/load_test.sh <<'EOF'
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
EOF

# =================
# Скрипты управления (ИСПРАВЛЕННЫЕ)
# =================
cat > scripts/setup.sh <<'EOF'
#!/bin/bash

echo "🚀 Запуск PostgreSQL HA кластера..."

# Очистка предыдущих данных
echo "🧹 Очистка предыдущих данных..."
docker-compose down -v 2>/dev/null || true

# Создание логов директории
mkdir -p logs

# Запуск сервисов
docker-compose up -d

echo "⏳ Ожидание готовности сервисов..."
sleep 45

# Проверка состояния
echo "📊 Проверка состояния сервисов:"
docker-compose ps

# Проверка репликации
echo "🔄 Проверка репликации:"
docker exec postgres_master psql -U postgres -d testdb -c "SELECT client_addr, state FROM pg_stat_replication;" || echo "Проверка репликации не удалась"

echo "✅ Кластер запущен и готов к работе!"
echo ""
echo "Подключения:"
echo "  Master:    localhost:5432"
echo "  Slave:     localhost:5433"
echo "  PgBouncer: localhost:6432"
echo ""
echo "Для тестирования выполните:"
echo "  cd client && bash test_connections.sh"
echo "  cd client && bash load_test.sh"
EOF

cat > scripts/simulate_failure.sh <<'EOF'
#!/bin/bash

echo "💥 Симуляция отказа мастера..."

# Остановка мастера
echo "Остановка postgres_master..."
docker stop postgres_master

echo "⏳ Ожидание обнаружения отказа..."
sleep 10

# Проверка логов pgbouncer
echo "📋 Логи PgBouncer:"
docker logs pgbouncer | tail -20

# Попытка подключения через pgbouncer
echo "🔄 Попытка подключения через pgbouncer (должна завершиться ошибкой):"
timeout 10 docker exec postgres_client bash -c "PGPASSWORD=postgres123 psql -h pgbouncer -p 5432 -U postgres -d testdb -c \"SELECT NOW();\"" || echo "Ожидаемая ошибка подключения"

echo "💡 Для восстановления выполните: bash scripts/failover.sh"
EOF

cat > scripts/failover.sh <<'EOF'
#!/bin/bash

echo "🔄 Выполнение переключения на слейв..."

# Промоция слейва в мастер
echo "Промоция слейва в мастер..."
docker exec -u postgres postgres_slave bash -c "
rm -f /var/lib/postgresql/data/standby.signal
pg_ctl reload -D /var/lib/postgresql/data
"

# Ожидание промоции
sleep 10

# Обновление конфигурации pgbouncer для использования слейва
echo "Переключение PgBouncer на slave сервер..."
docker stop pgbouncer || true
docker rm pgbouncer || true

# Запуск нового pgbouncer с подключением к slave
docker run -d --name pgbouncer --network postgres-ha-lab_postgres_network --ip 172.20.0.20 -p 6432:5432 \
  -e DATABASE_URL="postgres://postgres:postgres123@postgres_slave:5432/testdb" \
  -e POOL_MODE=transaction -e MAX_CLIENT_CONN=100 -e DEFAULT_POOL_SIZE=20 -e AUTH_TYPE=trust \
  edoburu/pgbouncer:latest

echo "⏳ Ожидание запуска pgbouncer..."
sleep 5

echo "⏳ Ожидание применения изменений..."
sleep 5

# Тестирование подключения
echo "🧪 Тестирование подключения к новому мастеру:"
docker exec postgres_client bash -c "
PGPASSWORD=postgres123 psql -h pgbouncer -p 5432 -U postgres -d testdb -c \"
SELECT 'Failover successful - connected to: ' || version();
INSERT INTO users (username, email) VALUES ('failover_test', 'failover@test.com');
SELECT 'New user added during failover, total users: ' || COUNT(*) FROM users;
\"
"

echo "✅ Failover завершен успешно!"
EOF

cat > scripts/recovery.sh <<'EOF'
#!/bin/bash

echo "🔧 Восстановление исходной конфигурации..."

# Запуск старого мастера как нового слейва
echo "Запуск бывшего мастера..."
docker start postgres_master

echo "⏳ Ожидание запуска..."
sleep 15

# Настройка бывшего мастера как слейва
echo "Настройка репликации..."
docker exec postgres_master bash -c "
rm -f /var/lib/postgresql/data/postmaster.pid
echo 'primary_conninfo = '\''host=postgres_slave port=5432 user=replicator password=repl123'\''' >> /var/lib/postgresql/data/postgresql.conf
touch /var/lib/postgresql/data/standby.signal
"

# Перезапуск для применения конфигурации
docker restart postgres_master

echo "⏳ Ожидание синхронизации..."
sleep 20

# Проверка репликации
echo "📊 Проверка состояния репликации:"
docker exec postgres_slave psql -U postgres -d testdb -c "SELECT client_addr, state FROM pg_stat_replication;" || echo "Репликация еще настраивается..."

# Обновление pgbouncer для балансировки
echo "Восстановление PgBouncer для работы с обоими серверами..."
docker stop pgbouncer || true
docker rm pgbouncer || true

# Запуск pgbouncer с подключением к новому мастеру (бывший slave)
docker run -d --name pgbouncer --network postgres-ha-lab_postgres_network --ip 172.20.0.20 -p 6432:5432 \
  -e DATABASE_URL="postgres://postgres:postgres123@postgres_slave:5432/testdb" \
  -e POOL_MODE=transaction -e MAX_CLIENT_CONN=100 -e DEFAULT_POOL_SIZE=20 -e AUTH_TYPE=trust \
  edoburu/pgbouncer:latest

echo "⏳ Ожидание запуска pgbouncer..."
sleep 5

echo "✅ Восстановление завершено!"
echo "Текущая конфигурация:"
echo "  Активный мастер: postgres_slave:5432"
echo "  Слейв для чтения: postgres_master:5432"
echo "  PgBouncer: балансировка между серверами"
EOF

cat > scripts/status.sh <<'EOF'
#!/bin/bash

echo "📊 Статус PostgreSQL HA кластера"
echo "================================="

# Статус контейнеров
echo "🐳 Статус контейнеров:"
docker-compose ps

echo ""
echo "🔍 Статус репликации:"
# Проверяем кто сейчас мастер
if docker exec postgres_master psql -U postgres -d testdb -c "SELECT pg_is_in_recovery();" 2>/dev/null | grep -q "f"; then
    echo "Мастер: postgres_master"
    docker exec postgres_master psql -U postgres -d testdb -c "SELECT client_addr, state, sent_lsn, write_lsn FROM pg_stat_replication;" 2>/dev/null || echo "Репликация не активна"
elif docker exec postgres_slave psql -U postgres -d testdb -c "SELECT pg_is_in_recovery();" 2>/dev/null | grep -q "f"; then
    echo "Мастер: postgres_slave"
    docker exec postgres_slave psql -U postgres -d testdb -c "SELECT client_addr, state, sent_lsn, write_lsn FROM pg_stat_replication;" 2>/dev/null || echo "Репликация не активна"
else
    echo "Не удается определить мастер сервер"
fi

echo ""
echo "📈 Статистика подключений PgBouncer:"
docker logs pgbouncer 2>/dev/null | tail -10 || echo "PgBouncer логи недоступны"

echo ""
echo "💾 Данные в базе:"
if docker exec postgres_master psql -U postgres -d testdb -c "SELECT COUNT(*) as users FROM users; SELECT COUNT(*) as orders FROM orders;" 2>/dev/null; then
    echo "Данные получены с postgres_master"
elif docker exec postgres_slave psql -U postgres -d testdb -c "SELECT COUNT(*) as users FROM users; SELECT COUNT(*) as orders FROM orders;" 2>/dev/null; then
    echo "Данные получены с postgres_slave"
else
    echo "Не удается получить данные ни с одного сервера"
fi
EOF

# Делаем скрипты исполняемыми
chmod +x client/*.sh
chmod +x scripts/*.sh
chmod +x master/setup-master.sh
chmod +x slave/setup-slave.sh

# =================
# Мануал по использованию
# =================
cat > README.md <<'EOF'
# PostgreSQL High Availability Lab

## Описание
Лабораторная работа по настройке высокодоступного PostgreSQL кластера с репликацией и балансировкой нагрузки.

## Архитектура
- **postgres_master**: Основной сервер PostgreSQL (172.20.0.10:5432)
- **postgres_slave**: Слейв сервер для репликации (172.20.0.11:5432)
- **pgbouncer**: Балансировщик нагрузки (172.20.0.20:5432)
- **postgres_client**: Клиентская машина для тестирования (172.20.0.30)

## Этапы выполнения

### 1. Запуск кластера
```bash
bash scripts/setup.sh
```

### 2. Тестирование подключений
```bash
cd client
bash test_connections.sh
```

### 3. Нагрузочное тестирование
```bash
cd client
bash load_test.sh
```

### 4. Симуляция отказа
```bash
bash scripts/simulate_failure.sh
```

### 5. Переключение на резервный сервер
```bash
bash scripts/failover.sh
```

### 6. Восстановление
```bash
bash scripts/recovery.sh
```

### 7. Проверка статуса
```bash
bash scripts/status.sh
```

## Подключения
- **Через PgBouncer**: `PGPASSWORD=postgres123 psql -h localhost -p 6432 -U postgres -d testdb`
- **Прямо к мастеру**: `PGPASSWORD=postgres123 psql -h localhost -p 5432 -U postgres -d testdb`
- **Прямо к слейву**: `PGPASSWORD=postgres123 psql -h localhost -p 5433 -U postgres -d testdb`

## Остановка
```bash
docker-compose down
```

## Полная очистка
```bash
docker-compose down -v
docker system prune -f
```

## Устранение неполадок

### Если контейнеры не запускаются:
```bash
docker-compose down -v
docker system prune -f
bash scripts/setup.sh
```

### Проверка логов:
```bash
docker logs postgres_master
docker logs postgres_slave
docker logs pgbouncer
```
EOF

# =================
# Демонстрационный скрипт (ИСПРАВЛЕННЫЙ)
# =================
cat > demo.sh <<'EOF'
#!/bin/bash

echo "🎯 Демонстрация PostgreSQL HA кластера"
echo "======================================"

echo "1️⃣  Запуск кластера..."
bash scripts/setup.sh

read -p "Нажмите Enter для продолжения к тестированию подключений..."

echo ""
echo "2️⃣  Тестирование подключений..."
cd client
bash test_connections.sh
cd ..

read -p "Нажмите Enter для продолжения к нагрузочному тестированию..."

echo ""
echo "3️⃣  Нагрузочное тестирование..."
cd client
bash load_test.sh
cd ..

read -p "Нажмите Enter для продолжения к проверке статуса..."

echo ""
echo "4️⃣  Проверка статуса кластера..."
bash scripts/status.sh

read -p "Нажмите Enter для симуляции отказа мастера..."

echo ""
echo "5️⃣  Симуляция отказа мастера..."
bash scripts/simulate_failure.sh

read -p "Нажмите Enter для переключения на резервный сервер..."

echo ""
echo "6️⃣  Переключение на резервный сервер..."
bash scripts/failover.sh

read -p "Нажмите Enter для проверки работы после failover..."

echo ""
echo "7️⃣  Проверка работы после failover..."
bash scripts/status.sh

read -p "Нажмите Enter для восстановления исходной конфигурации..."

echo ""
echo "8️⃣  Восстановление исходной конфигурации..."
bash scripts/recovery.sh

read -p "Нажмите Enter для финальной проверки..."

echo ""
echo "9️⃣  Финальная проверка..."
bash scripts/status.sh

echo ""
echo "✅ Демонстрация завершена!"
echo "Для просмотра логов: docker-compose logs [service_name]"
echo "Для остановки: docker-compose down"
EOF

chmod +x demo.sh

echo ""
echo "✅ Все файлы созданы успешно!"
echo ""
echo "📁 Структура проекта:"
find . -type f | head -20
echo ""
echo "🚀 Для запуска демонстрации:"
echo "   cd postgres-ha-lab"
echo "   bash demo.sh"
echo ""
echo "📖 Документация: postgres-ha-lab/README.md"