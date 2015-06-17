package models.recommender;

import models.database.DatabaseConnector;
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

    @Override
    public TrackList recommend() {
        TrackList result = suggest();
        result.addDistinctAll(evaluate());
        return result;
    }

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
                track.put(Track.SCORE, getWeight());
            }
            return result;
        }
        return new TrackList();
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
            track.put(Track.SCORE, track.get(Track.SCORE) + score(track, mean, deviation));
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
                    + track.get(Track.ID);
            double danceability = DatabaseConnector.getConnector().getSingleDouble(query, "danceability");
            if (danceability != 0.0) {
                track.put(Track.DANCEABILITY, danceability);
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
            for (Track track : likes) {
                if (track.containsKey(Track.DANCEABILITY)) {
                    mean += track.get(Track.DANCEABILITY);
                    count++;
                }
            }
        }
        if (count != 0) {
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
            for (Track track : likes) {
                if (track.containsKey(Track.DANCEABILITY)) {
                    variance += (track.get(Track.DANCEABILITY) - mean)
                            * (track.get(Track.DANCEABILITY) - mean);
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
        if (track.containsKey(Track.DANCEABILITY)) {
            double danceability = track.get(Track.DANCEABILITY);
            if (mean - 0.25 * deviation <= danceability
                    && mean + 0.25 * deviation >= danceability) {
                return getWeight();
            } else if (mean - 0.5 * deviation <= danceability
                    && mean + 0.5 * deviation >= danceability) {
                return 0.5 * getWeight();
            } else if (mean - deviation <= danceability
                    && mean + deviation >= danceability) {
                return 0.75 * getWeight();
            }
        }
        return 0.0;
    }
}
