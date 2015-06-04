package models.seeker;

import models.score.ScoreStorage;
import models.snippet.TimedSnippet;

/**
 * This class is a Seeker that uses feature essentia for the selecting of
 * the Snippet.
 * 
 * @see TimedSnippet
 * 
 * @since 29-05-2015
 * @version 29-05-2015
 *
 */
public class FeatureEssentiaSeeker implements Seeker {

    @Override
    public TimedSnippet seek() {
        return null;
    }

    @Override
    public TimedSnippet seek(final int duration) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ScoreStorage calculateScores(final int duration) {
        // TODO Auto-generated method stub
        return null;
    }
}
