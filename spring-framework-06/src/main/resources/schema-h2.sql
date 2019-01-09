DROP TABLE IF EXISTS author_isbn;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS publisher;
DROP TABLE IF EXISTS authors;

CREATE TABLE authors (
  author_id BIGINT NOT NULL AUTO_INCREMENT
, first_name VARCHAR(60) NOT NULL
, last_name VARCHAR(40) NOT NULL
, UNIQUE (first_name, last_name)
, PRIMARY KEY (author_id)
);

CREATE TABLE publisher (
  publisher_id BIGINT NOT NULL AUTO_INCREMENT
, publisher_name  VARCHAR(90) NOT NULL
, PRIMARY KEY (publisher_id)
);

CREATE TABLE book (
  book_id BIGINT NOT NULL AUTO_INCREMENT
, isbn VARCHAR(20) NOT NULL
, title VARCHAR(90) NOT NULL
, edition_number INT
, copyright VARCHAR(40) NOT NULL
, publisher_id BIGINT REFERENCES publisher(publisher_id)
, UNIQUE (isbn)
, PRIMARY KEY (book_id)
);

CREATE TABLE author_isbn (
  author_id BIGINT NOT NULL REFERENCES authors(author_id)
, isbn VARCHAR(20) NOT NULL REFERENCES book(isbn)
);
