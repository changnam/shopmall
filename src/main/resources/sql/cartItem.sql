--liquibase formatted sql

--changeset yourname:create-book-table
CREATE TABLE IF NOT EXISTS cartitem ( 
     id int(11) NOT NULL AUTO_INCREMENT,
    quantity INTEGER,  
    totalPrice  LONG ,  
      primary key(id) 
) ;


