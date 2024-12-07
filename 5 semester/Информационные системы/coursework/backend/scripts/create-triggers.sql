-----------------------------------------------------------------
--                        Триггер 1                            --
-----------------------------------------------------------------
-- Функция для записи изменений в таблицу user_info_change_log
CREATE OR REPLACE FUNCTION log_user_info_change()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.email <> OLD.email THEN
        INSERT INTO user_info_change_log(user_id, field, old_value, new_value)
        VALUES (NEW.id, 'email', OLD.email, NEW.email);
    END IF;

    IF NEW.password <> OLD.password THEN
        INSERT INTO user_info_change_log(user_id, field, old_value, new_value)
        VALUES (NEW.id, 'password', OLD.password, NEW.password);
    END IF;

    IF NEW.role <> OLD.role THEN
        INSERT INTO user_info_change_log(user_id, field, old_value, new_value)
        VALUES (NEW.id, 'role', OLD.role, NEW.role);
    END IF;

    IF NEW.phone <> OLD.phone THEN
        INSERT INTO user_info_change_log(user_id, field, old_value, new_value)
        VALUES (NEW.id, 'phone', OLD.phone, NEW.phone);
    END IF;

    IF NEW.name <> OLD.name THEN
        INSERT INTO user_info_change_log(user_id, field, old_value, new_value)
        VALUES (NEW.id, 'name', OLD.name, NEW.name);
    END IF;

    IF NEW.date_of_birth <> OLD.date_of_birth THEN
        INSERT INTO user_info_change_log(user_id, field, old_value, new_value)
        VALUES (NEW.id, 'date_of_birth', OLD.date_of_birth::TEXT, NEW.date_of_birth::TEXT);
    END IF;

    IF NEW.account_status <> OLD.account_status THEN
        INSERT INTO user_info_change_log(user_id, field, old_value, new_value)
        VALUES (NEW.id, 'account_status', OLD.account_status, NEW.account_status);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Триггер для срабатывания функции при обновлении таблицы users
CREATE TRIGGER trigger_log_user_info_change
AFTER UPDATE ON users
FOR EACH ROW
EXECUTE FUNCTION log_user_info_change();

-----------------------------------------------------------------
--                        Триггер 2                            --
-----------------------------------------------------------------
-- Функция для записи изменений в таблицу transaction_history_log
CREATE OR REPLACE FUNCTION log_transaction_history()
RETURNS TRIGGER AS $$
DECLARE
transaction_type TEXT;
    transaction_amount NUMERIC;
BEGIN
    transaction_amount := NEW.balance - OLD.balance;

    IF transaction_amount > 0 THEN
        transaction_type := 'REPLENISHMENT';
    ELSE
        transaction_type := 'DEBITING';
        transaction_amount := ABS(transaction_amount);
    END IF;

    INSERT INTO transaction_history_log(user_id, action, user_account_id, sum)
        VALUES (NEW.user_id, transaction_type, NEW.id, transaction_amount);

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Триггер для срабатывания функции при обновлении таблицы user_account
CREATE TRIGGER trigger_log_transaction_history
    AFTER UPDATE OF balance ON user_account
    FOR EACH ROW
    WHEN (OLD.balance IS DISTINCT FROM NEW.balance)  -- Проверка, что баланс изменился
EXECUTE FUNCTION log_transaction_history();

