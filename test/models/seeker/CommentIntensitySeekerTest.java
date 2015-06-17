package models.seeker;

import models.database.DatabaseConnector;
import models.record.Comment;
import models.record.Track;
import models.snippet.TimedSnippet;
import models.utility.CommentList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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
 * @author tomas heinsohn huala
 * @author stefan boodt
 * @version 16-06-2015
 * @see CommentIntensitySeeker
 * @since 08-05-2015
 */
public class CommentIntensitySeekerTest extends AbstractSeekerTest {

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

    /**
     * Does some set up before the class.
     *
     * @throws Exception If the set up fails.
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        DatabaseConnector databaseConnector = DatabaseConnector.getConnector();
        databaseConnector.loadDrivers();
        databaseConnector.makeConnection("jdbc:mysql://188.166.78.36/contextbase", "context", "password");
        databaseConnector.makeConnection("jdbc:mysql://188.166.78.36/contextbase",
                "context", "password");
    }

    /**
     * Does some clean up before the class.
     *
     * @throws Exception If the clean up fails.
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        DatabaseConnector.getConnector().closeConnection();

    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        Track track = new Track();
        track.put(Track.id, 32097940);
        track.put(Track.duration, 100000);
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
     *
     * @param seeker The new seeker under test.
     */
    public void setSeeker(final CommentIntensitySeeker seeker) {
        super.setSeeker(seeker);
        commentIntensitySeeker = seeker;
    }

    /**
     * Gets the seeker under test.
     *
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
        assertEquals(new Integer(100000), getSeeker().getTrack().get(Track.duration));
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

    /**
     * Tests the {@link CommentIntensitySeeker#getTrack()} method.
     */
    @Test
    public void testGetTrack() {
        assertNotNull(getSeeker().getTrack());
    }
}
