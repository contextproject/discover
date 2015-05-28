package models.snippet;

import models.database.CommentRetriever;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Selects which algorithm could be used best and also combines algorithms.
 */
public class AlgorithmSelector {

	/**
	 * The track id of the current song.
	 */
	private final int trackid;

	/**
	 * A variable which indicates if a song has enough comments.
	 */
	private boolean enoughComments = false;

	/**
	 * A comment retriever object to use.
	 */
	private CommentRetriever retriever;

	/**
	 * Constructor for making a AlgorithmSelector object.
	 *
	 * @param trackid
	 *            the id of a song.
	 */
	public AlgorithmSelector(final int trackid) {
		this.trackid = trackid;
		retriever = new CommentRetriever();
		int commentAmount = retriever.getAmountOfComments(trackid);
		if (commentAmount > 5) {
			enoughComments = true;
		}
	}

	/**
	 * Determines the start time with the algorithm selector functions.
	 * @return the start time
	 */
	public double determineStart() {
		int timeCI = contentFiltering();
		int timeFE = featureEssentia();
		final double x = 100.0;

		return (100 * timeCI + 0 * timeFE) / x;
	}

	/**
	 * This part of the algorithm selector where the comments are used in the ContentFilter.
	 * 
	 * @return the start time.
	 */
	private int contentFiltering() {
		Set<Comment> set = retriever.getComments(trackid);
		Map<Double, Comment> comments = new HashMap<Double, Comment>();
		ContentFilter cf = new ContentFilter();
		for (Comment c : set) {
			int value = cf.contentFilter(c.getBody());
			comments.put((double) value, c);
		}

		return commentIntensity(comments);
	}

	/**
	 * Uses the comment intensity on the weighted map.
	 * @param comments the map of comments with their value assigned by the contentfilter
	 * @return the start time computed by the intensity seeker
	 */
	private int commentIntensity(final Map<Double, Comment> comments) {
		CommentIntensitySeeker cis = new CommentIntensitySeeker();
		TimedSnippet ts  = cis.seek(comments);
		return ts.getStartTime();
	}

	/**
	 * This function is for the feature essentia.
	 * @return start time computed by this function
	 */
	private int featureEssentia() {
		return 0;
	}

	/**
	 * This function decides which algorithm to use and computes a snippet in
	 * different ways.
	 *
	 * @return the snippet for the start time and duration of a Snippet.
	 */
	public TimedSnippet getSnippet() {
		if (enoughComments) {
			// Set<Comment> map = retriever
			// .getComments(trackid);
			// Set<Comment> set = processContent(map);
			// CommentIntensitySeeker cis = new CommentIntensitySeeker();
			// TimedSnippet ts = cis.seek(set);

			return new TimedSnippet(0, TimedSnippet.getDefaultDuration());
		} else {
			return new TimedSnippet(0, TimedSnippet.getDefaultDuration());
		}
	}

	/**
	 * Processes the content of the comments retrieved with CommentRetriever.
	 *
	 * @param map
	 *            the hash map with the comments and their content.
	 * @return A set of comments
	 */
	private Set<Comment> processContent(final HashMap<Comment, String> map) {
		HashSet<Comment> result = new HashSet<Comment>();
		ContentFilter cf = new ContentFilter();

		for (Comment c : map.keySet()) {
			String s = map.get(c);
			if (cf.contentFilter(s) > 0) {
				result.add(c);
			}
		}

		if (result.size() > 5) {
			return result;
		} else {
			return map.keySet();
		}
	}

}
