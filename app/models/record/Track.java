package models.record;

import javax.persistence.Entity;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class to represent a track.
 */
@Entity
public class Track implements Record {

    /**
     * The id of the track.
     */
    private int trackid;

    /**
     * The duration of the track.
     */
    private int duration;

    /**
     * Constructor.
     */
    public Track() {
    }

    /**
     * Constructor that accepts a ResultSet to build the track.
     *
     * @param resultSet The ResultSet of the track
     */
    public Track(final ResultSet resultSet) {
        process(resultSet);
    }

    /**
     * Processes the ResultSet of the track.
     *
     * @param resultSet The ResultSet of the track
     * @return True if succeeds
     */
    protected boolean process(final ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                trackid = resultSet.getInt("track_id");
                duration = resultSet.getInt("duration");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Getter of the id of the track.
     *
     * @return The id of the track
     */
    public int getTrackid() {
        return trackid;
    }

    /**
     * Setter of the id of the track.
     *
     * @param trackid  The id of the track
     */
    public void setTrackid(final int trackid) {
        this.trackid = trackid;
    }

    /**
     * Getter of the duration of the track.
     *
     * @return The duration of the track
     */
    public int getDuration() {
        return duration;
    }

    /**
     * Setter of the duration of the track.
     *
     * @param duration The duration of the track
     */
    public void setDuration(final int duration) {
        this.duration = duration;
    }
}
