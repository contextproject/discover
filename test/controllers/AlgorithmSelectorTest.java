package controllers;

import models.database.DatabaseConnector;
import models.record.Track;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AlgorithmSelectorTest {

    /**
     * A Track object
     */
    private Track track;

    @Before
    public void setUp() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        databaseConnector.loadDrivers();
        databaseConnector.makeConnection("jdbc:mysql://188.166.78.36/contextbase", "context", "password");
        Application.setDatabaseConnector(databaseConnector);

        track = new Track();
        track.setTrackid(1);
        track.setDuration(50000);
    }

    @Test
    public void testDetermineStart() {
        assertNotNull(AlgorithmSelector.determineStart(track));
    }

    @Test
    public void testRandom() {
        int start = AlgorithmSelector.determineStart(track);

        assertTrue(track.getDuration() >= start);
        assertTrue(0  <= start);
    }
}