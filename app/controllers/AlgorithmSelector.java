package controllers;

import models.database.CommentRetriever;
import models.seeker.CommentContentSeeker;
import models.seeker.RandomSeeker;
import models.seeker.Seeker;
import models.snippet.Comment;

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
     * The set of comments of the current track.
     */
    private Set<Comment> comments;

    private int noComments;

    /**
     * Constructor for making a AlgorithmSelector object.
     *
     * @param trackid the id of a song.
     */
    public AlgorithmSelector(final int trackid) {
        this.trackid = trackid;
        CommentRetriever retriever = new CommentRetriever(trackid);
        comments = retriever.getComments();
        noComments = retriever.getNoComments();
    }

    public double masterMethod() {
        int timeCI = contentFiltering();
        int timeFE = featureEssentia();

        return (100 * timeCI + 0 * timeFE) / 100.0;
    }


    // Tomas
    private int contentFiltering() {
        Map<Double, Comment> comments = new HashMap<Double, Comment>();
        return -1;
    }

    // Daan
    private int commentIntensity() {
//        CommentIntensitySeeker cis = new CommentIntensitySeeker();
//        TimedSnippet ts = cis.seek(comments);
//        return ts.getStartTime();

        return 0;
    }


    // Jordy
    private int featureEssentia() {
        return 0;
    }


    /**
     * This function decides which algorithm to use and computes a snippet in different ways.
     *
     * @return the snippet for the start time and duration of a Snippet.
     */
//    public TimedSnippet getSnippet() {
//        if (enoughComments) {
//            HashMap<Comment, String> map = (HashMap<Comment, String>) retriever
//                    .getCommentsWithString(trackid);
//            Set<Comment> set = processContent(map);
//            CommentIntensitySeeker cis = new CommentIntensitySeeker();
//            TimedSnippet ts = cis.seek(set);
//
//            return ts;
//        } else {
//            return new TimedSnippet(0, TimedSnippet.getDefaultDuration());
//        }
//    }

    /**
     * Processes the content of the comments retrieved with CommentRetriever.
     *
     * @param map the hash map with the comments and their content.
     * @return A set of comments
     */
    private Set<Comment> processContent(final HashMap<Comment, String> map) {
        HashSet<Comment> result = new HashSet<Comment>();
        CommentContentSeeker cf = new CommentContentSeeker();

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
