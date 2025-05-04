--liquibase formatted sql

--changeset yourname:create-book-table
CREATE TABLE IF NOT EXISTS orderItem( 
    id int(11) NOT NULL AUTO_INCREMENT,
    bookid VARCHAR(10) NOT NULL,
    quantity INTEGER,
    totalPrice DOUBLE,   
    order_order_id INTEGER,
    orderItems_KEY VARCHAR(10) ,
    primary key(id) 
) ;


CREATE TABLE IF NOT EXISTS `orderitem_seq` (
  `next_val` bigint DEFAULT NULL
) ;

--changeset yourname:insert-orders_seq
insert into orderitem_seq values(1);