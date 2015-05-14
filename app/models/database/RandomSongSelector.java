package models.database;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class selects a random song from the database. The current version of
 * this class uses the singleton pattern.
 * 
 * @since 13-05-2015
 * @version 14-05-2015
 * 
 * @see DatabaseConnector
 * 
 * @author stefan boodt
 *
 */
public class RandomSongSelector {

	/**
	 * The Singleton selector that is used by this class.
	 */
	private static RandomSongSelector selector;
	
	/**
	 * The name of the track_id field in the database.
	 */
	private final String trackid = "track_id";
	
	/**
	 * The query to be executed.
	 */
	private final String query = "SELECT DISTINCT " + trackid
			+ " FROM without_features_comments "
			+ " ORDER BY RAND() " 
			+ " LIMIT 1";

	/**
	 * Private constructor that construct new random song selectors.
	 */
	private RandomSongSelector() {

	}

	/**
	 * Method that returns the track_id of a random song in the database.
	 * @return The track_id of a random song.
	 */
	public int getRandomSong() {
		return getRandomSong(new DatabaseConnector());
	}

	/**
	 * Method that returns the track_id of a random song in the database.
	 * @param dbc The databaseconnector.
	 * @return The track_id of a random song.
	 */
	public int getRandomSong(final DatabaseConnector dbc) {
		final ResultSet result = dbc.executeQuery(getQuery());
		try {
			return result.getInt(trackid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Error when doing query "
			+ query + " on the database", e);
		}
	}

	/**
	 * Retrieves a new random song selector to allow usage of the
	 * {@link #getRandomSong()} method.
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
	 * @return The track_id of a random song.
	 */
	public static int getRandomSongTrackId() {
		return getRandomSongSelector().getRandomSong();
	}

	/**
	 * Returns the query that is being executed to get the unique
	 * random song.
	 * @return The query to execute.
	 */
	protected String getQuery() {
		return query;
	}
}
