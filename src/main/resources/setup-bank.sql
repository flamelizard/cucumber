CREATE DATABASE bank;
CREATE USER 'bob'@'localhost' IDENTIFIED BY 'password';
GRANT ALL ON bank.* TO 'teller'@'localhost';