package models.database;

import controllers.Application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import models.snippet.Comment;

/**
 * Class to retrieve the comments from the database.
 */
public class CommentRetriever {

	/**
	 * Database connector.
	 */
	private DatabaseConnector databaseConnector;

	/**
	 * Constructor.
	 */
	public CommentRetriever() {
		databaseConnector = Application.getDatabaseConnector();
	}

	/**
	 * Gets the number of comments a comment has.
	 * 
	 * @param trackid
	 *            the track id of a song.
	 * @return amount of comments of that song.
	 */
	public final int getAmountOfComments(final int trackid) {
		ResultSet comments = databaseConnector
				  .executeQuery("SELECT comment_count "
						+ "FROM tracks WHERE track_id = " + trackid);
		int res = 0;

		try {
			while (comments.next()) {
				res = comments.getInt("comment_count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Gets the comments of the song corresponding to the given track id.
	 *
	 * @param trackid
	 *            the track id of the song
	 * @return A set of comments of the song
	 */
	public final Set<Comment> getComments(final int trackid) {
		ResultSet comments = databaseConnector
				  .executeQuery("SELECT user_id, timestamp,"
						+ " text FROM comments WHERE track_id = " + trackid);
		HashSet<Comment> result = new HashSet<Comment>();

		try {
			while (comments.next()) {
				Comment current = new Comment(comments.getInt("user_id"),
						   comments.getInt("timestamp"));
				result.add(current);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Gets the comment of a song and also gets the content of a comment.
	 * 
	 * @param trackid
	 *            the track id of the song
	 * @return A map of comments with their content
	 */
	public final Map<Comment, String> getCommentsWithString(final int trackid) {
		ResultSet comments = databaseConnector
				  .executeQuery("SELECT user_id, timestamp,"
						+ " text FROM comments WHERE track_id = " + trackid);
		HashMap<Comment, String> result = new HashMap<Comment, String>();

		try {
			while (comments.next()) {
				Comment current = new Comment(comments.getInt("user_id"),
						  comments.getInt("timestamp"));
				String content = comments.getString("text");
				result.put(current, content);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
}
