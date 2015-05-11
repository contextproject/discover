package models.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Connector to the database.
 */
public class DatabaseConnector {

    /**
     * Connection object to the database.
     */
    private Connection connection;

    /**
     * Statement object of the connection.
     */
    private Statement statement;

    /**
     * Constructor.
     */
    public DatabaseConnector() {
        loadDrivers();
        makeConnection();
    }

    /**
     * Executes the given query.
     *
     * @param query The query to be executed
     */
    public final void executeUpdate(final String query) {
        try {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes the given query and return the result.
     *
     * @param query The query to be executed
     * @return The result of the query
     */
    public final ResultSet executeQuery(final String query) {
        ResultSet result = null;
        try {
            result =  statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Loading the drivers to connect to a MySQL database.
     */
    public final void loadDrivers() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot find the driver in the classpath!", e);
        }
    }

    /**
     * Make a connection with the database.
     */
    public final void makeConnection() {
        try {
            String url = "jdbc:mysql://localhost:3306/contextbase";
            String username = "context";
            String password = "password";

            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect the database!", e);
        }
    }

    /**
     * Close the connection with the database.
     */
    public final void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ignore) {
            }
        }
    }

    /**
     * Getter of the statement object.
     *
     * @return The statement object of the database connection
     */
    public final Statement getStatement() {
        return statement;
    }

    /**
     * Getter of the connection object.
     *
     * @return The connection object to the database
     */
    public final Connection getConnection() {
        return connection;
    }
}
