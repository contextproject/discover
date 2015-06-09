package models.snippet;

/**
 * This class contains useful methods to create TimedSnippets.
 * 
 * @since 09-06-2015
 * @version 09-06-2015
 * 
 * @see TimedSnippet
 * 
 * @author stefan boodt
 *
 */
public final class TimedSnippetFactory {

    /**
     * Creates a new factory.
     */
    private TimedSnippetFactory() {
        
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
    public static TimedSnippet createSnippetWindowInSeconds(final int starttime,
            final int durationInSeconds) {
        return createSnippet(starttime, durationInSeconds * 1000);
    }
    
    /**
     * Creates a TimedSnippet with the given starttime and window.
     * @param starttime The starttime in ms.
     * @param duration The duration in ms.
     * @return The generated TimedSnippet.
     */
    public static TimedSnippet createSnippet(final int starttime,
            final int duration) {
        return new TimedSnippet(starttime, duration);
    }
    
    /**
     * Creates a TimedSnippet with the given starttime.
     * @param starttime The starttime in ms.
     * @return The generated TimedSnippet.
     */
    public static TimedSnippet createSnippet(final int starttime) {
        return new TimedSnippet(starttime);
    }
}
