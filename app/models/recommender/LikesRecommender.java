package models.recommender;

import models.profile.Profile;
import models.record.Key;
import models.record.Track;
import models.utility.TrackList;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * LikesRecommender is used to weigh a List of RecTuple object returned by the
 * recommender it holds. The class extends the RecommendDecorator abstract class
 * and follows the Decorator design pattern. LikesRecommender implements the
 * Recommender interface which makes sure it contains a recommend() method that
 * returns a List of RecTuple objects. s
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
        positiveModifier = 1;
        negativeModifier = -1;
        weight = super.getWeight();
        genreBoard = new HashMap<>();
        artistBoard = new HashMap<>();
    }

    /**
     * The recommend method either evaluates the decorated object or makes its
     * own.
     */
    @Override
    public TrackList recommend() {
        TrackList tracks = suggest();
        tracks.addAll(getRecommender().recommend());
        return tracks;
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
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM `tracks` WHERE ");
        Iterator<Object> it1 = genreBoard.keySet().iterator();
        Iterator<Object> it2 = artistBoard.keySet().iterator();
        while (it1.hasNext()) {
            Object i = it1.next();
            if (i != null) {
                query.append("genre = '").append(i).append("' OR ");
            }
        }
        while (it2.hasNext()) {
            Object j = it2.next();
            if (j != null) {
                query.append("user_id = '").append(j).append("' OR ");
            }
        }
        query.delete(query.length() - 3, query.length());
        query.append("ORDER BY RAND() LIMIT ").append(getAmount());
        return TrackList.get(query.toString());
    }

    /**
     * Evaluates the unweighed list of RecTuple object received from the
     * decorated recommender and adds additional score to the tracks using its
     * scoreboards.
     *
     * @param unweighed The TrackList form the decorated recommender
     * @return A List of RecTuple object with added score.
     */
    public TrackList evaluate(final TrackList unweighed) {
        for (Track track : unweighed) {
            String genre = track.get(new Key<>("genre", String.class));
            String artist = track.get(new Key<>("artist", String.class));
            double score = track.get(new Key<>("score", Double.class));
            if (genreBoard.containsKey(genre)) {
                score += genreBoard.get(genre);
            }
            if (artistBoard.containsKey(artist)) {
                score += artistBoard.get(artist);
            }
            track.put(new Key<>("score", Double.class), score);
        }
        return unweighed;
    }

    /**
     * Generates the scoreboards of the object using the profile's list of likes
     * and dislikes.
     */
    private void generateBoards() {
        if (getRecommender() != null) {
            Profile pro = this.getUserProfile();
            ArrayList<Track> likes = pro.getLikes();
            ArrayList<Track> dislikes = pro.getDislikes();
            for (Track track : likes) {
                updateBoard(genreBoard, track.get(new Key<>("genre", String.class)),
                        positiveModifier, likes.size());
                updateBoard(artistBoard, track.get(new Key<>("user_id", String.class)),
                        positiveModifier, likes.size());
            }
            for (Track track : dislikes) {
                updateBoard(genreBoard, track.get(new Key<>("genre", String.class)),
                        negativeModifier, dislikes.size());
                updateBoard(artistBoard, track.get(new Key<>("user_id", String.class)),
                        negativeModifier, dislikes.size());
            }
        }
    }

    /**
     * Updates a scoreboard to using the profile. A score is added to the key if
     * it exists and additional score if this is the first time its found.
     *
     * @param hm         The HashMap object containing the keywords and their score.
     * @param key        The key of the Track attribute
     * @param modifier   The modifier
     * @param sourceSize The size
     */
    private void updateBoard(final HashMap<Object, Double> hm,
                                    final Object key, final double modifier, final int sourceSize) {
        double value;
        if (key instanceof String) {
            value = 0.7;
        } else {
            value = 0.3;
        }
        if (hm.containsKey(key)) {
            hm.put(key, hm.get(key) + value * weight * (modifier / sourceSize));
        } else {
            hm.put(key, value * weight * (modifier / sourceSize));
        }
    }


    /**
     * Getter of the genre board.
     *
     * @return The genre board
     */
    public HashMap<Object, Double> getGenreBoard() {
        return genreBoard;
    }

    /**
     * Getter of the artist board.
     *
     * @return The artist board
     */
    public HashMap<Object, Double> getArtistBoard() {
        return artistBoard;
    }

    /**
     * Getter for the weight of the object.
     *
     * @return The weight of the object.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Setter for the the weight of the object.
     *
     * @param newWeight The new weight of the object.
     */
    public void setWeight(final double newWeight) {
        weight = newWeight;
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
