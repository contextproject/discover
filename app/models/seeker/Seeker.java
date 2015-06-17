package models.seeker;

import models.score.ScoreStorage;
import models.snippet.TimedSnippet;

/**
 * The Seeker interface. This Interface provides methods that make searching for
 * snippets in the song relatively easy and understandable. It's methods provide an
 * easy way to combine seekers by effectively allowing the use of a ScoreStorage and
 * then letting each seeker pass on on any previous results by other Seekers.
 * 
 * <p>
 * The result of this is that a good Seeker uses the Decorator pattern to handle it's search.
 * The default ending point of each Seeker is the NullSeeker which simply returns 0 scores for
 * every starttime.
 * </p>
 * 
 * @since 26-05-2015
 * @version 03-06-2015
 * 
 * @author daan schipper
 * @author stefan boodt
 * @author arthur hovenesyan
 * @author tomas heinsohn huala
 */
public interface Seeker {

    /**
     * Seeks for the best start time and gives back a TimedSnippet.
     *
     * @return A TimedSnippet object
     */
    TimedSnippet seek();
    
    /**
     * Seeks for the best start time and gives back a TimedSnippet.
     * @param duration The wanted DURATION of the snippet.
     * @return The snippet found to return.
     */
    TimedSnippet seek(final int duration);
    
    /**
     * Calculates the scores of the times and adds them to already existing
     * ones of the Seekers that lay underneath this one. A good Seeker uses this method on the
     * Seeker it decorates as well.
     * @param duration The window of the seeking. This number can be useful by some Seekers,
     * such as the CommentIntensitySeeker
     * @return The scores this seeker has aqcuired, added to any results a previous
     * Seeker has found.
     */
    ScoreStorage calculateScores(final int duration);
}
