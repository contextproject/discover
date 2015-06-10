package models.profile;

import models.record.Track2;
import models.utility.TrackList;

/**
 * A profile for the current user used by the recommendation system.
 * Collects some of the user interactions with the application during this session.
 */
public class Profile {

    /**
     * The user id.
     */
    private int userid;

    /**
     * The tracks the user has liked during the session.
     */
    private TrackList likes;

    /**
     * The tracks the user has disliked during the session.
     */
    private TrackList dislikes;

    /**
     * The tracks the user has favourite, these tracks are retrieved
     * from SoundCloud upon log in.
     */
    private TrackList favourites;

    /**
     * Constructor.
     */
    public Profile() {
        likes = new TrackList();
        dislikes = new TrackList();
        favourites = new TrackList();
        userid = -1;
    }

    /**
     * Add a Track to the likes.
     *
     * @param track The track to like
     */
    public void addLike(final Track2 track) {
        dislikes.remove(track);
        likes.add(track);
    }

    /**
     * Add a TrackList to the likes.
     *
     * @param tracks The tracks to add to the likes
     */
    public void addFavourites(final TrackList tracks) {
        for (Track2 track : tracks) {
            favourites.add(track);
        }
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

    /**
     * Getter of the favourites.
     *
     * @return The favourites
     */
    public TrackList getFavourites() {
        return favourites;
    }

    /**
     * Setter of the user id.
     *
     * @param userid The id of the user
     */
    public void setUserid(final int userid) {
        this.userid = userid;
    }

    /**
     * Getter of the user id.
     *
     * @return The user id
     */
    public int getUserid() {
        return userid;
    }
}
