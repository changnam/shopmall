--liquibase formatted sql

--changeset yourname:create-book-table
CREATE TABLE IF NOT EXISTS cart ( 
    cartId int(11) NOT NULL,  
    grandTotal  LONG,    
    primary key(cartId) 
);


