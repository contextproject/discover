package models.snippet;

import java.util.Set;

/**
 * A interface Seeker.
 *
 */
public interface Seeker {

	/**
     * Seeks for the best start time and gives back a TimedSnippet.
     *
     * @param coms The set of comments to use
     * @return A TimedSnippet object
     */
	TimedSnippet seek(Set<Comment> coms);
	
	/**
     * Seeks for the best start time of a given song with the duration known.
     *
     * @param coms     The set of comments to use
     * @param duration The duration of the song
     * @return A TimedSnippet object
     */
	TimedSnippet seekDuration(Set<Comment> coms, int duration);
}
