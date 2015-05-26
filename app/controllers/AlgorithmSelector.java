package controllers;

/**
 * Selects which algorithm could be used best and also combines algorithms.
 */
public class AlgorithmSelector {

    /**
     * The track id of the current song.
     */
    private int trackid;

    /**
     * Constructor for making a AlgorithmSelector object.
     *
     * @param trackid the id of a song.
     */
    public AlgorithmSelector(final int trackid) {
        this.trackid = trackid;
    }

    /**
     * Determine the start of the snippet for the track.
     *
     * @return The start of the snippet
     */
    public int determineStart() {
        return 0;
    }

    /**
     * Determine the start of the snippet based on the content of the comments.
     *
     * @return The start of the snippet
     */
    private int commentContent() {
        return 0;
    }

    /**
     * Determine the start of the snippet based on the intensity of the comments.
     *
     * @return The start of the snippet
     */
    private int commentIntensity() {
        return 0;
    }

    /**
     * Determine the start of the snippet based on the feature essentia.
     *
     * @return The start of the snippet
     */
    private int featureEssentia() {
        return 0;
    }
}
