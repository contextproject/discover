package models.mix;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import models.record.Track;

/**
 * The Class that is responsable for the splitting of the mix in several smaller
 * pieces.
 *
 * @author stefan boodt
 * @author arthur hovenesyan
 * @version 10-06-2015
 * @see Shingle
 * @since 21-05-2015
 */
public class MixSplitter {

    /**
     * The data of the wave format of the mix.
     */
    private List<Double> data;

    /**
     * The id of the track to split.
     */
    private Track track;
    
    /**
     * The default threshold for the splitting.
     */
    private static final double DEFAULT_THRESHOLD = 0.85;

    /**
     * Creates a new mixsplitter which can then split the given mix into
     * different pieces.
     *
     * @param data    The data to split into pieces for.
     * @param track The id of the mix to split.
     */
    public MixSplitter(final List<Double> data, final Track track) {
        this.setData(data);
        this.setTrack(track);
    }

    /**
     * Creates a new MixSplitter which can split the given mix into different
     * pieces.
     *
     * @param json    The data to split in a JsonNode format.
     * @param track   The track to split.
     */
    public MixSplitter(final JsonNode json, final Track track) {
        Iterator<JsonNode> it = json.elements();
        List<Double> data = new ArrayList<Double>();
        while (it.hasNext()) {
            final double number = 10000.00;
            data.add(Math.round(it.next().asDouble() * number) / number);
        }
        this.setData(data);
        this.setTrack(track);
    }

    /**
     * Splits the mix into different pieces.
     *
     * @return The list of new starttimes, for which every starttime is the
     * beginning of a new piece.
     */
    public List<Integer> split() {
        final int songtime = track.getDuration();
        return split(splitToShingles(), DEFAULT_THRESHOLD, songtime);
    }
    
    /**
     * Splits the song into numberOfSplits pieces.
     * @param numberOfSplits The number of splits you want. Or {@code 0} for default.
     * @return The list of starttimes that is numberOfSplits long.
     */
    public List<Integer> split(final int numberOfSplits) {
        if (numberOfSplits < 0) {
            throw new IllegalArgumentException("Invalid number of splits of " + numberOfSplits);
        }
        List<Integer> starttimes = split();
        if (numberOfSplits == 0 || numberOfSplits == starttimes.size()) {
            return starttimes;
        } else if (numberOfSplits > starttimes.size()) {
            final int splitsLeft = numberOfSplits - starttimes.size();
            return doTheSplit(splitsLeft, splitToShingles(),
                    DEFAULT_THRESHOLD, starttimes);
        }
        return starttimes.subList(0, numberOfSplits);
    }
    
    /**
     * Splits the shingles into number of splits pieces.
     * @param numberOfSplits The number of splits.
     * @param shingles The shingles to define.
     * @param threshold The threshold to work with.
     * @param current The current starttimes.
     * @return The starttimes of the pieces.
     */
    protected List<Integer> doTheSplit(final int numberOfSplits,
            final List<Shingle> shingles, final double threshold, final List<Integer> current) {
        if (numberOfSplits == 0) {
            Collections.sort(current);
            return current;
        } else if (numberOfSplits > 0) {
            if (numberOfSplits > shingles.size()) {
                return doTheSplit(0, shingles, threshold, current);
            } else {
                return addPieces(numberOfSplits, shingles, threshold, current);
            }
        } else {
            return doTheSplit(0, shingles, threshold,
                    current.subList(0, current.size() + numberOfSplits));
        }
    }

