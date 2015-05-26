package models.mix;

import basic.BasicTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static models.mix.MixSplitterTest.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * This class tests the Shingle class.
 * 
 * @since 26-05-2015
 * @version 26-05-2015
 * 
 * @author stefan boodt
 * @author arthur hovenesyan
 *
 */
public class ShingleTest extends BasicTest {

    /**
     * The shingle under test.
     */
    private Shingle shingle;
    
    @Override
    public void setUp() throws Exception {
        super.setUp();
        setShingle(new Shingle(new ArrayList<Float>()));
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
    
    /**
     * Sets the Shingle under test.
     * @param shingle The shingle to test.
     */
    public void setShingle(final Shingle shingle) {
        this.setObjectUnderTest(shingle);
        this.shingle = shingle;
    }
    
    /**
     * Get Shingle under test.
     * @return The shingle under test.
     */
    public Shingle getShingle() {
        return shingle;
    }

    /**
     * Tests the {@link Shingle#getData()} and {@link Shingle#setData(List)}
     * methods.
     */
    @Test
    public void testGetAndSet() {
        final List<Float> expected = asList(3.2f, 1.1f, -10.2f);
        getShingle().setData(expected);
        assertEquals(expected, getShingle().getData());
    }
    
    /**
     * Tests the {@link Shingle#copy()} method.
     */
    @Test
    public void testCopy() {
        assertFalse(getShingle().copy() == getShingle());
    }
    
    /**
     * Tests the {@link Shingle#copy()} method. This check checks if altering
     * of the copy, results in altering of the original.
     */
    @Test
    public void testCopyIsDeep() {
        final Shingle s = getShingle();
        final int expected = s.size();
        final Shingle copy = s.copy();
        copy.add(1.0f);
        assertEquals(expected, s.size());
    }

    /**
     * Tests the {@link Shingle#jaccardDistance(Shingle)} method.
     */
    @Test
    public void testGetJaccardDistanceBothEmpty() {
        final Shingle empty = new Shingle(new ArrayList<Float>());
        final double expected = 0.0;
        final double delta = 0.001;
        assertEquals(expected, empty.jaccardDistance(empty),
                delta);
    }

    /**
     * Tests the {@link Shingle#jaccardDistance(Shingle)} method.
     */
    @Test
    public void testGetJaccardDistanceFirstEmpty() {
        final Shingle empty = new Shingle(new ArrayList<Float>());
        final Shingle second = new Shingle(asList(3.9f, 2.1f, 1.0f));
        final double expected = 1.0;
        final double delta = 0.001;
        assertEquals(expected, empty.jaccardDistance(second),
                delta);
    }

    /**
     * Tests the {@link Shingle#jaccardDistance(Shingle)} method.
     */
    @Test
    public void testGetJaccardDistanceSecondEmpty() {
        final Shingle empty = new Shingle(new ArrayList<Float>());
        final Shingle first = new Shingle(asList(3.0f, 2.1f, 1.1f));
        final double expected = 1.0;
        final double delta = 0.001;
        assertEquals(expected, first.jaccardDistance(empty),
                delta);
    }

    /**
     * Tests the {@link Shingle#jaccardDistance(Shingle)} method.
     */
    @Test
    public void testGetJaccardDistance() {
        final Shingle first = new Shingle(asList(3.0f, 1.1f, 4.3f, 88.6f, 2.1f));
        final Shingle second = new Shingle(asList(8.0f, 2.1f, 1.1f));
        final double expected = (double) 4 / 6;
        final double delta = 0.001;
        assertEquals(expected, first.jaccardDistance(second),
                delta);
    }

    /**
     * Tests the {@link Shingle#jaccardDistance(Shingle)} method.
     */
    @Test
    public void testGetJaccardDistanceAgain() {
        final Shingle second = new Shingle(asList(0.0f, 1.2f, 4.3f, 88.6f, -2.2f));
        final Shingle first = new Shingle(asList(3.0f, 2.1f, 1.1f, 0.0f));
        final double expected = 0.875;
        final double delta = 0.001;
        assertEquals(expected, first.jaccardDistance(second),
                delta);
    }

    /**
     * Tests the {@link Shingle#jaccardDistance(Shingle)} method.
     */
    @Test
    public void testGetJaccardDistanceNoIntersect() {
        final Shingle first = new Shingle(asList(3.0f, -10.2f, 1.2f, 4.3f, 88.6f, -2.2f));
        final Shingle second = new Shingle(asList(3.3f, 2.1f, 1.1f, 0.0f, 100.1f));
        final double expected = 1.0;
        final double delta = 0.001;
        assertEquals(expected, first.jaccardDistance(second),
                delta);
    }

    /**
     * Tests the {@link Shingle#jaccardDistance(Shingle)} method.
     */
    @Test
    public void testGetJaccardDistanceDoublesInLists() {
        final Shingle first = new Shingle(asList(3.0f, 2.1f, 3.0f));
        final Shingle second = new Shingle(asList(4.2f, 3.0f, 6.1f));
        final double expected = 0.75;
        final double delta = 0.001;
        assertEquals(expected, first.jaccardDistance(second),
                delta);
    }

    /**
     * Tests the {@link Shingle#jaccardDistance(Shingle)} method.
     */
    @Test
    public void testGetJaccardDistanceDoublesInBothLists() {
        final Shingle first = new Shingle(asList(3.0f, 2.1f, 3.0f));
        final Shingle second = new Shingle(asList(4.2f, 3.0f, 4.2f, 6.1f, 3.0f));
        final double expected = 0.75;
        final double delta = 0.001;
        assertEquals(expected, first.jaccardDistance(second),
                delta);
    }
}
