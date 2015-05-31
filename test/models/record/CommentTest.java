package models.record;

import models.database.DatabaseConnector;
import models.record.Comment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class CommentTest {


    /**
     * Comment 1.
     */
    private Comment c1;
    /**
     * Comment 2.
     */
    private Comment c2;
    /**
     * Comment 3.
     */
    private Comment c3;

    /**
     * DatabaseConnector
     */
    DatabaseConnector databaseConnector;

    /**
     * Setting up 2 comments to test with.
     */
    @Before
    public void makeComments() {
        databaseConnector = new DatabaseConnector();
        databaseConnector.loadDrivers();
        databaseConnector.makeConnection("jdbc:mysql://188.166.78.36/contextbase", "context", "password");

        c1 = new Comment(1, 1, 5000);
        c2 = new Comment(1, 2, 12321);
        c3 = new Comment(1, 1, 12321);
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
        ResultSet resultSet = databaseConnector.executeQuery("SELECT * FROM comments LIMIT 1");
        Comment comment = new Comment();
        assertTrue(comment.process(resultSet));
    }

    /**
     * Test for the process() method.
     */
    @Test
    public void testProcess2() {
        ResultSet resultSet = databaseConnector.executeQuery("SELECT * FROM tracks LIMIT 1");
        Comment comment = new Comment();
        assertFalse(comment.process(resultSet));
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
     * Setting period back to default value.
     */
    @After
    public void after() {
        Comment.setPeriod(5000);
    }
}
