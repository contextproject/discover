package models.database.retriever;

import controllers.Application;
import models.database.DatabaseConnector;
import models.utility.CommentList;
import models.record.Record;

/**
 * Class to retrieve the comments from the database.
 */
public class CommentRetriever implements Record {

    /**
     * Database connector.
     */
    private DatabaseConnector databaseConnector;

    /**
     * The comments of the track.
     */
    private CommentList comments;

    /**
     * Constructor.
     *
     * @param trackid The id of the track
     */
    public CommentRetriever(final int trackid) {
        this.databaseConnector = Application.getDatabaseConnector();
        comments = new CommentList(databaseConnector.executeQuery(
                "SELECT * FROM comments WHERE track_id = " + trackid
        ));
    }

    /**
     * Get the comments of the track.
     *
     * @return Set of comments of the track
     */
    public CommentList getComments() {
        return comments;
    }

    /**
     * Get the number of comments of the track.
     *
     * @return The number of comments of the track.
     */
    public int getNoComments() {
        return comments.size();
    }
}
