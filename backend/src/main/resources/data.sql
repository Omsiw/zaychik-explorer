-- Статусы игроков
INSERT INTO status (name) VALUES  -- вместо status
    ('SEARCHING'),
    ('IN_GAME'),
    ('OFFLINE');

INSERT INTO cell_type (type, movement_cost) VALUES
    ('Лес', 1.3),
    ('Пустыня', 1.2),
    ('Горы', 1.8),
    ('Поле', 1.0) ,

    ('Река', 1.5),
    ('Болото', 2.5),
    ('Деревня', 0.7),
    ('Пещера', 1.2),
    ('Пляж', 1.0),
    ('Тундра', 1.3);