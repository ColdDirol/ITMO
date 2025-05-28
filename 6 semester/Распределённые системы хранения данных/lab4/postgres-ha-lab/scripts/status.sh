#!/bin/bash

echo "Статус PostgreSQL HA кластера"
echo "================================="

# Статус контейнеров
echo "Статус контейнеров:"
docker-compose ps

echo ""
echo "Статус репликации:"
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
echo "Статистика подключений PgBouncer:"
docker logs pgbouncer 2>/dev/null | tail -10 || echo "PgBouncer логи недоступны"

echo ""
echo "Данные в базе:"
if docker exec postgres_master psql -U postgres -d testdb -c "SELECT COUNT(*) as users FROM users; SELECT COUNT(*) as orders FROM orders;" 2>/dev/null; then
    echo "Данные получены с postgres_master"
elif docker exec postgres_slave psql -U postgres -d testdb -c "SELECT COUNT(*) as users FROM users; SELECT COUNT(*) as orders FROM orders;" 2>/dev/null; then
    echo "Данные получены с postgres_slave"
else
    echo "Не удается получить данные ни с одного сервера"
fi
