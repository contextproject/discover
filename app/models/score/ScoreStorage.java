package models.score;

/**
 * Interface that combines used for storing the scores of the Seekers. It can also
 * store the scores of other things if you change the meaning of the starttime variable in
 * add. It is a simple way to unify the Seekers and allows easy use of the 
 * @author stefanboodt
 *
 */
public interface ScoreStorage {
    
    /**
     * Adds the given score to the given time in the storage. If the starttime
     * is already stored in there once the storage should add {@code points} to the
     * given time.
     * @param starttime The starttime of the score to add.
     * @param points The number of points to add to the score.
     */
    void add(final int starttime, final int points);
    
    /**
     * Clears the scores in the storage. The storage should be empty afterwards.
     */
    void clear();
    
    /**
     * Checks if the structure is empty.
     * @return True if the scores datastructure is empty.
     */
    boolean isEmpty();
    
    /**
     * Seeks the storage for the maximum amount of points and returns it's starttime.
     * @return The first starttime of the maximum score in the storage. It should return -1 if
     * there is no maximal score, because every starttime has {@link Integer#MIN_VALUE} as score or
     * because there is no score in the storage.
     */
    int maxScoreStartTime();
    
    /**
     * Returns the score associated with the given starttime or {@code 0} when
     * the score is not found in the storage.
     * @param starttime The starttime to request from the storage.
     * @return The score associated with it.
     */
    int get(final int starttime);
    
    /**
     * Returns the maximum score in the storage. This can be efficiently achieved by combining
     * {@link #maxScoreStartTime()} and {@link #get(int)}.
     * @return The maximum score stored.
     */
    int maxScore();
}
