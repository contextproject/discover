package models.database.retriever;

import controllers.Application;
import models.database.DatabaseConnector;
import models.snippet.Comment;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the CommentRetriever class.
 */
public class CommentRetrieverTest {

    /**
     * DatabaseConnector object.
     */
    private DatabaseConnector databaseConnector;

    /**
     * Setting up the database connection.
     */
    @Before
    public void setUp() {
        databaseConnector = new DatabaseConnector();
        databaseConnector.loadDrivers();
        databaseConnector.makeConnection("jdbc:mysql://188.166.78.36/contextbase", "context", "password");
        Application.setDatabaseConnector(databaseConnector);
    }

    /**
     * Test for the getComments() method.
     *
     * @throws Exception Exception
     */
    @Test
    public void getCommentsTest() throws Exception {
        // Track with one comment and highest playback count
        CommentRetriever commentRetriever = new CommentRetriever(62683860);
        assertEquals(1, commentRetriever.getNoComments());

        Set<Comment> comments = commentRetriever.getComments();
        Comment comment = comments.iterator().next();
        assertEquals(11408439, comment.getUser());
        assertEquals(52052, comment.getTimestamp());
    }

    /**
     * Test for the getNoComments() method.
     *
     * @throws Exception Exception
     */
    @Test
    public void getNoCommentsTest() throws Exception {
        // Track with highest amount of comments
        CommentRetriever commentRetriever = new CommentRetriever(114419538);
        assertEquals(8135, commentRetriever.getNoComments());
    }
}
