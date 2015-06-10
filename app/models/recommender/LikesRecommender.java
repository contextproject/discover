package models.recommender;

import java.util.ArrayList;
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
    private static double positiveModifier;

    /**
     * The negative modifier used to give negative weight to the disliked
     * tracks.
     */
    private static double negativeModifier;

    /**
     * The weight used to give weight to tracks.
     */
    private static double weight;

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
    }

    /**
     * The recommend method either evaluates the decorated object or makes its
     * own.
     */
    @Override
    public TrackList recommend() {
        this.generateBoards();
        TrackList tracks = evaluate(recommender.recommend());
        tracks.addAll(suggest());
        // TO-DO: SORT tracks
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
            query += ("genre = '" + it1.next() + "'");
            query += " OR ";
        }
        while (it2.hasNext()) {
            query += ("user_id = '" + it2.next() + "'");
            query += " OR ";
        }
        query = query.substring(0, query.length() - 3);
        query += " ORDER BY RAND() LIMIT 3";
        TrackList list = GeneralTrackSelector.getInstance().execute(query);
        return evaluate(list);
    }

    /**
     * Evaluates the unweighed list of RecTuple object received from the
     * decorated recommender and adds additional score to the tracks using its
     * scoreboards.
     * 
     * @return A List of RecTuple object with added score.
     */
    private TrackList evaluate(TrackList unweighed) {
        for (Track2 tup : unweighed) {
            Object genre = tup.get("genre");
            Object artist = tup.get("user_id");
            if (genreBoard.containsKey(genre)) {
                tup.addScoreToTrack(genreBoard.get(genre));
            }
            if (artistBoard.containsKey(artist)) {
                tup.addScoreToTrack(artistBoard.get(artist));
            }
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
                updateBoard(genreBoard, track, positiveModifier);
                updateBoard(artistBoard, track, positiveModifier);
            }
            for (Track2 track : dislikes) {
                updateBoard(genreBoard, track, negativeModifier);
                updateBoard(artistBoard, track, negativeModifier);
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
    private static void updateBoard(final HashMap<Object, Double> hm,
            final Track2 track, final double modifier) {
        Object key = track.get("genre");
        if (hm.containsKey(key)) {
            hm.put(key, hm.get(key) + weight * modifier);
        } else {
            hm.put(key, weight * 2 * modifier);
        }
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
    public static double getPositiveModifier() {
        return positiveModifier;
    }

    /**
     * Setter for the positive modifier of the object.
     * 
     * @param positiveModifier
     *            The new modifier of the object.
     */
    public static void setPositiveModifier(final double positiveModifier) {
        LikesRecommender.positiveModifier = positiveModifier;
    }

    /**
     * Getter for the negative modifier of the object.
     * 
     * @return The modifier as a double.
     */
    public static double getNegativeModifier() {
        return negativeModifier;
    }

    /**
     * Setter for the negative modifier of the object.
     * 
     * @param negativeModifier
     *            The new modifier of the object.
     */
    public static void setNegativeModifier(final double negativeModifier) {
        LikesRecommender.negativeModifier = negativeModifier;
    }

}
