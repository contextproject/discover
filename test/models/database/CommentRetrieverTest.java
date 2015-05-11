package models.database;

import controllers.Application;
import models.snippet.Comment;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertEquals;

/**
 * Test class for the CommentRetriever class
 */
public class CommentRetrieverTest {

    private CommentRetriever commentRetriever;

    private DatabaseConnector databaseConnector;

    @Before
    public void setUp() {
        databaseConnector = new DatabaseConnector();
        databaseConnector.loadDrivers();
        databaseConnector.makeConnection();
        Application.setDatabaseConnector(databaseConnector);

        commentRetriever = new CommentRetriever();
    }

    @Test
    public void testGetComments() throws Exception {
        databaseConnector.executeUpdate("INSERT INTO comments_without_features VALUES (1, 1, 1, '2015-01-01 12:00:00', 1, 'test')");

        Set<Comment> comments = commentRetriever.getComments(1);
        Iterator<Comment> it = comments.iterator();
        while (it.hasNext()) {
            Comment comment = it.next();
            assertEquals(1, comment.getUser());
            assertEquals(1, comment.getTimestamp());
        }

        databaseConnector.executeUpdate("DELETE FROM comments_without_features WHERE track_id = 1 AND comment_id = 1 AND user_id = 1 AND created_at = '2015-01-01 12:00:00' AND timestamp = 1 AND text = 'test'");
    }
}