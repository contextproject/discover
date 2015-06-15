package models.seeker;

import models.score.ScoreMap;
import models.score.ScoreStorage;
import models.snippet.TimedSnippet;

/**
 * This class is the default for all seekers to reference. It provides a
 * way to easily give 0 as score to all the times.
 * 
 * @since 03-06-2015
 * @version 15-06-2015
 * 
 * @see AbstractSeeker
 * @see ScoreStorage
 * 
 * @author stefanboodt
 *
 */
public class NullSeeker extends AbstractSeeker {

    /**
     * The storage that stores all the scores.
     */
    private ScoreStorage storage;
    
    /**
     * Creates a new NullSeeker with the default storage.
     */
    protected NullSeeker() {
        this(new ScoreMap());
    }
    
    /**
     * Creates a new NullSeeker with the default storage.
     * @param storage The storage to use. It will clear upon setting.
     */
    protected NullSeeker(final ScoreStorage storage) {
        this.storage = storage;
        storage.clear();
    }
    
    /**
     * {@inheritDoc}
     * 
     * <p>
     * The seek for this class returns the {@link ScoreStorage#maxScoreStartTime()}
     * on an empty storage. This should be 0.
     * </p>
     */
    @Override
    public TimedSnippet seek() {
        return seek(TimedSnippet.getDefaultDuration());
    }

    @Override
    public ScoreStorage calculateScores(final int duration) {
        return storage;
    }

}
