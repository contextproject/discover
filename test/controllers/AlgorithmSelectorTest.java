package controllers;

import models.database.DatabaseConnector;
import models.record.Key;
import models.record.Track;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test for the AlgorithmSelector class.
 */
public class AlgorithmSelectorTest {

    /**
     * A Track object
     */
    private Track track;

    /**
     * Set up.
     */
    @Before
    public void setUp() {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        databaseConnector.loadDrivers();
        databaseConnector.makeConnection("jdbc:mysql://188.166.78.36/contextbase", "context", "password");
        Application.setDatabaseConnector(databaseConnector);

        track = new Track();
        track.put(new Key<>("id", Integer.class), 1);
        track.put(new Key<>("duration", Integer.class), 50000);
    }

    /**
     * Test of the determineStart() method.
     */
    @Test
    public void testDetermineStart() {
        assertNotNull(AlgorithmSelector.determineStart(track));
    }

    /**
     * Tets of the random() method.
     */
    @Test
    public void testRandom() {
        int start = AlgorithmSelector.determineStart(track);

        assertTrue(track.get(new Key<>("duration", Integer.class)) >= start);
        assertTrue(0 <= start);
    }
}