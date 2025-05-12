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

--changeset yourname:create-author-table
CREATE TABLE IF NOT EXISTS  `authors` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
)  ;

--changeset yourname:add-jenre-age-column
ALTER TABLE authors ADD COLUMN genre varchar(255);
ALTER TABLE authors ADD COLUMN age int(11);

--changeset yourname:create-board-table

CREATE TABLE IF NOT EXISTS `board` (
  `createdDate` datetime(6) DEFAULT NULL,
  `id` bigint NOT NULL,
  `modifiedDate` datetime(6) DEFAULT NULL,
  `writer` varchar(32) NOT NULL,
  `writerid` varchar(10) NOT NULL,
  `title` varchar(100) NOT NULL,
  `content` text NOT NULL,
  PRIMARY KEY (`id`)
) ;


CREATE TABLE IF NOT EXISTS `board_seq` (
  `next_val` bigint DEFAULT NULL
) ;

--changeset yourname:insert-board_seq
insert into board_seq values(1);
--changeset yourname:create-book-table
CREATE TABLE IF NOT EXISTS book(
	b_bookId VARCHAR(10) NOT NULL,
	b_name VARCHAR(30),
	b_unitPrice  INTEGER,
	b_author VARCHAR(50),
	b_description TEXT,
	b_publisher VARCHAR(20),
	b_category VARCHAR(20),
	b_unitsInStock LONG,	
	b_releaseDate VARCHAR(20),
	b_condition VARCHAR(20),
	b_fileName  VARCHAR(20),
	PRIMARY KEY (b_bookId)
);

--changeset yourname:insert-books
DELETE FROM book;
INSERT INTO book VALUES
('ISBN1234', '자바스크립트 입문', 30000,'조현영', '자바스크립트의 기초부터 심화까지 핵심 문법을 학습한 후 12가지 프로그램을 만들며 학습한 내용을 확인할 수 있습니다. 문법 학습과 실습이 적절히 섞여 있어 프로그램을 만드는 방법을 재미있게 익힐 수 있습니다.','길벗','IT전문서', 1000,'2024/02/20','new','ISBN1234.jpg'),
('ISBN1235', '파이썬의 정석', 29800, '조용주,임좌상', '4차 산업혁명의 핵심인 머신러닝, 사물 인터넷(IoT), 데이터 분석 등 다양한 분야에 활용되는 직관적이고 간결한 문법의 파이썬 프로그래밍 언어를 최신 트렌드에 맞게 예제 중심으로 학습할 수 있습니다.','길벗','IT교육교재',1000, '2023/01/10','new', 'ISBN1235.jpg'), 
('ISBN1236', '안드로이드 프로그래밍', 25000, '송미영', '안드로이드의 기본 개념을 체계적으로 익히고, 이를 실습 예제를 통해 익힙니다. 기본 개념과 사용법을 스스로 실전에 적용하는 방법을 학습한 다음 실습 예제와 응용 예제를 통해 실전 프로젝트 응용력을 키웁니다.', '길벗', 'IT교육교재', 1000, '2023/06/30', 'new','ISBN1236.jpg');

--changeset yourname:add-eauthor-column
ALTER TABLE book ADD COLUMN author_id int(11);

--changeset yourname:add-isbn-column
ALTER TABLE book ADD COLUMN b_isbn varchar(255);

--changeset yourname:add-title-column
ALTER TABLE book ADD COLUMN b_title varchar(255);

--changeset yourname:create-book-table
CREATE TABLE IF NOT EXISTS cart ( 
    cartId int(11) NOT NULL,  
    grandTotal  LONG,    
    primary key(cartId) 
);


--changeset yourname:create-book-table
CREATE TABLE IF NOT EXISTS cartitem ( 
     id int(11) NOT NULL AUTO_INCREMENT,
    quantity INTEGER,  
    totalPrice  LONG ,  
      primary key(id) 
) ;

--changeset yourname:create-book-table

CREATE TABLE IF NOT EXISTS customer ( 
    id INTEGER AUTO_INCREMENT NOT NULL ,   
    customerId varchar(40) not null,
    name varchar(40) ,   
    phone varchar(40),   
    address_id INTEGER,  
    primary key(id) 
) ;
--changeset yourname:create-book-table
CREATE TABLE IF NOT EXISTS item(
	b_itemId VARCHAR(10) NOT NULL,
	b_name VARCHAR(30),
	b_unitPrice  INTEGER,
	b_author VARCHAR(50),
	b_description TEXT,
	b_publisher VARCHAR(20),
	b_category VARCHAR(20),
	b_unitsInStock LONG,	
	b_releaseDate VARCHAR(20),
	b_condition VARCHAR(20),
	b_fileName  VARCHAR(20),
	PRIMARY KEY (b_itemId)
);
--changeset yourname:create-book-table

CREATE TABLE IF NOT EXISTS `members` (
  `num` bigint NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `memberId` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `role` enum('ADMIN','USER') DEFAULT NULL,
  PRIMARY KEY (`num`),
  UNIQUE KEY `UKot56t3ekpfce2r89ql49oqjry` (`memberId`)
) ;


CREATE TABLE IF NOT EXISTS `members_seq` (
  `next_val` bigint DEFAULT NULL
) ;

--changeset yourname:insert-books
insert into members_seq values(1);
insert into members values (1,'버드나루로130 (강변래미안 302동 904호)','aa@honsoft.com','cngoh','changnam go','$2a$10$jN2RFsRFAH2.lvKhHVMPuODTMh8X/1gJWfwHvImYrkDtY6XKa1RcO','010','USER');

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
--changeset yourname:create-book-table

CREATE TABLE IF NOT EXISTS Persons (
  p_id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(45) NOT NULL,
  age int NOT NULL,
  email  varchar(45) NOT NULL,
  PRIMARY KEY (p_id)
);


--changeset yourname:create-book-table
CREATE TABLE IF NOT EXISTS shipping ( 
    id int(11) NOT NULL AUTO_INCREMENT,
    address_id INTEGER,
    name varchar(40),
    date varchar(40),   
    primary key(id) 
) ;
