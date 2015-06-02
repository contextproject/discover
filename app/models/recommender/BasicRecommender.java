package models.recommender;

import java.util.List;

import models.record.Track;
import models.utility.TrackList;

public class BasicRecommender implements Recommender {

	TrackList userCollection;
	
	@Override
	public TrackList recommend() {
		return null;
	}
	
	public TrackList getUserCollection() {
		return userCollection;
	}
	
	public List<Track> setUserCollection() {
		return userCollection;
	}
}
