package models.snippet;



public class Comment {
    private static int period = 5000;
    private int userid;
    private int timestamp;

    /**
     * Creates a new comment object. Contains simple get methods to get the userid or timestamp and a
     * set method to change the period which will change the timestamp
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

    public int getTime() {

      return (int) (Math.floor(timestamp / period) * period);
    }

    public int getUser() {
      return userid;
    }
    
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Changes the period in which the timestamp should be computed.
     * 
     * @param period
     *          new period time
     */
    public static void setPeriod(int per) {
      period = per;
    }

    public static int getPeriod() {
      return period;
    }

    public boolean equals(Object other) {
      return this.equals((Comment) other);

    }

    public boolean equals(Comment com) {
      return this.getUser() == com.getUser() && this.getTime() == com.getTime();
    }

    public int hashCode() {
      return userid + timestamp;
    }
  }

