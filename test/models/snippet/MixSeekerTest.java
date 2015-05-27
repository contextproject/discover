package models.snippet;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests the MixSeeker class.
 * 
 * @since 27-05-2015
 * @version 27-05-2015
 * 
 * @see MixSeeker
 * @see CommentIntensitySeekerTest
 * 
 * @author stefan boodt
 * @author arthur hovenesyan
 *
 */
public class MixSeekerTest /* extends CommentIntensitySeekerTest */ {

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
        //super.makeComments();
        setSeeker(new MixSeeker(asList(2, 1)));
    }

    /**
     * Does some clean up for the class.
     * @throws Exception If the clean up fails.
     */
    @After
    public void tearDown() throws Exception {
        // For this class this is only a hook method.
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
        this.mixseeker = seeker;
    }
    
    /**
     * Gets the seeker under test.
     * @return The seeker under test.
     */
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
     * Tests the {@link MixSeeker#setStarttimes()} method.
     */
    @Test (expected = IllegalArgumentException.class)
    public void testSetStartTimesEmpty() {
        final List<Integer> expected = asList();
        mixseeker.setStarttimes(expected);
    }
}
