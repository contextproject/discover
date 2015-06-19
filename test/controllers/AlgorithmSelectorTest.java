package controllers;

import java.util.ArrayList;
import java.util.List;

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
    private Track track, ntrack;
    
    /**
     * Set up.
     * @throws Exception If the set up fails.
     */
    @Before
    public void setUp() throws Exception {
        DatabaseConnector databaseConnector = DatabaseConnector.getConnector();
        databaseConnector.loadDrivers();
        databaseConnector.makeConnection("jdbc:mysql://188.166.78.36/contextbase", "context", "password");

        ntrack = new Track();
        ntrack.put(new Key<>("id", Integer.class), 1);
        ntrack.put(new Key<>("duration", Integer.class), 0);
        track = new Track();
        track.put(new Key<>("id", Integer.class), 1);
        track.put(new Key<>("duration", Integer.class), 50000);
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
     * Gets an empty waveform.
     * @return The waveform.
     */
    protected List<Double> getWaveform() {
        return new ArrayList<Double>();
    }

    /**
     * Test of the determineStart() method.
     */
    @Test
    public void testDetermineStartAUTO() {
        AlgorithmSelector.setMode("auto");
        assertNotNull(AlgorithmSelector.determineStart(track, getWaveform()));
    }
    
    /**
     * Test of the determineStart() method.
     */
    @Test
    public void testDetermineStartAUTO_Zero() {
        AlgorithmSelector.setMode("auto");
        assertNotNull(AlgorithmSelector.determineStart(ntrack, getWaveform()));
    }
    
    /**
     * Test of the determineStart() method.
     */
    @Test
    public void testDetermineStartCONTENT() {
        AlgorithmSelector.setMode("content");
        assertNotNull(AlgorithmSelector.determineStart(track, getWaveform()));
    }
    
    /**
     * Test of the determineStart() method.
     */
    @Test
    public void testDetermineStartRANDOM() {
        AlgorithmSelector.setMode("random");
        assertNotNull(AlgorithmSelector.determineStart(track, getWaveform()));
    }

    /**
     * Test of the random() method.
     */
    @Test
    public void testRandom() {
        int start = AlgorithmSelector.determineStart(track, getWaveform()).getStartTime();
        assertTrue(track.get(new Key<>("duration", Integer.class)) >= start);
        assertTrue(0 <= start);
    }
}