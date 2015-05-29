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
 * @version 28-05-2015
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
    protected static List<Double> asList(final double... numbers) {
        List<Double> doubles = new ArrayList<Double>(numbers.length);
        for (double f : numbers) {
            doubles.add(f);
        }
        return doubles;
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
        getSplitter().setData(new ArrayList<Double>());
        assertEquals(expected, getSplitter().split());
    }

    /**
     * Tests the {@link MixSplitter#split(List, double, int)} method on an empty dataset.
     */
    @Test
    public void testSplitEmptyHardEmpty() {
        final List<Integer> expected = new ArrayList<Integer>();
        expected.add(0);
        getSplitter().setData(new ArrayList<Double>());
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
        final double f0 = 10.0;
        final double f1 = 1.4;
        final double f2 = 4.2;
        final double f3 = 1.8;
        final double threshold = 0.0;
        final List<Shingle> shingles = new ArrayList<Shingle>();
        shingles.add(new Shingle(asList(0.0, 2.0, 1.0)));
        shingles.add(new Shingle(asList(0.0, f2, 1.0)));
        shingles.add(new Shingle(asList(0.0, f0, f1)));
        shingles.add(new Shingle(asList(0.0, 1.0, f1)));
        shingles.add(new Shingle(asList(f3, f2, 2.0)));
        assertEquals(expected, getSplitter().split(shingles, threshold, songtime));
    }

    /**
     * Tests the {@link MixSplitter#split(List, double, int)} method.
     */
    @Test
    public void testSplitThresholdOf0DuplicateShingles() {
        final List<Integer> expected = asListInt(0, 8, 16);
        final int songtime = 20;
        final double f0 = 10.0;
        final double f1 = 1.4;
        final double f2 = 4.2;
        final double f3 = 1.8;
        final double threshold = 0.0;
        final List<Shingle> shingles = new ArrayList<Shingle>();
        shingles.add(new Shingle(asList(0.0, 2.0, 1.0)));
        shingles.add(new Shingle(asList(0.0, 2.0, 1.0)));
        shingles.add(new Shingle(asList(0.0, f0, f1)));
        shingles.add(new Shingle(asList(0.0, f0, f1)));
        shingles.add(new Shingle(asList(f3, f2, 2.0)));
        assertEquals(expected, getSplitter().split(shingles, threshold, songtime));
    }

    /**
     * Tests the {@link MixSplitter#split(List, double, int)} method.
     */
    @Test
    public void testSplitThresholdOf0NoIntersection() {
        final List<Integer> expected = asListInt(0, 4, 8);
        final int songtime = 12;
        final double f0 = 10.0;
        final double f1 = 1.4;
        final double f2 = 4.2;
        final double f3 = 1.8;
        final double threshold = 0.0;
        final List<Shingle> shingles = new ArrayList<Shingle>();
        shingles.add(new Shingle(asList(0.0, 2.0, 1.0)));
        shingles.add(new Shingle(asList(f0, f2, f1)));
        shingles.add(new Shingle(asList(0.0, f3, 1.0)));
        assertEquals(expected, getSplitter().split(shingles, threshold, songtime));
    }

    /**
     * Tests the {@link MixSplitter#split(List, double, int)} method.
     */
    @Test
    public void testSplitThresholdOfZeroNineNoIntersection() {
        final List<Integer> expected = asListInt(0, 4, 8);
        final int songtime = 12;
        final double f0 = 10.0;
        final double f1 = 1.4;
        final double f2 = 4.2;
        final double f3 = 1.8;
        final double threshold = 0.9;
        final List<Shingle> shingles = new ArrayList<Shingle>();
        shingles.add(new Shingle(asList(0.0, 2.0, 1.0)));
        shingles.add(new Shingle(asList(f0, f2, f1)));
        shingles.add(new Shingle(asList(0.0, f3, 1.0)));
        assertEquals(expected, getSplitter().split(shingles, threshold, songtime));
    }

    /**
     * Tests the {@link MixSplitter#split(List, double, int)} method.
     */
    @Test
    public void testSplitThresholdOf1NoIntersection() {
        final List<Integer> expected = asListInt(0);
        final int songtime = 12;
        final double f0 = 10.0;
        final double f1 = 1.4;
        final double f2 = 4.2;
        final double f3 = 1.8;
        final double threshold = 1.0;
        final List<Shingle> shingles = new ArrayList<Shingle>();
        shingles.add(new Shingle(asList(0.0, 2.0, 1.0)));
        shingles.add(new Shingle(asList(f0, f2, f1)));
        shingles.add(new Shingle(asList(0.0, f3, 1.0)));
        assertEquals(expected, getSplitter().split(shingles, threshold, songtime));
    }

    /**
     * Tests the {@link MixSplitter#split(List, double, int)} method.
     */
    @Test
    public void testSplitThresholdOf1CopiedShingles() {
        final List<Integer> expected = asListInt(0);
        final int songtime = 20;
        final double f0 = 10.0;
        final double f1 = 1.4;
        final double f2 = 4.2;
        final double f3 = 1.8;
        final double threshold = 1.0;
        final List<Shingle> shingles = new ArrayList<Shingle>();
        shingles.add(new Shingle(asList(0.0, 2.0, 1.0)));
        shingles.add(new Shingle(asList(0.0, 2.0, 1.0)));
        shingles.add(new Shingle(asList(0.0, f0, f1)));
        shingles.add(new Shingle(asList(0.0, f0, f1)));
        shingles.add(new Shingle(asList(f3, f2, 2.0)));
        assertEquals(expected, getSplitter().split(shingles, threshold, songtime));
    }

    /**
     * Tests the {@link MixSplitter#split(List, double, int)} method.
     */
    @Test
    public void testSplitThresholdOf1() {
        final List<Integer> expected = asListInt(0);
        final int songtime = 20;
        final double f0 = 10.0;
        final double f1 = 1.4;
        final double f2 = 4.2;
        final double f3 = 1.8;
        final double threshold = 1.0;
        final List<Shingle> shingles = new ArrayList<Shingle>();
        shingles.add(new Shingle(asList(0.0, 2.0, 1.0)));
        shingles.add(new Shingle(asList(0.0, f2, 1.0)));
        shingles.add(new Shingle(asList(0.0, f0, f1)));
        shingles.add(new Shingle(asList(0.0, 1.0, f1)));
        shingles.add(new Shingle(asList(f3, f2, 2.0)));
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
        final List<Double> data = asList(4.0, 2.1, 3.9);
        split.setData(data);
        assertEquals(data, split.getData());
    }

    /**
     * Tests the {@link MixSplitter#getJaccardDistance(List, List)} method.
     */
    @Test
    public void testGetJaccardDistanceBothEmpty() {
        final List<Double> empty = new ArrayList<Double>();
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
        final List<Double> empty = new ArrayList<Double>();
        final List<Double> second = asList(3.0, 2.1, 1.1);
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
        final List<Double> empty = new ArrayList<Double>();
        final List<Double> first = asList(3.0, 2.1, 1.1);
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
        final List<Double> first = asList(3.0, 1.2, 4.3, 88.6, 2.1);
        final List<Double> second = asList(3.0, 2.1, 1.1);
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
        final List<Double> second = asList(3.0, 1.2, 4.3, 88.6, -2.2);
        final List<Double> first = asList(3.0, 2.1, 1.1, 0.0);
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
        final List<Double> first = asList(3.0, 1.2, 4.3, 88.6, -2.2);
        final List<Double> second = asList(3.3, 2.1, 1.1, 0.0);
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
        final List<Double> first = asList(3.0, 2.1, 3.0);
        final List<Double> second = asList(4.2, 3.0, 6.1);
        final double expected = 0.75;
        final double delta = 0.001;
        assertEquals(expected, getSplitter().getJaccardDistance(first, second),
                delta);
    }
    
    /**
     * Tests the {@link MixSplitter#splitToShingles(int, int)} method.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testSplitToShinglesBoth0() {
        splitter.splitToShingles(0, 0);
    }
    
    /**
     * Tests the {@link MixSplitter#splitToShingles(int, int)} method.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testSplitToShinglesSize0() {
        splitter.splitToShingles(0, 1);
    }
    
    /**
     * Tests the {@link MixSplitter#splitToShingles(int, int)} method.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testSplitToShinglesStepsize0() {
        splitter.splitToShingles(1, 0);
    }
    
    /**
     * Tests the {@link MixSplitter#splitToShingles(int, int)} method.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testSplitToShinglesNegativeSize() {
        splitter.splitToShingles(-1, 1);
    }
    
    /**
     * Tests the {@link MixSplitter#splitToShingles(int, int)} method.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testSplitToShinglesNegativeStepsize() {
        splitter.splitToShingles(1, -1);
    }
    
    /**
     * Tests the {@link MixSplitter#splitToShingles()} method.
     */
    @Test
    public void testSplitToShinglesEmpty() {
        splitter.setData(asList());
        List<Shingle> expected = new ArrayList<Shingle>();
        assertEquals(expected, splitter.splitToShingles());
    }
    
    /**
     * Tests the {@link MixSplitter#splitToShingles(int, int)} method.
     */
    @Test
    public void testSplitToShinglesEmptyWithArguments() {
        splitter.setData(asList());
        List<Shingle> expected = new ArrayList<Shingle>();
        assertEquals(expected, splitter.splitToShingles(10, 1));
    }
    
    /**
     * Tests the {@link MixSplitter#splitToShingles(int, int)} method.
     */
    @Test
    public void testSplitToShinglesTooLargeSize() {
        final List<Double> doubles = asList(1.0, 2.0, 0.0);
        splitter.setData(doubles);
        List<Shingle> expected = new ArrayList<Shingle>();
        expected.add(new Shingle(doubles));
        expected.add(new Shingle(asList(2.0, 0.0)));
        expected.add(new Shingle(asList(0.0)));
        assertEquals(expected, splitter.splitToShingles(10, 1));
    }
    
    /**
     * Tests the {@link MixSplitter#splitToShingles(int, int)} method.
     */
    @Test
    public void testSplitToShinglesTooLargeStepsize() {
        final List<Double> doubles = asList(1.0, 2.0, 0.0);
        splitter.setData(doubles);
        List<Shingle> expected = new ArrayList<Shingle>();
        expected.add(new Shingle(asList(1.0)));
        assertEquals(expected, splitter.splitToShingles(1, 4));
    }
    
    /**
     * Tests the {@link MixSplitter#splitToShingles(int, int)} method.
     */
    @Test
    public void testSplitToShinglesBothTooLarge() {
        final List<Double> doubles = asList(1.0, 2.0, 0.0);
        splitter.setData(doubles);
        List<Shingle> expected = new ArrayList<Shingle>();
        expected.add(new Shingle(doubles));
        assertEquals(expected, splitter.splitToShingles(10, 5));
    }
    
    /**
     * Tests the {@link MixSplitter#splitToShingles(int, int)} method.
     */
    @Test
    public void testSplitToShinglesWithGaps() {
        final List<Double> doubles = asList(1.0, 2.0, 0.0, 2.0);
        splitter.setData(doubles);
        List<Shingle> expected = new ArrayList<Shingle>();
        expected.add(new Shingle(asList(1.0)));
        expected.add(new Shingle(asList(0.0)));
        assertEquals(expected, splitter.splitToShingles(1, 2));
    }
    
    /**
     * Tests the {@link MixSplitter#splitToShingles(int, int)} method.
     */
    @Test
    public void testSplitToShinglesBoth1() {
        final List<Double> doubles = asList(1.0, 2.0, 0.0);
        splitter.setData(doubles);
        List<Shingle> expected = new ArrayList<Shingle>();
        expected.add(new Shingle(asList(1.0)));
        expected.add(new Shingle(asList(2.0)));
        expected.add(new Shingle(asList(0.0)));
        assertEquals(expected, splitter.splitToShingles(1, 1));
    }
}
