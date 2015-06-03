package models.profile;

import models.record.Track;
import models.utility.TrackList;

/**
 * A profile extended with the data from Soundcloud.
 *
 * @author Daan Schipper
 * @see Profile
 */
public class SoundcloudProfile extends Profile {

    /**
     * The user id.
     */
    private int userid;

    /**
     * Constructor.
     *
     * @param userid The id of the user
     */
    public SoundcloudProfile(final int userid) {
        super();
        this.userid = userid;
    }

    /**
     * Adds the likes from Soundcloud to the addLike collection.
     *
     * @param tracks The tracks to add to the likes
     */
    public void addLikes(final TrackList tracks) {
        for (Track track : tracks) {
            addLike(track);
        }
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
