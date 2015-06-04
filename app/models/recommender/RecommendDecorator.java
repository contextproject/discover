package models.recommender;

import java.util.List;

import models.profile.Profile;

public abstract class RecommendDecorator implements Recommender {

    protected Recommender recommender;

    public RecommendDecorator(Recommender recommender) {
        this.recommender = recommender;
    }

    @Override
    public abstract List<RecTuple> recommend();

    @Override
    public Profile getUserProfile() {
        return recommender.getUserProfile();
    }

}
