package models.recommender;

import java.util.List;

import models.profile.Profile;

public interface Recommender {

	List<RecTuple> recommend();
	
	Profile getUserProfile();
}