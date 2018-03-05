create DATABASE IF NOT EXISTS demo;
use demo;
CREATE TABLE student(
    ID VARCHAR(5),
    name VARCHAR(20),
    age int(3),
    FM VARCHAR(1),
    PRIMARY KEY(ID)
);
show TABLES ;

SELECT * FROM student;
DELETE FROM student;
