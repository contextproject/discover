package controllers;

import models.seeker.CommentIntensitySeeker;
import models.snippet.TimedSnippet;

/**
 * Selects which algorithm could be used best and also combines algorithms.
 */
public class AlgorithmSelector {

	/**
	 * The track id of the current song.
	 */
	private final int trackid;


	/**
	 * Constructor for making a AlgorithmSelector object.
	 *
	 * @param trackid
	 *            the id of a song.
	 */
	public AlgorithmSelector(final int trackid) {
		this.trackid = trackid;
	}

	/**
	 * Determines the start time with the algorithm selector functions.
	 * @return the start time
	 */
	public double determineStart() {
		int timeCI = commentIntensity();
		int timeFE = featureEssentia();
		final double x = 100.0;

		return (100 * timeCI + 0 * timeFE) / x;
	}
//
//	/**
//	 * This part of the algorithm selector where the comments are used in the ContentFilter.
//	 * 
//	 * @return the start time.
//	 */
//	private int getComments() {
//		CommentList set = retriever.getComments();
//		Map<Double, Comment> comments = new HashMap<Double, Comment>();
//		CommentContentSeeker cf = new CommentContentSeeker();
//		for (Comment c : set) {
//			int value = cf.contentFilter(c.getBody());
//			comments.put((double) value, c);
//		}
//
//		return commentIntensity(comments);
//	}

	/**
	 * Uses the comment intensity on the weighted map.
	 * @param comments the map of comments with their value assigned by the contentfilter
	 * @return the start time computed by the intensity seeker
	 */
	private int commentIntensity() {
		CommentIntensitySeeker cis = new CommentIntensitySeeker(trackid);
		TimedSnippet ts  = cis.seek();
		return ts.getStartTime();
	}

	/**
	 * This function is for the feature essentia.
	 * @return start time computed by this function
	 */
	private int featureEssentia() {
		return 0;
	}
}
