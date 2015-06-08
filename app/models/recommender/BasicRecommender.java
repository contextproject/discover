package models.recommender;

import java.util.List;

import models.database.retriever.GeneralTrackSelector;
import models.profile.Profile;
import models.record.Track2;

/**
 * The BasicRecommender class is the most basic Recommender. It returns a list
 * of possible tracks with out any bias. BasicRecommender can be used by
 * RecommenderDecorator classes to add weight to the tracks returned by this
 * class.
 */
public class BasicRecommender implements Recommender {

    /** The Profile object used for extracting information about the user. */
    private Profile userProfile;

    /** The query used for selecting tracks from the database. */
    private String query;

    private int amount;

    /**
     * Constructor for the BasicRecommender class.
     * 
     * @param profile
     *            The profile object containing several data about the user.
     * @param amount
     *            The size of the list the recommender will return.
     */
    public BasicRecommender(final Profile profile, final int amount) {
        this.userProfile = profile;
        this.query = "SELECT * FROM tracks ORDER BY RAND()";
        if (amount != -1) {
            query += (" LIMIT " + amount);
        }
        System.out.println("query: " + query);
    }

    public BasicRecommender(final Track2 track, final int amount) {
        this(new Profile(), amount);
        Profile profile = new Profile();
        profile.addLike(track);
        setUserProfile(profile);
    }

    /**
     * The recommend method is used to return a list of weighted Object
     * containing a Track object and its score. The method in this class returns
     * a list of Tracks where every track in the list has the same score.
     * 
     * @return List of RecTuple objects.
     */
    @Override
    public List<RecTuple> recommend() {
        GeneralTrackSelector seeker = new GeneralTrackSelector(query);
        List<RecTuple> res = seeker.asWeightedList(0.0);
        System.out.println("basic size: " + res.size());
        return res;
    }

    /**
     * Getter for the query of the object.
     * 
     * @return query The query as a String.
     */
    public String getQuery() {
        return query;
    }

    /**
     * Setter for the query of the object.
     * 
     * @param query
     *            The query as a String.
     */
    public void setQuery(final String query) {
        this.query = query;
    }

    /**
     * Getter for the profile of the object.
     * 
     * @return The object's profile.
     */
    public Profile getUserProfile() {
        return userProfile;
    }

    /**
     * Getter for the profile of the object.
     * 
     * @param userProfile
     *            The object's new profile.
     */
    public void setUserProfile(final Profile userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
