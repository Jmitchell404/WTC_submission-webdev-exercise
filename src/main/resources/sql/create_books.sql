CREATE TABLE Books (
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,
    title TEXT NOT NULL ,
    genre_code TEXT NOT NULL ,
    FOREIGN KEY(genre_code) REFERENCES Genres(code)
);