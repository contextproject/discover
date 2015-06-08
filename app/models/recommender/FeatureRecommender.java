package models.recommender;

import models.profile.Profile;
import models.utility.TrackList;

import java.util.List;

public class FeatureRecommender extends RecommendDecorator implements Recommender {

    /**
     * The constructor of the class.
     *
     * @param recommender The decorated recommender object.
     */
    public FeatureRecommender(Recommender recommender) {
        super(recommender);
    }

    public void method() {
        Profile profile = getUserProfile();
    }


    public TrackList suggest() {
        int danceability = 2;
        String query = "SELECT track_id, danceability, "
                + "abs(danceability - " + danceability + ") as distance"
                + "FROM     `features`"
                + "ORDER BY distance"
                + "LIMIT " + getAmount();
        return TrackList.get(query);
    }


    @Override
    public TrackList recommend() {
        return evaluate();
    }

    public TrackList evaluate() {
        return null;
    }

    public int getAmount(){
        return super.getAmount();
    }
}
