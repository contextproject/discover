package models.recommender;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import models.profile.Profile;
import models.record.Key;
import models.record.Track2;
import models.utility.TrackList;

import org.junit.Before;
import org.junit.Test;

public class LikesRecommenderTest {

    private LikesRecommender rec1;

    @Before
    public void setUp() throws Exception {
        Profile pro1 = mock(Profile.class);
        BasicRecommender basic1 = new BasicRecommender(pro1, 3);

        Key key1 = new Key<>("user_id", int.class);
        Key key2 = new Key<>("genre", String.class);
        Key key3 = new Key<>("score", double.class);


        Track2 tr1 = new Track2();
        Track2 tr2 = new Track2();
        Track2 tr3 = new Track2();
        tr1.put(key1, 1);
        tr1.put(key2, "Rap");
        tr1.put(key3, 0.0);
        tr2.put(key1, 2);
        tr2.put(key2, "Rap");
        tr2.put(key3, 0.0);
        tr3.put(key1, 3);
        tr3.put(key2, "Electro");
        tr3.put(key3, 0.0);

        TrackList likes = new TrackList();
        TrackList result = new TrackList();
        likes.add(tr2);
        result.add(tr1);
        result.add(tr3);
        when(pro1.getLikes()).thenReturn(likes);
        when(pro1.getDislikes()).thenReturn(new TrackList());
        rec1 = new LikesRecommender(basic1);
    }
    
    @Test
    public void testDefaultModifiers() {
        assertEquals(rec1.getNegativeModifier(), -1.0, 0.0);
        assertEquals(rec1.getPositiveModifier(), 1.0, 0.0);
    }
    
    @Test
    public void testDifferentPositiveModifier() {
        rec1.setPositiveModifier(0.3);
        assertEquals(rec1.getPositiveModifier(), 0.3, 0.0);
    }
    
    @Test
    public void testDifferentNegativeModifier() {
        rec1.setNegativeModifier(-5.3);
        assertEquals(rec1.getNegativeModifier(), -5.3, 0.0);
    }
    
    @Test
    public void testNewWeight() {
        assertEquals(rec1.getWeight(), 10.0, 0.0);
        rec1.setWeight(26);
        assertEquals(rec1.getWeight(), 26.0, 0.0);
    }

//    @Test
//    public void testGoodWeatherSuggest() {
//        assertTrue(rec1.getUserProfile().getLikes() != null);
//        rec1.setSelector(selector);
//        assertEquals(rec1.suggest(), result);
//    }
    
//    @Test
//    public void testGoodWeatherRecommend() {
//        assertTrue(rec1.getUserProfile().getLikes() != null);
//        rec1.getUserProfile().addDislike(tr3);
//        assertTrue(rec1.getUserProfile().getDislikes() != null);
//        rec1.setSelector(selector);
//        assertEquals(rec1.recommend(), result);
//    }
    
}