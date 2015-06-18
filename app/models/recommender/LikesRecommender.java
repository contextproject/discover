package models.recommender;

import models.profile.Profile;
import models.record.Track;
import models.utility.TrackList;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * LikesRecommender is used to weigh a List of Track objects returned by the
 * recommender it holds. The class extends the RecommendDecorator abstract class
 * and follows the Decorator design pattern. LikesRecommender implements the
 * Recommender interface which makes sure it contains a recommend() method that
 * returns a List of Track objects. s
 */
public class LikesRecommender extends RecommendDecorator implements Recommender {

    /**
     * Map object used to create a scoreboard for liked genres.
     */
    private HashMap<Object, Double> genreBoard;

    /**
     * Map object used to create a scoreboard for liked artists.
     */
    private HashMap<Object, Double> artistBoard;

    /**
     * The positive modifier used to give positive weight to the liked tracks.
     */
    private double positiveModifier;

    /**
     * The negative modifier used to give negative weight to the disliked
     * tracks.
     */
    private double negativeModifier;

    /**
     * The weight used to give weight to tracks.
     */
    private double weight;

    /**
     * Constructor for the LikesRecommender class.
     *
     * @param smallFish The recommender object that the class decorates.
     */
    public LikesRecommender(@Nonnull final Recommender smallFish) {
        super(smallFish);
        setPositiveModifier(1);
        setNegativeModifier(-1);
        weight = super.getWeight();
        genreBoard = new HashMap<>();
        artistBoard = new HashMap<>();
    }

    /**
     * The recommend method either evaluates the decorated object or makes its
     * own.
     *
     * @return The recommended tracks
     */
    @Override
    public TrackList recommend() {
        TrackList tracks = suggest();
        tracks.addAll(getRecommender().recommend());
        return evaluate(tracks);
    }

    /**
     * The suggest() method finds a possible track the user might like based on
     * his profile. The method creates a query based on the genres and artists
     * from the profiles likes list.
     *
     * @return a TrackList containing tracks filtered on genre and artists from
     * the profiles likes list.
     */
    @Override
    public TrackList suggest() {
        this.generateBoards();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT * FROM `tracks` ");
        if (!genreBoard.isEmpty() || !artistBoard.isEmpty()) {
            queryBuilder.append("WHERE ");
            Iterator<Object> it1 = genreBoard.keySet().iterator();
            Iterator<Object> it2 = artistBoard.keySet().iterator();
            while (it1.hasNext()) {
                Object i = it1.next();
                if (i != null) {
                    queryBuilder.append("genre = '").append(i).append("' OR ");
                }
            }
            while (it2.hasNext()) {
                Object j = it2.next();
                if (j != null) {
                    queryBuilder.append("user_id = '").append(j).append("' OR ");
                }
            }
            queryBuilder.delete(queryBuilder.length() - 3, queryBuilder.length());
        }
        queryBuilder.append("ORDER BY RAND() LIMIT ").append(getAmount());
        return TrackList.get(queryBuilder.toString());
    }

    /**
     * Evaluates the unweighed list of Track objects received from the decorated
     * recommender and adds additional score to the tracks using its
     * scoreboards.
     *
     * @param unweighed The TrackList form the decorated recommender
     * @return A List of RecTuple object with added score.
     */
    public TrackList evaluate(final TrackList unweighed) {
        for (Track track : unweighed) {
            double score = track.get(Track.SCORE);
            if (genreBoard.containsKey(Track.GENRE)) {
                score += genreBoard.get(Track.GENRE);
            }
            if (artistBoard.containsKey(Track.USER_ID)) {
                score += artistBoard.get(Track.USER_ID);
            }
            track.put(Track.SCORE, score);
        }
        return unweighed;
    }

    /**
     * Generates the scoreboards of the object using the profile's list of likes
     * and dislikes.
     */
    private void generateBoards() {
        Profile pro = this.getUserProfile();
        ArrayList<Track> likes = pro.getLikes();
        ArrayList<Track> dislikes = pro.getDislikes();
        for (Track track : likes) {
            updateBoard(genreBoard, track.get(Track.GENRE), positiveModifier);
            updateBoard(artistBoard, track.get(Track.USER_ID), positiveModifier);
        }
        for (Track track : dislikes) {
            updateBoard(genreBoard, track.get(Track.GENRE), negativeModifier);
            updateBoard(artistBoard, track.get(Track.USER_ID), negativeModifier);
        }
    }

    /**
     * Updates a scoreboard to using the profile. A score is added to the key if
     * it exists and additional score if this is the first time its found.
     *
     * @param hm       The HashMap object containing the keywords and their score.
     * @param key      Track object that is being added.
     * @param modifier The modifier for the score as a double.
     */
    private void updateBoard(final HashMap<Object, Double> hm, final Object key, final double modifier) {
        double value = key instanceof String ? 0.7 : 0.3;
        if (hm.containsKey(key)) {
            hm.put(key, hm.get(key) + value * weight * (modifier));
        } else {
            hm.put(key, value * weight * (modifier));
        }
    }

    /**
     * Getter for the positive modifier of the object.
     *
     * @return The modifier as a double.
     */
    public double getPositiveModifier() {
        return positiveModifier;
    }

    /**
     * Setter for the positive modifier of the object.
     *
     * @param positiveModifier The new modifier of the object.
     */
    public void setPositiveModifier(final double positiveModifier) {
        this.positiveModifier = positiveModifier;
    }

    /**
     * Getter for the negative modifier of the object.
     *
     * @return The modifier as a double.
     */
    public double getNegativeModifier() {
        return negativeModifier;
    }

    /**
     * Setter for the negative modifier of the object.
     *
     * @param negativeModifier The new modifier of the object.
     */
    public void setNegativeModifier(final double negativeModifier) {
        this.negativeModifier = negativeModifier;
    }
}
