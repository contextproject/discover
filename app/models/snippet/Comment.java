package models.snippet;

/**
 * The Comment class which can contain the user id and timestamp of a comment. Contains simple get
 * methods to get the userid or timestamp and a set method to change the period which will change
 * the timestamp.
 */
public class Comment {

  /**
   * A standard number for computing the rounded time of a timestamp.
   */
  private static int period = 5000;

  /**
   * The userid of a comment.
   */
  private int userid;

  /**
   * The actual timestamp.
   */
  private int timestamp;

  /**
   * Creates a new comment object.
   * 
   * @param userId
   *          id of the user
   * @param time
   *          time of the given comment, will be saved as thousand
   */
  public Comment(int userId, int time) {
    userid = userId;
    timestamp = time;
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
   * @param period
   *          new period time
   */
  public static void setPeriod(int per) {
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
   * First equals method to minimize if-statements.
   */
  @Override
  public boolean equals(Object other) {
    return other.equals((Comment) this);
  }

  /**
   * Second equals method to actually test the similarity between two comments.
   * 
   * @param com
   *          the comment to compare with
   * @return true or false
   */
  public boolean equals(Comment com) {
    return this.getUser() == com.getUser() && this.getTime() == com.getTime();
  }

  /**
   * Simple hash method based on user id and time stamp.
   */
  public int hashCode() {
    return userid + timestamp;
  }
}
