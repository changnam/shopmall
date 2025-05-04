--liquibase formatted sql

--changeset yourname:create-book-table

CREATE TABLE IF NOT EXISTS customer ( 
    id INTEGER AUTO_INCREMENT NOT NULL ,   
    customerId varchar(40) not null,
    name varchar(40) ,   
    phone varchar(40),   
    address_id INTEGER,  
    primary key(id) 
) ;


