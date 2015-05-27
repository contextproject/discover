package models.snippet;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class returns the start time for a snippet, based on the comment
 * intensity of that song.
 */
public class CommentIntensitySeeker implements Seeker {

	/**
     * Generates a start time for a snippet.
     *
     * @param coms     The set of comments of a given song
     * @param duration The duration of that given song
     * @return a start time
     */
    protected static int getStartTime(final Set<Comment> coms, final int duration) {
        if (coms.isEmpty()) {
            return 0;
        }
        int start = 0;
        int maxcount = 0;
        Set<Integer> passed = new TreeSet<Integer>();
        for (Comment c : coms) {
            int count = 0;
            if (!passed.contains(c.getTime())) {
                for (Comment c2 : coms) {
                    if (c2.getTime() >= c.getTime() && c2.getTime() <= (c.getTime() + duration)) {
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
			return 2;
		} else {
			return 1;
		}

	}

	/**
	 * Seeks the Snippet to be used of a given song with a unknown duration.
	 *
	 * @param coms
	 *            The set of comments to use
	 * @return A TimedSnippet object
	 */
	@Override
	public TimedSnippet seek(final Set<Comment> coms) {
		return new TimedSnippet(getStartTime(coms,
				TimedSnippet.getDefaultDuration()));
	}
	
	/**
	 * Seeks the Snippet to be used of a given song with a unknown duration.
	 *
	 * @param coms
	 *            The set of comments to use
	 * @return A TimedSnippet object
	 */
	public TimedSnippet seek(final Map<Double, Comment> coms) {
		return new TimedSnippet(getStartTime(coms,
				TimedSnippet.getDefaultDuration()));
	}

	/**
	 * Seeks the snippet to be used of a given song with the duration known.
	 *
	 * @param coms
	 *            The set of comments to use
	 * @param duration
	 *            The duration of the song
	 * @return A TimedSnippet object
	 */
	@Override
	public TimedSnippet seekDuration(final Set<Comment> coms, final int duration) {
		return new TimedSnippet(getStartTime(coms, duration), duration);
	}
	
	/**
	 * Seeks the snippet to be used of a given song with the duration known.
	 *
	 * @param coms
	 *            The map of comments with their weights to use
	 * @param duration
	 *            The duration of the song
	 * @return A TimedSnippet object
	 */
	public TimedSnippet seekDuration(final Map<Double, Comment> coms, final int duration) {
		return new TimedSnippet(getStartTime(coms, duration), duration);
	}
}
