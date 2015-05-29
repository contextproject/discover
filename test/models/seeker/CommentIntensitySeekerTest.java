package models.seeker;

import java.util.HashSet;
import java.util.Set;

import models.record.Comment;
import models.seeker.CommentIntensitySeeker;
import models.snippet.TimedSnippet;
import models.utility.CommentList;

import org.junit.Before;
import org.junit.Test;





import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test for the CommentIntensitySeeker.
 */
public class CommentIntensitySeekerTest {

    /**
     * CommentIntensitySeeker object.
     */
    private CommentIntensitySeeker mockedCIS;

    /**
     * Comment objects.
     */
    private Comment c1, c3, c4, c5, c6, c7, c8, c9;

    /**
     * Setting up 2 classes to test with.
     */
    @Before
    public void makeComments() {
        mockedCIS = new CommentIntensitySeeker(1);
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
     * Test the seek function with a set of zero comments.
     */
    @Test
    public void testSeekZero() {
    	CommentList set = new CommentList();
    	mockedCIS.setComments(set);
        TimedSnippet ts = mockedCIS.seek();
        
        assertEquals(0, ts.getStartTime());
        assertEquals(30000, ts.getWindow());
    }

    /**
     * Test the seek function with a set of comments with the timestamp centered at one point.
     */
    @Test
    public void testSeek1() {
    	CommentList set = new CommentList();
        set.add(c3);
        set.add(c4);
        set.add(c5);
        set.add(c6);
        mockedCIS.setComments(set);
        TimedSnippet ts = mockedCIS.seek();
        assertEquals(15000, ts.getStartTime());
        assertEquals(30000, ts.getWindow());
    }

    /**
     * Test the seek function with a set of comments with the timestamp centered at a other point.
     */
    @Test
    public void testSeek2() {
    	CommentList set = new CommentList();
        assertEquals(30000, TimedSnippet.getDefaultDuration());
        assertEquals(5000, Comment.getPeriod());
        set.add(c7);
        set.add(c8);
        set.add(c9);
        mockedCIS.setComments(set);
        TimedSnippet ts = mockedCIS.seek();
        assertEquals(30000, TimedSnippet.getDefaultDuration());
        assertEquals(5000, Comment.getPeriod());
        assertEquals(40000, ts.getStartTime());
        assertEquals(30000, ts.getWindow());
    }

    /**
     * Test the seek function with only 2 comments.
     */
    @Test
    public void testSeek3() {
    	CommentList set = new CommentList();
        set.add(c1);
        set.add(c9);
        mockedCIS.setComments(set);
        TimedSnippet ts = mockedCIS.seek();
        assertEquals(5000, ts.getStartTime());
        assertEquals(30000, ts.getWindow());
    }

    /**
     * Testing if the functions of the Comment class are used.
     */
    @Test
    public void testDependency() {
    	CommentList set = new CommentList();
        Comment com = mock(Comment.class);
        when(com.getTime()).thenReturn(0);
        when(com.getBody()).thenReturn(" ");
        set.add(com);
        mockedCIS.setComments(set);
        TimedSnippet ts = mockedCIS.seek();
        assertEquals(0, ts.getStartTime());
        assertEquals(30000, ts.getWindow());
        verify(com, times(5)).getTime();
        verify(com).hashCode();
    }
}
