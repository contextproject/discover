package models.mix;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class that is responsable for the splitting of the mix in several smaller
 * pieces.
 * 
 * @since 21-05-2015
 * @version 27-05-2015
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
        final double threshold = 0.4f;
        final int songtime = 30000;
        return split(splitToShingles(), threshold, songtime);
    }

    /**
     * Splits the mix into different pieces.
     * 
     * @param shingles The shingles that are to be compared.
     * @param threshold The threshold to check, when the threshold is passed the two
     * Shingles belong to a different song in our assumption.
     * @param songtime The duration of the song you are splitting.
     * @return The list of new starttimes, for which every starttime is the
     *         beginning of a new piece.
     */
    protected List<Integer> split(final List<Shingle> shingles, final double threshold
            , final int songtime) {
        if (threshold < 0.0 || threshold > 1.0) {
            throw new IllegalArgumentException("The threshold to split mix "
                    + trackID + " was equal to " + threshold + " but must be between 0.0 and 1.0");
        } else if (songtime <= 0) {
            throw new IllegalArgumentException("The songtime of the mix "
                    + trackID + " was equal to " + songtime + " but must be larger than 0.");
        }
        List<Integer> starttimes = new ArrayList<Integer>();
        starttimes.add(0);
        /*
         *  Just in case someone enters a LinkedList or other list that
         *  calculates it's size instead of remembering it.
         */
        final int amountOfShingles = shingles.size();
        // The - 1 is to not do this to the last one.
        for (int i = 0; i < amountOfShingles - 1; i++) {
            final double distance = shingles.get(i).jaccardDistance(shingles.get(i + 1));
            if (distance > threshold) {
                starttimes.add(new Double(((i + 1) * songtime) / amountOfShingles).intValue());
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
        return new ArrayList<Shingle>();
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
