package models.database;

import play.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Connector to the database.
 */
public final class DatabaseConnector {

    /**
     * Connection object to the database.
     */
    private Connection connection;

    /**
     * Statement object of the connection.
     */
    private Statement statement;

    /**
     * The database connector instance.
     */
    private static DatabaseConnector connector;

    /**
     * Constructs a new DatabaseConnector.
     */
    private DatabaseConnector() {
    }

    /**
     * Executes the given query.
     *
     * @param query The query to be executed
     * @return true if the update succeeds.
     */
    public boolean executeUpdate(final String query) {
        try {
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Executes the given query and return the result.
     *
     * @param query The query to be executed
     * @return The result of the query
     */
    public ResultSet executeQuery(final String query) {
        ResultSet result = null;
        try {
            result = statement.executeQuery(query);
        } catch (SQLException e) {
            System.err.println("Something went wrong with the following query! " + query);
        }
        return result;
    }

    /**
     * Retrieves the String from the provided column from the provided query.
     *
     * @param query  The query to execute
     * @param column The column name
     * @return The String from the column name
     */
    public String getSingleString(final String query, final String column) {
        ResultSet resultSet = executeQuery(query);
        try {
            if (resultSet != null && resultSet.next()) {
                return resultSet.getString(column);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error when doing query "
                    + query + " on the database.", e);
        }
        return null;
    }

    /**
     * Retrieves the Integer from the provided column from the provided query.
     *
     * @param query  The query to execute
     * @param column The column name
     * @return The String from the column name
     */
    public int getSingleInt(final String query, final String column) {
        ResultSet resultSet = executeQuery(query);
        try {
            if (resultSet != null && resultSet.next()) {
                return resultSet.getInt(column);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Retrieves the Integer from the provided column from the provided query.
     *
     * @param query  The query to execute
     * @param column The column name
     * @return The String from the column name
     */
    public double getSingleDouble(final String query, final String column) {
        ResultSet resultSet = executeQuery(query);
        try {
            if (resultSet != null && resultSet.next()) {
                return resultSet.getDouble(column);
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return 0;
    }


    /**
     * Loading the drivers to connect to a MySQL database.
     */
    public void loadDrivers() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Cannot find the driver in the classpath!", e);
        }
    }

    /**
     * Make a connection with the database.
     *
     * @param url      The url of the MySQL database
     * @param username The username to connect with
     * @param password The password of the username
     */
    public void makeConnection(final String url, final String username,
                               final String password) {
        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
            Logger.info("Database connected established");
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to the database!", e);
        }
    }

    /**
     * Close the connection with the database.
     */
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ignore) {
                ignore.printStackTrace();
            }
        }
    }

    /**
     * Getter of the statement object.
     *
     * @return The statement object of the database connection
     */
    public Statement getStatement() {
        return statement;
    }

    /**
     * Setter of the statement object.
     *
     * @param statement The statement object to set the statement of the database connection
     */
    public void setStatement(final Statement statement) {
        this.statement = statement;
    }

    /**
     * Getter of the connection object.
     *
     * @return The connection object to the database
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Retrieves a database connector to be used.
     *
     * @return A database connector you can use.
     */
    public static DatabaseConnector getConnector() {
        if (connector == null) {
            connector = new DatabaseConnector();
        }
        return connector;
    }
}
