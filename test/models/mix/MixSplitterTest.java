package models.mix;

import basic.BasicTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the MixSplitter class.
 * 
 * @see MixSplitter
 * @see BasicTest
 * 
 * @since 21-05-2015
 * @version 27-05-2015
 * 
 * @author stefan boodt
 * @author arthur hovenesyan
 *
 */
public class MixSplitterTest extends BasicTest {

    /*
     * temporary turn CHECKSTYLE:OFF because of the default trackid that is
     * initialized here.
     */

    /**
     * Default trackid of the tested mixsplitter.
     */
    protected static int DEFAULT_TRACKID;

    // Turn the CHECKSTYLE:ON again for the rest of the file.

    /**
     * The splitter under test.
     */
    private MixSplitter splitter;

    /**
     * Does some set up for the class.
     * 
     * @throws Exception
     *             If the set up fails.
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        DEFAULT_TRACKID = 2431002;
        setSplitter(new MixSplitter(asList(), DEFAULT_TRACKID));
    }

    /**
     * Returns the given numbers as a list.
     * 
     * @param numbers
     *            The numbers to have in the list.
     * @return The list containing the given numbers.
     */
    protected static List<Float> asList(final float... numbers) {
        List<Float> floats = new ArrayList<Float>(numbers.length);
        for (float f : numbers) {
            floats.add(f);
        }
        return floats;
    }

    /**
     * Returns the given numbers as a list.
     * 
     * @param numbers
     *            The numbers to have in the list.
     * @return The list containing the given numbers.
     */
    protected static List<Integer> asListInt(final int... numbers) {
        List<Integer> ints = new ArrayList<Integer>(numbers.length);
        for (int f : numbers) {
            ints.add(f);
        }
        return ints;
    }

    /**
     * Sets the splitter under test.
     * 
     * @param splitter
     *            The new tested splitter.
     */
    public void setSplitter(final MixSplitter splitter) {
        super.setObjectUnderTest(splitter);
        this.splitter = splitter;
    }

    /**
     * Gets the splitter under test.
     * 
     * @return The splitter that is being tested.
     */
    public MixSplitter getSplitter() {
        return splitter;
    }

    /**
     * Tests the {@link MixSplitter#split()} method on an empty dataset.
     */
    @Test
    public void testSplitEmpty() {
        final List<Integer> expected = new ArrayList<Integer>();
        expected.add(0);
        getSplitter().setData(new ArrayList<Float>());
        assertEquals(expected, getSplitter().split());
    }

    /**
     * Tests the {@link MixSplitter#split(List, double, int)} method on an empty dataset.
     */
    @Test
    public void testSplitEmptyHardEmpty() {
        final List<Integer> expected = new ArrayList<Integer>();
        expected.add(0);
        getSplitter().setData(new ArrayList<Float>());
        assertEquals(expected, getSplitter().split(new ArrayList<Shingle>(), 1.0, 1));
    }

    /**
     * Tests the {@link MixSplitter#split(List, double, int)} method.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testSplitNegativeThreshold() {
        getSplitter().split(new ArrayList<Shingle>(), -1.0, 0);
    }
    
    /**
     * Tests the {@link MixSplitter#split(List, double, int)} method.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testSplitTooHighThreshold() {
        getSplitter().split(new ArrayList<Shingle>(), 2.0, 0);
    }
    
    /**
     * Tests the {@link MixSplitter#split(List, double, int)} method.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testSplitNegativeSongTime() {
        getSplitter().split(new ArrayList<Shingle>(), 0.0, -1);
    }
    
    /**
     * Tests the {@link MixSplitter#split(List, double, int)} method.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testSplitSongTimeIs0() {
        getSplitter().split(new ArrayList<Shingle>(), 0.0, 0);
    }

    /**
     * Tests the {@link MixSplitter#split(List, double, int)} method.
     */
    @Test
    public void testSplitThresholdOf0() {
        final List<Integer> expected = asListInt(0, 4, 8, 12, 16);
        final int songtime = 20;
        final float f0 = 10.0f;
        final float f1 = 1.4f;
        final float f2 = 4.2f;
        final float f3 = 1.8f;
        final double threshold = 0.0;
        final List<Shingle> shingles = new ArrayList<Shingle>();
        shingles.add(new Shingle(asList(0.0f, 2.0f, 1.0f)));
        shingles.add(new Shingle(asList(0.0f, f2, 1.0f)));
        shingles.add(new Shingle(asList(0.0f, f0, f1)));
        shingles.add(new Shingle(asList(0.0f, 1.0f, f1)));
        shingles.add(new Shingle(asList(f3, f2, 2.0f)));
        assertEquals(expected, getSplitter().split(shingles, threshold, songtime));
    }

