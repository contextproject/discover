package models.snippet;

/**
 * Snipped that has a duration and ends when the given time is finished.
 * @author stefanboodt
 * @version 29-04-2015
 * @since 29-04-2015
 */
public class TimedSnippet {

  /**
   * The start time of the snippet.
   */
  private final int starttime;

  /**
   * The duration of the snippet.
   */
  private final int duration;

  /**
   * The default duration in ms.
   */
  private static int DEFAULT_DURATION = 30000;

  /**
   * Creates a timed snippet that starts at the given time.
   * @param starttime
   *          The starttime of the snippet in ms.
   */
  public TimedSnippet(int starttime) {
    this(starttime, DEFAULT_DURATION);
  }

  /**
   * Creates a timed snippet that starts at the given time.
   * @param starttime
   *          The starttime of the snippet in ms. A negative starttime results in starttime is 0.
   * @param duration
   *          The duration of the snippet in ms. If the duration is 0 or less the default is used.
   */
  public TimedSnippet(int starttime, int duration) {
    if (starttime < 0) {
      this.starttime = 0;
    } else {
      this.starttime = starttime;
    }
    if (duration > 0) {
      this.duration = duration;
    } else {
      this.duration = DEFAULT_DURATION;
    }
  }

  /**
   * Gets the starttime of the snippet.
   * @return The starttime of the snippet in ms.
   */
  public int getStartTime() {
    return starttime;
  }

  /**
   * Gets the duration of this snippet in ms.
   * @return The snippet duration in ms.
   */
  public int getDuration() {
    return duration;
  }

  /**
   * Returns the end time of the snippet.
   * @return The endtime of the snippet in ms.
   */
  public int getEndTime() {
    return getStartTime() - getDuration();
  }

  /**
   * Returns the default duration.
   * @return The default duration of the
   */
  public static int getDefaultDuration() {
    return DEFAULT_DURATION;
  }

  /**
   * Sets the default duration of the snippet. If duration is less than 0 this method does nothing.
   * @param duration
   *          The new default duration.
   */
  public static void setDefaultDuration(int duration) {
    if (duration > 0) {
      DEFAULT_DURATION = duration;
    }
  }

  /**
   * Creates a new TimedSnippet with the given starttime and duration.
   * @param starttime
   *          The starttime in ms.
   * @param durationInSeconds
   *          The duration of the snippet in seconds.
   * @return The newly created snippet.
   */
  public static TimedSnippet createSnippet(int starttime, int durationInSeconds) {
    return new TimedSnippet(starttime, durationInSeconds * 1000);
  }

  /**
   * {@inheritDoc}
   * <p/>
   * <p>
   * The hashCode of a TimedSnippet is a function of both the starttime and the duration.
   * </p>
   */
  @Override
  public int hashCode() {
    return (3 * getStartTime()) + (2 * getDuration());
  }

  /**
   * Copies the snippet.
   * @return A snippet with another address but with the same values. Equivalent to calling the
   *         constructor with the same values.
   */
  public TimedSnippet copy() {
    return new TimedSnippet(this.starttime, this.duration);
  }
}
