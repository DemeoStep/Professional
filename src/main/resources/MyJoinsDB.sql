DROP DATABASE IF EXISTS MyJoinsDB;
CREATE DATABASE MyJoinsDB;
USE MyJoinsDB;

CREATE TABLE persons
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(60) UNIQUE NOT NULL,
    phone VARCHAR(15) UNIQUE NOT NULL
);

CREATE TABLE positions
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    person INT UNIQUE NOT NULL,
    position VARCHAR(50) NOT NULL,
    salary INT NOT NULL,
    FOREIGN KEY (person) REFERENCES persons(id)
);

CREATE TABLE person_info
(
    id INT PRIMARY KEY AUTO_INCREMENT,
    person INT UNIQUE NOT NULL,
    status VARCHAR(30) NOT NULL,
    birthday DATE NOT NULL,
    address VARCHAR(100) NOT NULL,
    FOREIGN KEY (person) REFERENCES persons(id)
);

DROP PROCEDURE IF EXISTS add_worker;

DELIMITER %%
CREATE PROCEDURE add_worker(name VARCHAR(45), phone VARCHAR(15), salary INT, position VARCHAR(60),
                            status VARCHAR(15), address VARCHAR(60), birthday DATE)
BEGIN
    DECLARE id INT;

    START TRANSACTION;

    INSERT INTO persons (persons.name, persons.phone)
        VALUE (name, phone);

    SELECT persons.id FROM persons WHERE persons.name = name AND persons.phone = phone limit 1 INTO id;

    IF EXISTS (SELECT * FROM persons WHERE persons.name = name AND persons.phone = phone AND persons.id != id)
        THEN
            ROLLBACK;
        ELSE
            INSERT INTO positions (positions.person, positions.salary, positions.position)
            VALUE (id, salary, position);

            INSERT INTO person_info (person_info.person, person_info.status, person_info.address, person_info.birthday)
                VALUE (id, status, address, birthday);

            COMMIT;
        END IF;
END %%

DELIMITER ;

CALL add_worker('Горяинов Алексей', '050-828-34-66', 50000, 'Главный директор', 'Холост', 'г. Дружковка, ул. Вавилова, 9', '1980-10-21');
CALL add_worker('Горяинов Валерий','050-828-34-55', 8000, 'Рабочий', 'Женат', 'г. Дружковка, ул. П.Коммуны, 79-21', '1956-02-24');
CALL add_worker('Горяинова Елена','050-828-34-33', 20000, 'Менеджер', 'Замужем', 'г. Дружковка, ул. Вавилова, 9', '1984-02-22');
CALL add_worker('Горяинова Татьяна','050-828-34-44', 25000, 'Менеджер', 'Замужем', 'г. Дружковка, ул. Вавилова, 9', '1960-12-30');
