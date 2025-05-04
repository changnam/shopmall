--liquibase formatted sql

--changeset yourname:create-book-table
CREATE TABLE IF NOT EXISTS address ( 
    id int(11) NOT NULL AUTO_INCREMENT,
    country varchar(40),
    zipcode varchar(40),
    addressname varchar(40),
    detailname varchar(40) ,
    primary key(id) 
) ;

CREATE TABLE IF NOT EXISTS `address_seq` (
  `next_val` bigint DEFAULT NULL
) ;

--changeset yourname:insert-address_seq
insert into address_seq values(1);