package models.database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

/**
 * Test class for the Database class
 */
public class DatabaseConnectorTest {

    private DatabaseConnector databaseConnector;

    @Before
    public void setUp() {
        databaseConnector = new DatabaseConnector();
        databaseConnector.loadDrivers();
        databaseConnector.makeConnection("jdbc:mysql://188.166.78.36/contextbase", "context", "password");
    }

    @Test
    public void connectionTest() throws Exception {
        assertNotNull(databaseConnector.getConnection());
    }

    @Test
    public void statementTest() throws Exception {
        assertNotNull(databaseConnector.getStatement());
    }

    @After
    public void tearDown() throws SQLException {
        databaseConnector.closeConnection();
    }
}