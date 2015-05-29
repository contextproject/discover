package models.seeker;

import models.database.retriever.CommentRetriever;
import models.record.Comment;
import models.utility.CommentList;
import models.snippet.TimedSnippet;

import java.util.Set;
import java.util.TreeSet;

/**
 * This class returns the start time for a snippet, based on the comment
 * intensity of the track.
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
	 * @param trackid
	 *            The id of the track
	 */
	public CommentIntensitySeeker(final int trackid) {
		this.trackid = trackid;
		comments = new CommentRetriever(trackid).getComments();
	}

	//don't know if we still need this
//	/**
//	 * Generates a start time for a snippet.
//	 *
//	 * @return a start time
//	 */
//	private int getStartTime1() {
//		int start = -1;
//		int maxcount = 0;
//		Set<Integer> passed = new TreeSet<Integer>();
//		for (Comment c : comments) {
//			int count = 0;
//			if (!passed.contains(c.getTime())) {
//				for (Comment c2 : comments) {
//					if (c2.getTime() >= c.getTime()
//							&& c2.getTime() <= c.getTime()) {
//						count++;
//					}
//				}
//				if (count > maxcount) {
//					maxcount = count;
//					start = c.getTime();
//				}
//				passed.add(c.getTime());
//			}
//		}
//		return start;
//	}

	/**
	 * Generates a start time for a snippet.
	 * @return a start time
	 */
	protected int getStartTime() {
		
		if (comments.isEmpty()) {
			return 0;
		}
		int start = 0;
		int maxcount = 0;
		Set<Integer> passed = new TreeSet<Integer>();

		for (Comment c : comments) {
			int count = 0;
			if (!passed.contains(c.getTime())) {

				for (Comment c2 : comments) {
					if (isInRange(c2.getTime(), c.getTime(), TimedSnippet.getDefaultDuration())) {
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
    private static boolean isInRange(final int time, final int bottom, final int window) {
        return time >= bottom && time <= (bottom + window);
    }
    
    /**
     * Gets the weigth of the comment.
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
		return new TimedSnippet(getStartTime());
	}

	/**
	 * Returns the trackid of where this seeker is invoked on.
	 * @return track id
	 */
	public int getTrackid() {
		return trackid;
	}

	/**
	 * Sets the track id for a this seeker instance.
	 * @param trackid id of the song.
	 */
	public void setTrackid(final int trackid) {
		this.trackid = trackid;
	}

	/**
	 * Returns the needed commentlist for the intensity seeker.
	 * @return the actual commentlist.
	 */
	public CommentList getComments() {
		return comments;
	}

	/**
	 * Sets a commentlist for a instance of this seeker.
	 * @param comments the new commentlist.
	 */
	public void setComments(final CommentList comments) {
		this.comments = comments;
	}
}
