DROP TABLE students IF EXISTS;

CREATE TABLE students  (
    id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    name VARCHAR(20),
    passport_number VARCHAR(20)
);