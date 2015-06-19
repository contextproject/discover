package models.recommender;

import models.database.DatabaseConnector;
import models.profile.Profile;
import models.record.Key;
import models.record.Track;
import models.utility.TrackList;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for the class FeatureRecommender.
 */
public class FeatureRecommenderTest {

    FeatureRecommender featureRecommender1, featureRecommender2, featureRecommender3;
    Profile profile1, profile2, profile3;
    Key key1, key2;
    
    /**
     * Does some set up before the class.
     * @throws Exception If the set up fails.
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        DatabaseConnector databaseConnector = DatabaseConnector.getConnector();
        databaseConnector.loadDrivers();
        databaseConnector.makeConnection("jdbc:mysql://188.166.78.36/contextbase", "context", "password");
    } 
    
    /**
     * Does some clean up after the class has been run.
     * @throws Exception If the clean up fails.
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        DatabaseConnector.getConnector().closeConnection();
    }


    @Before
    public void setUp() {
        key1 = new Key<>("id", Integer.class);
        key2 = new Key<>("danceability", Double.class);

        Track track;
        // Profile with 3 tracks, all of them have a tag danceability
        profile1 = new Profile();
        track = new Track();
        track.put(key1, 1);
        track.put(key2, 2.0);
        profile1.addLike(track);
        for(int i = 2; i < 4; i++) {
            track = new Track();
            track.put(key1, i);
            track.put(key2, 2.5);
            profile1.addLike(track);
        }
        for(int i = 4; i < 8; i++) {
            track = new Track();
            track.put(key1, i);
            track.put(key2, 1.75);
            profile1.addLike(track);
        }

        track = new Track();
        track.put(key1, 8);
        track.put(key2, 1.0);
        profile1.addLike(track);
        track = new Track();
        track.put(key1, 9);
        track.put(key2, 3.0);
        profile1.addLike(track);

        track = new Track();
        profile1.addLike(track);
        track = new Track();
        profile1.addLike(track);

        // Profile with no tracks
        profile2 = new Profile();

        // Profile with a track with no danceability tag
        profile3 = new Profile();
        track = new Track();
        track.put(key1, 5);
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