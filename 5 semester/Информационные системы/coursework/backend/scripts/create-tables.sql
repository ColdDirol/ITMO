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
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(63) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    name VARCHAR(127) NOT NULL,
    date_of_birth DATE NOT NULL,
    status VARCHAR(31) NOT NULL
);

CREATE TABLE user_info_change_log (
    id BIGSERIAL PRIMARY KEY,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    user_id BIGINT REFERENCES users(id) NOT NULL,
    field VARCHAR(63) NOT NULL,
    old_value TEXT NOT NULL,
    new_value TEXT NOT NULL
);

CREATE TABLE path (
    id BIGSERIAL PRIMARY KEY,
    bucket VARCHAR(127) NOT NULL,
    file_name VARCHAR(255) NOT NULL
);

CREATE TABLE user_profile_picture_path (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) NOT NULL,
    path_id BIGINT REFERENCES path(id) NOT NULL
);

CREATE TABLE video_path (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(127) NOT NULL,
    description TEXT NOT NULL,
    path_id BIGINT REFERENCES path(id) NOT NULL
);

CREATE TABLE administrator_action_with_user_log (
    id BIGSERIAL PRIMARY KEY,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    administrator_id BIGINT REFERENCES users(id) NOT NULL,
    action VARCHAR(127) NOT NULL,
    user_id BIGINT REFERENCES users(id) NOT NULL
);

CREATE TABLE user_account (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) NOT NULL,
    name VARCHAR(127) NOT NULL,
    balance NUMERIC(15,2) NOT NULL,
    currency_id BIGINT NOT NULL
);

CREATE TABLE transaction_history_log (
    id BIGSERIAL PRIMARY KEY,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    user_id BIGINT REFERENCES users(id) NOT NULL,
    action VARCHAR(127) NOT NULL,
    user_account_id BIGINT REFERENCES user_account(id) NOT NULL,
    sum NUMERIC(15,2) NOT NULL
);

CREATE TABLE currency (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(127) NOT NULL,
    short_name VARCHAR(15) NOT NULL
);

CREATE TABLE currency_exchange_rate (
    id BIGSERIAL PRIMARY KEY,
    currency_id BIGINT REFERENCES currency(id) NOT NULL,
    status VARCHAR(31) NOT NULL,
    coefficient NUMERIC(15,4) NOT NULL
);

CREATE TABLE currency_in_user_possession (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) NOT NULL,
    currency_id BIGINT REFERENCES currency(id) NOT NULL,
    user_account_id BIGINT REFERENCES user_account(id) NOT NULL
);