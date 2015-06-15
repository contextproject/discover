package models.seeker;

import models.database.retriever.CommentRetriever;
import models.record.Comment;
import models.record.Track;
import models.score.ScoreStorage;
import models.utility.CommentList;

import java.util.Collections;

/**
 * This class returns the start time for a snippet, based on the comment
 * intensity of the track.
 * 
 * @since 07-05-2015
 * @version 15-06-2015
 * 
 * @author stefan boodt
 * @author tomas heinsohn huala
 */
public class CommentIntensitySeeker extends AbstractSeeker {

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
     * The filter to get the scores out of.
     */
    private final CommentContentSeeker filter;

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
        this(track, decorate, new CommentContentSeeker());
    }

    
    /**
     * Creates a new CommentIntensitySeeker that moves over the given track and
     * decorates the given Seeker.
     * @param track The track to search.
     * @param decorate The Seeker to decorate.
     * @param filter The comment content filter to use and the appropriate scores within it.
     */
    public CommentIntensitySeeker(final Track track, final Seeker decorate,
            final CommentContentSeeker filter) {
        setTrack(track);
        this.decorate = decorate;
        setComments(new CommentRetriever(track.getId()).getComments());
        this.filter = filter;
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
        Collections.sort(comments);
        final int commentsize = comments.size(); // Done here for efficientcy.
        final int tracklength = track.getDuration();
        final int stepsize = Comment.getPeriod();
        for (int time = 0; time < tracklength; time += stepsize) {
            int count = 0;
            boolean finished = false;
            for (int i = 0; !finished && i < commentsize; i++) {
                Comment c2 = comments.get(i);
                if (isInRange(c2.getTime(), time, duration)) {
                    count += getWeight(c2);
                } else if (!isBefore(c2.getTime(), time + duration)) {
                    finished = true;
                }
            }
            storage.add(time, count);
        }
        return storage;
    }
    
    /**
     * Checks if time is less than or equal to upperbound.
     * @param time The time.
     * @param upperbound The time to check against.
     * @return true iff time <= upperbound.
     */
    protected boolean isBefore(final int time, final int upperbound) {
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
    protected boolean isInRange(final int time, final int bottom, final int window) {
        return time >= bottom && isBefore(time, bottom + window);
    }

    /**
     * Gets the weigth of the comment.
     *
     * @param comment The comment to gain the weight of.
     * @return The weight of the comment.
     */
    protected int getWeight(final Comment comment) {
        return 2 + getFilter().contentFilter(comment.getBody());
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
    
    /**
     * Returns the filter used by this instance to retrieve the scores.
     * @return The Filter used.
     */
    public CommentContentSeeker getFilter() {
        return filter;
    }
}
