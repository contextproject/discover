package models.mix;

import java.util.ArrayList;
import java.util.List;

/**
 * The ShingleSet class contains a sorted set of shingles. *
 * 
 * @version 26-05-2015
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
	public double jaccardDistance(final Shingle other) {
		Shingle part = this;
		part.getData().retainAll(other.getData());
		final Shingle union = union(other);
		final double unionsize = union.size();
		final double ans;
		// Some safety for the double rounding errors.
		// Variable created to prevent magic numbers.
		final double lessThanOne = 0.9;
		if (unionsize < lessThanOne) {
		    ans = 1.0;
		} else {
		    ans = (double) part.size() / unionsize;
		}
		return 1.0 - ans;
	}
	
	/**
	 * Unionizes two Shingles. This method uses a functional way of using
	 * to ensure that both of the shingles are unaltered.
	 * @param other The other shingle.
	 * @return The union of the two shingles.
	 */
	public Shingle union(final Shingle other) {
	    List<Float> union = new ArrayList<Float>(this.size() + other.size());
	    for (Float f: data) {
	        if (!union.contains(f)) {
                union.add(f);
            }
	    }
	    for (Float f: other.getData()) {
	        if (!union.contains(f)) {
                union.add(f);
            }
        }
	    return new Shingle(union);
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
