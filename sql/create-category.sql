CREATE DATABASE Categories;
CREATE USER 'categoryuser'@'%' IDENTIFIED BY 'categoryuser';
GRANT ALL PRIVILEGES on Categories.* to 'categoryuser'@'%';

USE Categories;

CREATE TABLE category (
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB;