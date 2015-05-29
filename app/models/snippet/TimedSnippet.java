package models.snippet;

/**
 * Snipped that has a window and ends when the given time is finished.
 *
 * @author stefanboodt
 * @version 30-05-2015
 * @since 29-04-2015
 */
public class TimedSnippet {

    /**
     * The start time of the snippet.
     */
    private final int starttime;

    /**
     * The window of the snippet.
     */
    private final int window;

    /**
     * The default window in ms.
     */
    private static int defaultDURATION = 30000;

    /**
     * Creates a timed snippet that starts at the given time.
     *
     * @param starttime
     *            The starttime of the snippet in ms.
     */
    public TimedSnippet(final int starttime) {
        this(starttime, defaultDURATION);
    }

    /**
     * Creates a timed snippet that starts at the given time.
     *
     * @param starttime
     *            The starttime of the snippet in ms. A negative starttime
     *            results in starttime is 0.
     * @param duration
     *            The window of the snippet in ms. If the window is 0 or less
     *            the default is used.
     */
    public TimedSnippet(final int starttime, final int duration) {
        if (starttime < 0) {
            this.starttime = 0;
        } else {
            this.starttime = starttime;
        }
        if (duration > 0) {
            this.window = duration;
        } else {
            this.window = defaultDURATION;
        }
    }

    /**
     * Gets the starttime of the snippet.
     *
     * @return The starttime of the snippet in ms.
     */
    public int getStartTime() {
        return starttime;
    }

    /**
     * Gets the window of this snippet in ms.
     *
     * @return The snippet window in ms.
     */
    public int getWindow() {
        return window;
    }

    /**
     * Returns the end time of the snippet.
     *
     * @return The endtime of the snippet in ms.
     */
    public int getEndTime() {
        return getStartTime() - getWindow();
    }

    /**
     * Returns the default window.
     *
     * @return The default window of the
     */
    public static int getDefaultDuration() {
        return defaultDURATION;
    }

    /**
     * Sets the default window of the snippet. If window is less than 0 this
     * method does nothing.
     *
     * @param duration
     *            The new default window.
     */
    public static void setDefaultDuration(final int duration) {
        if (duration > 0) {
            defaultDURATION = duration;
        }
    }

    /**
     * Creates a new TimedSnippet with the given starttime and window.
     *
     * @param starttime
     *            The starttime in ms.
     * @param durationInSeconds
     *            The window of the snippet in seconds.
     * @return The newly created snippet.
     */
    public static TimedSnippet createSnippet(final int starttime,
            final int durationInSeconds) {
        return new TimedSnippet(starttime, durationInSeconds * 1000);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>
     * The TimedSnippet equals compares both starttime and window.
     * </p>
     */
    @Override
    public boolean equals(final Object other) {
        if (other instanceof TimedSnippet) {
            TimedSnippet that = (TimedSnippet) other;
            return this.starttime == that.starttime
                    && this.window == that.window;
        }
        return false;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * The hashCode of a TimedSnippet is a function of both the starttime and
     * the window.
     * </p>
     */
    @Override
    public int hashCode() {
        return 3 * getStartTime() + 2 * getWindow();
    }

    /**
     * Copies the snippet.
     *
     * @return A snippet with another address but with the same values.
     *         Equivalent to calling the constructor with the same values.
     */
    public TimedSnippet copy() {
        return new TimedSnippet(this.starttime, this.window);
    }

    @Override
    public String toString() {
        return "TimedSnippet(" + getStartTime() + ", " + getWindow() + ")";
    }
}
