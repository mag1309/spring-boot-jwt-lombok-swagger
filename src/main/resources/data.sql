DROP TABLE IF EXISTS USERS;

CREATE TABLE USERS (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  first_name VARCHAR(100),
  last_name VARCHAR(100),
  email VARCHAR(100),
  phone VARCHAR(10),
  password VARCHAR(100) DEFAULT NULL
);

INSERT INTO USERS (id, first_name, last_name, email, phone, password) VALUES
 (1, 'Manish', 'Gupta', 'manish.gupta@test.com','2456789760','$2y$12$/QCxue3/krz95LkfFG5JxuHtwKuRbpmM5iHx/xC7nHaCmrUigHAJG'),
 (2, 'Bill', 'Gates', 'bill.gates@test.com','2476789760','$2y$12$evSg7yiGIWF8V0QCur1UM.QH7hlmC87Xgau.A6g/3hwmgiMS6EUEW');