    /**
     * Adds additional pieces to the song.
     * @param numberOfSplits The number of splits still left.
     * @param shingles The list of shingles.
     * @param threshold The threshold to allow them for this step.
     * @param current The current list of starttimes.
     * @return The new list of starttimes.
     */
    public List<Integer> addPieces(final int numberOfSplits,
            final List<Shingle> shingles, final double threshold,
            final List<Integer> current) {
        if (shingles.size() < numberOfSplits) {
            throw new IllegalStateException("Can't instantiate " + numberOfSplits + " pieces"
                    + " because there only were " + shingles.size() + " shingles.");
        }
        final List<Integer> newList = getNewList(shingles, threshold, current);
        final double thresholddifference = 0.05;
        if (threshold < -thresholddifference * 2) {
            throw new IllegalStateException("Infinite loop. Amount of snippets"
                    + " can't be supplied when enough are present. Program hacked.");
        }
        final int newSplits = newList.size() - current.size();
        // For readability, a variable is created. Can be done without it.
        return doTheSplit(numberOfSplits - newSplits, shingles,
                threshold - thresholddifference, newList);
    }

    /**
     * Returns the new List that is returned after this iteration.
     * @param shingles The shingles of this song.
     * @param threshold The threshold that is currently used.
     * @param current The current list.
     * @return The new list of starttimes.
     */
    private List<Integer> getNewList(final List<Shingle> shingles,
            final double threshold, final List<Integer> current) {
        if (threshold >= 0.0) {
            return getNewList(current, split(shingles, threshold,
                    track.getDuration()));
        } else {
            return getNewList(current, addAllShingles(shingles));
        }
    }
    
    /**
     * Builds a new list that contains current and then adds all the new values
     * from the second list.
     * @param current The current list to add to.
     * @param newOnes The new ones to be added.
     * @return The new List of starttimes.
     */
    private List<Integer> getNewList(final List<Integer> current,
            final List<Integer> newOnes) {
        final List<Integer> result = new ArrayList<Integer>(current);
        for (Integer i : newOnes) {
            if (!(result.contains(i))) {
                result.add(i);
            }
        }
        return result;
    }

    /**
     * Adds all the shingles to the starttimes.
     * @param shingles The shingles to add.
     * @return The list of starttimes.
     */
    private List<Integer> addAllShingles(final List<Shingle> shingles) {
        // Two variables are made for efficiency. Calculating them once saves time and energy.
        final int amountOfShingles = shingles.size();
        final int songDuration = track.getDuration();
        final List<Integer> starttimes = new ArrayList<Integer>(amountOfShingles);
        for (int i = 0; i < amountOfShingles; i++) {
            starttimes.add(getShingleStarttime(i, amountOfShingles, songDuration));
        }
        return starttimes;
    }

    /**
     * Splits the mix into different pieces.
     *
     * @param shingles  The shingles that are to be compared.
     * @param threshold The threshold to check, when the threshold is passed the two
     *                  Shingles belong to a different song in our assumption.
     * @param songtime  The duration of the song you are splitting.
     * @return The list of new starttimes, for which every starttime is the
     * beginning of a new piece.
     */
    protected List<Integer> split(final List<Shingle> shingles,
                                  final double threshold, final int songtime) {
        checkValid(threshold, songtime);
        return splitUnsafe(shingles, threshold, songtime);
    }

    /**
     * Checks if the threshold and songtime are valid.
     * IllegalArgumentExceptions are thrown when an invalid value has
     * been found among them.
     * @param threshold The threshold to seek at, must be between 0 and 1.
     * @param songtime The duration of the song.
     */
    public void checkValid(final double threshold, final int songtime) {
        if (threshold < 0.0 || threshold > 1.0) {
            throw new IllegalArgumentException("The threshold to split mix "
                    + track.toString() + " was equal to " + threshold
                    + " but must be between 0.0 and 1.0");
        } else if (songtime <= 0) {
            throw new IllegalArgumentException("The songtime of the mix "
                    + track.toString() + " was equal to " + songtime
                    + " but must be larger than 0.");
        }
    }

