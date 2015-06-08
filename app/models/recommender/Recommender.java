package models.recommender;

import java.util.List;

import models.profile.Profile;

/**
 * The Recommender Interface implemented by Recommender classes used for
 * recommending track to the user.
 */
public interface Recommender {

    /**
     * The recommender method is used to evaluate a List of RecTuple objects.
     * The objects contain a track and its score. This score can then be used to
     * choose most fitting tracks.
     * 
     * @return List of RecTuple objects.
     */
    List<RecTuple> recommend();

    /**
     * Getter for the Profile of the user.
     * @return The profile of the user.
     */
    Profile getUserProfile();

    int getAmount();
}
