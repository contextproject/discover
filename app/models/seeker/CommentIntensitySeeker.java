package models.seeker;

import models.database.retriever.CommentRetriever;
import models.record.CommentList;
import models.record.Comment;
import models.snippet.TimedSnippet;

import java.util.Map;
import java.util.Map.Entry;
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
					if (c2.getTime() >= c.getTime()
							&& c2.getTime() <= c.getTime()) {
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
	 * Generates a start time for a snippet.
	 *
	 * @param coms
	 *            A map of comments of a given song
	 * @param duration
	 *            The duration of that given song
	 * @return a start time
	 */
	protected static int getStartTime(final Map<Double, Comment> coms,
			  final int duration) {
		if (coms.isEmpty()) {
			return 0;
		}
		int start = 0;
		int maxcount = 0;
		Set<Entry<Double, Comment>> entryset = coms.entrySet();
		Set<Integer> passed = new TreeSet<Integer>();

		for (Entry<Double, Comment> c : entryset) {
			int count = 0;
			if (!passed.contains(c.getValue().getTime())) {

				for (Entry<Double, Comment> c2 : entryset) {
					if (c2.getValue().getTime() >= c.getValue().getTime()
							  && c2.getValue().getTime() <= (c.getValue()
									.getTime() + duration)) {
						count += computeWeight(c, c2);
					}
				}

				if (count > maxcount) {
					maxcount = count;
					start = c.getValue().getTime();
				}
				passed.add(c.getValue().getTime());
			}
		}
		return start;
	}

	/**
	 * Computes the weight of two comment who are in the same range.
	 * 
	 * @param c
	 *            first comment entry.
	 * @param d
	 *            second comment entry.
	 * @return the weight of 2 comments.
	 */
	private static int computeWeight(final Entry<Double, Comment> c,
			  final Entry<Double, Comment> d) {
		if (c.getKey() == 1 && d.getKey() == 1) {
			return 3;
		} else if ((c.getKey() == 1 | d.getKey() == 1)) {
			return 2;
		} else {
			return 1;
		}

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
