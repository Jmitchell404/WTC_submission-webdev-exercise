package wethinkcode.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Exercise 3.1
 */
public class Tables {
    private final Connection connection;

    /**
     * Create an instance of the Tables object using the provided database connection
     * @param connection The JDBC connection to use
     */
    public Tables(Connection connection) {
        this.connection = connection;
    }

    /**
     * 3.1 Complete this method
     *
     * Create the Genres table
     * @return true if the table was successfully created, otherwise false
     */
    public boolean createGenres() {
        boolean result = createTable("CREATE TABLE Genres (\n" +
                "    code TEXT NOT NULL,\n" +
                "    description TEXT NOT NULL,\n" +
                "    PRIMARY KEY(code)\n" +
                ");");

        return result;
    }

    /**
     * 3.1 Complete this method
     *
     * Create the Books table
     * @return true if the table was successfully created, otherwise false
     */
    public boolean createBooks() {
        createGenres();
        boolean result = createTable("CREATE TABLE Books (\n" +
                "    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ,\n" +
                "    title TEXT NOT NULL ,\n" +
                "    genre_code TEXT NOT NULL ,\n" +
                "    FOREIGN KEY(genre_code) REFERENCES Genres(code)\n" +
                ");");

        return result;
    }

    /**
     * 3.1 Complete this method
     *
     * Execute a SQL statement containing an SQL command to create a table.
     * If the SQL statement is not a create statement, it should return false.
     *
     * @param sql the SQL statement containing the create command
     * @return true if the command was successfully executed, else false
     */
    protected boolean createTable(String sql) {
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
