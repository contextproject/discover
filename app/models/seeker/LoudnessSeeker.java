package models.seeker;

import models.score.ScoreStorage;
import models.snippet.TimedSnippet;

/**
 * This class selects the loudest part of the song to be awarded the most points
 * so so it is likely to be selected to be used by the snippets. It then can
 * selects the snippet with the highest score.
 * 
 * @since 15-06-2015
 * @version 15-06-2015
 * 
 * @see Seeker
 * @see ScoreStorage
 * 
 * @author stefan boodt
 *
 */
public class LoudnessSeeker implements Seeker {

    @Override
    public TimedSnippet seek() {
        return seek(TimedSnippet.getDefaultDuration());
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
