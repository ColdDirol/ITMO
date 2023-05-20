-- id 1
INSERT INTO type(name) VALUES('Опасная');
-- id 2
INSERT INTO type(name) VALUES('Безопасная');

-- id 1
INSERT INTO size(name) VALUES('Большая');
-- id 2
INSERT INTO size(name) VALUES('Маленькая');
-- id 3
INSERT INTO size(name) VALUES('Очень большая');
-- id 4
INSERT INTO size(name) VALUES('Очень маленькая');

-- id 1
INSERT INTO biome(water, mountains, forest, desert) VALUES(FALSE, FALSE, FALSE, TRUE);
-- id 2
INSERT INTO biome(water, mountains, forest, desert) VALUES(FALSE, FALSE, TRUE, TRUE);
-- id 3
INSERT INTO biome(water, mountains, forest, desert) VALUES(FALSE, TRUE, TRUE, TRUE);
-- id 4
INSERT INTO biome(water, mountains, forest, desert) VALUES(TRUE, FALSE, TRUE, TRUE);
-- id 5
INSERT INTO biome(water, mountains, forest, desert) VALUES(TRUE, TRUE, TRUE, TRUE);

-- id 1
INSERT INTO location(name, type, size, biome, mainland) 
VALUES('Северная Африка', 1, 3, 2, 'Африка');
-- id 2
INSERT INTO location(name, type, size, biome, mainland) 
VALUES('Южная Африка', 2, 4, 1, 'Африка');
-- id 3
INSERT INTO location(name, type, size, biome, mainland) 
VALUES('Западная Африка', 2, 2, 4, 'Африка');
-- id 4
INSERT INTO location(name, type, size, biome, mainland) 
VALUES('Восточная Африка', 1, 1, 5, 'Африка');
-- id 5
INSERT INTO location(name, type, size, biome, mainland) 
VALUES('Центральная Африка', 2, 2, 3, 'Африка');



-- id 1
INSERT INTO class(name) VALUES('Человек');
-- id 2
INSERT INTO class(name) VALUES('Животное');
-- id 3
INSERT INTO class(name) VALUES('Овощ');
-- id 4
INSERT INTO class(name) VALUES('Человек-овощ');

-- id 1
INSERT INTO home(location, name) VALUES(4, 'Клоповник');
-- id 2
INSERT INTO home(location, name) VALUES(4, 'Избушка на курьих ножках');
-- id 3
INSERT INTO home(location, name) VALUES(4, 'Дворец');
-- id 4
INSERT INTO home(location, name) VALUES(4, 'Замок');
-- id 5
INSERT INTO home(location, name) VALUES(5, 'Избушка на курьих ножках (новостройка)');

-- id 1
INSERT INTO peticantrop(name, sex, hunger, thirst) VALUES('Олег', 'Мужчина', FALSE, TRUE);
-- id 2
INSERT INTO peticantrop(name, sex, hunger, thirst) VALUES('Оля', 'Женщина', FALSE, FALSE);
-- id 3
INSERT INTO peticantrop(name, sex, hunger, thirst) VALUES('Баба-яга', 'Женщина', TRUE, TRUE);
-- id 4
INSERT INTO peticantrop(name, sex, hunger, thirst) VALUES('Дедушка-яга', 'Мужчина', TRUE, TRUE);
-- id 5
INSERT INTO peticantrop(name, sex, hunger, thirst) VALUES('Мама-яга', 'Женщина', TRUE, TRUE);
-- id 6
INSERT INTO peticantrop(name, sex, hunger, thirst) VALUES('Батя-яга', 'Мужчина', TRUE, TRUE);

-- id 1
INSERT INTO creature(name, location, class, peticantrop, status)
VALUES('Человек', 4, 1, 1, 'Живой');
-- id 2
INSERT INTO creature(name, location, class, peticantrop, status)
VALUES('Человек', 4, 4, 2, 'Живой');
-- id 3
INSERT INTO creature(name, location, class, peticantrop, status)
VALUES('Человек', 4, 3, 3, 'Неживой');
-- id 4
INSERT INTO creature(name, location, class, peticantrop, status)
VALUES('Человек', 5, 3, 4, 'Неживой');
-- id 5
INSERT INTO creature(name, location, class, peticantrop, status)
VALUES('Человек', 5, 3, 5, 'Неживой');
-- id 6
INSERT INTO creature(name, location, class, peticantrop, status)
VALUES('Человек', 5, 4, 6, 'Неживой');



-- id 1
INSERT INTO creature_homes(home, creature)
VALUES(4, 1);
-- id 2
INSERT INTO creature_homes(home, creature)
VALUES(3, 2);
-- id 3
INSERT INTO creature_homes(home, creature)
VALUES(2, 3);
-- id 4
INSERT INTO creature_homes(home, creature)
VALUES(5, 4);
-- id 5
INSERT INTO creature_homes(home, creature)
VALUES(5, 5);
-- id 6
INSERT INTO creature_homes(home, creature)
VALUES(5, 6);