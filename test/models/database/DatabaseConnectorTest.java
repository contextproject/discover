package models.database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.OutputStream;
import java.io.PrintStream;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


/**
 * Test class for the Database class.
 */
public class DatabaseConnectorTest {

    /**
     * Easier reference to the connector.
     */
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
     * Tests the get single string method.
     */
    @Test
    public void testGetSingleStringEmptyQuery() {
        assertNull(databaseConnector.getSingleString("", "track_id"));
    }
    
    /**
     * Tests the get single string method.
     */
    @Test (expected = RuntimeException.class)
    public void testGetSingleStringNotExistingColumn() {
        databaseConnector.getSingleString("SELECT * FROM tracks LIMIT 1", "ksjksbdfnmbjlk");
    }
    
    /**
     * Tests the get single string method.
     */
    @Test
    public void testGetSingleString0Results() {
        assertNull(databaseConnector.getSingleString("SELECT * FROM tracks LIMIT 0", "genre"));
    }
    
    /**
     * Tests the get single string method.
     */
    @Test
    public void testGetSingleString() {
        assertEquals("Rap", databaseConnector.getSingleString(
                "SELECT * FROM tracks WHERE track_id = 32098962", "genre"));
    }
    
    /**
     * Tests the get single string method.
     */
    @Test
    public void testGetSingleInt() {
        assertEquals(32098962, databaseConnector.getSingleInt(
                "SELECT * FROM tracks WHERE track_id = 32098962", "track_id"));
    }
    
    /**
     * Tests the get single string method.
     */
    @Test
    public void testGetSingleIntEmptyQuery() {
        assertEquals(0, databaseConnector.getSingleInt("", "track_id"));
    }
    
    /**
     * Tests the get single string method.
     */
    @Test
    public void testGetSingleIntNotExistingColumn() {
        PrintStream err = System.err;
        OutputStream mock = mock(OutputStream.class);
        System.setErr(new PrintStream(mock));
        assertEquals(0, databaseConnector.getSingleInt("SELECT * FROM tracks LIMIT 1", "ksjksbdfnmbjlk"));
        System.setErr(err);
    }
    
    /**
     * Tests the get single string method.
     */
    @Test
    public void testGetSingleInt0Results() {
        assertEquals(0, databaseConnector.getSingleInt("SELECT * FROM tracks LIMIT 0", "track_id"));
    }
    
    /**
     * Tests the get single string method.
     */
    @Test
    public void testGetSingleDouble() {
        assertEquals(32098962.0, databaseConnector.getSingleDouble(
                "SELECT * FROM tracks WHERE track_id = 32098962", "track_id"), 0.5);
    }
    
    /**
     * Tests the get single string method.
     */
    @Test
    public void testGetSingleDoubleEmptyQuery() {
        assertEquals(0.0, databaseConnector.getSingleDouble("", "track_id"), 0.1);
    }
    
    /**
     * Tests the get single string method.
     */
    @Test
    public void testGetSingleDoubleNotExistingColumn() {
        PrintStream err = System.err;
        OutputStream mock = mock(OutputStream.class);
        System.setErr(new PrintStream(mock));
        assertEquals(0.0, databaseConnector.getSingleDouble("SELECT * FROM tracks LIMIT 1",
                "ksjksbdfnmbjlk"), 0.1);
        System.setErr(err);
    }
    
    /**
     * Tests the get single string method.
     */
    @Test
    public void testGetSingleDouble0Results() {
        assertEquals(0.0, databaseConnector.getSingleDouble("SELECT * FROM tracks LIMIT 0",
                "track_id"), 0.1);
    }
    
    /**
     * Tests what happens when you close the connection twice. Since
     * this is implementation dependant it simply does not check that
     * something random happens but it will be run to see if the program
     * crashes.
     */
    @Test
    public void doubleClose() {
        PrintStream err = System.err;
        PrintStream out = mock(PrintStream.class);
        System.setErr(out);
        databaseConnector.closeConnection();
        databaseConnector.closeConnection();
        System.setErr(err);
        verify(out, atLeast(0)).println(anyString());
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
