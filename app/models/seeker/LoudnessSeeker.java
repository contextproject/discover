package models.seeker;

import java.util.List;

import models.mix.MixSplitter;
import models.record.Track;
import models.score.ScoreStorage;

/**
 * This class selects the loudest part of the song to be awarded the most points
 * so so it is likely to be selected to be used by the snippets. It then can
 * selects the snippet with the highest score.
 * 
 * @since 15-06-2015
 * @version 15-06-2015
 * 
 * @see AbstractSeeker
 * @see Seeker
 * @see ScoreStorage
 * 
 * @author stefan boodt
 *
 */
public class LoudnessSeeker extends AbstractSeeker {
    
    /**
     * The waveform to operate on.
     */
    private List<Double> waveform;
    
    /**
     * The default number of points awarded for the
     * loudest part of the music.
     */
    protected static final int DEFAULT_POINTS = 1000;
    
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
        super(track, decorate);
        setWaveform(waveform);
    }

    @Override
    public ScoreStorage calculateScores(final int duration) {
        final ScoreStorage storage = getDecorate().calculateScores(duration);
        final int amountOfBars = waveform.size();
        for (int i = 0; i < amountOfBars; i++) {
            final int time = getWaveformPart(amountOfBars, i);
            final int points = (int) Math.round(DEFAULT_POINTS * waveform.get(i));
            storage.add(time, points);
        }
        return storage;
    }
    
    /**
     * Returns the starttime of bar barIndex.
     * @param amountOfBars The number of bars in the waveform of the song.
     * @param barIndex The index of the bar.
     * @return The starttime of the bar.
     */
    public int getWaveformPart(final int amountOfBars,
            final int barIndex) {
        return MixSplitter.getWaveformPart(getTrack().get(Track.duration), amountOfBars, barIndex);
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
