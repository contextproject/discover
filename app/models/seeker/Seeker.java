package models.seeker;

import models.snippet.TimedSnippet;

/**
 * A Seeker interface.
 */
public interface Seeker {

    /**
     * Seeks for the best start time and gives back a TimedSnippet.
     *
     * @return A TimedSnippet object
     */
    TimedSnippet seek();
}
