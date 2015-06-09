package controllers;

import models.record.Track;
import models.seeker.CommentIntensitySeeker;
import models.seeker.RandomSeeker;

/**
 * Selects which algorithm could be used best and also combines algorithms.
 */
public final class AlgorithmSelector {

    public enum Mode {
        AUTO, INTENSITY, CONTENT, RANDOM
    }

    private static Mode curMode = AlgorithmSelector.Mode.AUTO;

    /**
     * Determine the start of the snippet for the track.
     *
     * @param track
     *            The track
     * @return The start of the snippet
     */
    public static int determineStart(final Track track) {

        switch (curMode) {
        case INTENSITY:
            return commentIntensity(track);
        case RANDOM:
            return random(track);
        default:
            int start;
            start = commentIntensity(track);

            // no comments for the track
            if (start == 0) {
                start = random(track);
            }
            return start;
        }
    }

    /**
     * Determine the start of the snippet based on the intensity of the
     * comments.
     *
     * @param track
     *            The track
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
     * Determine a random start of the snippet.
     *
     * @param track
     *            The track
     * @return The start of the snippet
     */
    private static int random(final Track track) {
        return new RandomSeeker(track).seek().getStartTime();
    }

    public static void setMode(String mode) {
        if (mode.equalsIgnoreCase("intensity")) {
            curMode = Mode.INTENSITY;
        } else if (mode.equalsIgnoreCase("random")) {
            curMode = Mode.RANDOM;
        } else {
            curMode = Mode.AUTO;
        }
    }
}
