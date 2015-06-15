package models.seeker;

import java.util.ArrayList;
import java.util.List;

import basic.BasicTest;
import models.record.Track;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class tests the LoudnessSeeker test.
 * 
 * @since 15-06-2015
 * @version 15-06-2015
 * 
 * @see BasicTest
 * @see LoudnessSeekerTest
 * 
 * @author stefan boodt
 *
 */
public class LoudnessSeekerTest extends BasicTest {

    /**
     * The seeker under test.
     */
    private LoudnessSeeker seeker;
    
    /**
     * Default track used by the tests.
     */
    protected static final Track DEFAULT_TRACK = new Track(10, 1000);
    
    @Override
    public void setUp() throws Exception {
        super.setUp();
        setSeeker(new LoudnessSeeker(DEFAULT_TRACK, asList()));
    }

    /**
     * Returns the seeker under test.
     * @return the seeker under test.
     */
    public LoudnessSeeker getSeeker() {
        return seeker;
    }

    /**
     * Sets the seeker under test.
     * @param seeker the new seeker.
     */
    public void setSeeker(final LoudnessSeeker seeker) {
        setObjectUnderTest(seeker);
        this.seeker = seeker;
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
     * Tests the {@link LoudnessSeeker#getTrack()} method.
     */
    @Test
    public void testGetTrack() {
        assertEquals(DEFAULT_TRACK, getSeeker().getTrack());
    }

    /**
     * Tests the {@link LoudnessSeeker#getTrack()} method.
     */
    @Test
    public void testGetTrackAfterSetting() {
        final Track expected = new Track(11, -1);
        getSeeker().setTrack(expected);
        assertEquals(expected, getSeeker().getTrack());
    }
    
    /**
     * Tests the {@link LoudnessSeeker#getWaveform()} method.
     */
    @Test
    public void testGetWaveform() {
        assertEquals(asList(), getSeeker().getWaveform());
    }
    
    /**
     * Tests the {@link LoudnessSeeker#getWaveform()} method.
     */
    @Test
    public void testGetWaveformAfterSetting() {
        final List<Double> expected = asList(2.0, 1.0, 0.0, 0.5, 1.0);
        getSeeker().setWaveform(expected);
        assertEquals(expected, getSeeker().getWaveform());
    }
    
}
