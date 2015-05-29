package models.record;

import javax.persistence.Entity;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class to represent a comment.
 */
@Entity
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
	 * @param resultSet
	 *            The ResultSet object of the comment
	 */
	public Comment(final ResultSet resultSet) {
		try {
			trackid = resultSet.getInt("track_id");
			userid = resultSet.getInt("user_id");
			timestamp = resultSet.getInt("timestamp");
			body = resultSet.getString("text");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Other Comment constructor.
	 * @param trackid The trackid of the Song. 
	 * @param userid Id of the user.
	 * @param timestamp The place in a song where this comment is placed.
	 * @param body The content of a comment.
	 */
	public Comment(final int trackid, final int userid, final int timestamp, final String body) {
		this.trackid = trackid;
		this.userid = userid;
		this.timestamp = timestamp;
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
	 * Changes the period in which the time stamp should be computed.
	 *
	 * @param per
	 *            new period time
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
	@Override
	public int hashCode() {
		return userid + timestamp;
	}
}
