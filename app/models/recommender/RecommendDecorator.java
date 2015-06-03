package models.recommender;

import java.util.List;

import models.utility.TrackList;

public abstract class RecommendDecorator implements Recommender {
	
	public Recommender recommender;
	
	public RecommendDecorator(Recommender recommender) {
		this.recommender = recommender;
	}
	
	@Override
	public abstract List<RecTuple> recommend();

	@Override
	public TrackList getUserCollection() {
		return recommender.getUserCollection();
	}

}
