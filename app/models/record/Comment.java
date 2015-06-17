package models.record;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class to represent a comment.
 */
public class Comment implements Record, Comparable<Comment> {

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
     * The body of the comment.
     */
    private String body;

    /**
     * Constructor.
     */
    public Comment() {
    }

    /**
     * Constructor.
     *
     * @param resultSet The ResultSet object of the comment
     */
    public Comment(final ResultSet resultSet) {
        process(resultSet);
    }

    /**
     * Constructor.
     *
     * @param trackid   The id of the track
     * @param userid    The user id of the comment
     * @param timestamp The timestamp of the comment
     */
    public Comment(final int trackid, final int userid, final int timestamp) {
        this(trackid, userid, timestamp, "");
    }

    /**
     * Constructor.
     *
     * @param trackid   The id of the track
     * @param userid    The user id of the comment
     * @param timestamp The timestamp of the comment
     * @param body      The content of the comment
     */
    public Comment(final int trackid, final int userid, final int timestamp, final String body) {
        this.trackid = trackid;
        this.userid = userid;
        this.timestamp = timestamp;
        this.body = body;
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
     * Changes the period in which the time stamp should be computed.
     *
     * @param per new period time
     */
    public static void setPeriod(final int per) {
        period = per;
    }

    /**
     * Processes the ResultSet of comments of the track.
     *
     * @param resultSet The ResultSet of the track
     * @return True if succeeds
     */
    protected boolean process(final ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                trackid = resultSet.getInt("track_id");
                userid = resultSet.getInt("user_id");
                timestamp = resultSet.getInt("timestamp");
                body = resultSet.getString("text");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Gives the rounded time of a comment.
     *
     * @return the rounded time
     */
    public int getTime() {
        final double division = (double) timestamp / period;
        return (int) (Math.floor(division) * period);
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
     * Getter of the body of the comment.
     *
     * @return The body of the comment
     */
    public String getBody() {
        return body;
    }

    /**
     * Equals method for comments.
     *
     * @param other The other object
     * @return True if objects are equal
     */
    @Override
    public boolean equals(final Object other) {
        if (other instanceof Comment) {
            Comment com = (Comment) other;
            return this.getUserid() == com.getUserid()
                    && this.getTime() == com.getTime();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return userid + timestamp;
    }

    @Override
    public int compareTo(@Nonnull final Comment other) {
        final int result;
        final int track = this.trackid - other.trackid;
        if (track == 0) {
            result = this.getTime() - other.getTime();
        } else {
            result = track;
        }
        return result;
    }
}
