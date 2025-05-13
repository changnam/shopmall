ALTER TABLE book
MODIFY COLUMN author_id bigint;

ALTER TABLE book
ADD CONSTRAINT fk_author
FOREIGN KEY (author_id)
REFERENCES authors(id);
