package models.seeker;

import java.util.ArrayList;
import java.util.List;

import models.record.Track;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the MixSeeker class.
 * 
 * @since 27-05-2015
 * @version 03-06-2015
 * 
 * @see MixSeeker
 * @see CommentIntensitySeekerTest
 * 
 * @author stefan boodt
 * @author arthur hovenesyan
 *
 */
public class MixSeekerTest extends CommentIntensitySeekerTest {

    /**
     * The mixseeker under test.
     */
    private MixSeeker mixseeker;
    
    /**
     * Does some set up for the class.
     * @throws Exception If the set up fails.
     */
    @Before
    public void setUp() throws Exception {
        super.setUp();
        Track track = new Track();
        track.setDuration(10000);
        track.setTrackid(1029204);
        setSeeker(new MixSeeker(asList(0, 2, 1), track));
    }

    /**
     * Returns the given numbers as a list.
     * 
     * @param numbers
     *            The numbers to have in the list.
     * @return The list containing the given numbers.
     */
    protected static List<Integer> asList(final int... numbers) {
        List<Integer> ints = new ArrayList<Integer>(numbers.length);
        for (int f : numbers) {
            ints.add(f);
        }
        return ints;
    }
    
    /**
     * Sets the seeker under test to the given value.
     * @param seeker The seeker under test.
     */
    public void setSeeker(final MixSeeker seeker) {
        super.setSeeker(seeker);
        this.mixseeker = seeker;
    }
    
    /**
     * Gets the seeker under test.
     * @return The seeker under test.
     */
    @Override
    public MixSeeker getSeeker() {
        return mixseeker;
    }

    /**
     * Tests the {@link MixSeeker#getStarttimesOfPieces()} as well as the
     * {@link MixSeeker#setStarttimes(List)} method.
     */
    @Test
    public void testGetStartTimes() {
        final List<Integer> expected = asList(0, 1, 2, 3);
        getSeeker().setStarttimes(expected);
        assertEquals(expected, getSeeker().getStarttimesOfPieces());
    }

    /**
     * Tests the {@link MixSeeker#getStarttimesOfPieces()} as well as the
     * {@link MixSeeker#setStarttimes(List)} method.
     */
    @Test
    public void testGetStartTimesWrongOrder() {
        final List<Integer> expected = asList(0, 1, 2, 3);
        getSeeker().setStarttimes(asList(0, 2, 3, 1));
        assertEquals(expected, getSeeker().getStarttimesOfPieces());
    }

    /**
     * Tests the {@link MixSeeker#setStarttimes()} method.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testSetStartTimesEmpty() {
        final List<Integer> expected = asList();
        mixseeker.setStarttimes(expected);
    }
    
    /**
     * Tests the {@link MixSeeker#getEndOfSong()} method against itself.
     */
    @Test
    public void testGetEndOfSong() {
        /*
         *  This tests actually only checks if the call is succeeding and if the
         *  result is consistent, which means the same every time.
         */
        assertEquals(MixSeeker.getEndOfSong(), MixSeeker.getEndOfSong());
    }
    
    /**
     * Tests the {@link MixSeeker#getNextPieceStarttime(int)} method.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testgetNextPieceStarttimeNegativeTime() {
        final List<Integer> starttimes = asList(10, 20, 30);
        mixseeker.setStarttimes(starttimes);
        final int expected = 10;
        assertEquals(expected, mixseeker.getNextPieceStarttime(-10));
    }
    
    /**
     * Tests the {@link MixSeeker#getNextPieceStarttime(int)} method.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testgetNextPieceStarttimeUnderFirstBound() {
        final List<Integer> starttimes = asList(10, 20, 30);
        mixseeker.setStarttimes(starttimes);
        final int expected = 10;
        assertEquals(expected, mixseeker.getNextPieceStarttime(0));
    }
    
    /**
     * Tests the {@link MixSeeker#getNextPieceStarttime(int)} method.
     */
    @Test
    public void testgetNextPieceStarttimeOnFirstBound() {
        final List<Integer> starttimes = asList(0, 10, 20, 30);
        mixseeker.setStarttimes(starttimes);
        final int expected = 10;
        assertEquals(expected, mixseeker.getNextPieceStarttime(0));
    }
    
    /**
     * Tests the {@link MixSeeker#getNextPieceStarttime(int)} method.
     */
    @Test
    public void testgetNextPieceStarttime() {
        final List<Integer> starttimes = asList(0, 10, 20, 30);
        mixseeker.setStarttimes(starttimes);
        final int expected = 20;
        assertEquals(expected, mixseeker.getNextPieceStarttime(11));
    }
    
    /**
     * Tests the {@link MixSeeker#getNextPieceStarttime(int)} method.
     */
    @Test
    public void testgetNextPieceStarttimeLastValue() {
        final List<Integer> starttimes = asList(0, 10, 20, 30);
        mixseeker.setStarttimes(starttimes);
        final int expected = 30;
        assertEquals(expected, mixseeker.getNextPieceStarttime(28));
    }
    
    /**
     * Tests the {@link MixSeeker#getNextPieceStarttime(int)} method.
     */
    @Test
    public void testgetNextPieceStarttimeOnLastBound() {
        final List<Integer> starttimes = asList(0, 10, 20, 30);
        mixseeker.setStarttimes(starttimes);
        final int expected = MixSeeker.getEndOfSong();
        assertEquals(expected, mixseeker.getNextPieceStarttime(30));
    }
    
    /**
     * Tests the {@link MixSeeker#getNextPieceStarttime(int)} method.
     */
    @Test
    public void testgetNextPieceStarttimeOverLastBound() {
        final List<Integer> starttimes = asList(0, 10, 20, 30);
        mixseeker.setStarttimes(starttimes);
        final int expected = MixSeeker.getEndOfSong();
        assertEquals(expected, mixseeker.getNextPieceStarttime(100));
    }
    
    /**
     * Tests the {@link MixSeeker#isInRange(int, int, int)} method.
     */
    @Test
    public void testIsInRangeWhenInOtherPart() {
        MixSeeker ms = getSeeker();
        ms.setStarttimes(asList(9, 20, 40));
        assertEquals("expected was false but got true.", false, ms.isInRange(23, 10, 30));
    }
}
