package models.recommender;

import models.database.retriever.GeneralTrackSelector;
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


    public List<RecTuple> suggest() {
        int danceability = 2;
        String query = "SELECT danceability, "
                + "abs(danceability - " + danceability + ") as distance"
                + "FROM     `features`"
                + "ORDER BY distance"
                + "LIMIT " + getAmount();
        GeneralTrackSelector selector = new GeneralTrackSelector(query);
        TrackList list = selector.execute();

        return null;
    }


    @Override
    public List<RecTuple> recommend() {
        List<RecTuple> unweighted = recommender.recommend();


        return unweighted;
    }

    public int getAmount(){
        return super.getAmount();
    }
}
