package models.seeker;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import models.score.ScoreParser;
import models.score.XMLScoreParser;

/**
 * Filters the content of a comment.
 */
public class CommentContentSeeker {

    /**
     * The map containing the Scores.
     */
    private Map<String, Integer> scores;
    
    /**
     * The sortedEntries in the scores map. Used to highly increase the
     * speed of the {@link #contentFilter(String)} method since it now no
     * longer needs to sort it for every comment fired at it.
     */
    private Set<Entry<String, Integer>> sortedEntries; 
    
    /**
     * Creates a new CommentContentSeeker.
     */
    public CommentContentSeeker() {
        this(getURI("/public/xml/default-scores.xml"));
    }
    
    /**
     * Builds the CommentIntensitySeeker from the given uri. If the URI throws
     * any errors when parsing it is set to a empty map.
     * @param xmluri The uri to an XML file containing the scores. It's
     * body.
     */
    public CommentContentSeeker(final URI xmluri) {
        this(xmluri, XMLScoreParser.createParser());
    }
    
    /**
     * Builds the CommentIntensitySeeker from the given uri. If an exception is
     * thrown it initializes to an empty map.
     * @param uri The uri to an XML file containing the scores. It's
     * body.
     * @param parser The parser to use to get the comments.
     */
    public CommentContentSeeker(final URI uri, final ScoreParser parser) {
        try {
            setScores(parser.parseCaught(uri));
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
            setScores(new HashMap<String, Integer>());
        }
    }
    
    /**
     * Gets the Default URI or throws an IllegalArgumentException if it is not
     * valid.
     * @param path The URI path.
     * @return The URI.
     */
    protected static URI getURI(final String path) {
        try {
            return CommentContentSeeker.class.getResource(path).toURI();
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("URI for " + path + " is not valid.");
        }
    }
    
    /**
     * Sets the scores map to the given value. It also builds the sortedEntries
     * so it can be easily used by the filter.
     * @param scores The new Scores.
     */
    public void setScores(final Map<String, Integer> scores) {
        this.scores = scores;
        final Set<Entry<String, Integer>> entrySet = scores.entrySet();
        sortedEntries = doTheSort(entrySet);
    }
    
    /**
     * Gets the scores map used by the scores.
     * @return The scores used.
     */
    public Map<String, Integer> getScores() {
        return scores;
    }

    /**
     * Checks if a string contains a word from the list above.
     *
     * @param content
     *            the content of a comment
     * @return true if the content contains a positive message
     */
    public int contentFilter(final String content) {
        String body = content.toLowerCase();
        for (Entry<String, Integer> entry : sortedEntries) {
            if (body.contains(entry.getKey())) {
                return entry.getValue();
            }
        }
        return findEmoticons(content);
    }
    
    /**
     * This method sorts the given Set to put the highest Integer first so
     * we can check the highest score first.
     * @param entries The Set to sort.
     * @return The sorted set.
     */
    protected TreeSet<Entry<String, Integer>> doTheSort(final Set<Entry<String, Integer>> entries) {
        final Comparator<Entry<String, Integer>> comp = new Comparator<Entry<String, Integer>>() {
            @Override
            public int compare(final Entry<String, Integer> e1,
                    final Entry<String, Integer> e2) {
                return e2.getValue() - e1.getValue();
            }
        };
        final TreeSet<Entry<String, Integer>> tree = new TreeSet<Entry<String, Integer>>(comp);
        tree.addAll(entries);
        return tree;
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
