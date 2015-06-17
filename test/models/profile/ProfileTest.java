package models.profile;

import models.record.Track;
import models.utility.TrackList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for the Profile class.
 */
public class ProfileTest {

    private Profile profile;

    /**
     * Set up.
     */
    @Before
    public void setUp() {
        profile = new Profile();
    }

    /**
     * Test for the constructor.
     */
    @Test
    public void testProfile() {
        assertNotNull(profile);
    }

    /**
     * Test for the addLike() method.
     *
     * @throws Exception Exception
     */
    @Test
    public void testAddLike() throws Exception {
        assertEquals(0, profile.getLikes().size());
        profile.addLike(new Track());
        assertEquals(1, profile.getLikes().size());
    }

    @Test
    public void testAddFavourites() {
        TrackList trackList = new TrackList();
        trackList.add(new Track());
        profile.addFavourites(trackList);
        assertEquals(1, profile.getFavourites().size());
    }

    /**
     * Test for the addDislike() method.
     *
     * @throws Exception Exception
     */
    @Test
    public void testAddDislike() throws Exception {
        assertEquals(0, profile.getDislikes().size());
        profile.addDislike(new Track());
        assertEquals(1, profile.getDislikes().size());
    }

    @Test
    public void testUserId() throws Exception {
        assertEquals(-1, profile.getUserid());
        profile.setUserid(0);
        assertEquals(0, profile.getUserid());
    }
}