package models.recommender;

import models.database.DatabaseConnector;
import models.record.Key;
import models.record.Track;
import models.utility.TrackList;

import javax.annotation.Nonnull;

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
    public FeatureRecommender(@Nonnull final Recommender recommender) {
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
            for (Track track : result) {
                track.put(new Key<>("score", Double.class), getWeight());
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
        TrackList unweighted = getRecommender().recommend();
        updateTracks(unweighted);
        double mean = mean();
        double deviation = deviation();
        for (Track track : unweighted) {
            Key key = new Key<>("score", Double.class);
            track.put(key, (double) track.get(key) + score(track, mean, deviation));
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
        for (Track track : trackList) {
            String query = "SELECT * FROM features WHERE features.track_id = "
                    + track.get(new Key<>("id", Integer.class));
            double danceability = DatabaseConnector.getSingleDouble(query, "danceability");
            if (danceability != 0.0) {
                track.put(new Key<>("danceability", Double.class), danceability);
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
        boolean calculate = false;
        if (likes.size() != 0) {
            Key<Double> key = new Key<>("danceability", Double.class);
            for (Track track : likes) {
                if (track.containsKey(key)) {
                    mean += track.get(key);
                    count++;
                    calculate = true;
                }
            }
        }
        if (calculate) {
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
            Key<Double> key = new Key<>("danceability", Double.class);
            for (Track track : likes) {
                if (track.containsKey(key)) {
                    variance += (track.get(key) - mean)
                            * (track.get(key) - mean);
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
    private double score(final Track track, final double mean, final double deviation) {
        Key<Double> key = new Key<>("danceability", Double.class);
        if (track.containsKey(key)) {
            double danceability = track.get(key);
            final double quarter = 0.25;
            final double half = 0.5;
            final double threequarter = 0.75;
            if (((mean - (quarter * deviation)) <= danceability)
                    && ((mean + (quarter * deviation)) >= danceability)) {
                return getWeight();
            } else if (mean - half * deviation <= danceability
                    && mean + half * deviation >= danceability) {
                return half * getWeight();
            } else if (mean - deviation <= danceability
                    && mean + deviation >= danceability) {
                return threequarter * getWeight();
            }
        }
        return 0.0;
    }
}
