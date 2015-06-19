package controllers;

import java.util.List;

import models.json.Json;
import models.record.Track;
import models.seeker.CommentIntensitySeeker;
import models.seeker.LoudnessSeeker;
import models.seeker.RandomSeeker;
import models.snippet.TimedSnippet;

/**
 * Selects which algorithm could be used best and also combines algorithms.
 */
public final class AlgorithmSelector {

    /**
     * The Mode of the AlgorithmSelector that dictates its choice.
     */
    public enum Mode {
        /**
         * Mode: AUTO. The selector will make the choice between every algorithm
         * himself.
         */
        AUTO,
        /**
         * Mode: INTENSITY. The selector will choose the intensity seeker to
         * calculate the preview.
         */
        INTENSITY,
        /**
         * Mode: CONTENT. The selector will choose the intensity seeker along
         * with the to calculate the preview.
         */
        CONTENT,
        /**
         * Mode: LOUDNESS. The selector will choose the loudest part of the song
         * as specified by the waveform.
         */
        LOUDNESS,
        /**
         * Mode: RANDOM. The selector will choose a random time to start based
         * on its duration.
         */
        RANDOM
    }

    /**
     *
     */
    private static Mode curMode = AlgorithmSelector.Mode.AUTO;

    /**
     * Class constructor.
     */
    private AlgorithmSelector() { }
    
    /**
     * Determine the start of the snippet for the track.
     *
     * @param track The track
     * @param waveform The waveform of the track.
     * @return The start of the snippet
     */
    public static TimedSnippet determineStart(final Track track, final List<Double> waveform) {
        int start;
        switch (curMode) {
            case CONTENT:
                start = commentContent(track);
                break;
            case RANDOM:
                start = random(track);
                break;
            case LOUDNESS:
                start = loudness(track, waveform);
                break;
            default:
                start = commentContent(track);
                if (start == 0) {
                    if (waveform.isEmpty()) {
                        start = random(track);
                    } else {
                        start = loudness(track, waveform);
                    }
                }
                break;
        }
        return new TimedSnippet(start);
    }

    /**
     * Determine the start of the snippet based on the content of the comments.
     *
     * @param track The track
     * @return The start of the snippet
     */
    private static int commentContent(final Track track) {
        return new CommentIntensitySeeker(track).seek().getStartTime();
    }

    /**
     * Determine a random start of the snippet.
     *
     * @param track The track
     * @return The start of the snippet
     */
    private static int random(final Track track) {
        return new RandomSeeker(track).seek().getStartTime();
    }
    
    /**
     * Returns the starttime based on the waveform.
     * @param track The track to seek.
     * @param waveform The waveform of the track.
     * @return The starttime of the loudness.
     */
    private static int loudness(final Track track, final List<Double> waveform) {
        return new LoudnessSeeker(track, waveform).seek().getStartTime();
    }

    /**
     * Sets the mode of the Algorithm Selector.
     *
     * @param mode String representation of the new Mode.
     */
    public static void setMode(final String mode) {
        if (mode.equalsIgnoreCase("content")) {
            curMode = Mode.CONTENT;
        } else if (mode.equalsIgnoreCase("random")) {
            curMode = Mode.RANDOM;
        } else if (mode.equalsIgnoreCase("loudness")) {
            curMode = Mode.LOUDNESS;
        } else {
            curMode = Mode.AUTO;
        }
    }
}
