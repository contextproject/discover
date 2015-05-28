package models.snippet;

/**
 * Filters the content of a comment.
 */
public class ContentFilter {

	/**
	 * List of positive words.
	 */
	private final String[] positive = { "great", "brilliant", "beautiful",
		"awesome", "amazing", "perfect", "good", "like", "love", "best",
		"nice", "super", "cool", "massive", "wicked", "sick" , "wonderfull" };

	/**
	 * List of negative words.
	 */
	private final String[] negative = { "hate", "n't", "nt", "not", "bad",
		"terrible", "worst", "suck" };

	/**
	 * Checks if a string contains a word from the list above.
	 *
	 * @param content
	 *            the content of a comment
	 * @return true if the content contains a positive message
	 */
	public int contentFilter(final String content) {
		String body = content.toLowerCase();
		int res = 0;
		for (String p : positive) {
			if (body.contains(p)) {
				res = 1;
				break;
			}
		}

		for (String n : negative) {
			if (body.contains(n)) {
				return -1;
			}
		}

		if (res == 1) {
			return res;
		} else {
			return findEmoticons(body);
		}
	}

	/**
	 * Checks if the content of a comment contains a positive emoticon.
	 *
	 * @param body
	 *            content of a comment
	 * @return true if the content contains a happy emoticon
	 */
	public int findEmoticons(final String body) {
		if (body.contains(":)") || body.contains("<3") || body.contains(":d")
				  || body.contains(":-)") || body.contains("=d")) {
			return 1;
		} else {
			return 0;
		}
	}
}
