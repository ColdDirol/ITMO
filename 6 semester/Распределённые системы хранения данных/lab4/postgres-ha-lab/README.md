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
