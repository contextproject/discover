package models.recommender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import models.database.retriever.GeneralTrackSelector;
import models.profile.Profile;
import models.record.Track;
import models.utility.TrackList;

/**
 * LikesRecommender is used to weigh a List of RecTuple object returned by the
 * recommender it holds. The class extends the RecommendDecorator abstract class
 * and follows the Decorator design pattern. LikesRecommender implements the
 * Recommender interface which makes sure it contains a recommend() method that
 * returns a List of RecTuple objects. s
 */
public class LikesRecommender extends RecommendDecorator implements Recommender {

    /** Map object used to create a scoreboard for liked genres. */
    private static HashMap<Object, Double> genreBoard;

    /** Map object used to create a scoreboard for liked artists. */
    // private static HashMap<Object, Double> artistBoard;

    private static double weight;

    /**
     * Constructor for the LikesRecommender class.
     * 
     * @param smallFish
     *            The recommender object that the class decorates.
     */
    public LikesRecommender(final Recommender smallFish) {
        super(smallFish);
        weight = 1.5;
        genreBoard = new HashMap<Object, Double>();
        // /artistBoard = new HashMap<Object, Double>();
    }
    
    /**
     * The recommend method either evaluates the decorated object or makes its own.
     */
    @Override
    public List<RecTuple> recommend() {
        this.generateBoards();
        return evaluate();
        // if (recommender != null) {
        // return evaluate();
        // } else {
        // return suggest();
        // }
    }

    /**
     * The suggest() method finds a possible track the user might like based on
     * his profile. The method creates a query based on the genres and artists
     * from the profiles likes list.
     * 
     * @return a TrackList containing tracks filtered on genre and artists from
     *         the profiles likes list.
     */
    public TrackList suggest() {
        this.generateBoards();
        String query = "SELECT * FROM `tracks` WHERE ";
        Iterator<Object> it = genreBoard.keySet().iterator();
        while (it.hasNext()) {
            System.out.println("ZUB");
            query += ("genre = '" + it.next() + "'");
            query += " OR ";
        }
        query = query.substring(0, query.length() - 3);
        query += " ORDER BY RAND() LIMIT 5";
        System.out.println("suggest: " + query);
        GeneralTrackSelector selector = new GeneralTrackSelector(query);
        TrackList list = selector.execute();
        // probleem ligt ergens hier. De selector geeft alleen maar 1 record
        // wanneer hij geëxecute wordt.
        System.out.println("list size: " + list.size());
        System.out.println(list.toString());
        return list;
    }

    /**
     * Evaluates the unweighted list of RecTuple object received from the
     * decorated recommender and adds additional score to the tracks using its
     * scoreboards.
     * 
     * @return A List of RecTuple object with added score.
     */
    private List<RecTuple> evaluate() {
        List<RecTuple> unweighted = recommender.recommend();
        System.out.println("Size unweighted: " + unweighted.size());
        for (RecTuple rt : unweighted) {
            Object key = rt.getTrack().getGenre();
            if (genreBoard.containsKey(key)) {
                rt.addScore(genreBoard.get(key));
            }
            // if(artistBoard.containsKey(key)) {
            // rt.addScore(artistBoard.get(key));
            // }
        }
        return unweighted;
    }

    /**
     * Generates the scoreboards of the object using the profile's list of likes
     * and dislikes.
     */
    private void generateBoards() {
        if (recommender != null) {
            Profile pro = this.getUserProfile();
            ArrayList<Track> likes = pro.getLikes();
            ArrayList<Track> dislikes = pro.getDislikes();
            for (Track track : likes) {
                updateBoardPositive(genreBoard, track);
                // updateBoardPositive(artistBoard, track);
            }
            for (Track track : dislikes) {
                updateBoardNegative(genreBoard, track);
                // updateBoardNegative(artistBoard, track);
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
    private static void updateBoardPositive(final HashMap<Object, Double> hm,
            final Track track) {
        Object key = track.getGenre();
        if (hm.containsKey(key)) {
            hm.put(key, hm.get(key) + weight);
        } else {
            hm.put(key, weight * 2);
        }
    }

    /**
     * Does the same thing as updateBoardPositive() but instead of adding it
     * subtracts score. Making the method it seemed logical that subtracting
     * should operate differently using its own method but is now amost the same
     * as the updateBoardPositive() method. If the idea behind subtracting
     * doesn't change this method will be removed and a more general one will be
     * created.
     * 
     * @param hm
     *            The HashMap object containing the keywords and their score.
     * @param track
     *            Track object that is being added.
     */
    
    private static void updateBoardNegative(final HashMap<Object, Double> hm,
            final Track track) {
        Object key = track.getGenre();
        if (hm.containsKey(key)) {
            hm.put(key, hm.get(key) - (weight * 2));
        } else {
            hm.put(key, -(weight * 2));
        }
    }

    @Override
    public Profile getUserProfile() {
        return super.getUserProfile();
    }

    /**
     * Getter for the weight of the object.
     * @return The weight of the object.
     */
    public static double getWeight() {
        return weight;
    }
    
    /**
     * Setter for the the weight of the object.
     * @param weight The new weight of the object.
     */
    public static void setWeight(final double weight) {
        LikesRecommender.weight = weight;
    }

}
