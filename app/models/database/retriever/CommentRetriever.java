package models.database.retriever;

import models.database.DatabaseConnector;
import models.record.Record;
import models.utility.CommentList;

/**
 * Class to retrieve the comments from the database.
 */
public class CommentRetriever implements Record {

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
        comments = new CommentList(DatabaseConnector.executeQuery(
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
