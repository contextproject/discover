package models.profile;

import models.record.Track;

import java.util.ArrayList;

/**
 * A profile for the current user used by the recommendation system.
 * Collects some of the user interactions with the application during this session.
 *
 * @author Daan Schipper
 */
public class Profile {

    /**
     * The tracks the user has liked during the session.
     */
    private ArrayList<Track> likes;

    /**
     * The tracks the user has disliked during the session.
     */
    private ArrayList<Track> dislikes;

    /**
     * Constructor.
     */
    public Profile() {
        likes = new ArrayList<Track>();
        dislikes = new ArrayList<Track>();
    }

    /**
     * Add a track to the likes.
     *
     * @param track The track to like
     */
    public void addLike(final Track track) {
        likes.add(track);
    }

    /**
     * Add a track to the dislike.
     *
     * @param track The track to dislike
     */
    public void addDislike(final Track track) {
        dislikes.add(track);
    }

    /**
     * Getter of the likes.
     *
     * @return The likes
     */
    public ArrayList<Track> getLikes() {
        return likes;
    }

    /**
     * Getter of the dislikes.
     *
     * @return The dislikes
     */
    public ArrayList<Track> getDislikes() {
        return dislikes;
    }
}
