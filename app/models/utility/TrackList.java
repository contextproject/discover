package models.utility;

import models.database.DatabaseConnector;
import models.record.Key;
import models.record.Track2;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class to represent a collection of tracks.
 */
public class TrackList extends ArrayList<Track2> {

    /**
     * Constructor.
     */
    public TrackList() {
    }

    /**
     * Constructor. Generates a collection with all the tracks from the ResultSet.
     *
     * @param resultSet The ResultSet containing the comments
     */
    public TrackList(final ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                Track2 track = new Track2();
                track.put(new Key<>("int", Integer.class), resultSet.getInt("track_id"));
                track.put(new Key<>("duration", String.class), resultSet.getString("duration"));
                track.put(new Key<>("genre", String.class), resultSet.getString("genre").toLowerCase());
                track.put(new Key<>("title", String.class), resultSet.getString("title"));
                track.put(new Key<>("user_id", Integer.class), resultSet.getInt("user_id"));
                track.put(new Key<>("score", Double.class), 0.0);
                if (hasColumn(resultSet, "danceability")) {
                    track.put(new Key<>("danceability", Double.class), resultSet.getDouble("danceability"));
                }
                add(track);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get a TrackList of the tracks retrieved from the query.
     *
     * @param query The query
     * @return The TrackList
     */
    public static TrackList get(final String query) {
        return new TrackList(DatabaseConnector.executeQuery(query));
    }

    /**
     * Checks if a ResultSet contains the provided column.
     *
     * @param rs         The ResultSet
     * @param columnName The column name
     * @return True if the ResultSet contains the column
     * @throws SQLException SQLException
     */
    public boolean hasColumn(final ResultSet rs, final String columnName) throws SQLException {
        ResultSetMetaData resultSetMetaData = rs.getMetaData();
        int columns = resultSetMetaData.getColumnCount();
        for (int x = 1; x <= columns; x++) {
            if (columnName.equals(resultSetMetaData.getColumnName(x))) {
                return true;
            }
        }
        return false;
    }
}
