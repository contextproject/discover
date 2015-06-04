package models.seeker;

import basic.BasicTest;

import models.record.Comment;
import models.record.Track;

import models.snippet.TimedSnippet;

import models.utility.CommentList;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test for the CommentIntensitySeeker.
 * 
 * @since 08-05-2015
 * @version 03-06-2015
 * 
 * @see CommentIntensitySeeker
 * 
 * @author tomas heinsohn huala
 * @author stefan boodt
 * 
 */
public class CommentIntensitySeekerTest extends BasicTest {

    /**
     * CommentIntensitySeeker object.
     */
    private CommentIntensitySeeker commentIntensitySeeker;

    /**
     * Comment objects.
     */
    private Comment c1, c3, c4, c5, c6, c7, c8, c9;

    /**
     * CommentList object.
     */
    private CommentList list;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Track track = new Track();
        track.setTrackid(32097940);
        track.setDuration(100000);
        list = new CommentList();
        setSeeker(new CommentIntensitySeeker(track));
        c1 = new Comment(1, 1, 5000, "l ");
        c3 = new Comment(1, 3, 15000, "l ");
        c4 = new Comment(1, 4, 16000, "l ");
        c5 = new Comment(1, 5, 18000, "l ");
        c6 = new Comment(1, 6, 21000, "l ");
        c7 = new Comment(1, 7, 50000, "l ");
        c8 = new Comment(1, 8, 41000, "l ");
        c9 = new Comment(1, 9, 42000, "l ");
    }
    
    /**
     * Sets the seeker under test.
     * @param seeker The new seeker under test.
     */
    public void setSeeker(final CommentIntensitySeeker seeker) {
        commentIntensitySeeker = seeker;
        setObjectUnderTest(seeker);
    }
    
    /**
     * Gets the seeker under test.
     * @return The seeker under test.
     */
    public CommentIntensitySeeker getSeeker() {
        return commentIntensitySeeker;
    }

    /**
     * Test the seek function with a list of zero comments.
     */
    @Test
    public void testSeekZero() {
        list.clear();
        commentIntensitySeeker.setComments(list);
        TimedSnippet ts = commentIntensitySeeker.seek();
        assertEquals(0, ts.getStartTime());
        assertEquals(30000, ts.getWindow());
    }

    /**
     * Test the seek function with a list of comments with the timestamp
     * centered at one point.
     */
    @Test
    public void testSeek1() {
        list.clear();
        list.add(c3);
        list.add(c4);
        list.add(c5);
        list.add(c6);
        commentIntensitySeeker.setComments(list);
        TimedSnippet ts = commentIntensitySeeker.seek();
        assertEquals(0, ts.getStartTime());
        assertEquals(30000, ts.getWindow());
    }

    /**
     * Test the seek function with a list of comments with the timestamp
     * centered at a other point.
     */
    @Test
    public void testSeek2() {
        list.clear();
        assertEquals(30000, TimedSnippet.getDefaultDuration());
        assertEquals(5000, Comment.getPeriod());
        list.add(c7);
        list.add(c8);
        list.add(c9);
        commentIntensitySeeker.setComments(list);
        TimedSnippet ts = commentIntensitySeeker.seek();
        assertEquals(30000, TimedSnippet.getDefaultDuration());
        assertEquals(5000, Comment.getPeriod());
        assertEquals(100000, getSeeker().getTrack().getDuration());
        final String message = "ScoreStorage returned was "
                + commentIntensitySeeker.calculateScores(TimedSnippet.getDefaultDuration());
        assertEquals(message, 20000, ts.getStartTime());
        assertEquals(30000, ts.getWindow());
    }

    /**
     * Test the seek function with only 2 comments.
     */
    @Test
    public void testSeek3() {
        list.clear();
        list.add(c1);
        list.add(c9);
        commentIntensitySeeker.setComments(list);
        TimedSnippet ts = commentIntensitySeeker.seek();
        assertEquals(0, ts.getStartTime());
        assertEquals(30000, ts.getWindow());
    }

    /**
     * Testing if the functions of the Comment class are used.
     */
    @Test
    public void testDependency() {
        list.clear();
        Comment com = mock(Comment.class);
        when(com.getTime()).thenReturn(0);
        when(com.getBody()).thenReturn(" ");
        list.add(com);
        commentIntensitySeeker.setComments(list);
        TimedSnippet ts = commentIntensitySeeker.seek();
        assertEquals(0, ts.getStartTime());
        assertEquals(30000, ts.getWindow());
    }
    
    /**
     * Tests the {@link CommentIntensitySeeker#isInRange(int, int, int)} method.
     */
    @Test
    public void testIsInRange() {
        assertTrue(getSeeker().isInRange(20, 10, 30));
    }
    
    /**
     * Tests the {@link CommentIntensitySeeker#isInRange(int, int, int)} method.
     */
    @Test
    public void testIsInRangeBefore() {
        assertFalse(getSeeker().isInRange(0, 10, 30));
    }
    
    /**
     * Tests the {@link CommentIntensitySeeker#isInRange(int, int, int)} method.
     */
    @Test
    public void testIsInRangeAfter() {
        assertFalse(getSeeker().isInRange(50, 10, 30));
    }
    
    /**
     * Tests the {@link CommentIntensitySeeker#isInRange(int, int, int)} method.
     */
    @Test
    public void testIsInRangeTop() {
        assertTrue(getSeeker().isInRange(40, 10, 30));
    }
    
    /**
     * Tests the {@link CommentIntensitySeeker#getFilter()} method.
     */
    @Test
    public void testGetFilter() {
        assertNotNull(getSeeker().getFilter());
    }
}
