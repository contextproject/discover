//package models.snippet;
//
//import java.util.HashSet;
//import java.util.Set;
//
//import models.seeker.CommentIntensitySeeker;
//import org.junit.Before;
//import org.junit.Test;
//
//
//import static org.junit.Assert.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
///**
// * Test for the CommentIntensitySeeker.
// */
//public class CommentIntensitySeekerTest {
//
//    /**
//     * CommentIntensitySeeker object.
//     */
//    private CommentIntensitySeeker mockedCIS;
//
//    /**
//     * Comment objects.
//     */
//    private Comment c1, c2, c3, c4, c5, c6, c7, c8, c9;
//
//    /**
//     * Setting up 2 classes to test with.
//     */
//    @Before
//    public void makeComments() {
//        CommentIntensitySeeker mockedCIS = mock(CommentIntensitySeeker.class);
//        Set<Comment> comments = new HashSet<Comment>();
//        c1 = new Comment(1, 5000);
//        c3 = new Comment(3, 15000);
//        c4 = new Comment(4, 16000);
//        c5 = new Comment(5, 18000);
//        c6 = new Comment(6, 21000);
//        c7 = new Comment(7, 50000);
//        c8 = new Comment(8, 41000);
//        c9 = new Comment(9, 42000);
//        comments.add(c1);
//        comments.add(c2);
//        comments.add(c3);
//        comments.add(c4);
//        comments.add(c5);
//        comments.add(c6);
//        comments.add(c7);
//        comments.add(c8);
//        comments.add(c9);
//        mockedCIS.setComments(comments);
//    }
//
//    /**
//     * Test the seek function with a set of zero comments.
//     */
//    @Test
//    public void testSeekZero() {
//        Set<Comment> set = new HashSet<Comment>();
//        TimedSnippet ts = mockedCIS.seek();
//        assertEquals(0, ts.getStartTime());
//        assertEquals(30000, ts.getWindow());
//    }
//
//    /**
//     * Test the seek function with a set of comments with the timestamp centered at one point.
//     */
//    @Test
//    public void testSeek1() {
//        Set<Comment> set = new HashSet<Comment>();
//        set.add(c3);
//        set.add(c4);
//        set.add(c5);
//        set.add(c6);
//        TimedSnippet ts = mockedCIS.seek();
//        assertEquals(15000, ts.getStartTime());
//        assertEquals(30000, ts.getWindow());
//    }
//
//    /**
//     * Test the seek function with a set of comments with the timestamp centered at a other point.
//     */
//    @Test
//    public void testSeek2() {
//        Set<Comment> set = new HashSet<Comment>();
//        assertEquals(30000, TimedSnippet.getDefaultDuration());
//        assertEquals(5000, Comment.getPeriod());
//        set.add(c7);
//        set.add(c8);
//        set.add(c9);
//        TimedSnippet ts = mockedCIS.seek();
//        assertEquals(30000, TimedSnippet.getDefaultDuration());
//        assertEquals(5000, Comment.getPeriod());
//        assertEquals(40000, ts.getStartTime());
//        assertEquals(30000, ts.getWindow());
//    }
//
//    /**
//     * Test the seek function with only 2 comments.
//     */
//    @Test
//    public void testSeek3() {
//        Set<Comment> set = new HashSet<Comment>();
//        set.add(c1);
//        set.add(c9);
//        TimedSnippet ts = mockedCIS.seek();
//        assertEquals(5000, ts.getStartTime());
//        assertEquals(30000, ts.getWindow());
//    }
//
//    /**
//     * Testing if the functions of the Comment class are used.
//     */
//    @Test
//    public void testDependency() {
//        Set<Comment> set = new HashSet<Comment>();
//        Comment com = mock(Comment.class);
//        when(com.getTime()).thenReturn(0);
//        set.add(com);
//        TimedSnippet ts = mockedCIS.seek();
//        assertEquals(0, ts.getStartTime());
//        assertEquals(30000, ts.getWindow());
//        verify(com, times(7)).getTime();
//        verify(com).hashCode();
//    }
//}
