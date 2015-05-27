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
        setShingle(new Shingle(new ArrayList<Float>()));
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

    /**
     * Tests the {@link Shingle#jaccardDistance(Shingle)} method.
     */
    @Test
    public void testGetJaccardDistance2() {
        final Shingle first = new Shingle(asList(3.0f, 1.1f, 4.3f, 88.6f, 2.1f));
        final Shingle second = new Shingle(asList(8.0f, 2.1f, 1.1f, 1.1f, 20.3f, 3.0f, 12.9f));
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
        final Shingle first = new Shingle(asList(3.0f, 3.0f, 3.0f, 2.1f));
        final Shingle second = new Shingle(asList(2.99f, 8.0f, 2.1f, 1.1f, 20.3f, 3.0f, 12.9f));
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
        final List<Float> floats = asList(3.0f, 2.1f);
        shingle.setData(floats);
        final String expected = "Shingle(3.0, 2.1)";
        assertEquals(expected, shingle.toString());
    }
    
    /**
     * Tests the {@link Shingle#toString()} method.
     */
    @Test
    public void testToStringEmpty() {
        final List<Float> floats = asList();
        shingle.setData(floats);
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
        final float num = 4.0f;
        other.add(num);
        assertFalse(shingle.equals(other));
    }
    
    /**
     * Tests the {@link Shingle#equals(Object)} method.
     */
    @Test
    public void testEqualsDifferentLists() {
        final Shingle other = new Shingle(asList(1.0f, 0.0f));
        final List<Float> floats = asList(2.0f, 1.0f);
        shingle.setData(floats);
        assertFalse(shingle.equals(other));
    }
    
    /**
     * Tests the {@link Shingle#union(Shingle)} method.
     */
    @Test
    public void testUnion() {
        final Shingle other = new Shingle(asList(1.0f, 0.0f));
        final List<Float> floats = asList(2.0f, 1.0f);
        shingle.setData(floats);
        final Shingle union = new Shingle(asList(2.0f, 1.0f, 0.0f));
        assertEquals(union, shingle.union(other));
    }
    
    /**
     * Tests the {@link Shingle#union(Shingle)} method.
     */
    @Test
    public void testUnionBothEmpty() {
        final List<Float> empty = asList();
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
        final List<Float> empty = asList();
        final Shingle other = new Shingle(asList(1.0f, 2.0f));
        shingle.setData(empty);
        final Shingle union = other;
        assertEquals(union, shingle.union(other));
    }
    
    /**
     * Tests the {@link Shingle#union(Shingle)} method.
     */
    @Test
    public void testUnionOtherEmpty() {
        final List<Float> empty = asList();
        final Shingle other = new Shingle(empty);
        shingle.setData(asList(1.0f));
        final Shingle union = shingle;
        assertEquals(union, shingle.union(other));
    }
    
    /**
     * Tests the {@link Shingle#union(Shingle)} method to see if the
     * current Shingle changes value, which it should not.
     */
    @Test
    public void testUnionAltered() {
        final List<Float> list = asList(1.0f);
        final Shingle other = new Shingle(list);
        shingle.setData(asList(2.0f));
        final List<Float> expected = shingle.getData();
        shingle.union(other);
        assertEquals(expected, shingle.getData());
    }
    
    /**
     * Tests the {@link Shingle#union(Shingle)} method to see if the
     * current Shingle changes value, which it should not.
     */
    @Test
    public void testUnionAlteredWithDoubles() {
        final List<Float> list = asList(1.0f);
        final Shingle other = new Shingle(list);
        shingle.setData(asList(2.0f, 2.0f));
        final List<Float> expected = shingle.getData();
        shingle.union(other);
        assertEquals(expected, shingle.getData());
    }
    
    /**
     * Tests the {@link Shingle#union(Shingle)} method to see if the
     * current Shingle changes value, which it should not.
     */
    @Test
    public void testUnionWithDoubles() {
        final List<Float> list = asList(1.0f);
        final Shingle other = new Shingle(list);
        shingle.setData(asList(2.0f, 2.0f));
        final Shingle expected = new Shingle(asList(1.0f, 2.0f));
        assertEquals(expected, shingle.union(other));
    }
    
    /**
     * Tests the {@link Shingle#unique()} method.
     */
    @Test
    public void testUniqueEmpty() {
        final List<Float> empty = asList();
        shingle.setData(empty);
        shingle.unique();
        assertEquals(empty, shingle.getData());
    }
    
    /**
     * Tests the {@link Shingle#unique()} method.
     */
    @Test
    public void testUniqueNoDoubles() {
        final List<Float> nums = asList(2.0f, 1.0f, 0.0f);
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
        shingle.setData(asList(0.0f, 1.0f));
        assertEquals(2, shingle.size());
    }
    
    /**
     * Tests the {@link Shingle#size()} method.
     */
    @Test
    public void testSizeWithDoubles() {
        shingle.setData(asList(0.0f, 1.0f, 2.0f, 1.0f));
        final int expected = 4;
        assertEquals(expected, shingle.size());
    }
    
    /**
     * Tests the {@link Shingle#add(Float)} method.
     */
    @Test
    public void testAdd() {
        shingle.setData(asList());
        shingle.add(0.0f);
        assertEquals(1, shingle.size());
    }
}
