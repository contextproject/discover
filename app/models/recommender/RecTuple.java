package models.recommender;

import models.record.Track2;

/**
 * Class used to give track a score implements Comparable Interface.
 */
public class RecTuple implements Comparable<RecTuple> {

    /**
     * The track object.
     */
    private Track2 track;

    /**
     * The score of the Tuple.
     */
    private double score;

    /**
     * Constructor of the class.
     *
     * @param track The Track object.
     * @param score The score of the tuple.
     */
    public RecTuple(final Track2 track, final Double score) {
        this.track = track;
        this.score = score;
    }

    /**
     * The compareTo method used with the Comparable Interface to be able to
     * sort a list containing objects of this class.
     */
    @Override
    public int compareTo(final RecTuple other) {
        return (int) Math.round(this.getScore() - other.getScore());
    }

    /**
     * Adds a score to the existing one.
     *
     * @param difference the amount that needs to be added.
     */
    public void addScore(final double difference) {
        score += difference;
    }

    /**
     * Creates a String representation of the object.
     *
     * @return String representation of the object
     */
    public String toString() {
        return "[ id: " + track.get("id") + " score: " + score + " ]";
    }

    /**
     * Getter for the Track object.
     *
     * @return The Track object
     */
    public Track2 getTrack() {
        return track;
    }

    /**
     * Setter for the Track object.
     *
     * @param track The new Track object.
     */
    public void setTrack(final Track2 track) {
        this.track = track;
    }

    /**
     * Getter for the score of the object.
     *
     * @return The score of the object.
     */
    public double getScore() {
        return score;
    }

    /**
     * Setter for the score of the Tuple.
     *
     * @param score The new score of the object.
     */
    public void setScore(final double score) {
        this.score = score;
    }
}
