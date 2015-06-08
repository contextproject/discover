package models.utility;

import models.database.DatabaseConnector;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for the TrackList class.
 */
public class TrackListTest {

    /**
     * DatabaseConnector object.
     */
    DatabaseConnector databaseConnector;

    /**
     * Set up.
     * @throws Exception If the Exception fails.
     */
    @Before
    public void setUp() throws Exception {
        databaseConnector = DatabaseConnector.getConnector();
        databaseConnector.loadDrivers();
        databaseConnector.makeConnection("jdbc:mysql://188.166.78.36/contextbase", "context", "password");
    }
    
    /**
     * Does some clean up.
     * @throws Exception If the clean up fails.
     */
    @After
    public void tearDown() {
        databaseConnector.closeConnection();
    }

    /**
     * Test for the constructor.
     */
    @Test
    public void testTrackList() {
        assertNotNull(new TrackList());
    }

    /**
     * Test for the constructor.
     */
    @Test
    public void testTrackList2() {
        TrackList trackList = new TrackList(databaseConnector.executeQuery("SELECT * FROM tracks LIMIT 1"));
        assertNotNull(trackList);
        assertEquals(1, trackList.size());
    }
}