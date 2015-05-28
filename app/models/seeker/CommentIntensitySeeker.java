package models.seeker;

import models.database.retriever.CommentRetriever;
import models.record.Comment;
import models.record.Track;
import models.snippet.TimedSnippet;
import models.utility.CommentList;

import java.util.Set;
import java.util.TreeSet;

/**
 * This class returns the start time for a snippet,
 * based on the comment intensity of the track.
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
     * Constructor.
     *
     * @param track The track
     */
    public CommentIntensitySeeker(final Track track) {
        this.track = track;
        this.comments = new CommentRetriever(track.getTrackid()).getComments();
    }

    /**
     * Generates a start time for a snippet.
     *
     * @return a start time
     */
    private int getStartTime() {
        int start = 0;
        int maxcount = 0;
        Set<Integer> passed = new TreeSet<Integer>();
        for (Comment c : comments) {
            int count = 0;
            if (!passed.contains(c.getTime())) {
                for (Comment c2 : comments) {
                    if (c2.getTime() >= c.getTime() && c2.getTime() <= c.getTime()) {
                        count++;
                    }
                }
                if (count > maxcount) {
                    maxcount = count;
                    start = c.getTime();
                }
                passed.add(c.getTime());
            }
        }
        return start;
    }

    /**
     * Seeks the snippet to be used of a given song.
     *
     * @return A TimedSnippet object
     */
    @Override
    public TimedSnippet seek() {
        return new TimedSnippet(getStartTime());
    }

    /**
     * Getter of the track
     *
     * @return The track
     */
    public Track getTrack() {
        return track;
    }

    /**
     * Setter of the track
     *
     * @param track The track
     */
    public void setTrack(final Track track) {
        this.track = track;
    }

    public CommentList getComments() {
        return comments;
    }

    public void setComments(CommentList comments) {
        this.comments = comments;
    }
}
