package models.snippet;

/**
 * The Comment class which can contain the user id and timestamp of a comment.
 * Contains simple get methods to get the user id or timestamp and a set method
 * to change the period which will change the timestamp.
 */
public class Comment {

    /**
     * A standard number for computing the rounded time of a timestamp.
     */
    private static int period = 5000;

    /**
     * The user id of a comment.
     */
    private int userid;

    /**
     * The actual timestamp.
     */
    private int timestamp;

    private String body;

    /**
     * Creates a new comment object.
     *
     * @param userId id of the user
     * @param time   time of the given comment, will be saved as thousand
     */
    public Comment(final int userId, final int time, final String body) {
        this.userid = userId;
        this.timestamp = time;
        this.body = body;
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
     * Give the user id of a comment.
     *
     * @return user id
     */
    public int getUser() {
        return userid;
    }

    /**
     * A method to simplify tests with comments.
     *
     * @return the actual time stamp
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
    @Override
    public boolean equals(final Object other) {
        if (other instanceof Comment) {
            Comment com = (Comment) other;
            return this.getUser() == com.getUser()
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
