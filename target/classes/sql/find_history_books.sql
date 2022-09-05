SELECT COUNT(*)
FROM Books, Genres
WHERE Books.genre_code=Genres.code AND Genres.description="History"