package models.seeker;

import models.record.Track;
import models.snippet.TimedSnippet;

/**
 * Generates a random start time for the track.
 */
public class RandomSeeker implements Seeker {

    /**
     * The track.
     */
    private Track track;

    /**
     * Constructor.
     *
     * @param track The track
     */
    public RandomSeeker(final Track track) {
        this.track = track;
    }

    /**
     * Generates a random start time for a snippet.
     *
     * @return The start time
     */
    private int getStartTime() {
        return (int) (Math.random() * track.get(Track.duration));
    }

    /**
     * Seeks the snippet to be used of a given song.
     *
     * @return A TimedSnippet object
     */
    @Override
    public TimedSnippet seek() {
        return new TimedSnippet(getStartTime());
    }
}
