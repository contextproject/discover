package models.recommender;

import java.util.List;

import models.profile.Profile;

public class LikesRecommender extends RecommendDecorator implements Recommender {

	public LikesRecommender(Recommender smallFish) {
		super(smallFish);
	}

	@Override
	public List<RecTuple> recommend() {
		if (recommender != null) {
			return evaluate();
		} else {
			return suggest();
		}
	}

	public List<RecTuple> evaluate() {
		List<RecTuple> weighted = recommender.recommend();
		return weighted;
	}

	private List<RecTuple> suggest() {
		return null;
	}
	
	@Override
	public Profile getUserProfile() {
		return recommender.getUserProfile();
	}

}
