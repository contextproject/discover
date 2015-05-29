package models.mix;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The Class that is responsable for the splitting of the mix in several smaller
 * pieces.
 * 
 * @since 21-05-2015
 * @version 29-05-2015
 * 
 * @see Shingle
 * 
 * @author stefan boodt
 * @author arthur hovenesyan
 *
 */
public class MixSplitter {

	/**
	 * The data of the wave format of the mix.
	 */
	private List<Double> data;

	/**
	 * The id of the track to split.
	 */
	private int trackID;

	/**
	 * Creates a new mixsplitter which can then split the given mix into
	 * different pieces.
	 * 
	 * @param data
	 *            The data to split into pieces for.
	 * @param trackID
	 *            The id of the mix to split.
	 */
	public MixSplitter(final List<Double> data, final int trackID) {
		this.setData(data);
		this.setTrackID(trackID);
	}

	/**
	 * Creates a new MixSplitter which can split the given mix into different
	 * pieces.
	 * 
	 * @param json
	 *            The data to split in a JsonNode format.
	 * @param trackID
	 *            The id of the track to split.
	 */
	public MixSplitter(final JsonNode json, final int trackID) {
		Iterator<JsonNode> it = json.elements();
		List<Double> data = new ArrayList<Double>();
		while (it.hasNext()) {
			data.add(it.next().asDouble());
		}
		this.setData(data);
		this.setTrackID(trackID);
	}

	/**
	 * Splits the mix into different pieces.
	 * 
	 * @return The list of new starttimes, for which every starttime is the
	 *         beginning of a new piece.
	 */
	public List<Integer> split() {
		final double threshold = 0.4;
		final int songtime = 30000;
		return split(splitToShingles(), threshold, songtime);
	}

	/**
	 * Splits the mix into different pieces.
	 * 
	 * @param shingles
	 *            The shingles that are to be compared.
	 * @param threshold
	 *            The threshold to check, when the threshold is passed the two
	 *            Shingles belong to a different song in our assumption.
	 * @param songtime
	 *            The duration of the song you are splitting.
	 * @return The list of new starttimes, for which every starttime is the
	 *         beginning of a new piece.
	 */
	protected List<Integer> split(final List<Shingle> shingles,
	        final double threshold, final int songtime) {
		if (threshold < 0.0 || threshold > 1.0) {
			throw new IllegalArgumentException("The threshold to split mix "
					+ trackID + " was equal to " + threshold
					+ " but must be between 0.0 and 1.0");
		} else if (songtime <= 0) {
			throw new IllegalArgumentException("The songtime of the mix "
					+ trackID + " was equal to " + songtime
					+ " but must be larger than 0.");
		}
		List<Integer> starttimes = new ArrayList<Integer>();
		starttimes.add(0);
		/*
		 * Just in case someone enters a LinkedList or other list that
		 * calculates it's size instead of remembering it.
		 */
		final int amountOfShingles = shingles.size();
		// The - 1 is to not do this to the last one.
		for (int i = 0; i < amountOfShingles - 1; i++) {
			final double distance = shingles.get(i).jaccardDistance(
			        shingles.get(i + 1));
			if (distance > threshold) {
				final int integer = ((i + 1) * songtime) / amountOfShingles;
				starttimes.add(integer);
			}
		}
		return starttimes;
	}

	/**
	 * Splits the {@link #data} into different lists that can then be compared.
	 * 
	 * @return The list of Shingles that you can then compare.
	 */
	public List<Shingle> splitToShingles() {
		return splitToShingles(10, 5);
	}

	/**
	 * Splits the {@link #data} into different Shingles that can then be
	 * compared.
	 * 
	 * @param size
	 *            The size of each Shingle.
	 * @param stepsize
	 *            The size of the steps between two shingles, when set to 1
	 *            every shingle units will start next to each other, when set to
	 *            5 then the start of each shingle will be 5 away from the
	 *            previous one
	 * @return The list of Shingles that you can then compare.
	 */
	public List<Shingle> splitToShingles(final int size, final int stepsize) {
		if (size <= 0) {
			throw new IllegalArgumentException(
					"The size of the shingle should be positive but was "
							+ size);
		} else if (stepsize <= 0) {
			throw new IllegalArgumentException(
					"The stepsize of the shingle should be positive"
							+ "but was " + stepsize);
		}
		List<Shingle> shingles = new ArrayList<Shingle>();
		final int datasize = data.size(); // Saves a lot of time on really large
											// collections.
		for (int i = 0; i < datasize; i += stepsize) {
			shingles.add(new Shingle(data.subList(i,
			        Math.min(i + size, datasize))));
		}
		return shingles;
	}

	/**
	 * Gets the jaccard distance between the first and the second list of
	 * floats.
	 * 
	 * @param first
	 *            The first of the lists of floats to compare.
	 * @param second
	 *            The second list.
	 * @return The jaccard distance between first and second.
	 */
	public double getJaccardDistance(final List<Double> first,
	        final List<Double> second) {
		return getJaccardDistance(new Shingle(first), new Shingle(second));
	}

	/**
	 * Gets the jaccard distance between the first and the second list of
	 * floats.
	 * 
	 * @param first
	 *            The first of the lists of floats to compare.
	 * @param second
	 *            The second list.
	 * @return The jaccard distance between first and second.
	 */
	public double getJaccardDistance(final Shingle first, final Shingle second) {
		return first.jaccardDistance(second);
	}

	/**
	 * Returns the list of data on which we operate while splitting the mix into
	 * smaller pieces.
	 * 
	 * @return The list of floats that contain the information on which to
	 *         split.
	 */
	public List<Double> getData() {
		return data;
	}

	/**
	 * Sets the data to be used to the given value. This given value should be
	 * the result of the waveformat of the mix.
	 * 
	 * @param data
	 *            The data on which to split the mix.
	 */
	public void setData(final List<Double> data) {
		this.data = data;
	}

	/**
	 * Returns the id of the mix.
	 * 
	 * @return The track id of the mix that is currently being split.
	 */
	public int getTrackID() {
		return trackID;
	}

	/**
	 * Sets the trackid to the given value.
	 * 
	 * @param trackID
	 *            The id of the track that is being played.
	 */
	public void setTrackID(final int trackID) {
		this.trackID = trackID;
	}
}
