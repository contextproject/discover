package models.recommender;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import models.database.retriever.GeneralTrackSelector;
import models.profile.Profile;
import models.record.Track2;
import models.utility.TrackList;

import org.junit.Before;
import org.junit.Test;

public class BasicRecommenderTest {

    private BasicRecommender rec1, rec2;

    private Profile pro1;

    private GeneralTrackSelector selector;
    
    TrackList list;
    
    @Before
    public void setUp() throws Exception {
        list = new TrackList();
        list.add(new Track2());
        pro1 = new Profile();
        pro1.setUserid(69);
        rec1 = new BasicRecommender(pro1, 3);
        rec2 = new BasicRecommender(pro1, -1);
        selector = mock(GeneralTrackSelector.class);
        when(selector.execute("abc")).thenReturn(list);
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
        assertEquals(rec2.getQuery(),"SELECT * FROM tracks INNER JOIN features "
                        + "ON tracks.track_id = features.track_id ORDER BY RAND()");
    }
    
//    @Test
//    public void testRecommend() {
//        rec1.setQuery("abc");
//        rec1.recommend();
//        verify(selector, times(5)).execute(any());
////        assertEquals(list,rec1.recommend());
//    }
}
