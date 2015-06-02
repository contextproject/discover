package models.score;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class parses the Scores in an XML file that is given to it and is
 * written in a way that complies to the supplied conventions.
 *
 * <p>
 * These conventions comply of the following things:
 * <ul>
 * <li>The outermost part is {@code <scores>}</li>
 * <li>Every score is then defined in between after this one.</li>
 * <li>Every score is defined between {@code <score>}</li>
 * <li>Every score has two subparts a {@code <string>} and {@code <value>}</li>
 * </ul>
 * </p>
 * 
 * <p>
 * This class is now a Singleton because that makes life so much easier and is
 * easier on memory. See the {@link #createParser()} method for the method of creating a
 * XMLScoreParser.
 * </p>
 * 
 * @since 01-06-2015
 * @version 02-06-2015
 * 
 * @see Document
 * @see DocumentBuilderFactory
 * 
 * @author stefan boodt
 *
 */
public final class XMLScoreParser implements ScoreParser {
    
    /**
     * The parser to be used.
     */
    private static XMLScoreParser parser;

    /**
     * The score nodes tagname.
     */
    private final String scorenode = "score";

    /**
     * The tagname of the text.
     */
    private final String string = "string";

    /**
     * The tagname of the points belonging to the text.
     */
    private final String points = "value";
    
    /**
     * Creates an XMLScoreParser.
     */
    private XMLScoreParser() {
        
    }

    @Override
    public Map<String, Integer> parseCaught(final URI uri) throws SAXException,
            IOException, ParserConfigurationException {
        Map<String, Integer> scores;
        try {
            scores = parse(uri);
        } catch (InvalidXMLFormatException e) {
            e.printStackTrace();
            scores = new HashMap<String, Integer>();
        }
        return scores;
    }

    @Override
    public Map<String, Integer> parse(final URI uri) throws SAXException,
            IOException, ParserConfigurationException,
            InvalidXMLFormatException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(uri.toString());
        doc.getDocumentElement().normalize();
        return getScores(doc.getElementsByTagName(scorenode));
    }

    /**
     * Gets the scores in the nodelist and parses them.
     * 
     * @param nodelist
     *            The list of scores to be used by the file.
     * @return The scores in the file.
     * @throws InvalidXMLFormatException
     *             If the XML file is not formatted well.
     */
    protected Map<String, Integer> getScores(final NodeList nodelist)
            throws InvalidXMLFormatException {
        final int nodesnum = nodelist.getLength();
        Map<String, Integer> scores = new HashMap<String, Integer>();
        for (int i = 0; i < nodesnum; i++) {
            Node node = nodelist.item(i);

            // Is always true but we check to be certain.
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element scorenode = (Element) node;
                final NodeList text = scorenode.getElementsByTagName(string);
                final NodeList pointsNode = scorenode
                        .getElementsByTagName(points);
                if (text.getLength() == 0) {
                    throw new InvalidXMLFormatException(
                            "There was a score with no strings.");
                } else if (pointsNode.getLength() != 1) {
                    throw new InvalidXMLFormatException(
                            "More than 1 score defined for some word"
                                    + " in node " + scorenode.toString());
                }
                final int pts = Integer.parseInt(pointsNode.item(0)
                        .getTextContent().trim());
                final Map<String, Integer> newscores = addScores(text, pts);
                final String duplicate = getDouble(scores, newscores);
                if (duplicate != null) {
                    throw new InvalidXMLFormatException("Duplicate string "
                            + duplicate + " found in the file.");
                }
                scores.putAll(newscores);
            }
        }
        return scores;
    }

    /**
     * Gets a double from the given lists. Checks if newones contains a key that
     * is already in checked.
     * 
     * @param checked
     *            The old list.
     * @param newones
     *            The new list.
     * @return The duplicate string or {@code null} if none is found.
     */
    protected String getDouble(final Map<String, Integer> checked,
            final Map<String, Integer> newones) {
        final Set<String> olds = checked.keySet();
        final Set<String> newStrings = newones.keySet();
        for (String s : newStrings) {
            if (olds.contains(s.toLowerCase())) {
                return s;
            }
        }
        return null;
    }

    /**
     * Adds the scores to the scores map.
     * 
     * @param texts
     *            The text to add points to.
     * @param pts
     *            The points awarded to text.
     * @return The new scores map.
     * @throws InvalidXMLFormatException
     *             If the XML is not correctly formatted.
     */
    protected Map<String, Integer> addScores(final NodeList texts, final int pts)
            throws InvalidXMLFormatException {
        final Map<String, Integer> scores = new HashMap<String, Integer>();
        for (int k = 0; k < texts.getLength(); k++) {
            final String t = texts.item(k).getTextContent().trim().toLowerCase();
            final Integer oldpoints = scores.put(t, pts);
            if (oldpoints != null) {
                throw new InvalidXMLFormatException("Duplicate string " + t
                        + " found in the file.");
            }
        }
        return scores;
    }
    
    /**
     * Creates a XMLScoreParser. If one already exists it returns that one
     * instead of creating a new one. This enforces the Singleton design pattern.
     * Due to the fact that all the XMLScoreParsers are equivalent and equal this method
     * of creation has no backfires but saves a lot of memory if a lot of parsers are created.
     * @return An XMLScoreParser you can use.
     */
    public static XMLScoreParser createParser() {
        if (parser == null) {
            parser = new XMLScoreParser();
        }
        return parser;
    }

    /**
     * This class warns about the invalidness of the XML file supplied to the
     * parser.
     * 
     * @since 01-06-2015
     * @version 01-06-2015
     * 
     * @see Exception
     * @see XMLScoreParser
     * 
     * @author stefan boodt
     */
    public static class InvalidXMLFormatException extends Exception {

        /**
         * The serial number of the exception.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Creates a new InvalidXML Format Exception.
         */
        public InvalidXMLFormatException() {
            super();
        }

        /**
         * Creates a new XML Format Exception with the given message.
         * 
         * @param message
         *            The error message.
         */
        public InvalidXMLFormatException(final String message) {
            super(message);
        }
    }
}
