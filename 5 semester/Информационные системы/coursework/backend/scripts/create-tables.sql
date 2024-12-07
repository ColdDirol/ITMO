DROP TABLE IF EXISTS currency_in_user_possession CASCADE;
DROP TABLE IF EXISTS currency_exchange_rate CASCADE;
DROP TABLE IF EXISTS currency CASCADE;
DROP TABLE IF EXISTS transaction_history_log CASCADE;
DROP TABLE IF EXISTS user_account CASCADE;
DROP TABLE IF EXISTS administrator_action_with_user_log CASCADE;
DROP TABLE IF EXISTS video_path CASCADE;
DROP TABLE IF EXISTS user_profile_picture_path CASCADE;
DROP TABLE IF EXISTS path CASCADE;
DROP TABLE IF EXISTS user_info_change_log CASCADE;
DROP TABLE IF EXISTS users CASCADE;
    
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(50),
    phone VARCHAR(20),
    name VARCHAR(100),
    date_of_birth DATE,
    status VARCHAR(20)
);

CREATE TABLE user_info_change_log (
    id BIGSERIAL PRIMARY KEY,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT REFERENCES users(id),
    field VARCHAR(50),
    old_value TEXT,
    new_value TEXT
);

CREATE TABLE path (
    id BIGSERIAL PRIMARY KEY,
    bucket VARCHAR(100),
    file_name VARCHAR(255)
);

CREATE TABLE user_profile_picture_path (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    path_id BIGINT REFERENCES path(id)
);

CREATE TABLE video_path (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100),
    description TEXT,
    path_id BIGINT REFERENCES path(id)
);

CREATE TABLE administrator_action_with_user_log (
    id BIGSERIAL PRIMARY KEY,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    administrator_id BIGINT REFERENCES users(id),
    action VARCHAR(100),
    user_id BIGINT REFERENCES users(id)
);

CREATE TABLE user_account (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    name VARCHAR(100),
    balance NUMERIC(15,2),
    currency_id BIGINT
);

CREATE TABLE transaction_history_log (
    id BIGSERIAL PRIMARY KEY,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT REFERENCES users(id),
    action VARCHAR(100),
    user_account_id BIGINT REFERENCES user_account(id),
    sum NUMERIC(15,2)
);

CREATE TABLE currency (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100),
    short_name VARCHAR(10)
);

CREATE TABLE currency_exchange_rate (
    id BIGSERIAL PRIMARY KEY,
    currency_id BIGINT REFERENCES currency(id),
    status VARCHAR(20),
    coefficient NUMERIC(15,4)
);

CREATE TABLE currency_in_user_possession (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id),
    currency_id BIGINT REFERENCES currency(id),
    user_account_id BIGINT REFERENCES user_account(id)
);