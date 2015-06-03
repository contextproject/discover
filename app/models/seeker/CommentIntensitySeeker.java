package models.seeker;

import models.database.retriever.CommentRetriever;
import models.record.Comment;
import models.record.Track;
import models.score.ScoreStorage;
import models.snippet.TimedSnippet;
import models.utility.CommentList;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class returns the start time for a snippet, based on the comment
 * intensity of the track.
 * 
 * @since 07-05-2015
 * @version 03-06-2015
 * 
 * @author stefan boodt
 * @author tomas heinsohn huala
 */
public class CommentIntensitySeeker implements Seeker {

    /**
     * The id of the track.
     */
    private Track track;

    /**
     * The set of comments of the track.
     */
    private CommentList comments;
    
    /**
     * The Seeker that is decorated by this one.
     */
    private final Seeker decorate;

    /**
     * Creates a new CommentIntensitySeeker that moves over the given track.
     *
     * @param track The track
     */
    public CommentIntensitySeeker(final Track track) {
        this(track, new NullSeeker());
    }
    
    /**
     * Creates a new CommentIntensitySeeker that moves over the given track and
     * decorates the given Seeker.
     * @param track The track to search.
     * @param decorate The Seeker to decorate.
     */
    public CommentIntensitySeeker(final Track track, final Seeker decorate) {
        setTrack(track);
        this.decorate = decorate;
        setComments(new CommentRetriever(track.getTrackid()).getComments());
        
    }

    /**
     * Generates a start time for a snippet.
     * 
     * @param duration The duration of the snippet.
     *
     * @return a start time
     */
    private int getStartTime(final int duration) {
        return calculateScores(duration).maxScoreStartTime();
    }

    @Override
    public ScoreStorage calculateScores(final int duration) {
        ScoreStorage storage = decorate.calculateScores(duration);
        return updateStorage(duration, storage);
    }
    
    /**
     * Updates the storage storage by the score from the comment intensity seeker.
     * @param duration The duration of the snippet and thus the search window.
     * @param storage The storage to update.
     * @return The updated storage.
     */
    private ScoreStorage updateStorage(final int duration, final ScoreStorage storage) {
        Set<Integer> passed = new TreeSet<Integer>();
        Collections.sort(comments);
        final int commentsize = comments.size(); // Done here for efficientcy.
        for (Comment c : comments) {
            int count = 0;
            final int time = c.getTime();
            if (!passed.contains(time)) {
                boolean finished = false;
                for (int i = 0; !finished && i < commentsize; i++) {
                    Comment c2 = comments.get(i);
                    if (!isBefore(c2.getTime(), time + duration)) {
                        finished = true;
                    } else if (isInRange(c2.getTime(), time, duration)) {
                        count += getWeight(c2);
                    }
                }
                passed.add(time);
                storage.add(time, count);
            }
        }
        return storage;
    }
    
    /**
     * Checks if time is less than or equal to upperbound.
     * @param time The time.
     * @param upperbound The time to check against.
     * @return true iff time <= upperbound.
     */
    private static boolean isBefore(final int time, final int upperbound) {
        return time <= upperbound;
    }

    /**
     * Checks if time is between bottom and bottom + window.
     *
     * @param time   The time to check.
     * @param bottom The bottom of the range.
     * @param window The size of the range.
     * @return true iff it is in the range.
     */
    protected static boolean isInRange(final int time, final int bottom, final int window) {
        return time >= bottom && isBefore(time, bottom + window);
    }

    /**
     * Gets the weigth of the comment.
     *
     * @param comment The comment to gain the weight of.
     * @return The weight of the comment.
     */
    private static int getWeight(final Comment comment) {
        CommentContentSeeker filt = new CommentContentSeeker();
        return 2 + filt.contentFilter(comment.getBody());

    }

    /**
     * Seeks the snippet to be used of a given song.
     *
     * @return A TimedSnippet object
     */
    @Override
    public TimedSnippet seek() {
        return seek(TimedSnippet.getDefaultDuration());
    }
    
    @Override
    public TimedSnippet seek(final int duration) {
        return new TimedSnippet(getStartTime(duration), duration);
    }

    /**
     * Getter of the track.
     *
     * @return The track
     */
    public Track getTrack() {
        return track;
    }

    /**
     * Setter of the track.
     *
     * @param track The track
     */
    public void setTrack(final Track track) {
        this.track = track;
    }

    /**
     * Getter of the comments.
     *
     * @return The comments
     */
    public CommentList getComments() {
        return comments;
    }

    /**
     * Setter of the comments.
     *
     * @param comments The comments
     */
    public void setComments(final CommentList comments) {
        this.comments = comments;
    }
}
