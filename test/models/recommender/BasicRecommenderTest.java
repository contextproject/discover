package models.recommender;

import models.profile.Profile;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BasicRecommenderTest {

    private BasicRecommender rec1, rec2;

    @Before
    public void setUp() throws Exception {
        Profile pro1 = new Profile();
        pro1.setUserid(69);
        rec1 = new BasicRecommender(pro1, 3);
        rec2 = new BasicRecommender(pro1, -1);
    }

    @Test
    public void testGetProfile() {
        assertEquals(rec1.getUserProfile().getUserid(), 69);
    }

    @Test
    public void testSetProfile() {
        Profile pro = new Profile();
        pro.setUserid(420);
        rec1.setUserProfile(pro);
        assertEquals(rec1.getUserProfile().getUserid(), 420);
    }

    @Test
    public void testSetQuery() {
        rec1.setQuery("SELECT * FROM comments");
        assertEquals(rec1.getQuery(), "SELECT * FROM comments");
    }

    @Test
    public void testNoLimitSet() {
        assertFalse(rec2.getQuery().contains("LIMIT"));
    }

    @Test
    public void testLimitSet() {
        assertTrue(rec1.getQuery().contains("LIMIT"));
    }

    @Test
    public void testStandardQuery() {
        assertEquals(rec2.getQuery(), "SELECT * FROM tracks INNER JOIN features "
                + "ON tracks.track_id = features.track_id ORDER BY RAND()");
    }
}