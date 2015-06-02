package models.recommender;

import models.utility.TrackList;

public interface Recommender {

	TrackList recommend();

	TrackList getUserCollection();
}