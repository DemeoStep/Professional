DROP DATABASE IF EXISTS carsshop;
CREATE DATABASE carsshop;
USE carsshop;

CREATE TABLE marks
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    mark VARCHAR(20) NULL,
    CONSTRAINT mark
        UNIQUE (mark)
);

INSERT INTO carsshop.marks (mark) VALUES ('Audi');
INSERT INTO carsshop.marks (mark) VALUES ('Porsche');

CREATE TABLE cars
(
    id      INT         AUTO_INCREMENT PRIMARY KEY,
    mark_id INT         NOT NULL,
    model   VARCHAR(20) NOT NULL,
    price   INT         NOT NULL,
    CONSTRAINT cars_ibfk_1
        FOREIGN KEY (mark_id) REFERENCES marks (id)
);

CREATE INDEX mark_id
    ON cars (mark_id);

INSERT INTO carsshop.cars (mark_id, model, price) VALUES (1, 'A5', 40000);
INSERT INTO carsshop.cars (mark_id, model, price) VALUES (2, 'Panamera', 100000);
INSERT INTO carsshop.cars (mark_id, model, price) VALUES (2, 'Cayman', 88000);

CREATE TABLE clients
(
    id    INT           AUTO_INCREMENT PRIMARY KEY,
    name  VARCHAR(30)   NOT NULL ,
    age   TINYINT       NOT NULL ,
    phone VARCHAR(15)   NOT NULL
);

INSERT INTO carsshop.clients (name, age, phone) VALUES ('Petro', 20, '050-856-47-52');
INSERT INTO carsshop.clients (name, age, phone) VALUES ('Vasya', 25, '063-457-81-55');
INSERT INTO carsshop.clients (name, age, phone) VALUES ('Vitaly', 75, '099-758-43-16');

CREATE TABLE orders
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    car_id      INT NOT NULL,
    client_id   INT NOT NULL,
    CONSTRAINT orders_ibfk_1 FOREIGN KEY (car_id) REFERENCES cars (id),
    CONSTRAINT orders_ibfk_2 FOREIGN KEY (client_id) REFERENCES clients (id)
);

CREATE INDEX car_id
    ON orders (car_id);

CREATE INDEX client_id
    ON orders (client_id);

INSERT INTO carsshop.orders (car_id, client_id) VALUES (1, 1);
INSERT INTO carsshop.orders (car_id, client_id) VALUES (2, 2);
INSERT INTO carsshop.orders (car_id, client_id) VALUES (1, 3);