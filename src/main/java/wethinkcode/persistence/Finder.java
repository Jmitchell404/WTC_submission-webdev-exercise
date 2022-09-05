package wethinkcode.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Exercise 3.3
 */
public class Finder {

    private final Connection connection;

    /**
     * Create an instance of the Finder object using the provided database connection
     *
     * @param connection The JDBC connection to use
     */
    public Finder(Connection connection) {
        this.connection = connection;
    }

    /**
     * 3.3 (part 1) Complete this method
     * <p>
     * Finds all genres in the database
     *
     * @return a list of `Genre` objects
     * @throws SQLException the query failed
     */
    public List<Genre> findAllGenres() throws SQLException {
        PreparedStatement ps;
        List<Genre> Genreslist = new ArrayList<>();
        ps = connection.prepareStatement("SELECT * FROM Genres;");
        ps.execute();
        ResultSet GenreResults = ps.getResultSet();
        while (GenreResults.next()) {
            Genre genre = new Genre(GenreResults.getString("code"), GenreResults.getString("description"));
            Genreslist.add(genre);
        }
        return Genreslist;
    }

    /**
     * 3.3 (part 2) Complete this method
     * <p>
     * Finds all genres in the database that have specific substring in the description
     *
     * @param pattern The pattern to match
     * @return a list of `Genre` objects
     * @throws SQLException the query failed
     */
    public List<Genre> findGenresLike(String pattern) throws SQLException {
        PreparedStatement ps;
        List<Genre> Genreslikelist = new ArrayList<>();
        ps = connection.prepareStatement("SELECT * FROM Genres WHERE description LIKE '%"+pattern+"%';");
        ps.execute();
        ResultSet GenreResults = ps.getResultSet();
        while (GenreResults.next()) {
            Genre genrelike = new Genre(GenreResults.getString("code"), GenreResults.getString("description"));
            Genreslikelist.add(genrelike);
        }
        return Genreslikelist;
    }

    /**
     * 3.3 (part 3) Complete this method
     * <p>
     * Finds all books with their genres
     *
     * @return a list of `BookGenreView` objects
     * @throws SQLException the query failed
     */
    public List<BookGenreView> findBooksAndGenres() throws SQLException {
        PreparedStatement ps;
        List<BookGenreView> BookAndGenrelist = new ArrayList<>();
        ps = connection.prepareStatement("SELECT Books.title, Genres.description FROM Books INNER JOIN Genres on Books.genre_code = Genres.code;");
        ps.execute();
        ResultSet BookResults = ps.getResultSet();
        while (BookResults.next()) {
            BookGenreView BookAndGenre = new BookGenreView(BookResults.getString("title"), BookResults.getString("description"));
            BookAndGenrelist.add(BookAndGenre);
        }
        return BookAndGenrelist;
    }

    /**
     * 3.3 (part 4) Complete this method
     * <p>
     * Finds the number of books in a genre
     *
     * @return the number of books in the genre
     * @throws SQLException the query failed
     */
    public int findNumberOfBooksInGenre(String genreCode) throws SQLException {
        PreparedStatement ps;
        int total=0;
        ps = connection.prepareStatement("SELECT COUNT(*) FROM Books WHERE genre_code=\'"+genreCode+"\'");
        ps.execute();
        ResultSet NumberOfBookinGenre = ps.getResultSet();
        while (NumberOfBookinGenre.next()) {
            total = NumberOfBookinGenre.getInt(1);
        }
        return total;
    }
}