    /**
     * Tests the {@link MixSplitter#split(List, double, int)} method.
     */
    @Test
    public void testSplitThresholdOf0DuplicateShingles() {
        final List<Integer> expected = asListInt(0, 8, 16);
        final int songtime = 20;
        final float f0 = 10.0f;
        final float f1 = 1.4f;
        final float f2 = 4.2f;
        final float f3 = 1.8f;
        final double threshold = 0.0;
        final List<Shingle> shingles = new ArrayList<Shingle>();
        shingles.add(new Shingle(asList(0.0f, 2.0f, 1.0f)));
        shingles.add(new Shingle(asList(0.0f, 2.0f, 1.0f)));
        shingles.add(new Shingle(asList(0.0f, f0, f1)));
        shingles.add(new Shingle(asList(0.0f, f0, f1)));
        shingles.add(new Shingle(asList(f3, f2, 2.0f)));
        assertEquals(expected, getSplitter().split(shingles, threshold, songtime));
    }

    /**
     * Tests the {@link MixSplitter#split(List, double, int)} method.
     */
    @Test
    public void testSplitThresholdOf0NoIntersection() {
        final List<Integer> expected = asListInt(0, 4, 8);
        final int songtime = 12;
        final float f0 = 10.0f;
        final float f1 = 1.4f;
        final float f2 = 4.2f;
        final float f3 = 1.8f;
        final double threshold = 0.0;
        final List<Shingle> shingles = new ArrayList<Shingle>();
        shingles.add(new Shingle(asList(0.0f, 2.0f, 1.0f)));
        shingles.add(new Shingle(asList(f0, f2, f1)));
        shingles.add(new Shingle(asList(0.0f, f3, 1.0f)));
        assertEquals(expected, getSplitter().split(shingles, threshold, songtime));
    }

    /**
     * Tests the {@link MixSplitter#split(List, double, int)} method.
     */
    @Test
    public void testSplitThresholdOfZeroNineNoIntersection() {
        final List<Integer> expected = asListInt(0, 4, 8);
        final int songtime = 12;
        final float f0 = 10.0f;
        final float f1 = 1.4f;
        final float f2 = 4.2f;
        final float f3 = 1.8f;
        final double threshold = 0.9;
        final List<Shingle> shingles = new ArrayList<Shingle>();
        shingles.add(new Shingle(asList(0.0f, 2.0f, 1.0f)));
        shingles.add(new Shingle(asList(f0, f2, f1)));
        shingles.add(new Shingle(asList(0.0f, f3, 1.0f)));
        assertEquals(expected, getSplitter().split(shingles, threshold, songtime));
    }

    /**
     * Tests the {@link MixSplitter#split(List, double, int)} method.
     */
    @Test
    public void testSplitThresholdOf1NoIntersection() {
        final List<Integer> expected = asListInt(0);
        final int songtime = 12;
        final float f0 = 10.0f;
        final float f1 = 1.4f;
        final float f2 = 4.2f;
        final float f3 = 1.8f;
        final double threshold = 1.0;
        final List<Shingle> shingles = new ArrayList<Shingle>();
        shingles.add(new Shingle(asList(0.0f, 2.0f, 1.0f)));
        shingles.add(new Shingle(asList(f0, f2, f1)));
        shingles.add(new Shingle(asList(0.0f, f3, 1.0f)));
        assertEquals(expected, getSplitter().split(shingles, threshold, songtime));
    }

    /**
     * Tests the {@link MixSplitter#split(List, double, int)} method.
     */
    @Test
    public void testSplitThresholdOf1CopiedShingles() {
        final List<Integer> expected = asListInt(0);
        final int songtime = 20;
        final float f0 = 10.0f;
        final float f1 = 1.4f;
        final float f2 = 4.2f;
        final float f3 = 1.8f;
        final double threshold = 1.0;
        final List<Shingle> shingles = new ArrayList<Shingle>();
        shingles.add(new Shingle(asList(0.0f, 2.0f, 1.0f)));
        shingles.add(new Shingle(asList(0.0f, 2.0f, 1.0f)));
        shingles.add(new Shingle(asList(0.0f, f0, f1)));
        shingles.add(new Shingle(asList(0.0f, f0, f1)));
        shingles.add(new Shingle(asList(f3, f2, 2.0f)));
        assertEquals(expected, getSplitter().split(shingles, threshold, songtime));
    }

    /**
     * Tests the {@link MixSplitter#split(List, double, int)} method.
     */
    @Test
    public void testSplitThresholdOf1() {
        final List<Integer> expected = asListInt(0);
        final int songtime = 20;
        final float f0 = 10.0f;
        final float f1 = 1.4f;
        final float f2 = 4.2f;
        final float f3 = 1.8f;
        final double threshold = 1.0;
        final List<Shingle> shingles = new ArrayList<Shingle>();
        shingles.add(new Shingle(asList(0.0f, 2.0f, 1.0f)));
        shingles.add(new Shingle(asList(0.0f, f2, 1.0f)));
        shingles.add(new Shingle(asList(0.0f, f0, f1)));
        shingles.add(new Shingle(asList(0.0f, 1.0f, f1)));
        shingles.add(new Shingle(asList(f3, f2, 2.0f)));
        assertEquals(expected, getSplitter().split(shingles, threshold, songtime));
    }

