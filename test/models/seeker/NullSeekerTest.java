package models.seeker;

import static org.junit.Assert.assertEquals;
import models.score.ScoreMap;
import models.snippet.TimedSnippet;

import org.junit.Test;

import basic.BasicTest;

/**
 * Tests the NullSeeker class.
 * 
 * @since 03-06-2015
 * @version 03-06-2015
 * 
 * @see NullSeeker
 * @see BasicTest
 * 
 * @author stefanboodt
 *
 */
public class NullSeekerTest extends BasicTest {
    
    /**
     * The seeker to test.
     */
    private NullSeeker seeker;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        setSeeker(new NullSeeker());
    }
    
    /**
     * Returns the NullSeeker under test.
     * @return The null seeker that is being tested.
     */
    protected NullSeeker getSeeker() {
        return seeker;
    }
    
    /**
     * Sets the seeker under test.
     * @param seeker The new Seeker under test.
     */
    protected void setSeeker(final NullSeeker seeker) {
        setObjectUnderTest(seeker);
        this.seeker = seeker;
    }

    /**
     * Tests the {@link NullSeeker#seek()} method.
     */
    @Test
    public void testSeek() {
        assertEquals(new TimedSnippet(0), getSeeker().seek());
    }

    /**
     * Tests the {@link NullSeeker#seek()} method and the
     * {@link NullSeeker#NullSeeker(models.score.ScoreStorage)} constructor.
     */
    @Test
    public void testSeekOnConstructorWithArgument() {
        final ScoreMap map = new ScoreMap();
        map.add(30000, 20);
        assertEquals(new TimedSnippet(0), new NullSeeker(map).seek());
    }

}
