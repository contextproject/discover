package models.recommender;

import java.util.List;

import models.database.retriever.GeneralTrackSelector;
import models.record.Track;
import models.utility.TrackList;

public class BasicRecommender implements Recommender {

	TrackList userCollection;
	
	private String query;
	
	public BasicRecommender(int size, TrackList collection) {
		this.userCollection = collection;
		this.query = "SELECT * FROM tracks ORDER BY RAND( )";
		if(size != -1) {
			query += (" LIMIT " + size);
		}
	}
	
	@Override
	public List<RecTuple> recommend() {
		GeneralTrackSelector seeker = new GeneralTrackSelector(query);
		return seeker.asWeightedList(0.0);
	}
	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public TrackList getUserCollection() {
		return userCollection;
	}
	
	public List<Track> setUserCollection() {
		return userCollection;
	}
}
