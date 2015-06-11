package models.recommender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import models.database.retriever.GeneralTrackSelector;
import models.profile.Profile;
import models.record.Track2;
import models.utility.TrackList;

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
    private static HashMap<Object, Double> genreBoard;

    /**
     * Map object used to create a scoreboard for liked artists.
     */
    private static HashMap<Object, Double> artistBoard;

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
    private static double weight;

    /**
     * The selector object used to get different results from the database.
     */
    private GeneralTrackSelector selector;

    /**
     * Constructor for the LikesRecommender class.
     * 
     * @param smallFish
     *            The recommender object that the class decorates.
     */
    public LikesRecommender(final Recommender smallFish) {
        super(smallFish);
        positiveModifier = 1;
        negativeModifier = -1;
        weight = super.getWeight();
        genreBoard = new HashMap<Object, Double>();
        artistBoard = new HashMap<Object, Double>();
        selector = GeneralTrackSelector.getInstance();
    }

    /**
     * The recommend method either evaluates the decorated object or makes its
     * own.
     */
    @Override
    public TrackList recommend() {
        TrackList tracks = suggest();
        tracks.addAll(recommender.recommend());
        Collections.sort(evaluate(tracks));
        return tracks;
    }

    /**
     * The suggest() method finds a possible track the user might like based on
     * his profile. The method creates a query based on the genres and artists
     * from the profiles likes list.
     * 
     * @return a TrackList containing tracks filtered on genre and artists from
     *         the profiles likes list.
     */
    @Override
    public TrackList suggest() {
        this.generateBoards();
        String query = "SELECT * FROM `tracks` WHERE ";
        Iterator<Object> it1 = genreBoard.keySet().iterator();
        Iterator<Object> it2 = artistBoard.keySet().iterator();
        while (it1.hasNext()) {
            Object i = it1.next();
            if(i != null) {
                query += ("genre = '" + i + "'");
                query += " OR ";
            }
        }
        while (it2.hasNext()) {
            Object j = it2.next();
            if(j != null) {
                query += ("user_id = '" + j + "'");
                query += " OR ";
            }
        }
        query = query.substring(0, query.length() - 3);
        query += " ORDER BY RAND() LIMIT " + amount;
        TrackList list = selector.execute(query);
        return list;
    }

    /**
     * Evaluates the unweighed list of RecTuple object received from the
     * decorated recommender and adds additional score to the tracks using its
     * scoreboards.
     * 
     * @return A List of RecTuple object with added score.
     */
    public TrackList evaluate(TrackList unweighed) {
        for (Track2 tup : unweighed) {
            Object genre = tup.get("genre");
            Object artist = tup.get("user_id");
            double score = (Double) tup.get("score");
            if (genreBoard.containsKey(genre)) {
                score += genreBoard.get(genre);
            }
            if (artistBoard.containsKey(artist)) {
                score += artistBoard.get(artist);
            }
            tup.put("score", score);
        }
        return unweighed;
    }

    /**
     * Generates the scoreboards of the object using the profile's list of likes
     * and dislikes.
     */
    private void generateBoards() {
        if (recommender != null) {
            Profile pro = this.getUserProfile();
            ArrayList<Track2> likes = pro.getLikes();
            ArrayList<Track2> dislikes = pro.getDislikes();
            for (Track2 track : likes) {
                updateBoard(genreBoard, track.get("genre"), positiveModifier, likes.size());
                updateBoard(artistBoard, track.get("user_id"), positiveModifier, likes.size());
            }
            for (Track2 track : dislikes) {
                updateBoard(genreBoard, track.get("genre"), negativeModifier, dislikes.size());
                updateBoard(artistBoard, track.get("user_id"), negativeModifier, dislikes.size());
            }
        }
    }

    /**
     * Updates a scoreboard to using the profile. A score is added to the key if
     * it exists and additional score if this is the first time its found.
     * 
     * @param hm
     *            The HashMap object containing the keywords and their score.
     * @param track
     *            Track object that is being added.
     */
    private static void updateBoard(HashMap<Object, Double> hm, Object key, double modifier, int sourceSize) {
        double value;
        if(key instanceof String) {
            value = 0.7;
        } else {
            value = 0.3;
        }
        if (hm.containsKey(key)) {
            hm.put(key, hm.get(key) + value * weight * (modifier/sourceSize));
        } else {
            hm.put(key, value * weight * (modifier/sourceSize));
        }
    }

    
    
    public HashMap<Object, Double> getGenreBoard() {
        return genreBoard;
    }

    public HashMap<Object, Double> getArtistBoard() {
        return artistBoard;
    }

    @Override
    public Profile getUserProfile() {
        return super.getUserProfile();
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
     * @param weight
     *            The new weight of the object.
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
     * @param positiveModifier
     *            The new modifier of the object.
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
     * @param negativeModifier
     *            The new modifier of the object.
     */
    public void setNegativeModifier(final double negativeModifier) {
        this.negativeModifier = negativeModifier;
    }

    /**
     * Setter for the database selector of the object.
     * 
     * @param selector
     *            The GeneralTrackSelector of the object.
     */
    public void setSelector(GeneralTrackSelector selector) {
        this.selector = selector;
    }

}
