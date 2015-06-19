package models.database;

/**
 * This class selects a random song from the database. The current version of
 * this class uses the singleton pattern.
 *
 * @author stefan boodt
 * @version 08-06-2015
 * @see DatabaseConnector
 * @since 13-05-2015
 */
public final class RandomSongSelector {

    /**
     * The Singleton selector that is used by this class.
     */
    private static RandomSongSelector selector;

    /**
     * The query to be executed.
     */
    private final String query;

    /**
     * Private constructor that construct new random song selectors.
     */
    private RandomSongSelector() {
        query = "SELECT DISTINCT track_id FROM tracks ORDER BY RAND() LIMIT 1";
    }

    /**
     * Method that returns the track_id of a random song in the database.
     *
     * @return The track_id of a random song.
     */
    public int getRandomSong() {
        return DatabaseConnector.getConnector().getSingleInt(query, "track_id");
    }

    /**
     * Retrieves a new random song selector to allow usage of the
     * {@link #getRandomSong()} method.
     *
     * @return A new RandomSongSelector or the already created one if one
     * already existed.
     */
    public static RandomSongSelector getRandomSongSelector() {
        if (selector == null) {
            selector = new RandomSongSelector();
        }
        return selector;
    }

    /**
     * This method returns the track_id of a random song in the database. This
     * method is equivalent to {@link #getRandomSongSelector()} and then use
     * {@link #getRandomSong()} on it. This method therefore is only a
     * convenience method.
     *
     * @return The track_id of a random song.
     */
    public static int getRandomSongTrackId() {
        return getRandomSongSelector().getRandomSong();
    }

    /**
     * Returns the query that is being executed to get the unique
     * random song.
     *
     * @return The query to execute.
     */
    protected String getQuery() {
        return query;
    }
}
