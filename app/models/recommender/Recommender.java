package models.recommender;

import java.util.List;

import models.utility.TrackList;

public interface Recommender {

	List<RecTuple> recommend();
	
	TrackList getUserCollection();
}