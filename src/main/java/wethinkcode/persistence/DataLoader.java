package wethinkcode.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Exercise 3.2
 */
public class DataLoader {
    private final Connection connection;

    /**
     * These are the Genres that must be persisted to the database
     */
    private final Map<String, Genre> genres = Map.of(
            "PROG", new Genre("PROG", "Programming"),
            "BIO", new Genre("BIO", "Biography"),
            "SCIFI", new Genre("SCIFI", "Science Fiction"));

    /**
     * These are the Books that must be persisted to the database
     */
    private final List<Book> books = List.of(
            new Book("Test Driven Development", genres.get("PROG")),
            new Book("Programming in Haskell", genres.get("PROG")),
            new Book("Scatterlings of Africa", genres.get("BIO")));

    /**
     * Create an instance of the DataLoader object using the provided database connection
     *
     * @param connection The JDBC connection to use
     */
    public DataLoader(Connection connection) {
        this.connection = connection;
    }

    /**
     * 3.2 (part 1) Complete this method
     * <p>
     * Inserts data from the `Genres` collection into the `Genres` table.
     *
     * @return true if the data was successfully inserted, otherwise false
     */
    public boolean insertGenres() {
        for(Genre genre: genres.values()){
            PreparedStatement ps;
            String code = genre.getCode();
            String description=genre.getDescription();
            try {
                ps=connection.prepareStatement("INSERT INTO Genres (code, description) VALUES (\""+code+"\", \""+description+"\");");
                ps.execute();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        return true;
    }
    /**
     * 3.2 (part 1) Complete this method
     * <p>
     * Inserts data from the `Books` collection into the `Books` table.
     *
     * @return true if the data was successfully inserted, otherwise false
     */
    public List<Book> insertBooks() throws SQLException {
        insertGenres();
        for(Book book: books){
            PreparedStatement ps;
            String title = book.getTitle();
            String genre_code = book.getGenre().getCode();
            ps = connection.prepareStatement("INSERT INTO Books (title, genre_code) VALUES (\"" + title + "\", \"" + genre_code + "\");");
            ps.execute();
            ResultSet s = ps.getGeneratedKeys();
            s.next();
            book.assignId(s.getInt(1));
        }
        return books;
    }
//

    /**
     * Get the last id generated from the prepared statement
     *
     * @param s the prepared statement
     * @return the last id generated
     * @throws SQLException if the id was not generated
     */
    private int getGeneratedId(PreparedStatement s) throws SQLException {
        ResultSet generatedKeys = s.getGeneratedKeys();
        if (!generatedKeys.next()) throw new SQLException("Id was not generated");
        return generatedKeys.getInt(1);
    }

    protected boolean genres_execute(String sql) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.execute();
        } catch (SQLException e) {
            return false;
        }

        return true;
    }
}


