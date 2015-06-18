package models.mix;

import basic.BasicTest;

import java.util.ArrayList;
import java.util.List;

import models.record.Track;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the MixSplitter class.
 * 
 * @see MixSplitter
 * @see BasicTest
 * 
 * @since 21-05-2015
 * @version 10-06-2015
 * 
 * @author stefan boodt
 * @author arthur hovenesyan
 *
 */
public class MixSplitterTest extends BasicTest {

    /**
     * Default trackid of the tested mixsplitter.
     */
    protected static final int DEFAULT_TRACKID = 2431002;
    
    /**
     * The default duration of the track.
     */
    protected static final int DEFAULT_DURATION = 300000;

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
        setSplitter(new MixSplitter(asList(),
                new Track(DEFAULT_TRACKID, DEFAULT_DURATION)));
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
     * Tests the {@link MixSplitter#split(int)} method on an empty dataset.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    @Test (expected = IllegalStateException.class)
    public void testSplitWithSizeEmptyImpossibleToFulfill() {
        getSplitter().setData(new ArrayList<Double>());
        getSplitter().addPieces(3, getSplitter().splitToShingles(), 0.85, getSplitter().split());
    }

    /**
     * Tests the {@link MixSplitter#split(int)} method on an empty dataset.
     */
    @Test
    public void testSplitWithSizeEmpty() {
        final List<Integer> expected = new ArrayList<Integer>();
        expected.add(0);
        getSplitter().setData(new ArrayList<Double>());
        assertEquals(expected, getSplitter().split(1));
    }

    /**
     * Tests the {@link MixSplitter#split(int)} method.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    @Test
    public void testSplitWithSize0() {
        getSplitter().setData(asList(3.0, 1.0, 100.0, 2.0, 0.0, 13.0, 2014.0, 1.1, 3.9,
                2.4, -1.2, 100.4, 532.9, 201.4, 734.2, -104.2, 0.3, 10.2, 192.2, 53.2, 921.6));
        assertEquals(getSplitter().split(), getSplitter().split(0));
    }

    /**
     * Tests the {@link MixSplitter#split(int)} method on an empty dataset.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    @Test
    public void testSplitWithSize1() {
        final List<Integer> expected = new ArrayList<Integer>();
        expected.add(0);
        getSplitter().setData(asList(3.0, 1.0, 100.0, 2.0, 0.0, 13.0, 2014.0, 1.1, 3.9,
                2.4, -1.2, 100.4, 532.9, 201.4, 734.2, -104.2, 0.3, 10.2, 192.2, 53.2, 921.6));
        assertEquals(expected, getSplitter().split(1));
    }

    /**
     * Tests the {@link MixSplitter#split(int)} method on an empty dataset.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testSplitWithSizeNegative() {
        final List<Integer> expected = new ArrayList<Integer>();
        expected.add(0);
        getSplitter().setData(new ArrayList<Double>());
        getSplitter().split(-1);
    }

    /**
     * Tests the {@link MixSplitter#split(int)} method on an empty dataset.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    @Test
    public void testSplitWithSizeToSmall() {
        getSplitter().setData(asList(3.0, 1.0, 100.0, 2.0, 0.0, 13.0, 2014.0, 1.1, 3.9,
                2.4, -1.2, 100.4, 532.9, 201.4, 734.2, -104.2, 0.3, 10.2, 192.2, 53.2, 921.6,
                201.3, 21.5, 4.3, 92.563, 600.0, 2.7, 7.7, 54.8, 201.4, 642.15));
        final List<Integer> expected = asListInt(0);
        assertEquals(expected, getSplitter().split(1));
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
     * Tests the {@link MixSplitter#getTrack()} method.
     */
    @Test
    public void testGetTrack() {
        final Track expected = new Track(DEFAULT_TRACKID, DEFAULT_DURATION);
        assertEquals(expected, getSplitter().getTrack());
    }

    /**
     * Tests the {@link MixSplitter#getTrack()} method.
     */
    @Test
    public void testGetTrackAfterSetting() {
        final int id = 2104921042;
        final Track expected = new Track(id, DEFAULT_DURATION);
        final MixSplitter split = getSplitter();
        split.setTrack(expected);
        assertEquals(expected, split.getTrack());
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
    
    /**
     * Tests the {@link MixSplitter#doTheSplit(int, List, double, List)} method.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    @Test
    public void testDoTheSplitZeroSplits() {
        getSplitter().setData(asList(3.0, 1.0, 100.0, 2.0, 0.0, 13.0, 2014.0, 1.1, 3.9,
                2.4, -1.2, 100.4, 532.9, 201.4, 734.2, -104.2, 0.3, 10.2, 192.2, 53.2, 921.6));
        final List<Shingle> shingles = getSplitter().splitToShingles();
        assertEquals(asListInt(0), getSplitter().doTheSplit(0, shingles, 0.8, asListInt(0)));
    }
    
    /**
     * Tests the {@link MixSplitter#doTheSplit(int, List, double, List)} method.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    @Test
    public void testDoTheSplitThreeSplits() {
        getSplitter().setData(asList(3.0, 1.0, 100.0, 2.0, 0.0, 13.0, 2014.0, 1.1, 3.9,
                2.4, -1.2, 100.4, 532.9, 201.4, 734.2, -104.2, 0.3, 10.2, 192.2, 53.2, 921.6));
        final List<Shingle> shingles = new ArrayList<Shingle>();
        shingles.add(new Shingle(asList(3.0, 1.0, 100.0, 2.0, 0.0, 13.0, 2014.0)));
        shingles.add(new Shingle(asList(1.1, 3.9, 2.4, -1.2, 100.4, 532.9, 201.4)));
        shingles.add(new Shingle(asList(734.2, -104.2, 0.3, 10.2, 192.2, 53.2, 921.6)));
        final int thirdOfSong = DEFAULT_DURATION / 3;
        assertEquals(asListInt(0, thirdOfSong, 2 * thirdOfSong),
                getSplitter().doTheSplit(3, shingles, 0.8, asListInt()));
    }
    
    /**
     * Tests the {@link MixSplitter#doTheSplit(int, List, double, List)} method.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    @Test
    public void testDoTheSplitTwoSplitsAlreadyCurrent() {
        getSplitter().setData(asList(3.0, 1.0, 100.0, 2.0, 0.0, 13.0, 2014.0, 1.1, 3.9,
                2.4, -1.2, 100.4, 532.9, 201.4, 734.2, -104.2, 0.3, 10.2, 192.2, 53.2, 921.6));
        final List<Shingle> shingles = new ArrayList<Shingle>();
        shingles.add(new Shingle(asList(3.0, 1.0, 100.0, 2.0, 0.0, 13.0, 2014.0)));
        shingles.add(new Shingle(asList(1.1, 3.9, 2.4, -1.2, 100.4, 532.9, 201.4)));
        shingles.add(new Shingle(asList(734.2, -104.2, 0.3, 10.2, 192.2, 53.2, 921.6)));
        final int thirdOfSong = DEFAULT_DURATION / 3;
        assertEquals(asListInt(0, thirdOfSong, 2 * thirdOfSong, DEFAULT_DURATION),
                getSplitter().doTheSplit(2, shingles, 0.8, asListInt(0, DEFAULT_DURATION)));
    }
    
    /**
     * Tests the {@link MixSplitter#doTheSplit(int, List, double, List)} method. This test case
     * actually hacks the program by checking what happens if the number can be supplied but then
     * breaks the methods usefulness. Since 10-06 this is an Illegal State. Before that time this
     * was simply a Stack overflow.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    @Test (expected = IllegalStateException.class)
    public void testDoTheSplitThreeSplitsHackedProgram() {
        getSplitter().setData(asList(3.0, 1.0, 100.0, 2.0, 0.0, 13.0, 2014.0, 1.1, 3.9,
                2.4, -1.2, 100.4, 532.9, 201.4, 734.2, -104.2, 0.3, 10.2, 192.2, 53.2, 921.6));
        final List<Shingle> shingles = new ArrayList<Shingle>();
        shingles.add(new Shingle(asList(3.0, 1.0, 100.0, 2.0, 0.0, 13.0, 2014.0)));
        shingles.add(new Shingle(asList(1.1, 3.9, 2.4, -1.2, 100.4, 532.9, 201.4)));
        shingles.add(new Shingle(asList(734.2, -104.2, 0.3, 10.2, 192.2, 53.2, 921.6)));
        getSplitter().doTheSplit(3, shingles, 0.8, asListInt(0, DEFAULT_DURATION));
    }
    
    /**
     * Tests the {@link MixSplitter#doTheSplit(int, List, double, List)} method.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    @Test
    public void testDoTheSplitThreeSplitsAlreadyCurrent() {
        getSplitter().setData(asList(3.0, 1.0, 100.0, 2.0, 0.0, 13.0, 2014.0, 1.1, 3.9,
                2.4, -1.2, 100.4, 532.9, 201.4, 734.2, -104.2, 0.3, 10.2, 192.2, 53.2, 921.6));
        final List<Shingle> shingles = new ArrayList<Shingle>();
        shingles.add(new Shingle(asList(3.0, 1.0, 100.0, 2.0, 0.0, 13.0, 2014.0)));
        shingles.add(new Shingle(asList(1.1, 3.9, 2.4, -1.2, 100.4, 532.9, 201.4)));
        shingles.add(new Shingle(asList(734.2, -104.2, 0.3, 10.2, 192.2, 53.2, 921.6)));
        final int thirdOfSong = DEFAULT_DURATION / 3;
        assertEquals(asListInt(0, thirdOfSong, 2 * thirdOfSong, DEFAULT_DURATION),
                getSplitter().doTheSplit(3, shingles, 0.8, asListInt(DEFAULT_DURATION)));
    }
    
    /**
     * Tests the {@link MixSplitter#doTheSplit(int, List, double, List)} method. This test case
     * actually models what happens when an infinite loop has been created. It throws an
     * IllegalStateException that explains how you normally get it. Filling in a large negative
     * value in the given method. The method should not learn to handle it since it is a behind
     * the scenes method.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    @Test (expected = IllegalStateException.class)
    public void testDoTheSplitNegativeThreshold() {
        getSplitter().setData(asList(3.0, 1.0, 100.0, 2.0, 0.0, 13.0, 2014.0, 1.1, 3.9,
                2.4, -1.2, 100.4, 532.9, 201.4, 734.2, -104.2, 0.3, 10.2, 192.2, 53.2, 921.6));
        final List<Shingle> shingles = new ArrayList<Shingle>();
        shingles.add(new Shingle(asList(3.0, 1.0, 100.0, 2.0, 0.0, 13.0, 2014.0)));
        shingles.add(new Shingle(asList(1.1, 3.9, 2.4, -1.2, 100.4, 532.9, 201.4)));
        shingles.add(new Shingle(asList(734.2, -104.2, 0.3, 10.2, 192.2, 53.2, 921.6)));
        getSplitter().doTheSplit(3, shingles, Double.MIN_VALUE, asListInt(0, DEFAULT_DURATION));
    }
    
    /**
     * Tests the {@link MixSplitter#doTheSplit(int, List, double, List)} method. This test case
     * tests the third path through the method that is when the number of splits becomes to large.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    @Test
    public void testDoTheSplitNegativeSplitCount() {
        getSplitter().setData(asList(3.0, 1.0, 100.0, 2.0, 0.0, 13.0, 2014.0, 1.1, 3.9,
                2.4, -1.2, 100.4, 532.9, 201.4, 734.2, -104.2, 0.3, 10.2, 192.2, 53.2, 921.6));
        final List<Shingle> shingles = new ArrayList<Shingle>();
        shingles.add(new Shingle(asList(3.0, 1.0, 100.0, 2.0, 0.0, 13.0, 2014.0)));
        shingles.add(new Shingle(asList(1.1, 3.9, 2.4, -1.2, 100.4, 532.9, 201.4)));
        shingles.add(new Shingle(asList(734.2, -104.2, 0.3, 10.2, 192.2, 53.2, 921.6)));
        final int quarterOfSong = DEFAULT_DURATION / 4;
        List<Integer> result = getSplitter().doTheSplit(-2, shingles, 0.8, asListInt(0,
                quarterOfSong, 2 * quarterOfSong, 3 * quarterOfSong, DEFAULT_DURATION));
        final List<Integer> expected = asListInt(0, quarterOfSong, 2 * quarterOfSong);
        assertEquals(expected, result);
    }
}
