package models.recommender;

import static org.junit.Assert.*;
import models.profile.Profile;
import models.record.Key;
import models.record.Track;
import models.utility.TrackList;

import org.junit.Before;
import org.junit.Test;

public class LikesRecommenderTest {

    private LikesRecommender rec1, rec2;
    
    private Track tr1, tr2, tr3, tr4, tr5;
    
    private TrackList tl1; 

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Before
    public void setUp() throws Exception {
        Profile pro1 = new Profile();
        BasicRecommender basic1 = new BasicRecommender(pro1, 3);
        tl1 = new TrackList();
        
        Key key1 = new Key<>("user_id", int.class);
        Key key2 = new Key<>("GENRE", String.class);
        Key key3 = new Key<>("SCORE", double.class);


        tr1 = new Track();
        tr2 = new Track();
        tr3 = new Track();
        tr4 = new Track();
        tr5 = new Track();
        
        tr1.put(key1, 1);
        tr1.put(key2, "Rap");
        tr1.put(key3, 0.0);
        tr2.put(key1, 2);
        tr2.put(key2, "Rap");
        tr2.put(key3, 0.0);
        tr3.put(key1, 3);
        tr3.put(key2, "Electro");
        tr3.put(key3, 0.0);

        tr5.put(key1, null);
        tr4.put(key2, "Electro");
        tr4.put(key3, 0.0);
        
        tr5.put(key1, 5);
        tr5.put(key2, "");
        tr5.put(key3, 0.0);

        pro1.addLike(tr1);
        pro1.addLike(tr2);
        pro1.addLike(tr2);
        pro1.addLike(tr3);
        pro1.addLike(tr4);
        pro1.addLike(tr5);
        tl1.add(tr1);
        rec1 = new LikesRecommender(basic1);
        rec2 = new LikesRecommender(new BasicRecommender(new Profile(), 3));
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
        assertTrue(!rec1.getUserProfile().getLikes().isEmpty());
        assertEquals(rec1.suggest().size(), 3);
    }

    @Test
    public void testSuggestNoLikes() {
        rec2.getRecommender().getUserProfile();
        assertTrue(rec2.getUserProfile().getLikes().isEmpty());
        assertEquals(rec2.suggest().size(), 3);
    }
    
    @Test
    public void testGoodWeatherRecommend() {
        assertTrue(!rec1.getUserProfile().getLikes().isEmpty());
        assertEquals(rec1.recommend().size(), 6);
    }

    @Test
    public void testRecommendNoLikes() {
        assertTrue(rec2.getUserProfile().getLikes().isEmpty());
        assertEquals(rec2.recommend().size(), 6);
    }

    @Test
    public void testRecommendDislikes() {
        rec2.getRecommender().getUserProfile().addDislike(tr1);
        assertTrue(!rec2.getUserProfile().getDislikes().isEmpty());
        assertEquals(rec2.recommend().size(), 6);
    }
    
}