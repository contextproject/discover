package models.profile;

import models.record.Track;
import models.utility.TrackList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for the SoundcloudProfile class
 */
public class SoundcloudProfileTest {

    /**
     * The SoundcloudProfile object.
     */
    SoundcloudProfile soundcloudProfile;

    /**
     * Set up.
     */
    @Before
    public void setUp() {
        soundcloudProfile = new SoundcloudProfile(1);
    }

    @Test
    public void test() {
        assertTrue(Profile.class.isAssignableFrom(SoundcloudProfile.class));
    }

    /**
     * Test for the constructor.
     */
    @Test
    public void testSoundcloudProfile() {
        assertNotNull(soundcloudProfile);
    }
    /**
     * Test for the addLikes() method.
     */
    @Test
    public void testAddLikes() {
        TrackList trackList = new TrackList();
        trackList.add(new Track(1, 1));

        soundcloudProfile.addLikes(trackList);

        assertEquals(1, soundcloudProfile.likes.size());
    }

    /**
     * Test for the getter of the user id.
     */
    @Test
    public void testGetUserid() {
        assertEquals(1, soundcloudProfile.getUserid());
    }
}