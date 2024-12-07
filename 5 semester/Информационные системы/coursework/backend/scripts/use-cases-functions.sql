---------------------------------------------------------------
--                  UP-01 Регистрация (Mock)                 --
---------------------------------------------------------------
-- SELECT register_user(
--     'example@example.com',
--     'securepassword',
--     'John Doe',
--     'user',
--     '1234567890',
--     '1990-01-01'
-- );

CREATE OR REPLACE FUNCTION register_user(
    p_email VARCHAR,
    p_password VARCHAR,
    p_name VARCHAR,
    p_role VARCHAR DEFAULT 'user',
    p_phone VARCHAR DEFAULT NULL,
    p_date_of_birth DATE DEFAULT NULL
) RETURNS BOOLEAN AS $$
DECLARE
user_count INTEGER;
BEGIN
    -- Проверка существования пользователя по email
    SELECT COUNT(*) INTO user_count
        FROM users
        WHERE email = p_email;

    -- Email уже зарегистрирован
    IF user_count > 0 THEN
            RETURN FALSE;
    END IF;

    INSERT INTO users (email, password, name, role, phone, date_of_birth, status)
        VALUES (p_email, p_password, p_name, p_role, p_phone, p_date_of_birth, 'active');

RETURN TRUE;
END;
$$ LANGUAGE plpgsql;




---------------------------------------------------------------
--                 UP-02 Авторизация (Mock)                  --
---------------------------------------------------------------
-- SELECT authenticate_user(
--     'example@example.com',
--     'securepassword'
-- );

CREATE OR REPLACE FUNCTION authenticate_user(
    p_email VARCHAR,
    p_password VARCHAR
) RETURNS BOOLEAN AS $$
DECLARE
user_count INTEGER;
BEGIN
    -- Проверка соответствия email и пароля пользователя
    SELECT COUNT(*) INTO user_count
        FROM users
        WHERE email = p_email AND password = p_password;

    IF user_count = 1 THEN
        -- Авторизация успешна
        RETURN TRUE;
    ELSE
        RETURN FALSE;
    END IF;
END;
$$ LANGUAGE plpgsql;



---------------------------------------------------------------
--          UP-04 Изменение данных аккаунта (Mock)           --
---------------------------------------------------------------
-- SELECT update_user_data(
--     p_user_id := 1,
--     p_new_email := 'newemail@example.com',
--     p_new_name := 'Jane Doe',
--     p_new_phone := '9876543210',
--     p_new_date_of_birth := '1995-05-10'
-- );

CREATE OR REPLACE FUNCTION update_user_data(
    p_user_id BIGINT,
    p_new_email VARCHAR DEFAULT NULL,
    p_new_name VARCHAR DEFAULT NULL,
    p_new_phone VARCHAR DEFAULT NULL,
    p_new_date_of_birth DATE DEFAULT NULL
) RETURNS BOOLEAN AS $$
DECLARE
v_old_email VARCHAR;
    v_old_name VARCHAR;
    v_old_phone VARCHAR;
    v_old_date_of_birth DATE;
BEGIN
    IF NOT EXISTS (SELECT 1 FROM users WHERE id = p_user_id) THEN
        RETURN FALSE;
    END IF;

    SELECT email, name, phone, date_of_birth
        INTO v_old_email, v_old_name, v_old_phone, v_old_date_of_birth
        FROM users
        WHERE id = p_user_id;

    IF p_new_email IS NOT NULL AND p_new_email <> v_old_email THEN
        UPDATE users SET email = p_new_email WHERE id = p_user_id;
    END IF;

    IF p_new_name IS NOT NULL AND p_new_name <> v_old_name THEN
        UPDATE users SET name = p_new_name WHERE id = p_user_id;
    END IF;

    IF p_new_phone IS NOT NULL AND p_new_phone <> v_old_phone THEN
        UPDATE users SET phone = p_new_phone WHERE id = p_user_id;
    END IF;

    IF p_new_date_of_birth IS NOT NULL AND p_new_date_of_birth <> v_old_date_of_birth THEN
        UPDATE users SET date_of_birth = p_new_date_of_birth WHERE id = p_user_id;
    END IF;

    -- Обновление выполнено успешно
    RETURN TRUE;
END;
$$ LANGUAGE plpgsql;



---------------------------------------------------------------
-- UP-05 / AP-02 Заморозка/Блокировка/Удаление пользователя  --
---------------------------------------------------------------
-- Удаление пользователя с id = 1
-- SELECT delete_user_account(1);

