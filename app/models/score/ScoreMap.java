package models.score;

import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Class used to keep track of all the possible scores in the Seekers. It allows
 * easy control over the scores as well as an easy way to store them. This class
 * uses a SortedMap to keep track of the scores.
 * 
 * @since 03-06-2015
 * @version 03-06-2015
 * 
 * @see SortedMap
 * 
 * @author stefan boodt
 *
 */
public class ScoreMap implements ScoreStorage {

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
     * calling {@link #ScoreMap()} and using {@link #setScores(SortedSet)} on
     * it.
     * 
     * @param scores
     *            The new scores.
     */
    public ScoreMap(final SortedMap<Integer, Integer> scores) {
        setScores(scores);
    }

    @Override
    public void add(final int starttime, final int points) {
        final int score = get(starttime) + points;
        getScores().put(starttime, score);
    }

    @Override
    public void clear() {
        getScores().clear();
    }

    @Override
    public boolean isEmpty() {
        return getScores().isEmpty();
    }

    /**
     * Sets the scores to the given scores. It clears the scores first.
     * 
     * @param scores
     *            The new SortedSet of scores.
     */
    public void setScores(final SortedMap<Integer, Integer> scores) {
        this.scores = scores;
        clear();
    }

    /**
     * Gets the scores used by the map. Use with caution as you can destroy the
     * map using this. Again please refrain from using this to modify the
     * scores.
     * 
     * @return The scores.
     */
    protected SortedMap<Integer, Integer> getScores() {
        return scores;
    }

    @Override
    public int maxScoreStartTime() {
        return maxScoreStartTime(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public int maxScoreStartTime(final int lowerbound, final int upperbound) {
        final Set<Entry<Integer, Integer>> entries = getScores().entrySet();
        int maxscore = Integer.MIN_VALUE;
        int starttime = -1;
        for (Entry<Integer, Integer> e : entries) {
            if (e.getKey() >= lowerbound && e.getKey() <= upperbound) {
                if (maxscore < e.getValue()) {
                    maxscore = e.getValue();
                    starttime = e.getKey();
                } else if (starttime == -1) {
                    starttime = e.getKey();
                    // Just for the case that there are only MIN_VALUE in the storage.
                }
            }
        }
        return starttime;
    }

    @Override
    public int get(final int starttime) {
        return getScores().getOrDefault(starttime, 0);
    }

    @Override
    public int maxScore() {
        return get(maxScoreStartTime());
    }

    @Override
    public int size() {
        return getScores().size();
    }
    
    @Override
    public boolean equals(final Object other) {
        if (other instanceof ScoreMap) {
            ScoreMap that = (ScoreMap) other;
            return this.scores.equals(that.scores);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return getScores().hashCode();
    }
}
