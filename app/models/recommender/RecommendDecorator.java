package models.recommender;

import models.utility.TrackList;

public abstract class RecommendDecorator implements Recommender {
	
	public Recommender smallFish;
	
	public RecommendDecorator(Recommender recommender) {
		this.smallFish = recommender;
	}
	
	@Override
	public abstract TrackList recommend();

	@Override
	public  TrackList getUserCollection() {
		return smallFish.getUserCollection();
	}

}
