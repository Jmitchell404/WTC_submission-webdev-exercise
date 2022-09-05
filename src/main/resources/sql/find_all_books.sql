SELECT Books.title, Genres.description
FROM Books
INNER JOIN Genres on Books.genre_code = Genres.code;