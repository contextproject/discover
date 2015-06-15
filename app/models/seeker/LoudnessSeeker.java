package models.seeker;

import java.util.List;

import models.record.Track;
import models.score.ScoreStorage;
import models.snippet.TimedSnippet;
import models.snippet.TimedSnippetFactory;

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

    /**
     * The track to seek the snippet for.
     */
    private Track track;
    
    /**
     * The seeker that is being decorated.
     */
    private Seeker decorate;
    
    /**
     * The waveform to operate on.
     */
    private List<Double> waveform;
    
    /**
     * Creates a LoudnessSeeker that seeks an optimal snippet for the
     * given track. It uses no Seeker that is going to be decorated and gives
     * a fresh start because the underlying decorated seeker results without
     * any scores, and so all scores are based on the loudness. 
     * @param track The track to provide a snippet for.
     * @param waveform The data in the waveform.
     */
    public LoudnessSeeker(final Track track, final List<Double> waveform) {
        this(track, waveform, new NullSeeker());
    }
    
    /**
     * Creates a LoudnessSeeker that seeks an optimal snippet for the
     * given track based on loudness and any underlying decorated seeker
     * that is provided by the decorate variable.
     * @param track The track to search a snippet for.
     * @param waveform The data in the waveform.
     * @param decorate The seeker that is being decorated.
     */
    public LoudnessSeeker(final Track track, final List<Double> waveform,
            final Seeker decorate) {
        setTrack(track);
        setWaveform(waveform);
        setDecorate(decorate);
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

    @Override
    public ScoreStorage calculateScores(final int duration) {
        // TODO Auto-generated method stub
        return null;
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
     * Sets the waveform that is being used by this seeker.
     * @param waveform The waveform that contains the loudness of
     * every part of the song.
     */
    public void setWaveform(final List<Double> waveform) {
        this.waveform = waveform;
    }
    
    /**
     * Retrieves the waveform that is used for searching.
     * @return The waveform of the current track.
     */
    public List<Double> getWaveform() {
        return waveform;
    }
}
