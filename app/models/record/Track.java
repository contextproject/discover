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
     * The id of the user that uploaded the track.
     */
    private int userid;

    /**
     * Genre of the track.
     */
    private String genre;

    /**
     * Constructor that accepts a ResultSet to build the track.
     *
     * @param resultSet
     *            The ResultSet of the track
     */
    public Track(final ResultSet resultSet) {
        process(resultSet);
    }

    /**
     * Constructor.
     *
     * @param trackid
     *            The id of the track
     * @param duration
     *            The duration of the track
     * @param userid
     *            The user id of the track
     * @param genre
     *            The genre of the track
     */
    public Track(final int trackid, final int duration, final int userid,
            final String genre) {
        this.trackid = trackid;
        this.duration = duration;
        this.userid = userid;
        this.genre = genre;
    }

    /**
     * Processes the ResultSet of the track.
     *
     * @param resultSet
     *            The ResultSet of the track
     * @return True if succeeds
     */
    protected boolean process(final ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                trackid = resultSet.getInt("track_id");
                duration = resultSet.getInt("duration");
                userid = resultSet.getInt("user_id");
                genre = resultSet.getString("genre");
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
     * @param trackid
     *            The id of the track
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
     * @param duration
     *            The duration of the track
     */
    public void setDuration(final int duration) {
        this.duration = duration;
    }

    /**
     * Getter for the user id of the track.
     * 
     * @return The user id of the track
     */
    public int getUserid() {
        return userid;
    }

    /**
     * Setter of the user id of the track.
     *
     * @param userid
     *            The user id of the track.
     */
    public void setUserid(final int userid) {
        this.userid = userid;
    }

    /**
     * Getter for the genre of the track.
     * 
     * @return The genre of the track
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Setter of the genre of the Track.
     * 
     * @param genre
     *            The genre of the track.
     */
    public void setGenre(final String genre) {
        this.genre = genre;
    }

    /**
     * Equals method.
     *
     * @param o
     *            Other object
     * @return True if objects are equals
     */
    @Override
    public boolean equals(final Object o) {
        if (o instanceof Track) {
            if (((Track) o).trackid == this.trackid) {
                return true;
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
     * @param o
     *            The Track object to compare to
     * @return True if Objects are the same
     */
    @Override
    public int compareTo(@Nonnull final Track o) {
        return (this.trackid - o.trackid) + (this.duration - o.duration);
    }
}
