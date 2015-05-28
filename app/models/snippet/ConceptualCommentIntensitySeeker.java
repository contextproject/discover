package models.snippet;

import java.util.Set;
import java.util.TreeSet;

import models.seeker.Seeker;

/**
 * Class that can demonstrate how the CommentIntensitySeeker and
 * ContentFilter could be used together.
 * 
 * @author stefan boodt
 *
 */
public class ConceptualCommentIntensitySeeker implements Seeker {

    /**
     * The filter to use to determine a score for each comment.
     */
    private ContentFilter filter;
    
    /**
     * Creates a new CommentIntensitySeeker.
     * @param filter The filter to be used to request a score.
     */
    public ConceptualCommentIntensitySeeker(final ContentFilter filter) {
        this.filter = filter;
    }
    
    @Override
    public TimedSnippet seek(final Set<Comment> coms) {
        return new TimedSnippet(getStartTime(coms,
                TimedSnippet.getDefaultDuration()));
    }

    @Override
    public TimedSnippet seekDuration(final Set<Comment> coms, final int duration) {
        return new TimedSnippet(getStartTime(coms, duration), duration);
    }

    /**
     * Generates a start time for a snippet.
     *
     * @param coms
     *            The set of comments of a given song
     * @param duration
     *            The duration of that given song
     * @return a start time
     */
    protected int getStartTime(final Set<Comment> coms,
              final int duration) {
        if (coms.isEmpty()) {
            return 0;
        }
        int start = 0;
        int maxcount = 0;
        Set<Integer> passed = new TreeSet<Integer>();
        for (Comment c : coms) {
            int count = 0;
            count += getWeight(c);
            if (!passed.contains(c.getTime())) {
                for (Comment c2 : coms) {
                    if (isInRange(c2.getTime(), c.getTime(), duration)) {
                        count += getWeight(c2);
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
     * Checks if time is between bottom and bottom + window.
     * @param time The time to check.
     * @param bottom The bottom of the range.
     * @param window The size of the range.
     * @return true iff it is in the range.
     */
    protected boolean isInRange(final int time, final int bottom, final int window) {
        return time >= bottom && time <= (bottom + window);
    }
    
    /**
     * Gets the weigth of the comment.
     * @param comment The comment to gain the weight of.
     * @return The weight of the comment.
     */
    protected int getWeight(final Comment comment) {
        return 2 + filter.contentFilter(comment.getBody());
    }
}
