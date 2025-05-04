--liquibase formatted sql

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