package models.record;

import basic.BasicTest;
import models.database.DatabaseConnector;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the Comment class.
 *
 * @version 31-05-2015
 * @see Comment
 * @since 10-04-2015
 */
public class CommentTest extends BasicTest {

    /**
     * The comments.
     */
    private Comment c1, c2, c3;

    /**
     * The DatabaseConnector used to connect to the database.
     */
    private DatabaseConnector databaseConnector;

    /**
     * Sets the databaseConnector.
     *
     * @param databaseConnector The new connector.
     */
    protected void setDatabaseConnector(final DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    /**
     * Setting up 2 comments to test with.
     */
    @Override
    public void setUp() {
        setDatabaseConnector(new DatabaseConnector());
        databaseConnector.loadDrivers();
        DatabaseConnector.makeConnection("jdbc:mysql://188.166.78.36/contextbase",
                "context", "password");

        c1 = new Comment(1, 1, 5000);
        c2 = new Comment(1, 2, 12321);
        c3 = new Comment(1, 1, 12321);
        setObjectUnderTest(c1); // For some superclass tests.
    }

    /**
     * Test for the constructor.
     */
    @Test
    public void testComment() {
        assertNotNull(new Comment());
    }

    /**
     * Test for the process() method.
     */
    @Test
    public void testProcess1() {
        ResultSet resultSet = DatabaseConnector.executeQuery("SELECT * FROM comments LIMIT 1");
        Comment comment = new Comment();
        assertTrue(comment.process(resultSet));
    }

    /**
     * Test for the process() method.
     */
    @Test
    public void testProcess2() {
        ResultSet resultSet = DatabaseConnector.executeQuery("SELECT * FROM tracks LIMIT 1");
        Comment comment = new Comment();
        assertFalse(comment.process(resultSet));
    }

    @Test
    public void testGetTimestamp() {
        assertEquals(5000, c1.getTimestamp());
        assertEquals(12321, c2.getTimestamp());
    }

    /**
     * Testing the getUser method.
     */
    @Test
    public void testGetUser() {
        assertEquals(1, c1.getUserid());
        assertEquals(2, c2.getUserid());
    }

    /**
     * Testing the getTime method.
     */
    @Test
    public void testGetTime() {
        assertEquals(5000, c1.getTime());
        assertEquals(10000, c2.getTime());
    }

    /**
     * Testing the setPeriod. The timestamp can change after setting a new period
     */
    @Test
    public void testSetPeriod() {
        // before
        assertEquals(10000, c2.getTime());
        assertEquals(5000, c1.getTime());

        Comment.setPeriod(1000);

        // after
        assertEquals(12000, c2.getTime());
        assertEquals(5000, c1.getTime());
    }

    /**
     * Testing if get period gives the right number back after change it.
     */
    @Test
    public void testGetPeriod() {
        // default period should be 5000
        assertEquals(5000, Comment.getPeriod());

        Comment.setPeriod(1000);

        // after
        assertEquals(1000, Comment.getPeriod());
    }

    /**
     * Simple equals test completly False case.
     */
    @Test
    public void testEquals1() {
        assertFalse(c1.equals(c2));
    }

    /**
     * Simple equals test True case.
     */
    @Test
    public void testEquals2() {
        assertTrue(c1.equals(c1));
    }

    /**
     * Simple equals test False case if 1 of the conditions is true.
     */
    @Test
    public void testEquals3() {
        assertFalse(c1.equals(c3));
        assertFalse(c2.equals(c3));
    }

    /**
     * Test of the first equals method.
     */
    @Test
    public void testEquals4() {
        try {
            Object x1 = new Object();
            assertFalse(c1.equals(x1));
        } catch (StackOverflowError e) {
            e.printStackTrace();
        }
    }

    /**
     * Simple hashcode test.
     */
    @Test
    public void testHash() {
        assertEquals(5001, c1.hashCode());
    }

    /**
     * Tests the {@link Comment#compareTo(Comment)} method.
     */
    @Test
    public void testCompare() {
        assertEquals(0, c1.compareTo(c1));
    }

    /**
     * Tests the {@link Comment#compareTo(Comment)} method.
     */
    @Test
    public void testCompareEqualTracksLargerTime() {
        final Comment other = new Comment(c2.getTrackid(), c2.getUserid(),
                c2.getTime() + Comment.getPeriod());
        assertTrue(c2.compareTo(other) < 0);
    }

    /**
     * Tests the {@link Comment#compareTo(Comment)} method.
     * The comparing comment is smaller.
     */
    @Test
    public void testCompareSmallerTrack() {
        final Comment other = new Comment(c2.getTrackid(), c2.getUserid(),
                c2.getTime() - Comment.getPeriod());
        final int compared = c2.compareTo(other);
        assertTrue(compared > 0);
    }

    /**
     * Tests the {@link Comment#compareTo(Comment)} method.
     * The comparing comment is smaller by it's trackid.
     */
    @Test
    public void testCompareSmallerTrackID() {
        final Comment other = new Comment(c2.getTrackid() - 1, c2.getUserid(),
                c2.getTime() + Comment.getPeriod());
        final int compared = c2.compareTo(other);
        assertTrue(compared > 0);
    }

    /**
     * Setting period back to default value.
     */
    @Override
    public void tearDown() {
        Comment.setPeriod(5000);
    }
}
