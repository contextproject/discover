package models.database.retriever;

import controllers.Application;
import models.database.DatabaseConnector;
import models.record.CommentList;
import org.junit.Before;
import org.junit.Test;

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

        CommentList comments = commentRetriever.getComments();
        assertEquals(1, comments.size());
    }
}
