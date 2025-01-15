INSERT INTO currency (name, short_name) VALUES
    ('US Dollar', 'USD'),
    ('Euro', 'EUR'),
    ('Russian Ruble', 'RUB'),
    ('Mauritian Rupee', 'MUR');

INSERT INTO currency_exchange_rate (status, currency_id, coefficient) VALUES (0, 1, 1);
INSERT INTO currency_exchange_rate (status, currency_id, coefficient) VALUES (0, 2, 1);
INSERT INTO currency_exchange_rate (status, currency_id, coefficient) VALUES (0, 3, 0.01);

INSERT INTO bank_account (balance, name, user_id, currency_id) VALUES (100, 'USD', 1, 1);

INSERT INTO bank_account (balance, name, user_id, currency_id) VALUES (100, 'USD', 2, 1);
INSERT INTO bank_account (balance, name, user_id, currency_id) VALUES (100, 'RUB', 2, 3);

UPDATE users SET role = 0 WHERE id = 1; -- set super admin role