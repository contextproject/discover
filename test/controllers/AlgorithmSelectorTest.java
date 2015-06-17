package controllers;

import models.database.DatabaseConnector;
import models.record.Key;
import models.record.Track;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Test for the AlgorithmSelector class.
 */
public class AlgorithmSelectorTest {

    /**
     * A Track object.
     */
    private Track track;

    /**
     * Set up.
     * @throws Exception If the set up fails.
     */
    @Before
    public void setUp() throws Exception {
        DatabaseConnector databaseConnector = DatabaseConnector.getConnector();
        databaseConnector.loadDrivers();
        databaseConnector.makeConnection("jdbc:mysql://188.166.78.36/contextbase", "context", "password");

        track = new Track();
        track.put(new Key<>("ID", Integer.class), 1);
        track.put(new Key<>("DURATION", Integer.class), 50000);
    }
    
    /**
     * Does some clean up.
     * @throws Exception If the clean up fails.
     */
    @After
    public void tearDown() throws Exception {
        DatabaseConnector.getConnector().closeConnection();
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
        int start = AlgorithmSelector.determineStart(track).getStartTime();

        assertTrue(track.get(new Key<>("DURATION", Integer.class)) >= start);
        assertTrue(0 <= start);
    }
}