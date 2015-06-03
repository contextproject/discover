package models.seeker;

import java.util.Collections;
import java.util.List;

import models.record.Track;

/**
 * This class seeks the comment intensity in mixes. It uses a start and
 * stop time to do it in addition to the regular start and stop done by
 * the CommentIntensitySeeker. Its responsibility is to inquire to the
 * comment intensity in mixes.
 * 
 * @since 27-05-2015
 * @version 03-06-2015
 * 
 * @see CommentIntensitySeeker
 * @see MixSplitter
 * 
 * @author stefan boodt
 * @author arthur hovenesyan
 *
 */
public class MixSeeker extends CommentIntensitySeeker {
    
    /**
     * The Integer that describes the end of the song.
     */
    private static int endOfSong = Integer.MAX_VALUE;

    /**
     * The integers that contain the starttimes of the pieces of the mix.
     */
    private List<Integer> starttimesOfPieces;
    
    /**
     * Generates a new MixSeeker.
     * @param starttimesOfPieces The integers that contain the timestamps of the
     * startpoints of the different
     * @param track The track to search.
     */
    public MixSeeker(final List<Integer> starttimesOfPieces, final Track track) {
        this(starttimesOfPieces, track, new NullSeeker());
    }
    
    /**
     * Generates a new MixSeeker.
     * @param starttimesOfPieces The integers that contain the timestamps of the
     * startpoints of the different
     * @param track The track to search.
     * @param decorate The seeker to decorate and increase the functionality of.
     */
    public MixSeeker(final List<Integer> starttimesOfPieces, final Track track,
            final Seeker decorate) {
        super(track, decorate);
        setStarttimes(starttimesOfPieces);
    }
    
    /**
     * Returns the starttimes of the mix.
     * @return The starttimes that was presented for creating the mixseeker.
     */
    public List<Integer> getStarttimesOfPieces() {
        return starttimesOfPieces;
    }
    
    /**
     * Sets the starttimes of the pieces to the given value. This method also sorts the
     * starttimes in case they were not sorted so we can easily define interfalls for each
     * value in the song.
     * @param starttimesOfPieces The list of starttimes for the different pieces of the mix.
     */
    protected void setStarttimes(final List<Integer> starttimesOfPieces) {
        if (starttimesOfPieces.isEmpty()) {
            throw new IllegalArgumentException("The starttimesOfPieces was empty, please provide"
                    + " at least one starttime, so the song is split into at least one piece.");
        }
        Collections.sort(starttimesOfPieces);
        this.starttimesOfPieces = starttimesOfPieces;
    }
    
    /**
     * Returns the {@link #endOfSong} variable value.
     * @return The value of {@link #endOfSong}. Which is {@value #endOfSong}.
     */
    public static int getEndOfSong() {
        return endOfSong;
    }
    
    /**
     * This method returns the starttime of the next piece of the song.
     * @param currenttime The current time of which you want to know when the new
     * piece is starting.
     * @return The starttime of the piece of the song that comes after the current song piece.
     * The return value of {@link #endOfSong} describes that it was in the last piece.
     */
    public int getNextPieceStarttime(final int currenttime) {
        // This can be easily done this way since starttimes is sorted.
        if (currenttime < starttimesOfPieces.get(0)) {
            throw new IllegalArgumentException("Currenttime " + currenttime + " is to small. First"
                    + " part of the song begins at " + starttimesOfPieces.get(0));
        }
        for (Integer i: getStarttimesOfPieces()) {
            if (i > currenttime) {
                return i;
            }
        }
        return endOfSong;
    }
    
    @Override
    protected boolean isInRange(final int time, final int bottom, final int window) {
        return super.isInRange(time, bottom, window)
                && isBefore(time, getNextPieceStarttime(bottom));
    }
}
