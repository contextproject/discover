package models.utility;

import models.database.DatabaseConnector;
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
     */
    @Before
    public void setUp() {
        databaseConnector = new DatabaseConnector();
        databaseConnector.loadDrivers();
        databaseConnector.makeConnection("jdbc:mysql://188.166.78.36/contextbase", "context", "password");
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