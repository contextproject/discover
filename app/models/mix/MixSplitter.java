package models.mix;

import java.util.ArrayList;
import java.util.List;

public class MixSplitter {

	private List<Float> data;
	
	private int trackID;
	
	public MixSplitter(List<Float> data, int trackID) {
		this.setData(data);
		this.setTrackID(trackID);
	}
	
	
	public List<Integer> split() {
		List<Integer> starttimes = new ArrayList<Integer>();
		
		return starttimes;
	}
	
	public List<List<Float>> splitToShingles() {
		
		return null;
	}
	
	public double getJaccardDistance(List<Float> first, List<Float> second) {
		return 0.0;
	}
	
	public List<Float> getData() {
		return data;
	}

	public void setData(List<Float> data) {
		this.data = data;
	}

	public int getTrackID() {
		return trackID;
	}

	public void setTrackID(int trackID) {
		this.trackID = trackID;
	}
}
