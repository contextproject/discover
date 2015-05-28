package models.seeker;

import models.database.retriever.CommentRetriever;
import models.record.Comment;
import models.record.CommentList;
import models.snippet.TimedSnippet;

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
    private int trackid;

    /**
     * The set of comments of the track.
     */
    private CommentList comments;

    /**
     * Constructor.
     *
     * @param trackid The id of the track
     */
    public CommentIntensitySeeker(int trackid) {
        this.trackid = trackid;
        this.comments = new CommentRetriever(trackid).getComments();
    }

    /**
     * Generates a start time for a snippet.
     *
     * @return a start time
     */
    private int getStartTime() {
        int start = -1;
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


    public int getTrackid() {
        return trackid;
    }

    public void setTrackid(int trackid) {
        this.trackid = trackid;
    }

    public CommentList getComments() {
        return comments;
    }

    public void setComments(CommentList comments) {
        this.comments = comments;
    }
}
