package models.snippet;

/**
 * Filters the content of a comment.
 */
public class ContentFilter {

    /**
     * List of positive words.
     */
    private final String[] positive = {"great", "brilliant", "beautiful",
            "awesome", "amazing", "perfect", "good", "like", "love", "best",
            "nice", "super", "cool", "massive", "wicked", "sick"};

    /**
     * List of negative words.
     */
    private final String[] negative = {"hate", "n't", "nt", "not", "bad",
            "terrible", "worst"};

    /**
     * Checks if a string contains a word from the list above.
     *
     * @param content the content of a comment
     * @return true if the content contains a positive message
     */
    public boolean contentFilter(final String content) {
        String body = content.toLowerCase();
        boolean res = false;
        for (String p : positive) {
            if (body.contains(p)) {
                res = true;
                break;
            }
        }

        for (String n : negative) {
            if (body.contains(n)) {
                return false;
            }
        }

        if (res) {
            return res;
        } else {
            return findEmoticons(body);
        }
    }

    /**
     * Checks if the content of a comment contains a positive emoticon.
     *
     * @param body content of a comment
     * @return true if the content contains a happy emoticon
     */
    public boolean findEmoticons(final String body) {
        return body.contains(":)") || body.contains("<3")
                || body.contains(":d") || body.contains(":-)")
                || body.contains("=d");
    }
}
