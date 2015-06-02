package models.recommender;

import models.utility.TrackList;

public class LikesRecommender extends RecommendDecorator implements Recommender{

	public LikesRecommender(Recommender smallFish) {
		super(smallFish);
	}

	@Override
	public TrackList recommend() {
		return null;
	}

	@Override
	public TrackList getUserCollection() {
		return super.getUserCollection();
	}
}
