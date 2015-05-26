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

    private int trackid;

    private Set<Comment> comments;

    private int noComments;

    /**
     * Constructor.
     */
    public CommentRetriever(int trackid) {
        databaseConnector = Application.getDatabaseConnector();
        this.trackid = trackid;
        something();
    }

    /**
     * Gets the comments of the song corresponding to the given track id.
     *
     * @return A set of comments of the song
     */
    private final void something() {
        ResultSet resultSet = databaseConnector
                .executeQuery("SELECT user_id, timestamp,"
                        + " text FROM comments WHERE track_id = " + trackid);
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

    public Set<Comment> getComments() {
        return comments;
    }

    public int getNoComments() {
        return noComments;
    }
}