    /**
     * Tests the {@link MixSplitter#getTrackID()} method.
     */
    @Test
    public void testGetTrackID() {
        assertEquals(DEFAULT_TRACKID, getSplitter().getTrackID());
    }

    /**
     * Tests the {@link MixSplitter#getTrackID()} method.
     */
    @Test
    public void testGetTrackIDAfterSetting() {
        final int id = 2104921042;
        final MixSplitter split = getSplitter();
        split.setTrackID(id);
        assertEquals(id, split.getTrackID());
    }

    /**
     * Tests the {@link MixSplitter#getData()} method.
     */
    @Test
    public void testDataEmpty() {
        assertEquals(asList(), getSplitter().getData());
    }

    /**
     * Tests the {@link MixSplitter#getData()} method.
     */
    @Test
    public void testData() {
        final MixSplitter split = getSplitter();
        final List<Float> data = asList(4.0f, 2.1f, 3.9f);
        split.setData(data);
        assertEquals(data, split.getData());
    }

    /**
     * Tests the {@link MixSplitter#getJaccardDistance(List, List)} method.
     */
    @Test
    public void testGetJaccardDistanceBothEmpty() {
        final List<Float> empty = new ArrayList<Float>();
        final double expected = 0.0;
        final double delta = 0.001;
        assertEquals(expected, getSplitter().getJaccardDistance(empty, empty),
                delta);
    }

    /**
     * Tests the {@link MixSplitter#getJaccardDistance(List, List)} method.
     */
    @Test
    public void testGetJaccardDistanceFirstEmpty() {
        final List<Float> empty = new ArrayList<Float>();
        final List<Float> second = asList(3.0f, 2.1f, 1.1f);
        final double expected = 1.0;
        final double delta = 0.001;
        assertEquals(expected, getSplitter().getJaccardDistance(empty, second),
                delta);
    }

    /**
     * Tests the {@link MixSplitter#getJaccardDistance(List, List)} method.
     */
    @Test
    public void testGetJaccardDistanceSecondEmpty() {
        final List<Float> empty = new ArrayList<Float>();
        final List<Float> first = asList(3.0f, 2.1f, 1.1f);
        final double expected = 1.0;
        final double delta = 0.001;
        assertEquals(expected, getSplitter().getJaccardDistance(first, empty),
                delta);
    }

    /**
     * Tests the {@link MixSplitter#getJaccardDistance(List, List)} method.
     */
    @Test
    public void testGetJaccardDistance() {
        final List<Float> first = asList(3.0f, 1.2f, 4.3f, 88.6f, 2.1f);
        final List<Float> second = asList(3.0f, 2.1f, 1.1f);
        final double expected = (double) 4 / 6;
        final double delta = 0.001;
        assertEquals(expected, getSplitter().getJaccardDistance(first, second),
                delta);
    }

    /**
     * Tests the {@link MixSplitter#getJaccardDistance(List, List)} method.
     */
    @Test
    public void testGetJaccardDistanceAgain() {
        final List<Float> second = asList(3.0f, 1.2f, 4.3f, 88.6f, -2.2f);
        final List<Float> first = asList(3.0f, 2.1f, 1.1f, 0.0f);
        final double expected = 0.875;
        final double delta = 0.001;
        assertEquals(expected, getSplitter().getJaccardDistance(first, second),
                delta);
    }

    /**
     * Tests the {@link MixSplitter#getJaccardDistance(List, List)} method.
     */
    @Test
    public void testGetJaccardDistanceNoIntersect() {
        final List<Float> first = asList(3.0f, 1.2f, 4.3f, 88.6f, -2.2f);
        final List<Float> second = asList(3.3f, 2.1f, 1.1f, 0.0f);
        final double expected = 1.0;
        final double delta = 0.001;
        assertEquals(expected, getSplitter().getJaccardDistance(first, second),
                delta);
    }

    /**
     * Tests the {@link MixSplitter#getJaccardDistance(List, List)} method.
     */
    @Test
    public void testGetJaccardDistanceDoublesInLists() {
        final List<Float> first = asList(3.0f, 2.1f, 3.0f);
        final List<Float> second = asList(4.2f, 3.0f, 6.1f);
        final double expected = 0.75;
        final double delta = 0.001;
        assertEquals(expected, getSplitter().getJaccardDistance(first, second),
                delta);
    }
}
