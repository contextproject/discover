package models.recommender;

import models.profile.Profile;
import models.utility.TrackList;

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
    TrackList recommend();

    /**
     * Getter for the Profile of the user.
     * @return The profile of the user.
     */
    Profile getUserProfile();
    
    int getAmount();
}
