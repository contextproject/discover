package models.database.retriever;

import controllers.Application;
import models.database.DatabaseConnector;
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
     * The id of the track.
     */
    private int trackid;

    /**
     * The set of comments of the track.
     */
    private Set<Comment> comments;

    /**
     * The number of comments of the track.
     */
    private int noComments;

    /**
     * Constructor.
     *
     * @param trackid The id of the track
     */
    public CommentRetriever(final int trackid) {
        databaseConnector = Application.getDatabaseConnector();
        this.trackid = trackid;
        retrieveComments();
    }

    /**
     * Gets the comments of the song corresponding to the given track id.
     */
    private void retrieveComments() {
        ResultSet resultSet = databaseConnector.executeQuery(
                "SELECT * FROM comments WHERE track_id = " + trackid);
        HashSet<Comment> result = new HashSet<Comment>();

        try {
            while (resultSet.next()) {
                Comment current = new Comment(resultSet.getInt("user_id"),
                        resultSet.getInt("timestamp"));
                result.add(current);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        noComments = result.size();
        comments = result;
    }

    /**
     * Get the comments of the track.
     * @return Set of comments of the track
     */
    public Set<Comment> getComments() {
        return comments;
    }

    /**
     * Get the number of comments of the track.
     * @return The number of comments of the track.
     */
    public int getNoComments() {
        return noComments;
    }
}
