package models.mix;

import java.util.ArrayList;
import java.util.List;

/**
 * The ShingleSet class contains a sorted set of shingles. *
 */
public class Shingle {

	/**
	 * The data that the shingle consists of and operates on.
	 */
	private List<Float> data;

	/**
	 * @param data The data to be stored in the shingle.
	 */
	public Shingle(final List<Float> data) {
		this.data = data;
	}

	/**
	 * Calculates the Jaccard distance between this set and the other set.
	 * @param other The other set of shingles that this set will be compared to.
	 * @return The Jaccard distance between this set and the other set.
	 */
	public double jaccardDistance(Shingle other) {
		Shingle part = this;
		part.getData().retainAll(other.getData());
		double ans = (double) part.size() / (double) this.size();
		return 1-ans; 
	}

	public int size() {
		return data.size();
	}
	
	public void add(Float e) {
		data.add(e);
	}
	
	public List<Float> getData() {
		return data;
	}

	public void setData(ArrayList<Float> data) {
		this.data = data;
	}
	
}
