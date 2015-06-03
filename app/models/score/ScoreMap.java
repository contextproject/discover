package models.score;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Class used to keep track of all the possible scores in the Seekers. It allows
 * easy control over the scores as well as an easy way to store them. This class uses a SortedSet
 * to keep track of the scores.
 * 
 * @since 03-06-2015
 * @version 03-06-2015
 * 
 * @see SortedMap
 * 
 * @author stefan boodt
 *
 */
public class ScoreMap {
    
    /**
     * The Set storing the scores.
     */
    private SortedMap<Integer, Integer> scores;
    
    /**
     * Creates a new SortedMap.
     */
    public ScoreMap() {
        this(new TreeMap<Integer, Integer>());
    }
    
    /**
     * Creates a new SortedMap with the given scores. This is equivalent to
     * calling {@link #ScoreMap()} and using {@link #setScores(SortedSet)} on it.
     * @param scores The new scores.
     */
    public ScoreMap(final SortedMap<Integer, Integer> scores) {
        setScores(scores);
    }
    
    /**
     * Adds the given score to the given time in the map.
     * @param starttime The starttime of the score to add.
     * @param points The number of points to add to the score.
     */
    public void add(final int starttime, final int points) {
        final int score = getScores().getOrDefault(starttime, 0) + points;
        getScores().put(starttime, score);
    }
    
    /**
     * Clears the scores in the map. The map should be empty afterwards.
     */
    public void clear() {
        getScores().clear();
    }
    
    /**
     * Sets the scores to the given scores. It clears the scores first.
     * @param scores The new SortedSet of scores.
     */
    public void setScores(final SortedMap<Integer, Integer> scores) {
        clear();
        this.scores = scores;
    }
    
    /**
     * Gets the scores used by the map. Use with caution as you can destroy the map using this.
     * Again please refrain from using this to modify the scores.
     * @return The scores.
     */
    protected SortedMap<Integer, Integer> getScores() {
        return scores;
    }
}
