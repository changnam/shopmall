--liquibase formatted sql

--changeset yourname:create-book-table
CREATE TABLE IF NOT EXISTS shipping ( 
    id int(11) NOT NULL AUTO_INCREMENT,
    address_id INTEGER,
    name varchar(40),
    date varchar(40),   
    primary key(id) 
) ;
