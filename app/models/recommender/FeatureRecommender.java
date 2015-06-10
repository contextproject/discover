package models.recommender;

import models.record.Track2;
import models.utility.TrackList;

/**
 * FeatureRecommender is used to weigh a list of Track objects returned by the
 * recommender it holds. The class extends the RecommendDecorator abstract class
 * and follows the Decorator design pattern. FeatureRecommender implements the
 * Recommender interface which makes sure it contains a recommend() method that
 * returns a List of RecTuple objects.
 */
public class FeatureRecommender extends RecommendDecorator implements Recommender {

    /**
     * The mean danceability of the likes of the user.
     */
    private double mean = 1.2510925218177062;

    /**
     * The standard deviation of the danceability of the likes of the user.
     */
    private double deviation;

    /**
     * The constructor of the class.
     *
     * @param recommender The decorated recommender object.
     */
    public FeatureRecommender(final Recommender recommender) {
        super(recommender);
        mean();
        deviation();
    }

    /**
     * Recommends a TrackList.
     *
     * @return The TrackList.
     */
    @Override
    public TrackList suggest() {
        String query = "SELECT tracks.track_id, tracks.duration, tracks.genre, tracks.title "
                + "features.danceability, abs(features.danceability - " + mean + ") as distance "
                + "FROM features "
                + "INNER JOIN tracks "
                + "ON tracks.track_id = features.track_id "
                + "ORDER BY distance "
                + "LIMIT 10";
        TrackList result = TrackList.get(query);
        for (Track2 track : result) {
            track.put("score", weight);
        }
        return result;
    }

    @Override
    public TrackList recommend() {
        TrackList result = suggest();
        result.addAll(evaluate());
        return result;
    }

    /**
     * Evaluates the unweighted TrackList received from the decorated recommender
     * and adds additional score to the tracks.
     *
     * @return A TrackList with the updated score.
     */
    private TrackList evaluate() {
        TrackList unweighted = recommender.recommend();
        for (Track2 track : unweighted) {
            score(track);
        }
        return unweighted;
    }

    /**
     * Determines the mean of the danceability of the likes of the user.
     */
    private void mean() {
        TrackList likes = getUserProfile().getLikes();
        if (likes.size() != 0) {
            for (Track2 track : likes) {
                mean += (Double) track.get("score");
            }
            mean = mean / (double) likes.size();
        }
    }

    /**
     * Determines the standard deviation of the danceability of the likes of the user.
     */
    private void deviation() {
        TrackList likes = getUserProfile().getLikes();
        if (likes.size() != 0) {
            double variance = 0.0;
            for (Track2 track : likes) {
                variance += ((Double) track.get("score") - mean)
                        * ((Double) track.get("score") - mean);
            }
            deviation = Math.sqrt(variance);
        }
    }

    /**
     * Gets the required information and passes the updated track to the score(double) method.
     *
     * @param track The Track object
     * @return The score received from the score(double) method
     */
    private double score(final Track2 track) {
        String query = "SELECT * FROM features INNER JOIN tracks "
                + "ON tracks.track_id = features.track_id "
                + "WHERE features.track_id = " + track.get("id");
        if (TrackList.get(query).size() != 0) {
            return score((Double) TrackList.get(query).get(0).get("danceability"));
        }
        return 0.0;
    }

    /**
     * Gives a score to the track based on the danceability.
     *
     * @param danceability The danceability of the track
     * @return The score
     */
    private double score(final double danceability) {
        final double quarter = 0.25;
        final double half = 0.5;
        final double threequarters = 0.75;
        if (((mean - (quarter * deviation)) <= danceability)
                && ((mean + (quarter * deviation)) >= danceability)) {
            return weight;
        } else if (mean - threequarters * deviation <= danceability
                && mean + threequarters * deviation >= danceability) {
            return half * weight;
        } else if (mean - deviation <= danceability
                && mean + deviation >= danceability) {
            return quarter * weight;
        }
        return 0.0;
    }

    /**
     * Returns the amount of recommendations to make.
     *
     * @return The amount of recommendations
     */
    public int getAmount() {
        return super.getAmount();
    }
}
