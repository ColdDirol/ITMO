-- Ускоряет проверку email при регистрации и авторизации
-- Поддерживает уникальность email
CREATE UNIQUE INDEX idx_users_email ON users(email);

-- Ускоряет проверку баланса юзера
CREATE INDEX idx_user_account_user_currency ON user_account(user_id);

-- Ускоряет получение истории транзакций
CREATE INDEX idx_transaction_log_user_id ON transaction_history_log(user_id);

-- Ускоряет доступ к логам изменений данных пользователя
CREATE INDEX idx_user_info_log_user_id ON user_info_change_log(user_id);

-- Ускоряет поиск действий администратора
CREATE INDEX idx_admin_action_admin_id ON administrator_action_with_user_log(administrator_id);
