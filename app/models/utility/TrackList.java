package models.utility;

import models.database.DatabaseConnector;
import models.record.Track;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Class to represent a collection of tracks.
 */
public class TrackList extends ArrayList<Track> {

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
                Track track = new Track();
                track.put(Track.ID, resultSet.getInt("track_id"));
                track.put(Track.DURATION, resultSet.getInt("duration"));
                track.put(Track.GENRE, resultSet.getString("genre").toLowerCase());
                track.put(Track.TITLE, resultSet.getString("title"));
                track.put(Track.USER_ID, resultSet.getInt("user_id"));
                track.put(Track.SCORE, 0.0);
                if (hasColumn(resultSet, "danceability")) {
                    track.put(Track.DANCEABILITY, resultSet.getDouble("danceability"));
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
        return new TrackList(DatabaseConnector.getConnector()
                .executeQuery(query));
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

    /**
     * Distinct add the all the tracks from the provided tracklist to this tracklist.
     *
     * @param trackList The tracklist to add
     */
    public void addDistinctAll(final TrackList trackList) {
        for (Track track : trackList) {
            addDistinct(track);
        }
    }

    /**
     * Distinct add the track to this tracklist.
     *
     * @param otherTrack The track to add
     */
    private void addDistinct(final Track otherTrack) {
        if (!this.contains(otherTrack)) {
            add(otherTrack);
        } else {
            //update score entry
            if (otherTrack.containsKey(Track.SCORE)) {
                Track thisTrack = getSame(otherTrack);
                if (thisTrack != null) {
                    if (thisTrack.containsKey(Track.SCORE)) {
                        thisTrack.put(Track.SCORE, otherTrack.get(Track.SCORE) + thisTrack.get(Track.SCORE));
                    } else {
                        thisTrack.put(Track.SCORE, otherTrack.get(Track.SCORE));
                    }
                }
            }
        }
    }

    /**
     * Get the same track from this track list as the provided track.
     *
     * @param other The track to compare with
     * @return The same track form this track list
     */
    private Track getSame(final Track other) {
        for (Track track : this) {
            if (track.equals(other)) {
                return track;
            }
        }
        return null;
    }
}
