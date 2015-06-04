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
    private int id;

    /**
     * The duration of the track.
     */
    private int duration;

    /**
     * The artist of the track.
     */
    private String artist;

    /**
     * The title of the track.
     */
    private String title;

    /**
     * The url of the track.
     */
    private String url;

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
     * @param id       The id of the track
     * @param duration The duration of the track
     */
    public Track(final int id, final int duration) {
        this(id, duration, null, null, null);
    }

    /**
     * Constructor.
     *
     * @param id       The id of the track
     * @param duration The duration of the track
     * @param artist   The artist of the track
     * @param title    The title of the track
     * @param url      The SoundCloud url of the track
     */
    public Track(final int id, final int duration,
                 final String artist, final String title, final String url) {
        this.id = id;
        this.duration = duration;
        this.artist = artist;
        this.title = title;
        this.url = url;
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
                id = resultSet.getInt("track_id");
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
    public int getId() {
        return id;
    }

    /**
     * Setter of the id of the track.
     *
     * @param id The id of the track
     */
    public void setId(final int id) {
        this.id = id;
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
     * Getter of the artist of the track.
     *
     * @return The artist of the track
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Setter of the artist of the track.
     *
     * @param artist The artist of the track
     */
    public void setArtist(final String artist) {
        this.artist = artist;
    }

    /**
     * Getter of the title of the track.
     *
     * @return The title of the track
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setter of the title of the track.
     *
     * @param title The title of the track
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * Getter of the url of the track.
     *
     * @return The url of the track
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter of the url of the track.
     *
     * @param url The url of the track
     */
    public void setUrl(final String url) {
        this.url = url;
    }

    /**
     * Equals method.
     *
     * @param o Other object
     * @return True if objects are equals
     */
    @Override
    public boolean equals(final Object o) {
        return o instanceof Track && ((Track) o).id == this.id;
    }

    /**
     * Hashcode() method.
     *
     * @return Hash of the object
     */
    @Override
    public int hashCode() {
        int result = id;
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
        int result = (this.id - o.id);
        if (result == 0) {
            result += (this.duration - o.duration);
        }
        return result;
    }
}
