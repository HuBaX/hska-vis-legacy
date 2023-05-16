CREATE DATABASE Products CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE Products;
CREATE USER 'productuser'@'%' IDENTIFIED BY 'productuser';
GRANT ALL PRIVILEGES on Products.* to 'productuser'@'%';

CREATE TABLE product (
	id INT NOT NULL AUTO_INCREMENT,
	details VARCHAR(255),
	name VARCHAR(255),
	price DOUBLE,
	category_id INT,
	PRIMARY KEY (id)
) ENGINE=InnoDB;