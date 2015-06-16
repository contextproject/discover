package models.seeker;

import models.score.ScoreStorage;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the Random Seeker class.
 * 
 * @since 03-06-2015
 * @version 16-06-2015
 * 
 * @see RandomSeeker
 * @see BasicTest
 * 
 * @author stefan boodt
 *
 */
public class RandomSeekerTest extends AbstractSeekerTest {
    
    /**
     * Seeker under test.
     */
    private RandomSeeker seeker;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        setSeeker(new RandomSeeker(DEFAULT_TRACK));
    }
    
    /**
     * Sets the seeker under test.
     * @param seeker The seeker under test.
     */
    public void setSeeker(final RandomSeeker seeker) {
        super.setSeeker(seeker);
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
