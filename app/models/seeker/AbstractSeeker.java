package models.seeker;

import models.snippet.TimedSnippet;
import models.snippet.TimedSnippetFactory;

/**
 * This class provides some default implementation for the Seekers.
 * Extention of this class only provides an easy way to implement the
 * seek methods as these are usually the same across the different Seekers.
 * 
 * @since 15-06-2015
 * @version 15-06-2015
 * 
 * @see Seeker
 * @see TimedSnippetFactory
 * 
 * @author stefan boodt
 *
 */
public abstract class AbstractSeeker implements Seeker {

    @Override
    public TimedSnippet seek() {
        return seek(TimedSnippet.getDefaultDuration());
    }

    @Override
    public TimedSnippet seek(final int duration) {
        final int starttime = getStarttime(duration);
        return TimedSnippetFactory.createSnippet(starttime, duration);
    }

    /**
     * Returns the starttime of the snippet.
     * @param duration The duration of the snippet.
     * @return The starttime of the snippet.
     */
    protected int getStarttime(final int duration) {
        return calculateScores(duration).maxScoreStartTime();
    }

}
