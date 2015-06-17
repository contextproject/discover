package models.score;

import basic.BasicTest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Abstract test for all the SCORE storages.
 * 
 * @since 03-06-2015
 * @version 03-06-2015
 * 
 * @see ScoreStorage
 * @see BasicTest
 * 
 * @author stefan boodt
 *
 */
public abstract class ScoreStorageTest extends BasicTest {

    /**
     * The storage used for testing.
     */
    private ScoreStorage storage;
    
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }
    
    /**
     * REturns the storage under test.
     * @return The storage under test.
     */
    protected ScoreStorage getStorage() {
        return storage;
    }
    
    /**
     * Sets the storage under test.
     * @param storage The new storage under test.
     */
    protected void setStorage(final ScoreStorage storage) {
        setObjectUnderTest(storage);
        this.storage = storage;
    }

    /**
     * Tests the {@link ScoreStorage#isEmpty()} method.
     */
    @Test
    public void testIsEmpty() {
        getStorage().isEmpty();
    }
    
    /**
     * Tests the {@link ScoreStorage#isEmpty()} and {@link ScoreStorage#clear()}
     * methods.
     */
    @Test
    public void testIsEmptyAfterClear() {
        final ScoreStorage s = getStorage();
        s.add(1, 10);
        s.clear();
        assertTrue(s.isEmpty());
    }
    
    /**
     * Tests the {@link ScoreStorage#add(int, int)} method.
     */
    @Test
    public void testAdd() {
        final ScoreStorage s = getStorage();
        s.add(1, 10);
        assertFalse(s.isEmpty());
    }
    
    /**
     * Tests the {@link ScoreStorage#add(int, int)} method.
     */
    @Test
    public void testSize() {
        final ScoreStorage s = getStorage();
        s.add(1, 10);
        assertEquals(1, s.size());
    }
    
    /**
     * Tests the {@link ScoreStorage#size()} method.
     */
    @Test
    public void testSizeClear() {
        final ScoreStorage s = getStorage();
        s.add(1, 10);
        s.clear();
        assertEquals(0, s.size());
    }
    
    /**
     * Tests the {@link ScoreStorage#add(int, int)} and {@link ScoreStorage#size()} methods.
     */
    @Test
    public void testSizeDuplicate() {
        final ScoreStorage s = getStorage();
        s.add(1, 10);
        s.add(2, 11);
        s.add(1, 20);
        assertEquals(2, s.size());
    }
    
    /**
     * Tests the {@link ScoreStorage#get(int)} method.
     */
    @Test
    public void testGet() {
        final ScoreStorage s = getStorage();
        s.add(1, 10);
        s.add(2, 11);
        s.add(1, 20);
        assertEquals(11, s.get(2));
    }
    
    /**
     * Tests the {@link ScoreStorage#get(int)} and {@link ScoreStorage#add(int, int)} methods.
     */
    @Test
    public void testGetDuplicate() {
        final ScoreStorage s = getStorage();
        s.add(1, 10);
        s.add(2, 11);
        s.add(1, 20);
        assertEquals(30, s.get(1));
    }
    
    /**
     * Tests the {@link ScoreStorage#get(int)} method.
     */
    @Test
    public void testGetNotFound() {
        final ScoreStorage s = getStorage();
        s.add(1, 10);
        assertEquals(0, s.get(-199));
    }
    
    /**
     * Tests the {@link ScoreStorage#maxScoreStartTime()} method.
     */
    @Test
    public void testMaxScoreStartTime() {
        final ScoreStorage s = getStorage();
        s.add(1, 10);
        assertEquals(1, s.maxScoreStartTime());
    }
    
    /**
     * Tests the {@link ScoreStorage#maxScoreStartTime()} method.
     */
    @Test
    public void testMaxScoreStartTimeDuplicateValue() {
        final ScoreStorage s = getStorage();
        s.add(1, 8);
        s.add(2, 10);
        s.add(3, 10);
        s.add(4, 1);
        assertEquals(2, s.maxScoreStartTime());
    }
    
    /**
     * Tests the {@link ScoreStorage#maxScoreStartTime()} method.
     */
    @Test
    public void testMaxScoreStartTimeEmpty() {
        final ScoreStorage s = getStorage();
        assertEquals(-1, s.maxScoreStartTime());
    }
    
    /**
     * Tests the {@link ScoreStorage#maxScoreStartTime()} and
     * {@link ScoreStorage#add(int, int)} methods.
     */
    @Test
    public void testMaxScoreStartTimeDoubleAdd() {
        final ScoreStorage s = getStorage();
        s.add(1, 8);
        s.add(2, 10);
        s.add(3, 10);
        s.add(4, 1);
        s.add(1, 3);
        assertEquals(1, s.maxScoreStartTime());
    }
    
    /**
     * Tests the {@link ScoreStorage#maxScore()} method.
     */
    @Test
    public void testMaxScore() {
        final ScoreStorage s = getStorage();
        s.add(1, 10);
        assertEquals(10, s.maxScore());
    }
    
    /**
     * Tests the {@link ScoreStorage#maxScore()} and
     * {@link ScoreStorage#add(int, int)} methods.
     */
    @Test
    public void testMaxScoreDoubleAdd() {
        final ScoreStorage s = getStorage();
        s.add(1, 8);
        s.add(2, 10);
        s.add(3, 10);
        s.add(4, 1);
        s.add(1, 3);
        assertEquals(11, s.maxScore());
    }
    
    /**
     * Tests the {@link ScoreStorage#maxScore()} method.
     */
    @Test
    public void testMaxScoreEmpty() {
        final ScoreStorage s = getStorage();
        assertEquals(0, s.maxScore());
    }
    
    /**
     * Tests the {@link ScoreStorage#maxScore()} method.
     */
    @Test
    public void testMaxScoreMinValuesOnly() {
        final ScoreStorage s = getStorage();
        s.add(1, Integer.MIN_VALUE);
        s.add(2, Integer.MIN_VALUE);
        assertEquals(Integer.MIN_VALUE, s.maxScore()); // because of the default get.
    }
    
    /**
     * Tests the {@link ScoreStorage#maxScore()} method.
     */
    @Test
    public void testMaxScoreNegativeScores() {
        final ScoreStorage s = getStorage();
        s.add(1, -10);
        s.add(2, -4);
        s.add(3, -7);
        assertEquals(-4, s.maxScore());
    }
    
    /**
     * Tests the {@link ScoreStorage#maxScoreStartTime(int, int)} method.
     */
    @Test
    public void testMaxScoreWithBounds() {
        final ScoreStorage s = getStorage();
        s.add(1, 100);
        s.add(20, -30);
        s.add(40, 111);
        assertEquals(20, s.maxScoreStartTime(10, 30));
    }
    
    /**
     * Tests the {@link ScoreStorage#maxScoreStartTime(int, int)} method.
     */
    @Test
    public void testMaxScoreWithBoundsSameValue() {
        final ScoreStorage s = getStorage();
        s.add(1, 100);
        s.add(20, -30);
        s.add(40, 111);
        assertEquals(20, s.maxScoreStartTime(20, 20));
    }
    
    /**
     * Tests the {@link ScoreStorage#maxScoreStartTime(int, int)} method.
     */
    @Test
    public void testMaxScoreWithBoundsSameValueWithMinimalValue() {
        final ScoreStorage s = getStorage();
        s.add(1, 100);
        s.add(20, Integer.MIN_VALUE);
        s.add(40, 111);
        assertEquals(20, s.maxScoreStartTime(20, 20));
    }
    
    /**
     * Tests the {@link ScoreStorage#maxScoreStartTime(int, int)} method.
     */
    @Test
    public void testMaxScoreWithBoundsUpperBoundBeforeLowerBound() {
        final ScoreStorage s = getStorage();
        s.add(1, 100);
        s.add(20, -30);
        s.add(40, 111);
        assertEquals(-1, s.maxScoreStartTime(30, 10));
    }
}
