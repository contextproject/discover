package models.score;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.TreeMap;

import org.junit.Test;

/**
 * Tests the ScoreMap class.
 * 
 * @since 03-06-2015
 * @version 03-06-2015
 * 
 * @see ScoreMap
 * 
 * @author stefan boodt
 *
 */
public class ScoreMapTest extends ScoreStorageTest {

    /**
     * The ScoreMap under test.
     */
    private ScoreMap storage;
    
    @Override
    public void setUp() throws Exception {
        super.setUp();
        storage = new ScoreMap();
        setStorage(storage);
    }
    
    /**
     * Sets the storage under test.
     * @param map The score map under test.
     */
    public void setStorage(final ScoreMap map) {
        super.setStorage(map);
        this.storage = map;
    }
    
    @Override
    public ScoreMap getStorage() {
        return storage;
    }
    
    /**
     * Tests the {@link ScoreMap#setScores(java.util.SortedMap)} method.
     */
    @Test
    public void testSetScores() {
        ScoreMap map = getStorage();
        final TreeMap<Integer, Integer> expected = new TreeMap<Integer, Integer>();
        final TreeMap<Integer, Integer> putted = new TreeMap<Integer, Integer>();
        putted.put(1, 1);
        putted.put(3, 20);
        map.setScores(putted);
        assertEquals(expected, map.getScores());
    }
    
    /**
     * Tests the {@link ScoreMap#setScores(java.util.SortedMap)} method.
     */
    @Test
    public void testSetScoresSize() {
        ScoreMap map = getStorage();
        final TreeMap<Integer, Integer> putted = new TreeMap<Integer, Integer>();
        putted.put(1, 1);
        putted.put(3, 20);
        map.setScores(putted);
        assertEquals(0, map.size());
    }
    
    /**
     * Tests the {@link ScoreMap#setScores(java.util.SortedMap)} method.
     */
    @Test
    public void testSetScoresIsEmpty() {
        ScoreMap map = getStorage();
        final TreeMap<Integer, Integer> putted = new TreeMap<Integer, Integer>();
        putted.put(1, 1);
        putted.put(3, 20);
        map.setScores(putted);
        assertTrue(map.isEmpty());
    }
}
