package models.profile;

import models.record.Track2;
import models.utility.TrackList;

/**
 * A profile for the current user used by the recommendation system.
 * Collects some of the user interactions with the application during this session.
 */
public class Profile {

    /**
     * The tracks the user has liked during the session.
     */
    private TrackList likes;

    /**
     * The tracks the user has disliked during the session.
     */
    private TrackList dislikes;

    /**
     * Constructor.
     */
    public Profile() {
        likes = new TrackList();
        dislikes = new TrackList();
    }

    /**
     * Add a track to the likes.
     *
     * @param track The track to like
     */
    public void addLike(final Track2 track) {
        dislikes.remove(track);
        likes.add(track);
    }

    /**
     * Add a track to the dislike.
     *
     * @param track The track to dislike
     */
    public void addDislike(final Track2 track) {
        dislikes.add(track);
        likes.remove(track);
    }

    /**
     * Getter of the likes.
     *
     * @return The likes
     */
    public TrackList getLikes() {
        return likes;
    }

    /**
     * Getter of the dislikes.
     *
     * @return The dislikes
     */
    public TrackList getDislikes() {
        return dislikes;
    }
}
