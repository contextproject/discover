package models.snippet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import models.database.CommentRetriever;
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
	 * This function decides which algorithm to use.
	 * @return the start time for a preview.
	 */
	public int getStartTime() {
		if (enoughComments) {

			HashMap<Comment, String> map = (HashMap<Comment, String>) retriever
					  .getCommentsWithString(trackid);
			Set<Comment> set = processContent(map);
			TimedSnippet ts = CommentIntensitySeeker.seek(set);

			return ts.getStartTime();

		} else {
			return 0;
		}
	}

	/**
	 * Processes the content of the comments retrieved with CommentRetriever.
	 * @param map the hash map with the comments and their content.
	 * @return A set of comments
	 */
	private Set<Comment> processContent(final HashMap<Comment, String> map) {

		HashSet<Comment> result = new HashSet<Comment>();
		ContentFilter cf = new ContentFilter();

		for (Comment c : map.keySet()) {
			String s = map.get(c);
			if (cf.contentFilter(s)) {
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
