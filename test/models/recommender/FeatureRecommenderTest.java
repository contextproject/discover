package models.recommender;

import models.profile.Profile;
import models.record.Track2;
import models.utility.TrackList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for the class FeatureRecommender.
 */
public class FeatureRecommenderTest {

    Profile profile1, profile2, profile3;

    FeatureRecommender featureRecommender1, featureRecommender2, featureRecommender3;

    @Before
    public void setUp() {
        Track2 track;
        // Profile with 3 tracks, all of them have a tag danceability
        profile1 = new Profile();
        track = new Track2();
        track.put("id", 1);
        track.put("danceability", 2.0);
        profile1.addLike(track);
        for(int i = 2; i < 4; i++) {
            track = new Track2();
            track.put("id", i);
            track.put("danceability", 2.5);
            profile1.addLike(track);
        }
        for(int i = 4; i < 8; i++) {
            track = new Track2();
            track.put("id", i);
            track.put("danceability", 1.75);
            profile1.addLike(track);
        }

        track = new Track2();
        track.put("id", 8);
        track.put("danceability", 1.0);
        profile1.addLike(track);
        track = new Track2();
        track.put("id", 9);
        track.put("danceability", 3.0);
        profile1.addLike(track);

        track = new Track2();
        profile1.addLike(track);
        track = new Track2();
        profile1.addLike(track);

        // Profile with no tracks
        profile2 = new Profile();

        // Profile with a track with no danceability tag
        profile3 = new Profile();
        track = new Track2();
        track.put("id", 5);
        profile3.addLike(track);

        featureRecommender1 = new FeatureRecommender(new BasicRecommender(profile1, 5));
        featureRecommender2 = new FeatureRecommender(new BasicRecommender(profile2, 5));
        featureRecommender3 = new FeatureRecommender(new BasicRecommender(profile3, 5));
    }

    @Test
    public void testFeatureRecommender() throws Exception {
        assertNotNull(featureRecommender1);
        assertNotNull(featureRecommender2);
        assertNotNull(featureRecommender3);
    }

    @Test
    public void testSuggest1() throws Exception {
        TrackList trackList = featureRecommender1.suggest();
        assertEquals(3, trackList.size());
    }

    @Test
    public void testSuggest2() throws Exception {
        assertEquals(0, featureRecommender2.suggest().size());
    }

    @Test
    public void testSuggest3() throws Exception {
        assertEquals(0, featureRecommender3.suggest().size());
    }

    @Test
    public void testRecommend1() throws Exception {
        assertEquals(8, featureRecommender1.recommend().size());
    }

    @Test
    public void testRecommend2() throws Exception {
        assertEquals(5, featureRecommender2.recommend().size());
    }

    @Test
    public void testRecommend3() throws Exception {
        assertEquals(5, featureRecommender3.recommend().size());
    }
}