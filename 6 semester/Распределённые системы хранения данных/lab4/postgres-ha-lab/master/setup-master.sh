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