CREATE OR REPLACE FUNCTION delete_user_account(
    p_user_id BIGINT
) RETURNS BOOLEAN AS $$
BEGIN
    -- Проверка существования пользователя по email
    IF NOT EXISTS (SELECT 1 FROM users WHERE id = p_user_id) THEN
        RETURN FALSE;
    END IF;

    UPDATE users
        SET status = 'DELETED'
        WHERE id = p_user_id;

    -- Удаление выполнено успешно
    RETURN TRUE;
END;
$$ LANGUAGE plpgsql;


-- Заморозка пользователя с id = 1
-- SELECT froze_user_account(1);

CREATE OR REPLACE FUNCTION froze_user_account(
    p_user_id BIGINT
) RETURNS BOOLEAN AS $$
BEGIN
    -- Проверка существования пользователя по email
    IF NOT EXISTS (SELECT 1 FROM users WHERE id = p_user_id) THEN
        RETURN FALSE;
    END IF;

    UPDATE users
        SET status = 'FROZEN'
        WHERE id = p_user_id;

    -- Заморозка выполнена успешно
    RETURN TRUE;
END;
$$ LANGUAGE plpgsql;


-- Блокировка пользователя с id = 1
-- SELECT block_user_account(1);

CREATE OR REPLACE FUNCTION block_user_account(
    p_user_id BIGINT
) RETURNS BOOLEAN AS $$
BEGIN
    -- Проверка существования пользователя по email
    IF NOT EXISTS (SELECT 1 FROM users WHERE id = p_user_id) THEN
        RETURN FALSE;
    END IF;

    UPDATE users
        SET status = 'BLOCKED'
        WHERE id = p_user_id;

    -- Блокировка выполнена успешно
    RETURN TRUE;
END;
$$ LANGUAGE plpgsql;


-- Активация пользователя с id = 1
-- SELECT activate_user_account(1);

CREATE OR REPLACE FUNCTION activate_user_account(
    p_user_id BIGINT
) RETURNS BOOLEAN AS $$
BEGIN
    -- Проверка существования пользователя по email
    IF NOT EXISTS (SELECT 1 FROM users WHERE id = p_user_id) THEN
        RETURN FALSE;
    END IF;

    UPDATE users
        SET status = 'ACTIVE'
        WHERE id = p_user_id;

    -- Активация выполнена успешно
    RETURN TRUE;
END;
$$ LANGUAGE plpgsql;



---------------------------------------------------------------
--   MP-01 Перевод денежных средств другому пользователю     --
---------------------------------------------------------------
-- SELECT transfer_funds(
--     p_from_account_id := 1,    -- ID исходного счёта
--     p_to_account_id := 2,      -- ID целевого счёта
--     p_amount := 500.00,        -- Сумма перевода
--     p_user_id := 1             -- ID пользователя, совершающего перевод
-- );

CREATE OR REPLACE FUNCTION transfer_funds(
    p_from_account_id BIGINT,
    p_to_account_id BIGINT,
    p_amount NUMERIC(15,2),
    p_user_id BIGINT
) RETURNS BOOLEAN AS $$
DECLARE
v_balance_from NUMERIC(15,2);
    v_balance_to NUMERIC(15,2);
BEGIN
    -- Проверка на существование счетов
    IF NOT EXISTS (SELECT 1 FROM user_account WHERE id = p_from_account_id) OR
       NOT EXISTS (SELECT 1 FROM user_account WHERE id = p_to_account_id) THEN
        RETURN FALSE;  -- Один из счетов не существует
    END IF;

    -- Получение и конвертация балансов в числовой формат
    SELECT balance::NUMERIC INTO v_balance_from
        FROM user_account
        WHERE id = p_from_account_id;

    SELECT balance::NUMERIC INTO v_balance_to
        FROM user_account
        WHERE id = p_to_account_id;

    -- Проверка наличия достаточного баланса на исходном счёте
    IF v_balance_from < p_amount THEN
            RETURN FALSE;
    END IF;

    -- Обновление баланса отправителя (уменьшение)
    UPDATE user_account
        SET balance = (v_balance_from - p_amount)::VARCHAR
        WHERE id = p_from_account_id;

    -- Обновление баланса получателя (увеличение)
    UPDATE user_account
        SET balance = (v_balance_to + p_amount)::VARCHAR
        WHERE id = p_to_account_id;

--     -- Запись в лог транзакции для отправителя
--     INSERT INTO transaction_history_log(user_id, action, user_account_id, sum)
--         VALUES (p_user_id, 'TRANSFER_OUT', p_from_account_id, p_amount);
--
--     -- Запись в лог транзакции для получателя
--     INSERT INTO transaction_history_log(user_id, action, user_account_id, sum)
--         VALUES (p_user_id, 'TRANSFER_IN', p_to_account_id, p_amount);

    -- Перевод выполнен успешно
    RETURN TRUE;
END;
$$ LANGUAGE plpgsql;
