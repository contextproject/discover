package models.seeker;

import basic.BasicTest;
import models.record.Track;
import models.score.ScoreStorage;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the Random Seeker class.
 * 
 * @since 03-06-2015
 * @version 03-06-2015
 * 
 * @see RandomSeeker
 * @see BasicTest
 * 
 * @author stefanboodt
 *
 */
public class RandomSeekerTest extends BasicTest {
    
    /**
     * Seeker under test.
     */
    private RandomSeeker seeker;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Track track = new Track();
        track.setDuration(1000);
        track.setTrackid(100214);
        setSeeker(new RandomSeeker(track));
    }
    
    /**
     * Sets the seeker under test.
     * @param seeker The seeker under test.
     */
    public void setSeeker(final RandomSeeker seeker) {
        setObjectUnderTest(seeker);
        this.seeker = seeker;
    }
    
    /**
     * Gets the seeker under test.
     * @return The seeker under test.
     */
    public RandomSeeker getSeeker() {
        return seeker;
    }
    
    /**
     * Tests the {@link RandomSeeker#calculateScores(int)} method.
     */
    @Test
    public void testCalculateScoresSize() {
        ScoreStorage storage = getSeeker().calculateScores(0);
        assertEquals(1, storage.size());
    }
    
    /**
     * Tests the {@link RandomSeeker#calculateScores(int)} method.
     */
    @Test
    public void testCalculateScoresPoints() {
        ScoreStorage storage = getSeeker().calculateScores(0);
        assertEquals(RandomSeeker.getDefaultPoints(), storage.maxScore());
    }
}
