package models.database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;


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

    @Test
    public void executeEmptyUpdateTest() throws Exception {
        DatabaseConnector mockedDBC = mock(DatabaseConnector.class);
        mockedDBC.executeUpdate("");
        verifyZeroInteractions(mockedDBC);
    }

    @Test
    public void executeUpdateTest() throws Exception {
        DatabaseConnector mockedDBC = mock(DatabaseConnector.class);
        Statement mockedStatement = mock(Statement.class);
        mockedDBC.setStatement(mockedStatement);
        String query = "INSERT INTO table VALUES (1, 1, 1, '2015-01-01 12:00:00', 1, \"test\")";
        mockedDBC.executeUpdate(query);
        verify(mockedStatement).executeUpdate(query);
    }

    @After
    public void tearDown() throws SQLException {
        databaseConnector.closeConnection();
    }
}