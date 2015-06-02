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
     * The user id
     */
    private int userid;

    /**
     * Constructor.
     */
    public SoundcloudProfile(int userid) {
        super();
        this.userid = userid;
    }
    /**
     * Adds the likes from Soundcloud to the addLike collection.
     */
    public void addLikes(TrackList tracks) {
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
