package models.recommender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import models.profile.Profile;
import models.record.Track;

public class LikesRecommender extends RecommendDecorator implements Recommender {

    private static HashMap<Object, Double> genreBoard;

    private static HashMap<Object, Double> artistBoard;
    
    private static double weight;
    
	public LikesRecommender(Recommender smallFish) {
		super(smallFish);
		weight = 1.5;
		genreBoard = new HashMap<Object, Double>();
		artistBoard = new HashMap<Object, Double>();
	}

	@Override
	public List<RecTuple> recommend() {
	    this.generateBoards();
	    return evaluate();
//	    if (recommender != null) {
//			return evaluate();
//		} else {
//			return suggest();
//		}
	}

	private List<RecTuple> evaluate() {
		List<RecTuple> unweighted = recommender.recommend();
		System.out.println("Size unweighted: " + unweighted.size());
		for (RecTuple rt : unweighted) {
		    Object key = rt.getTrack().getGenre();
		    if(genreBoard.containsKey(key)) {
		        rt.addScore(genreBoard.get(key));
		    }
		    if(artistBoard.containsKey(key)) {
                rt.addScore(artistBoard.get(key));
            }
		}
		return unweighted;
	}

	private void generateBoards() {
	    if (recommender != null) {
	        Profile pro = this.getUserProfile();
	        ArrayList<Track> likes = pro.getLikes();
	        ArrayList<Track> dislikes = pro.getDislikes();
	        for (Track track : likes) {
	            updateBoardPositive(genreBoard, track);
	            updateBoardPositive(artistBoard, track);
            }
	        for (Track track : dislikes) {
	            updateBoardNegative(genreBoard, track);
	            updateBoardNegative(artistBoard, track);
            }
	        
	    }
	}
	
	private static void updateBoardPositive(HashMap<Object, Double> hm, Track track) {
	    Object key = track.getGenre();
        if (hm.containsKey(key)) {
            hm.put(key, hm.get(key) + weight);
        } else {
            hm.put(key, weight*2);
        }
    }

    private static void updateBoardNegative(HashMap<Object, Double> hm, Track track) {
        Object key = track.getGenre();
        if (hm.containsKey(key)) {
            hm.put(key, hm.get(key) - (weight*2));
        } else {
            hm.put(key, - (weight*2));
        }
    }
	
	@Override
	public Profile getUserProfile() {
		return recommender.getUserProfile();
	}
	

    private List<RecTuple> suggest() {
        return null;
    }
    
}
