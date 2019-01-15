
DELETE FROM author_isbn;
DELETE FROM author;
DELETE FROM book;
DELETE FROM publisher;
DELETE FROM genre;

INSERT INTO publisher VALUES
 (1, 'test_publisher_name_1')
;

INSERT INTO genre VALUES
 (2, 'test_genre_2')
;

INSERT INTO book VALUES
  (3, '9999999993', 'test_title_3', 3, '2001', 1, 2)
, (4, '9999999994', 'test_title_4', 4, '2002', 1, 2)
, (5, '9999999995', 'test_title_5', 5, '2003', 1, 2)
;

INSERT INTO author VALUES
  (6, 'test_first_name_6', 'test_last_name_6')
, (7, 'test_first_name_7', 'test_last_name_7')
, (8, 'test_first_name_8', 'test_last_name_8')
;

INSERT INTO author_isbn VALUES
  (6, 3)
, (6, 4)
, (7, 3)
, (7, 4)
, (8, 3)
, (8, 4)
;
