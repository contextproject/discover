package models.seeker;

import models.record.Track;

import models.score.ScoreStorage;

/**
 * Generates a random start time for the track.
 */
public class RandomSeeker extends AbstractSeeker {

    /**
     * The track.
     */
    private Track track;
    
    /**
     * The Seeker to decorate.
     */
    private Seeker decorate;
    
    /**
     * The number of points to award.
     */
    private int points;
    
    /**
     * The default points to be awarded by RandomSeeker.
     */
    private static final int DEFAULT_POINTS = 100;

    /**
     * Creates a new random seeker with the default underlying seeker and the
     * default points.
     *
     * @param track The track
     */
    public RandomSeeker(final Track track) {
        this(track, new NullSeeker());
    }

    /**
     * Constructor.
     *
     * @param track The track
     * @param decorate The Seeker to use under this one as this one decorates
     * the seeker by the name of decorate.
     */
    public RandomSeeker(final Track track, final Seeker decorate) {
        this(track, decorate, getDefaultPoints());
    }

    /**
     * Constructor.
     *
     * @param track The track
     * @param decorate The Seeker to use under this one as this one decorates
     * the seeker by the name of decorate.
     * @param points The number of points to award for being selected by random seeker.
     */
    public RandomSeeker(final Track track, final Seeker decorate, final int points) {
        this.track = track;
        this.decorate = decorate;
        this.points = points;
    }
    
    /**
     * Returns the default points to be awarded by the random seeker.
     * @return The default points.
     */
    public static int getDefaultPoints() {
        return DEFAULT_POINTS;
    }

    /**
     * Generates a random start time for a snippet.
     *
     * @return The start time
     */
    private int getStartTime() {
        return (int) (Math.random() * track.getDuration());
    }

    @Override
    public ScoreStorage calculateScores(final int duration) {
        ScoreStorage storage = decorate.calculateScores(duration);
        storage.add(getStartTime(), points);
        return storage;
    }
}
