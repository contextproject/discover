package models.record;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class to represent a comment.
 */
public class Comment implements Record {

    /**
     * A standard number for computing the rounded time of a timestamp.
     */
    private static int period = 5000;

    /**
     * The id of the track.
     */
    private int trackid;

    /**
     * The user id of the comment.
     */
    private int userid;

    /**
     * The timestamp of the comment.
     */
    private int timestamp;

    /**
     * Constructor.
     */
    public Comment() {}

    /**
     * Constructor.
     *
     * @param resultSet The ResultSet object of the comment
     */
    public Comment(ResultSet resultSet) {
        try {
            trackid = resultSet.getInt("track_id");
            userid = resultSet.getInt("user_id");
            timestamp = resultSet.getInt("timestamp");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Comment(int trackid, int userid, int timestamp) {
        this.trackid = trackid;
        this.userid = userid;
        this.timestamp = timestamp;
    }

    /**
     * Gives the rounded time of a comment.
     *
     * @return the rounded time
     */
    public int getTime() {
        return (int) (Math.floor(timestamp / period) * period);
    }

    /**
     * Getter of the trackid of the comment.
     *
     * @return The trackid of the comment
     */
    public int getTrackid() {
        return trackid;
    }

    /**
     * Getter of the userid of the comment.
     *
     * @return The userid of the comment
     */
    public int getUserid() {
        return userid;
    }

    /**
     * Getter of the timestamp of the comment.
     *
     * @return The timestamp of the comment
     */
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Changes the period in which the time stamp should be computed.
     *
     * @param per new period time
     */
    public static void setPeriod(final int per) {
        period = per;
    }

    /**
     * Gives the period in which a timestamp is being rounded.
     *
     * @return the period
     */
    public static int getPeriod() {
        return period;
    }

    /**
     * Equals method for comments.
     */
    public boolean equals(final Object other) {
        if (other instanceof Comment) {
            Comment com = (Comment) other;
            return this.getUserid() == com.getUserid()
                    && this.getTime() == com.getTime();
        }
        return false;
    }

    /**
     * Simple hash method based on user id and time stamp.
     *
     * @return hash of the user id and the timestamp
     */
    public int hashCode() {
        return userid + timestamp;
    }
}
