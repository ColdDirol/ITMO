INSERT INTO currency (name, short_name) VALUES
    ('Доллар США', 'USD'),
    ('Евро', 'EUR'),
    ('Российский рубль', 'RUB'),
    ('Китайский юань', 'CNY'),
    ('Британский фунт', 'GBP');

INSERT INTO currency_exchange_rate (currency_id, status, coefficient) VALUES
    ((SELECT id FROM currency WHERE short_name = 'USD'), 'active', 1.0000),
    ((SELECT id FROM currency WHERE short_name = 'EUR'), 'active', 0.9200),
    ((SELECT id FROM currency WHERE short_name = 'RUB'), 'active', 91.5000),
    ((SELECT id FROM currency WHERE short_name = 'CNY'), 'active', 7.1500),
    ((SELECT id FROM currency WHERE short_name = 'GBP'), 'active', 0.7900);

INSERT INTO users (email, password, role, phone, name, date_of_birth, status) VALUES
    ('john.doe@example.com', 'hashed_password_1', 'user', '+1-555-123-4567', 'John Doe', '1990-05-15', 'active'),
    ('maria.silva@example.com', 'hashed_password_2', 'user', '+55-11-98765-4321', 'Maria Silva', '1988-11-22', 'active'),
    ('admin@system.com', 'admin_password', 'admin', '+7-999-888-7777', 'System Administrator', '1985-03-10', 'active'),
    ('elena.petrova@example.com', 'hashed_password_3', 'user', '+7-912-345-6789', 'Elena Petrova', '1995-07-30', 'blocked'),
    ('michael.chan@example.com', 'hashed_password_4', 'user', '+86-138-1234-5678', 'Michael Chan', '1992-12-05', 'active');

INSERT INTO user_account (user_id, name, balance, currency_id) VALUES
    ((SELECT id FROM users WHERE email = 'john.doe@example.com'), 'Main Account', 5000.50, (SELECT id FROM currency WHERE short_name = 'USD')),
    ((SELECT id FROM users WHERE email = 'maria.silva@example.com'), 'Personal Account', 3200.75, (SELECT id FROM currency WHERE short_name = 'EUR')),
    ((SELECT id FROM users WHERE email = 'elena.petrova@example.com'), 'Savings', 15000.00, (SELECT id FROM currency WHERE short_name = 'RUB')),
    ((SELECT id FROM users WHERE email = 'michael.chan@example.com'), 'Business Account', 25000.25, (SELECT id FROM currency WHERE short_name = 'CNY'));

INSERT INTO currency_in_user_possession (user_id, currency_id, user_account_id) VALUES
    ((SELECT id FROM users WHERE email = 'john.doe@example.com'), (SELECT id FROM currency WHERE short_name = 'USD'), (SELECT id FROM user_account WHERE user_id = (SELECT id FROM users WHERE email = 'john.doe@example.com'))),
    ((SELECT id FROM users WHERE email = 'maria.silva@example.com'), (SELECT id FROM currency WHERE short_name = 'EUR'), (SELECT id FROM user_account WHERE user_id = (SELECT id FROM users WHERE email = 'maria.silva@example.com'))),
    ((SELECT id FROM users WHERE email = 'elena.petrova@example.com'), (SELECT id FROM currency WHERE short_name = 'RUB'), (SELECT id FROM user_account WHERE user_id = (SELECT id FROM users WHERE email = 'elena.petrova@example.com'))),
    ((SELECT id FROM users WHERE email = 'michael.chan@example.com'), (SELECT id FROM currency WHERE short_name = 'CNY'), (SELECT id FROM user_account WHERE user_id = (SELECT id FROM users WHERE email = 'michael.chan@example.com')));

INSERT INTO transaction_history_log (user_id, action, user_account_id, sum) VALUES
    ((SELECT id FROM users WHERE email = 'john.doe@example.com'), 'Deposit', (SELECT id FROM user_account WHERE user_id = (SELECT id FROM users WHERE email = 'john.doe@example.com')), 1000.25),
    ((SELECT id FROM users WHERE email = 'maria.silva@example.com'), 'Withdrawal', (SELECT id FROM user_account WHERE user_id = (SELECT id FROM users WHERE email = 'maria.silva@example.com')), 500.50),
    ((SELECT id FROM users WHERE email = 'elena.petrova@example.com'), 'Transfer', (SELECT id FROM user_account WHERE user_id = (SELECT id FROM users WHERE email = 'elena.petrova@example.com')), 2000.00),
    ((SELECT id FROM users WHERE email = 'michael.chan@example.com'), 'Deposit', (SELECT id FROM user_account WHERE user_id = (SELECT id FROM users WHERE email = 'michael.chan@example.com')), 5000.75);

INSERT INTO administrator_action_with_user_log (administrator_id, action, user_id) VALUES
    ((SELECT id FROM users WHERE email = 'admin@system.com'), 'Block user account', (SELECT id FROM users WHERE email = 'elena.petrova@example.com')),
    ((SELECT id FROM users WHERE email = 'admin@system.com'), 'Reset password', (SELECT id FROM users WHERE email = 'john.doe@example.com'));

INSERT INTO path (bucket, file_name) VALUES
    ('user-profiles', 'john_doe_profile.jpg'),
    ('user-profiles', 'maria_silva_profile.png'),
    ('videos', 'welcome_guide.mp4'),
    ('videos', 'tutorial_part1.mp4');

INSERT INTO user_profile_picture_path (user_id, path_id) VALUES
    ((SELECT id FROM users WHERE email = 'john.doe@example.com'), (SELECT id FROM path WHERE file_name = 'john_doe_profile.jpg')),
    ((SELECT id FROM users WHERE email = 'maria.silva@example.com'), (SELECT id FROM path WHERE file_name = 'maria_silva_profile.png'));

INSERT INTO video_path (name, description, path_id) VALUES
    ('Welcome Guide', 'Introductory video for new users', (SELECT id FROM path WHERE file_name = 'welcome_guide.mp4')),
    ('Tutorial Part 1', 'First part of system usage tutorial', (SELECT id FROM path WHERE file_name = 'tutorial_part1.mp4'));

INSERT INTO user_info_change_log (user_id, field, old_value, new_value) VALUES
    ((SELECT id FROM users WHERE email = 'john.doe@example.com'), 'phone', '+1-555-000-0000', '+1-555-123-4567'),
    ((SELECT id FROM users WHERE email = 'maria.silva@example.com'), 'email', 'maria.old@example.com', 'maria.silva@example.com');