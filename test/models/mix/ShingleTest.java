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
 * @version 27-05-2015
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
        setShingle(new Shingle(new ArrayList<Double>()));
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
        final List<Double> expected = asList(3.2, 1.1, -10.2);
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
        copy.add(1.0);
        assertEquals(expected, s.size());
    }

    /**
     * Tests the {@link Shingle#jaccardDistance(Shingle)} method.
     */
    @Test
    public void testGetJaccardDistanceBothEmpty() {
        final Shingle empty = new Shingle(new ArrayList<Double>());
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
        final Shingle empty = new Shingle(new ArrayList<Double>());
        final Shingle second = new Shingle(asList(3.9, 2.1, 1.0));
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
        final Shingle empty = new Shingle(new ArrayList<Double>());
        final Shingle first = new Shingle(asList(3.0, 2.1, 1.1));
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
        final Shingle first = new Shingle(asList(3.0, 1.1, 4.3, 88.6, 2.1));
        final Shingle second = new Shingle(asList(8.0, 2.1, 1.1));
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
        final Shingle second = new Shingle(asList(0.0, 1.2, 4.3, 88.6, -2.2));
        final Shingle first = new Shingle(asList(3.0, 2.1, 1.1, 0.0));
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
        final Shingle first = new Shingle(asList(3.0, -10.2, 1.2, 4.3, 88.6, -2.2));
        final Shingle second = new Shingle(asList(3.3, 2.1, 1.1, 0.0, 100.1));
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
        final Shingle first = new Shingle(asList(3.0, 2.1, 3.0));
        final Shingle second = new Shingle(asList(4.2, 3.0, 6.1));
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
        final Shingle first = new Shingle(asList(3.0, 2.1, 3.0));
        final Shingle second = new Shingle(asList(4.2, 3.0, 4.2, 6.1, 3.0));
        final double expected = 0.75;
        final double delta = 0.001;
        assertEquals(expected, first.jaccardDistance(second),
                delta);
    }

    /**
     * Tests the {@link Shingle#jaccardDistance(Shingle)} method.
     */
    @Test
    public void testGetJaccardDistance2() {
        final Shingle first = new Shingle(asList(3.0, 1.1, 4.3, 88.6, 2.1));
        final Shingle second = new Shingle(asList(8.0, 2.1, 1.1, 1.1, 20.3, 3.0, 12.9));
        final double expected = (double) 5 / 8;
        final double delta = 0.001;
        assertEquals(expected, first.jaccardDistance(second),
                delta);
    }

    /**
     * Tests the {@link Shingle#jaccardDistance(Shingle)} method.
     */
    @Test
    public void testGetJaccardDistance3() {
        final Shingle first = new Shingle(asList(3.0, 3.0, 3.0, 2.1));
        final Shingle second = new Shingle(asList(2.99, 8.0, 2.1, 1.1, 20.3, 3.0, 12.9));
        final double expected = (double) 5 / 7;
        final double delta = 0.001;
        assertEquals(expected, first.jaccardDistance(second),
                delta);
    }
    
    /**
     * Tests the {@link Shingle#toString()} method.
     */
    @Test
    public void testToString() {
        final List<Double> doubles = asList(3.0, 2.1);
        shingle.setData(doubles);
        final String expected = "Shingle(3.0, 2.1)";
        assertEquals(expected, shingle.toString());
    }
    
    /**
     * Tests the {@link Shingle#toString()} method.
     */
    @Test
    public void testToStringEmpty() {
        final List<Double> doubles = asList();
        shingle.setData(doubles);
        final String expected = "Shingle()";
        assertEquals(expected, shingle.toString());
    }
    
    /**
     * Tests the {@link Shingle#hashCode()} method.
     */
    @Test
    public void testHashCode() {
        assertEquals(shingle.copy().hashCode(), shingle.hashCode());
    }
    
    /**
     * Tests the {@link Shingle#equals(Object)} method.
     */
    @Test
    public void testEqualsOnCopy() {
        assertEquals(shingle.copy().hashCode(), shingle.hashCode());
    }
    
    /**
     * Tests the {@link Shingle#equals(Object)} method. This method is
     * relying on the good working of the {@link Shingle#copy()} method.
     */
    @Test
    public void testEqualsDifferentSizes() {
        final Shingle other = shingle.copy();
        final double num = 4.0;
        other.add(num);
        assertFalse(shingle.equals(other));
    }
    
    /**
     * Tests the {@link Shingle#equals(Object)} method.
     */
    @Test
    public void testEqualsDifferentLists() {
        final Shingle other = new Shingle(asList(1.0, 0.0));
        final List<Double> doubles = asList(2.0, 1.0);
        shingle.setData(doubles);
        assertFalse(shingle.equals(other));
    }
    
    /**
     * Tests the {@link Shingle#union(Shingle)} method.
     */
    @Test
    public void testUnion() {
        final Shingle other = new Shingle(asList(1.0, 0.0));
        final List<Double> doubles = asList(2.0, 1.0);
        shingle.setData(doubles);
        final Shingle union = new Shingle(asList(2.0, 1.0, 0.0));
        assertEquals(union, shingle.union(other));
    }
    
    /**
     * Tests the {@link Shingle#union(Shingle)} method.
     */
    @Test
    public void testUnionBothEmpty() {
        final List<Double> empty = asList();
        final Shingle other = new Shingle(empty);
        shingle.setData(empty);
        final Shingle union = new Shingle(asList());
        assertEquals(union, shingle.union(other));
    }
    
    /**
     * Tests the {@link Shingle#union(Shingle)} method.
     */
    @Test
    public void testUnionThisEmpty() {
        final List<Double> empty = asList();
        final Shingle other = new Shingle(asList(1.0, 2.0));
        shingle.setData(empty);
        final Shingle union = other;
        assertEquals(union, shingle.union(other));
    }
    
    /**
     * Tests the {@link Shingle#union(Shingle)} method.
     */
    @Test
    public void testUnionOtherEmpty() {
        final List<Double> empty = asList();
        final Shingle other = new Shingle(empty);
        shingle.setData(asList(1.0));
        final Shingle union = shingle;
        assertEquals(union, shingle.union(other));
    }
    
    /**
     * Tests the {@link Shingle#union(Shingle)} method to see if the
     * current Shingle changes value, which it should not.
     */
    @Test
    public void testUnionAltered() {
        final List<Double> list = asList(1.0);
        final Shingle other = new Shingle(list);
        shingle.setData(asList(2.0));
        final List<Double> expected = shingle.getData();
        shingle.union(other);
        assertEquals(expected, shingle.getData());
    }
    
    /**
     * Tests the {@link Shingle#union(Shingle)} method to see if the
     * current Shingle changes value, which it should not.
     */
    @Test
    public void testUnionAlteredWithDoubles() {
        final List<Double> list = asList(1.0);
        final Shingle other = new Shingle(list);
        shingle.setData(asList(2.0, 2.0));
        final List<Double> expected = shingle.getData();
        shingle.union(other);
        assertEquals(expected, shingle.getData());
    }
    
    /**
     * Tests the {@link Shingle#union(Shingle)} method to see if the
     * current Shingle changes value, which it should not.
     */
    @Test
    public void testUnionWithDoubles() {
        final List<Double> list = asList(1.0);
        final Shingle other = new Shingle(list);
        shingle.setData(asList(2.0, 2.0));
        final Shingle expected = new Shingle(asList(1.0, 2.0));
        assertEquals(expected, shingle.union(other));
    }
    
    /**
     * Tests the {@link Shingle#unique()} method.
     */
    @Test
    public void testUniqueEmpty() {
        final List<Double> empty = asList();
        shingle.setData(empty);
        shingle.unique();
        assertEquals(empty, shingle.getData());
    }
    
    /**
     * Tests the {@link Shingle#unique()} method.
     */
    @Test
    public void testUniqueNoDoubles() {
        final List<Double> nums = asList(2.0, 1.0, 0.0);
        shingle.setData(nums);
        shingle.unique();
        assertEquals(nums, shingle.getData());
    }
    
    /**
     * Tests the {@link Shingle#size()} method.
     */
    @Test
    public void testSize0() {
        shingle.setData(asList());
        assertEquals(0, shingle.size());
    }
    
    /**
     * Tests the {@link Shingle#size()} method.
     */
    @Test
    public void testSize() {
        shingle.setData(asList(0.0, 1.0));
        assertEquals(2, shingle.size());
    }
    
    /**
     * Tests the {@link Shingle#size()} method.
     */
    @Test
    public void testSizeWithDoubles() {
        shingle.setData(asList(0.0, 1.0, 2.0, 1.0));
        final int expected = 4;
        assertEquals(expected, shingle.size());
    }
    
    /**
     * Tests the {@link Shingle#add(Double)} method.
     */
    @Test
    public void testAdd() {
        shingle.setData(asList());
        shingle.add(0.0);
        assertEquals(1, shingle.size());
    }
}
