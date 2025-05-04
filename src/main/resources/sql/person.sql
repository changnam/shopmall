--liquibase formatted sql

--changeset yourname:create-book-table

CREATE TABLE IF NOT EXISTS Persons (
  p_id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(45) NOT NULL,
  age int NOT NULL,
  email  varchar(45) NOT NULL,
  PRIMARY KEY (p_id)
);

