package models.utility;

import static org.junit.Assert.assertTrue;

import models.database.DatabaseConnector;
import models.record.Track;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class used to unit test the RandomTrackStack class.
 */
public class RandomTrackStackTest {

    /**
     * The objects under testing.
     */
    private RandomTrackStack rts1, rts2;
    
    /**
     * Setting up the right objects needed for testing.
     */
    @Before
    public void setUp() {
        DatabaseConnector databaseConnector = DatabaseConnector.getConnector();
        databaseConnector.loadDrivers();
        databaseConnector.makeConnection("jdbc:mysql://188.166.78.36/contextbase", "context", "password");
        
        rts1 = new RandomTrackStack(2, true);
        rts2 = new RandomTrackStack(2, false);
    }

    /**
     * Test for the size of the initial object.
     */
    @Test
    public void testLimit() {
        assertTrue(rts1.size() == 2);
    }
    
    /**
     * Test for the pop(int) method of the class.
     */
    @Test
    public void testPopMultiple() {
        rts1.pop(2);
        assertTrue(rts1.isEmpty());
    }
    
    /**
     * Test for the pop(int) method of the class.
     */
    @Test
    public void testPopZero() {
        assertTrue(rts1.pop(0).isEmpty());
    }
    
    /**
     * Test for the refill functionality of the object.
     */
    @Test
    public void testAutoFillTrue() {
        assertTrue(rts1.pop(3).size() == 3);
    }
    
    /**
     * Test for the refill functionality of the object.
     */
    @Test (expected = NullPointerException.class)
    public void testAutoFillFalse() {
        rts2.pop(3);
    }
    
    /**
     * Test for the push() method of the class.
     */
    @Test
    public void testPush() {
        rts2.push(new Track());
        assertTrue(rts2.size() == 3);
    }
}
