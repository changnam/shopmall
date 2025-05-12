--changeset yourname:create-author-table
-- drop table authors;
CREATE TABLE IF NOT EXISTS  `authors` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique-ix-name` (`name`)
)  ;

--changeset yourname:add-jenre-age-column
ALTER TABLE authors ADD COLUMN genre varchar(255);
ALTER TABLE authors ADD COLUMN age int(11);