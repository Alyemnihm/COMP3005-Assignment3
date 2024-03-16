/*
    COMP3005: Assignment 3
    Author: Hajar Alyemni (101186632)
    Due: March 18, 2024
*/

--Students table
CREATE TABLE Students (
    student_id          SERIAL          PRIMARY KEY,
    first_name          VARCHAR(255)    NOT NULL,
    last_name           VARCHAR(255)    NOT NULL,
    email               VARCHAR(255)    NOT NULL UNIQUE,
    enrollment_date     DATE 
);