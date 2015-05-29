package models.seeker;

import java.util.List;

/**
 * This class seeks the comment intensity in mixes. It uses a start and
 * stop time to do it in addition to the regular start and stop done by
 * the CommentIntensitySeeker. Its responsability is to inquire to the
 * comment intensity in mixes.
 * 
 * @since 27-05-2015
 * @version 29-05-2015
 * 
 * @see CommentIntensitySeeker
 * @see MixSplitter
 * 
 * @author stefan boodt
 * @author arthur hovenesyan
 *
 */
public class MixSeeker {

    /**
     * The integers that contain the starttimes of the pieces of the mix.
     */
    private List<Integer> starttimesOfPieces;
    
    /**
     * Generates a new MixSeeker.
     * @param starttimesOfPieces The integers that contain the timestamps of the
     * startpoints of the different 
     */
    public MixSeeker(final List<Integer> starttimesOfPieces) {
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
     * Sets the starttimes of the pieces to the given value.
     * @param starttimesOfPieces The list of starttimes for the different pieces of the mix.
     */
    protected void setStarttimes(final List<Integer> starttimesOfPieces) {
        if (starttimesOfPieces.isEmpty()) {
            throw new IllegalArgumentException("The starttimesOfPieces was empty, please provide"
                    + " at least one starttime, so the song is split into at least one piece.");
        }
        this.starttimesOfPieces = starttimesOfPieces;
    }
}
