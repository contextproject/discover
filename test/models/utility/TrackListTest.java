package models.utility;

import models.database.DatabaseConnector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for the TrackList class.
 */
public class TrackListTest {

    DatabaseConnector databaseConnector;

    @Before
    public void setUp() {
        databaseConnector = new DatabaseConnector();
        databaseConnector.loadDrivers();
        databaseConnector.makeConnection("jdbc:mysql://188.166.78.36/contextbase", "context", "password");
    }

    @Test
    public void testTrackList() {
        assertNotNull(new TrackList());
    }

    @Test
    public void testTrackList2() {
        TrackList trackList = new TrackList(databaseConnector.executeQuery("SELECT * FROM tracks LIMIT 1"));
        assertNotNull(trackList);
        assertEquals(1, trackList.size());
    }
}