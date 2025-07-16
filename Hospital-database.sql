CREATE DATABASE hospital;
USE hospital;

CREATE TABLE patients(
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(50) NOT NULL,
age INT NOT NULL,
gender VARCHAR(10) NOT NULL
);

CREATE TABLE doctors(
id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(50) NOT NULL,
specialization VARCHAR(200) NOT NULL
);

CREATE TABLE appointments(
id INT PRIMARY KEY AUTO_INCREMENT,
patient_id INT NOT NULL,
doctor_id INT NOT NULL,
appointment_date DATE NOT NULL,
FOREIGN KEY (patient_id) REFERENCES patients(id),
FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);

INSERT INTO doctors(name,specialization) 
VALUES 
("Anjli Mehta","General Medicine"),
("Rajeev Sharma","Orthopedic Surgeon")
;
SELECT * FROM doctors;
SHOW TABLES;

