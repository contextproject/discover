package models.record;

import javax.annotation.Nonnull;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class to represent a track.
 */
public class Track implements Record, Comparable<Track> {

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
     * Constructor.
     *
     * @param trackid  The id of the track
     * @param duration The duration of the track
     */
    public Track(final int trackid, final int duration) {
        this.trackid = trackid;
        this.duration = duration;
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
     * @param trackid The id of the track
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

    /**
     * Equals method.
     *
     * @param o Other object
     * @return True if objects are equals
     */
    @Override
    public boolean equals(final Object o) {
        if (o instanceof Track) {
            if (((Track) o).trackid == this.trackid) {
                if (((Track) o).duration == this.duration) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Hashcode() method.
     *
     * @return Hash of the object
     */
    @Override
    public int hashCode() {
        int result = trackid;
        result = 31 * result + duration;
        return result;
    }

    /**
     * Comparable of a Track object.
     *
     * @param o The Track object to compare to
     * @return True if Objects are the same
     */
    @Override
    public int compareTo(@Nonnull final Track o) {
        return (this.trackid - o.trackid) + (this.duration - o.duration);
    }
}
