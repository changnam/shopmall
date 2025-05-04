--liquibase formatted sql

--changeset yourname:create-book-table

CREATE TABLE IF NOT EXISTS customer ( 
    id varchar(40) NOT NULL,   
    name varchar(40) ,   
    phone varchar(40),     
    primary key(id) 
) ;


