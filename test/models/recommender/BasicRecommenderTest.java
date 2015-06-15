package models.recommender;

import models.database.DatabaseConnector;
import models.profile.Profile;
import models.record.Track;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.html5.ResultSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class BasicRecommenderTest {

    private BasicRecommender rec1, rec2;

    private Profile pro1;

    private Track tr1, tr2;

    private DatabaseConnector dbc;

    private ResultSet res;

    @Before
    public void setUp() throws Exception {
        pro1 = new Profile();
        pro1.setUserid(69);
        rec1 = new BasicRecommender(pro1, 3);
        rec2 = new BasicRecommender(pro1, -1);
        dbc = mock(DatabaseConnector.class);
        res = new ResultSet(0, 0, null);
        //
        // when(com.getBody()).thenReturn(" ");
        // verify(pro, times(5)).getTime();
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

//    @Test
//    public void testRecommend() {
//        
//        when(dbc.executeQuery("abc")).thenReturn(null);
//        Application.setDatabaseConnector(dbc);
//        rec1.setQuery("abc");
//        assertEquals(dbc.executeQuery("abc"), rec1.recommend());
//    }
}