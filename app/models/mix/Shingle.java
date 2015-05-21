package models.mix;

import java.util.ArrayList;
import java.util.List;

/**
 * The ShingleSet class contains a sorted set of shingles. *
 */
public class Shingle {

	private List<Float> data;

	/**
	 * Constructor for the ShingleSet.
	 * @param k The size of the shingles.
	 */
	public Shingle(int k) {
		this.data = new ArrayList<Float>(k);
	}

	/**
	 * Add shingles of size k to the set from String s.
	 * @param s, The string that is to be transformed to shingles.
	 */
	public void shingleString(Float s) {
//		if (!(s.length() < size)) {
//			for (int i = 0; i+size <= s.length(); i++) {
//				this.add(s.substring(i,i+size));
//			}
//		}
	}

	/**
	 * Calculates the Jaccard distance between this set and the other set.
	 * @param other The other set of shingles that this set will be compared to.
	 * @return The Jaccard distance between this set and the other set.
	 */
	public double jaccardDistance(Shingle other) {
		double ans = 0;
//		TreeSet<String> part = (TreeSet) this.clone();
//		TreeSet<String> whole = (TreeSet) this.clone();
//		part.retainAll(other);
//		whole.addAll(other);
//		double ans = (double) part.size() / (double) whole.size();
		return 1-ans; 
	}
	

}
