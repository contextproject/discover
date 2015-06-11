package models.recommender;

import models.database.DatabaseConnector;
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
     * The constructor of the class.
     *
     * @param recommender The decorated recommender object.
     */
    public FeatureRecommender(final Recommender recommender) {
        super(recommender);
    }

    /**
     * Recommends a TrackList.
     *
     * @return The TrackList.
     */
    @Override
    public TrackList suggest() {
        if (mean() != 0) {
            String query = "SELECT tracks.track_id, tracks.duration, tracks.genre, "
                    + "tracks.title, tracks.user_id, features.danceability, "
                    + "abs(features.danceability - " + mean() + ") as distance "
                    + "FROM features "
                    + "INNER JOIN tracks "
                    + "ON tracks.track_id = features.track_id "
                    + "ORDER BY distance "
                    + "LIMIT 3";
            TrackList result = TrackList.get(query);
            for (Track2 track : result) {
                track.put("score", weight);
            }
            return result;
        }
        return new TrackList();
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
        updateTracks(unweighted);
        double mean = mean();
        double deviation = deviation();
        for (Track2 track : unweighted) {
            track.put("score", (Double) track.get("score") + score(track, mean, deviation));
        }
        return unweighted;
    }

    /**
     * Updates the Track with the danceability retrieved from the database.
     *
     * @param trackList The TrackList to update
     * @return The updated track
     */
    private TrackList updateTracks(final TrackList trackList) {
        for (Track2 track : trackList) {
            String query = "SELECT * FROM features WHERE features.track_id = " + track.get("id");
            String danceability = DatabaseConnector.getSingleString(query, "danceability");
            if (danceability != null) {
                track.put("danceability", Double.parseDouble(danceability));
            }
        }
        return trackList;
    }

    /**
     * Determines the mean of the danceability of the likes of the user.
     *
     * @return The mean of the danceability of the likes
     */
    private double mean() {
        TrackList likes = updateTracks(getUserProfile().getLikes());
        double mean = 0.0;
        double count = 0.0;
        if (likes.size() != 0) {
            for (Track2 track : likes) {
                if (track.containsKey("danceability")) {
                    mean += (Double) track.get("danceability");
                    count++;
                }
            }
            mean = mean / count;
        }
        return mean;
    }

    /**
     * Determines the standard deviation of the danceability of the likes of the user.
     *
     * @return The standard deviation of the danceability of the likes
     */
    private double deviation() {
        TrackList likes = getUserProfile().getLikes();
        double deviation = 0.0;
        double mean = mean();
        if (likes.size() != 0) {
            double variance = 0.0;
            for (Track2 track : likes) {
                if (track.containsKey("danceability")) {
                    variance += ((Double) track.get("danceability") - mean)
                            * ((Double) track.get("danceability") - mean);
                }
            }
            deviation = Math.sqrt(variance);
        }
        return deviation;
    }

    /**
     * Gives a score to the track based on the danceability.
     *
     * @param track     The track
     * @param mean      The mean of the likes of the user
     * @param deviation The standard deviation of the likes of the user
     * @return The score
     */
    private double score(final Track2 track, final double mean, final double deviation) {
        if (track.containsKey("danceability")) {
            double danceability = (Double) track.get("danceability");
            final double quarter = 0.25;
            final double half = 0.5;
            final double threequarter = 0.75;
            if (((mean - (quarter * deviation)) <= danceability)
                    && ((mean + (quarter * deviation)) >= danceability)) {
                return weight;
            } else if (mean - half * deviation <= danceability
                    && mean + half * deviation >= danceability) {
                return half * weight;
            } else if (mean - deviation <= danceability
                    && mean + deviation >= danceability) {
                return threequarter * weight;
            }
        }
        return 0.0;
    }
}
