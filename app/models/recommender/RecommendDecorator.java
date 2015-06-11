package models.recommender;

import models.profile.Profile;
import models.utility.TrackList;

/**
 * The RecommendDecorator is extended by classes that decorate a BasicRecommender object.
 * Implements the Recommender Interface.
 * For more information search for the design pattern: Decorator
 */
public abstract class RecommendDecorator implements Recommender {

    /**
     * The recommender object of the object.
     */
    protected Recommender recommender;

    /**
     * The weight the score should be changed with.
     */
    protected double weight = 10;

    /**
     * The constructor of the class.
     *
     * @param recommender The decorated recommender object.
     */
    public RecommendDecorator(final Recommender recommender) {
        this.recommender = recommender;
    }

    @Override
    public Profile getUserProfile() {
        return recommender.getUserProfile();
    }
    
    public abstract TrackList suggest();
    
    public double getWeight() {
        return this.weight;
    } 
    
    public void setWeight(double weight) {
        this.weight = weight;
    }

}
