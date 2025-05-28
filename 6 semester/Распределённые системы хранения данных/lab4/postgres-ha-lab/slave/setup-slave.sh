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
