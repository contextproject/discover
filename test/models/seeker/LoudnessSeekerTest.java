package models.seeker;

import java.util.ArrayList;
import java.util.List;

import models.score.ScoreStorage;
import models.score.ScoreMap;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the LoudnessSeeker test.
 * 
 * @since 15-06-2015
 * @version 16-06-2015
 * 
 * @see BasicTest
 * @see LoudnessSeekerTest
 * 
 * @author stefan boodt
 *
 */
public class LoudnessSeekerTest extends AbstractSeekerTest {

    /**
     * The seeker under test.
     */
    private LoudnessSeeker seeker;
    
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
        super.setSeeker(seeker);
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
    
    /**
     * Tests the {@link LoudnessSeeker#calculateScores(int)} method.
     */
    @Test
    public void testSeekEmpty() {
        final ScoreStorage expected = new ScoreMap();
        assertEquals(expected, seeker.calculateScores(1000));
    }
    
    /**
     * Tests the {@link LoudnessSeeker#calculateScores(int)} method.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    @Test
    public void testSeek() {
        final ScoreStorage expected = new ScoreMap();
        final int max = LoudnessSeeker.DEFAULT_POINTS;
        expected.add(0, (int) Math.round(0.1 * max));
        expected.add(100, max);
        expected.add(200, 0);
        expected.add(300, (int) Math.round(0.3 * max));
        expected.add(400, (int) Math.round(0.01 * max));
        expected.add(500, 0);
        expected.add(600, 0);
        expected.add(700, (int) Math.round(0.7 * max));
        expected.add(800, (int) Math.round(0.250 * max));
        expected.add(900, 0);
        seeker.setWaveform(asList(0.1, 1.0, 0.0, 0.3, 0.01, 0.0, 0.0,
                0.7, 0.25, 0.0));
        assertEquals(expected, seeker.calculateScores(100));
    }
    
}
