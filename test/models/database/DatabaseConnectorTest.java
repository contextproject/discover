package models.database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


/**
 * Test class for the Database class
 */
public class DatabaseConnectorTest {

    private DatabaseConnector databaseConnector;

    /**
     * Does some set up.
     * @throws Exception If the set up fails.
     */
    @Before
    public void setUp() throws Exception {
        databaseConnector = DatabaseConnector.getConnector();
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

    @Test
    public void executeEmptyUpdateTest() throws Exception {
        assertFalse(databaseConnector.executeUpdate(""));
    }

    @Test
    public void executeUpdateTest() throws Exception {
        DatabaseConnector mockedDBC = DatabaseConnector.getConnector();
        Statement mockedStatement = mock(Statement.class);
        mockedDBC.setStatement(mockedStatement);
        String query = "INSERT INTO table VALUES (1, 1, 1, '2015-01-01 12:00:00', 1, \"test\")";
        mockedDBC.executeUpdate(query);
        verify(mockedStatement).executeUpdate(query);
    }

    @Test
    public void executeUpdateExceptionTest() throws Exception {
        assertFalse(databaseConnector.executeUpdate("INVALID SQL QUERY"));
    }

    @Test
    public void executeQueryTest() throws Exception {
        assertNotNull(databaseConnector.executeQuery("SELECT * FROM comments LIMIT 1"));
    }

    @Test
    public void executeQueryExceptionTest() throws Exception {
        assertNull(databaseConnector.executeQuery(""));
    }
    
    /**
     * Does some clean up.
     * @throws Exception If the clean up fails.
     */
    @After
    public void tearDown() throws Exception {
        databaseConnector.closeConnection();
    }
}