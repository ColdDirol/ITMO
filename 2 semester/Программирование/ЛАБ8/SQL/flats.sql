CREATE TABLE flats
(
	id SERIAL PRIMARY KEY,
	users_id INT REFERENCES users(id) NOT NULL,
	
	name VARCHAR NOT NULL,
	x_coordinate BIGINT NOT NULL,
	y_coordinate BIGINT NOT NULL,
	creationdate DATE NOT NULL,
	area REAL NOT NULL,
	numberofrooms BIGINT NOT NULL,
	furnish_e FURNISH NOT NULL,
	view_e VIEW NOT NULL,
	transport_e TRANSPORT NOT NULL,
	house_name VARCHAR NOT NULL,
	house_year INT NOT NULL,
	house_numberoflifts INT NOT NULL
);