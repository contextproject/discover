package models.mix;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class that is responsable for the splitting of the mix in several smaller
 * pieces.
 * 
 * @since 21-05-2015
 * @version 21-05-2015
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
    private List<Float> data;

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
    public MixSplitter(final List<Float> data, final int trackID) {
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
        List<Integer> starttimes = new ArrayList<Integer>();

        return starttimes;
    }

    /**
     * Splits the {@link #data} into different lists that can then be compared.
     * 
     * @return The list of Shingles that you can then compare.
     */
    public List<List<Float>> splitToShingles() {

        return null;
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
    public double getJaccardDistance(final List<Float> first,
            final List<Float> second) {
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
    public List<Float> getData() {
        return data;
    }

    /**
     * Sets the data to be used to the given value. This given value should be
     * the result of the waveformat of the mix.
     * 
     * @param data
     *            The data on which to split the mix.
     */
    public void setData(final List<Float> data) {
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
