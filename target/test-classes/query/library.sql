SELECT count(*)
from books;
SELECT COUNT(*)
FROM users;
SELECT COUNT(*)
FROM book_borrow
where is_returned = 0;

select name
from book_categories;

SELECT *
FROM book_borrow;

SELECT b.name, isbn, year, author, c.name, b.description
FROM books b
         join book_categories c On b.book_category_id = c.id
where b.name = 'Agile Testing';

select *
from book_categories;

select *
from users
where email = 'librarian55@library';

select status
from users
where email = '66gj50@cydeo.com';

SELECT COUNT(*)
FROM users
WHERE status = 'ACTIVE'
  AND user_group_id <> 1;

SELECT COUNT(*)
FROM users
WHERE status = 'INACTIVE'
  AND user_group_id <> 1;
