package models.record;

import controllers.Application;
import models.database.DatabaseConnector;
import org.junit.Before;
import org.junit.Test;

import java.io.PrintStream;
import java.sql.ResultSet;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Tests for the Track class.
 */
public class TrackTest {

    DatabaseConnector databaseConnector;

    /**
     * Set up.
     */
    @Before
    public void setUp() {
        databaseConnector = new DatabaseConnector();
        databaseConnector.loadDrivers();
        databaseConnector.makeConnection("jdbc:mysql://188.166.78.36/contextbase", "context", "password");
    }

    /**
     * Test for the process() method.
     */
    @Test
    public void testProcess1() {
        ResultSet resultSet = databaseConnector.executeQuery("SELECT * FROM tracks LIMIT 1");
        Track track = new Track();
        assertTrue(track.process(resultSet));
    }

    /**
     * Test for the process() method.
     */
    @Test
    public void testProcess2() {
        ResultSet resultSet = databaseConnector.executeQuery("SELECT * FROM comments LIMIT 1");
        Track track = new Track();
        assertFalse(track.process(resultSet));
    }
}
