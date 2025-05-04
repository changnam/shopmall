--liquibase formatted sql

--changeset yourname:create-book-table
CREATE TABLE IF NOT EXISTS orders ( 
    orderId int(11) NOT NULL AUTO_INCREMENT,
    customer_id INTEGER,
    shipping_id INTEGER,
    grandTotal INTEGER,
    primary key(orderId) 
) ;

CREATE TABLE IF NOT EXISTS  `orders_seq` (
  `next_val` bigint DEFAULT NULL
) ;

--changeset yourname:insert-orders_seq
insert into orders_seq values(1);