CREATE TABLE IF NOT EXISTS type
(
id SERIAL PRIMARY KEY,
name VARCHAR CHECK(name = 'Опасная' OR name = 'Безопасная')
);

CREATE TABLE IF NOT EXISTS size
(
id SERIAL PRIMARY KEY,
name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS biome
(
id SERIAL PRIMARY KEY,
water BOOL,
mountains BOOL,
forest BOOL,
desert BOOL
);

CREATE TABLE IF NOT EXISTS location
(
id SERIAL PRIMARY KEY,
name VARCHAR NOT NULL,
type INT REFERENCES type(id),
size INT REFERENCES size(id),
biome INT REFERENCES biome(id),
mainland VARCHAR,
x NUMERIC(1000, 10),
y NUMERIC(1000, 10)
);



CREATE TABLE IF NOT EXISTS class
(
id SERIAL PRIMARY KEY,
name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS home
(
id SERIAL PRIMARY KEY,
location INT REFERENCES location(id),
name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS creature_homes
(
id SERIAL,
home INT,
creature INT
);

CREATE TABLE IF NOT EXISTS peticantrop
(
id SERIAL PRIMARY KEY,
name VARCHAR NOT NULL,
sex VARCHAR CHECK(sex = 'Мужчина' OR sex = 'Женщина'),
hunger BOOL,
thirst BOOL
);

CREATE TABLE IF NOT EXISTS creature
(
id SERIAL PRIMARY KEY,
name VARCHAR NOT NULL,
location INT REFERENCES location(id),
class INT REFERENCES class(id),
peticantrop INT REFERENCES peticantrop(id),
status VARCHAR CHECK(status = 'Живой' OR status = 'Неживой')
);