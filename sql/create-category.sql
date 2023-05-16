CREATE DATABASE Categories CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE Categories;
CREATE USER 'categoryuser'@'%' IDENTIFIED BY 'categoryuser';
GRANT ALL PRIVILEGES on Categories.* to 'categoryuser'@'%';



CREATE TABLE category (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB;