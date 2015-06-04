package models.recommender;

import java.util.List;

import models.database.retriever.GeneralTrackSelector;
import models.profile.Profile;

public class BasicRecommender implements Recommender {

    Profile userProfile;

    private String query;

    public BasicRecommender(Profile profile, int amount) {
        this.userProfile = profile;
        this.query = "SELECT * FROM tracks ORDER BY RAND( )";
        if (amount != -1) {
            query += (" LIMIT " + amount);
        }
    }

    @Override
    public List<RecTuple> recommend() {
        GeneralTrackSelector seeker = new GeneralTrackSelector(query);
        List<RecTuple> res = seeker.asWeightedList(0.0);
        System.out.println(res.size());
        return res;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Profile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(Profile userProfile) {
        this.userProfile = userProfile;
    }
}
