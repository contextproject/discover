package models.recommender;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import models.database.retriever.GeneralTrackSelector;
import models.profile.Profile;
import models.record.Track2;
import models.utility.TrackList;

import org.junit.Before;
import org.junit.Test;

public class LikesRecommenderTest {

    private Profile pro1 ,pro2;
    
    private BasicRecommender basic1, basic2;
    
    private LikesRecommender rec1, rec2;
    
    private Track2 tr1, tr2, tr3;
    
    private TrackList likes, result;
    
    private GeneralTrackSelector selector;
    
    @Before
    public void setUp() throws Exception {
        pro1 = mock(Profile.class);
        basic1 = new BasicRecommender(pro1, 3);
        basic1.setSelector(selector);
        
        tr1 = new Track2();
        tr2 = new Track2();
        tr3 = new Track2();
        tr1.put("user_id", 1);
        tr1.put("genre", "Rap");
        tr1.put("score", 0.0);
        tr2.put("user_id", 2);
        tr2.put("genre", "Rap");
        tr2.put("score", 0.0);
        tr3.put("user_id", 3);
        tr3.put("genre", "Electro");
        tr3.put("score", 0.0);
        
        likes = new TrackList();
        result = new TrackList();
        likes.add(tr2);
        result.add(tr1);
        result.add(tr3);
        when(pro1.getLikes()).thenReturn(likes);
        when(pro1.getDislikes()).thenReturn(new TrackList());
        rec1 = new LikesRecommender(basic1);
        rec2 = new LikesRecommender(basic2);
        
        selector = mock(GeneralTrackSelector.class);
//        when(selector.execute(any())).thenReturn(result);
        //when(basic2.get).thenReturn(0);
        // when(com.getBody()).thenReturn(" ");
        // verify(pro, times(5)).getTime();
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

    @Test
    public void testGoodWeatherSuggest() {
        assertTrue(rec1.getUserProfile().getLikes() != null);
        rec1.setSelector(selector);
        assertEquals(rec1.suggest(), result);
    }
    
//    @Test
//    public void testGoodWeatherRecommend() {
//        assertTrue(rec1.getUserProfile().getLikes() != null);
//        rec1.getUserProfile().addDislike(tr3);
//        assertTrue(rec1.getUserProfile().getDislikes() != null);
//        rec1.setSelector(selector);
//        assertEquals(rec1.recommend(), result);
//    }
    
}