    /**
     * Splits the song while not checking any values. You should avoid
     * to call this method. Please call the {@link #split()} method
     * instead. This one also checks if the values are a little sane.
     * @param shingles The shingles that need to be compared.
     * @param threshold The threshold to compare to.
     * @param songtime The duration of the song.
     * @return The list of starttimes.
     */
    private List<Integer> splitUnsafe(final List<Shingle> shingles,
            final double threshold, final int songtime) {
        List<Integer> starttimes = new ArrayList<Integer>();
        starttimes.add(0);
        /*
         * Just in case someone enters a LinkedList or other list that
         * calculates it's size instead of remembering it.
         */
        final int amountOfShingles = shingles.size();
        // The - 1 is to not do this to the last one, as it has no next one.
        for (int i = 0; i < amountOfShingles - 1; i++) {
            final double distance = shingles.get(i).jaccardDistance(
                    shingles.get(i + 1));
            if (distance > threshold) {
                final int integer = getShingleStarttime(songtime, amountOfShingles, i);
                starttimes.add(integer);
            }
        }
        return starttimes;
    }

    /**
     * Returns the starttime of shingle i.
     * @param songtime The duration of the song.
     * @param amountOfShingles The number of shingles in the song.
     * @param shingleIndex The index of the shingle.
     * @return The starttime of the shingle.
     */
    public int getShingleStarttime(final int songtime,
            final int amountOfShingles, final int shingleIndex) {
        return ((shingleIndex + 1) * songtime) / amountOfShingles;
    }

    /**
     * Splits the {@link #data} into different lists that can then be compared.
     *
     * @return The list of Shingles that you can then compare.
     */
    public List<Shingle> splitToShingles() {
        return splitToShingles(20, 15);
    }

    /**
     * Splits the {@link #data} into different Shingles that can then be
     * compared.
     *
     * @param size     The size of each Shingle.
     * @param stepsize The size of the steps between two shingles, when set to 1
     *                 every shingle units will start next to each other, when set to
     *                 5 then the start of each shingle will be 5 away from the
     *                 previous one
     * @return The list of Shingles that you can then compare.
     */
    public List<Shingle> splitToShingles(final int size, final int stepsize) {
        checkValidSizes(size, stepsize);
        return splitToShinglesUnsafe(size, stepsize);
    }

    /**
     * Checks if the two integers are valid sizes and throws an
     * IllegalArgumentException if they are not.
     * @param size The size of the list of shingles to test.
     * @param stepsize The number of data elements to skip when making a Shingle.
     */
    private void checkValidSizes(final int size, final int stepsize) {
        if (size <= 0) {
            throw new IllegalArgumentException(
                    "The size of the shingle should be positive but was "
                            + size);
        } else if (stepsize <= 0) {
            throw new IllegalArgumentException(
                    "The stepsize of the shingle should be positive"
                            + "but was " + stepsize);
        }
    }

    /**
     * Splits the shingles in an unsafe way. It being unsafe is a reference
     * to the lack of validity tests in this method.
     * 
     * <p>
     * Please refrain from calling this method and call the safe version instead.
     * The safe version is {@link #splitToShingles(int, int)}.
     * </p>
     * 
     * @param size The size of each shingle.
     * @param stepsize The size of the steps between two shingles.
     * @return The shingles found in the song with the given values.
     */
    private List<Shingle> splitToShinglesUnsafe(final int size,
            final int stepsize) {
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
     * @param first  The first of the lists of floats to compare.
     * @param second The second list.
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
     * @param first  The first of the lists of floats to compare.
     * @param second The second list.
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
     * split.
     */
    public List<Double> getData() {
        return data;
    }

    /**
     * Sets the data to be used to the given value. This given value should be
     * the result of the waveformat of the mix.
     *
     * @param data The data on which to split the mix.
     */
    public void setData(final List<Double> data) {
        this.data = data;
    }

    /**
     * Returns the mix.
     *
     * @return The track id of the mix that is currently being split.
     */
    public Track getTrack() {
        return track;
    }

    /**
     * Sets the track to the given value.
     *
     * @param track The id of the track that is being played.
     */
    public void setTrack(final Track track) {
        this.track = track;
    }
}
