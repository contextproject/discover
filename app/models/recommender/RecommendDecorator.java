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
    private Recommender recommender;

    /**
     * The weight the score should be changed with.
     */
    private double weight = 10;

    /**
     * The amount of tracks to recommend.
     */
    private int amount = 3;
    
    /**
     * The constructor of the class.
     *
     * @param recommender The decorated recommender object.
     */
    public RecommendDecorator(final Recommender recommender) {
        this.recommender = recommender;
    }

    /**
     * Suggest songs only based on your recommender.
     *
     * @return The suggested songs
     */
    public abstract TrackList suggest();

    @Override
    public Profile getUserProfile() {
        return recommender.getUserProfile();
    }

    /**
     * Getter of the recommender.
     *
     * @return The recommender
     */
    public Recommender getRecommender() {
        return recommender;
    }

    /**
     * Setter of the recommender.
     *
     * @param recommender The new recommender
     */
    public void setRecommender(final Recommender recommender) {
        this.recommender = recommender;
    }
    /**
     * Getter of the weight.
     *
     * @return The weight
     */
    public double getWeight() {
        return this.weight;
    }

    /**
     * Setter of the weight.
     *
     * @param weight The new weight
     */
    public void setWeight(final double weight) {
        this.weight = weight;
    }

    /**
     * Getter of the amount.
     *
     * @return the amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Setter of the amount.
     *
     * @param amount The new amount
     */
    public void setAmount(final int amount) {
        this.amount = amount;
    }
    
    public int getDecoratorAmount() {
        return 1 + recommender.getDecoratorAmount();
    }
}
