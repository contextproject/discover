package models.database;

import controllers.Application;
import models.snippet.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

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
     * Gets the comments of the song corresponding to the given track id.
     *
     * @param trackid the track id of the song
     * @return A set of comments of the song
     * @throws SQLException In case of a SQL exception
     */
    public final Set<Comment> getComments(final int trackid) {
        ResultSet comments = databaseConnector.executeQuery("SELECT user_id, timestamp, text FROM comments WHERE track_id = " + trackid);
        HashSet<Comment> result = new HashSet<>();

        try {
            while (comments.next()) {
                Comment current = new Comment(comments.getInt("user_id"), comments.getInt("timestamp"));
                result.add(current);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
