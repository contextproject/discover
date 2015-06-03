package models.recommender;

import models.record.Track;

public class RecTuple implements Comparable<RecTuple> {

	public Track track;
	
	public double score;
	
	public RecTuple(Track track, Double score) {
		this.track = track;
		this.score = score;
	}

	@Override
	public int compareTo(RecTuple other) {
		return (int) Math.round(this.getScore() - other.getScore());
	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
}
