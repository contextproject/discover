package models.seeker;

import models.snippet.TimedSnippet;

/**
 * A interface Seeker.
 */
public interface Seeker {

    /**
     * Seeks for the best start time and gives back a TimedSnippet.
     *
     * @param trackid The id of the track
     * @return A TimedSnippet object
     */
    TimedSnippet seek(int trackid);
}
