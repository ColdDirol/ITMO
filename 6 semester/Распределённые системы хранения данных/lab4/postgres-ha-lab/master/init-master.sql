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
