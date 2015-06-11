package models.recommender;

import models.profile.Profile;
import models.record.Track2;
import models.utility.TrackList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the class FeatureRecommender.
 */
public class FeatureRecommenderTest {

    Profile profile;

    FeatureRecommender featureRecommender;

    @Before
    public void setUp() {
        profile = new Profile();
        Track2 track = new Track2();
        track.put("id", 1);
        track.put("danceability", 2);
        profile.addLike(track);
        track = new Track2();
        track.put("id", 2);
        track.put("danceability", 2.3);
        profile.addLike(track);
        track = new Track2();
        track.put("id", 3);
        track.put("danceability", 1.9);
        profile.addLike(track);

        featureRecommender = new FeatureRecommender(new BasicRecommender(profile, 5));
    }

    @Test
    public void testSuggest() throws Exception {
        TrackList trackList = featureRecommender.suggest();
        assertEquals(5, trackList.size());
    }

    @Test
    public void testRecommend() throws Exception {

    }

    @Test
    public void testGetAmount() throws Exception {

    }
}