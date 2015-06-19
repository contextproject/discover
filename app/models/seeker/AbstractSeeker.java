package models.seeker;

import models.record.Track;
import models.snippet.TimedSnippet;
import models.snippet.TimedSnippetFactory;

/**
 * This class provides some default implementation for the Seekers.
 * Extention of this class only provides an easy way to implement the
 * seek methods as these are usually the same across the different Seekers.
 * 
 * @since 15-06-2015
 * @version 16-06-2015
 * 
 * @see Seeker
 * @see TimedSnippetFactory
 * 
 * @author stefan boodt
 *
 */
public abstract class AbstractSeeker implements Seeker {

    /**
     * The track to seek the snippet for.
     */
    private Track track;
    
    /**
     * The seeker that is being decorated.
     */
    private Seeker decorate;
    
    /**
     * Creates an Abstract Seeker with the given track and decorated seeker.
     * @param track The track to search a snippet for.
     * @param decorate The seeker that is decorated by this one.
     */
    public AbstractSeeker(final Track track, final Seeker decorate) {
        setDecorate(decorate);
        setTrack(track);
    }

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

    /**
     * Sets the track that is being searched.
     * @param track The track to search.
     */
    public void setTrack(final Track track) {
        this.track = track;
    }
    
    /**
     * Retrieves the track that is searched for.
     * @return The track that is currently searched for a snippet.
     */
    public Track getTrack() {
        return track;
    }
    
    /**
     * Sets the seeker that is being decorated by this one. It changes
     * the decorated seeker to the given value and hence can alter the
     * outcome of the {@link #seek()} and {@link #seek(int)} methods
     * before and after the setting. 
     * @param newSeeker The new seeker to be used as a basis.
     */
    public void setDecorate(final Seeker newSeeker) {
        decorate = newSeeker;
    }
    
    /**
     * Retrieves the seeker that is being decorated.
     * @return The seeker that is decorated.
     */
    protected Seeker getDecorate() {
        return decorate;
    }

}
