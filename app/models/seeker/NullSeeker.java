package models.seeker;

import models.score.ScoreMap;
import models.score.ScoreStorage;
import models.snippet.TimedSnippet;
import models.snippet.TimedSnippetFactory;

/**
 * This class is the default for all seekers to reference. It provides a
 * way to easily give 0 as score to all the times.
 * 
 * <p>
 * This class does not extend AbstractSeeker as it is the bottom of the
 * decorate pit and as such does not have a Seeker that it decorates.
 * Instead this class is the default class to reference when no other has
 * been inserted. Causing it to give no influenced values. Please be careful
 * when extending this class that the property of this class not decorating
 * any other Seeker is maintained.
 * </p>
 * 
 * @since 03-06-2015
 * @version 16-06-2015
 * 
 * @see AbstractSeeker
 * @see Seeker
 * @see ScoreStorage
 * 
 * @author stefan boodt
 *
 */
public class NullSeeker implements Seeker {

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

    @Override
    public TimedSnippet seek(final int duration) {
        final int starttime = calculateScores(duration).maxScoreStartTime();
        return TimedSnippetFactory.createSnippet(starttime, duration);
    }

}
