--liquibase formatted sql

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