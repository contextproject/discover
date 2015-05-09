package models.database;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for the Database class
 */
public class DatabaseConnectorTest {

    private DatabaseConnector databaseConnector;

    @Before
    public void setUp() throws Exception {
        databaseConnector = new DatabaseConnector();
    }

    @Test
    public void executeUpdateTest() throws SQLException {
        databaseConnector.executeUpdate("CREATE TABLE test (columna TEXT, columnb INT)");

        DatabaseMetaData dbm = databaseConnector.getConnection().getMetaData();
        ResultSet tables = dbm.getTables(null, null, "employee", null);
        assertTrue(tables.next());

        databaseConnector.executeUpdate("DROP TABLE test");
    }

    @Test
    public void executeQueryTest() throws SQLException {
        databaseConnector.executeUpdate("CREATE TABLE test (columna INT)");
        databaseConnector.executeUpdate("INSERT INTO test VALUES (1)");

        ResultSet resultSet = databaseConnector.executeQuery("SELECT * FROM test");
        assertTrue(resultSet.next());
        assertEquals(1, resultSet.getInt("columna"));

        databaseConnector.executeUpdate("DROP TABLE test");
    }


    @After
    public void tearDown() {
        databaseConnector.closeConnection();
    }
}