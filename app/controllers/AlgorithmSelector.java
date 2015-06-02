package controllers;

import models.record.Track;
import models.seeker.CommentIntensitySeeker;
import models.seeker.RandomSeeker;

/**
 * Selects which algorithm could be used best and also combines algorithms.
 */
public final class AlgorithmSelector {

    /**
     * Determine the start of the snippet for the track.
     *
     * @param track The track
     * @return The start of the snippet
     */
    public static int determineStart(final Track track) {
        int start;
        start = commentIntensity(track);

        // no comments for the track
        if (start == 0) {
            start = random(track);
        }
        return start;
    }

    /**
     * Determine the start of the snippet based on the intensity of the comments.
     *
     * @param track The track
     * @return The start of the snippet
     */
    private static int commentIntensity(final Track track) {
        return new CommentIntensitySeeker(track).seek().getStartTime();
    }

    /**
     * Determine the start of the snippet based on the content of the comments.
     *
     * @return The start of the snippet
     */
    private static int commentContent() {
        return 0;
    }

    /**
     * Determine the start of the snippet based on the feature essentia.
     *
     * @return The start of the snippet
     */
    private static int featureEssentia() {
        return 0;
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
}
