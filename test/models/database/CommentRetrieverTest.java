package models.database;

import controllers.Application;
import static org.junit.Assert.assertEquals;



import java.util.Iterator;
import java.util.Set;

import models.database.retriever.CommentRetriever;
import models.snippet.Comment;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the CommentRetriever class.
 */
public class CommentRetrieverTest {

	/**
	 * Comment retriever object.
	 */
	private CommentRetriever commentRetriever;

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
		databaseConnector
				  .makeConnection("jdbc:mysql://188.166.78.36/contextbase",
						"context", "password");
		Application.setDatabaseConnector(databaseConnector);

		
	}

	/**
	 * Checking the amountofcomments function.
	 * 
	 * @throws Exception
	 *             if something goes wrong.
	 */
	@Test
	public void getAmountOfCommentsTest() throws Exception {
		// Song with highest amount of comments
		commentRetriever = new CommentRetriever(114419538);
		int ac = commentRetriever.getNoComments();
		assertEquals(20120, ac);
	}

	/**
	 * Checking the getcomments function.
	 * 
	 * @throws Exception
	 *             if something goes wrong.
	 */
	@Test
	public void getCommentsTest() throws Exception {
		databaseConnector.executeUpdate("INSERT INTO with_features_comments "
				  + "VALUES (1, 1, 1, '2015-01-01 12:00:00', 1, 'test')");
		commentRetriever = new CommentRetriever(1);
		Set<Comment> comments = commentRetriever.getComments();
		Iterator<Comment> it = comments.iterator();
		while (it.hasNext()) {
			Comment comment = it.next();
			assertEquals(1, comment.getUser());
			assertEquals(1, comment.getTimestamp());
		}
		databaseConnector
				  .executeUpdate("DELETE FROM with_features_comments WHERE track_id = 1 "
						+ "AND comment_id = 1 "
						+ "AND user_id = 1 AND created_at = '2015-01-01 12:00:00' "
						+ "AND timestamp = 1 AND text = 'test'");
	}

}
