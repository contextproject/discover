package models.utility;

import models.record.Track2;

import java.sql.ResultSet;
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
                track.put("id", resultSet.getInt("track_id"));
                track.put("duration", resultSet.getString("duration"));
                track.put("genre", resultSet.getString("genre"));
                track.put("title", resultSet.getString("title"));
                add(track);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
