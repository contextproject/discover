package score;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

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
 *  <li> The outermost part is {@code <scores>}</li>
 *  <li> Every score is then defined in between after this one.</li>
 *  <li> Every score is defined between {@code <score>}</li>
 *  <li> Every score has two subparts a {@code <string>} and {@code <value>}</li>
 * </ul>
 * </p>
 * 
 * @since 01-06-2015
 * @version 01-06-2015
 * 
 * @see Document
 * @see DocumentBuilderFactory
 * 
 * @author stefanboodt
 *
 */
public class XMLScoreParser {
    
    /**
     * The score nodes tagname.
     */
    private String scorenode = "score";
    
    /**
     * The tagname of the text.
     */
    private String string = "string";
    
    /**
     * The tagname of the points belonging to the text.
     */
    private String points = "value";

    /**
     * Parses the XML file pointed to by the given URI.
     * @param uri The URI of the file.
     * @return The scores that were contained in the file.
     * @throws IOException If the IO fails.
     * @throws SAXException If a parse error occurs.
     * @throws ParserConfigurationException If a DocumentBuilder cannot be created.
     * @throws IllegalArgumentException If the uri is null.
     */
    public Map<String, Integer> parse(final URI uri) throws SAXException,
        IOException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(uri.toString());
        doc.getDocumentElement().normalize();
        Map<String, Integer> scores;
        try {
            scores = getScores(doc.getElementsByTagName(scorenode));
        } catch (InvalidXMLFormatException e) {
            e.printStackTrace();
            scores = new HashMap<String, Integer>();
        }
        return scores;
    }
    
    /**
     * Gets the scores in the nodelist and parses them.
     * @param nodelist The list of scores to be used by the file.
     * @return The scores in the file.
     * @throws InvalidXMLFormatException If the XML file is not formatted well.
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
                final int textlength = text.getLength();
                if (textlength == 0) {
                    throw new InvalidXMLFormatException("There was a score with no strings.");
                }
                final NodeList pointsNode = scorenode.getElementsByTagName(points);
                if (pointsNode.getLength() != 1) {
                    throw new InvalidXMLFormatException("More than 1 score defined for some word"
                            + " in node " + scorenode.toString());
                }
                final int pts = Integer.parseInt(pointsNode.item(0).getTextContent().trim());
                scores.putAll(addScores(text, pts));
            }
        }
        return scores;
    }
    
    /**
     * Adds the scores to the scores map.
     * @param texts The text to add points to.
     * @param pts The points awarded to text.
     * @return The new scores map.
     * @throws InvalidXMLFormatException If the XML is not correctly formatted.
     */
    protected Map<String, Integer> addScores(final NodeList texts, final int pts)
            throws InvalidXMLFormatException {
        final Map<String, Integer> scores = new HashMap<String, Integer>();
        for (int k = 0; k < texts.getLength(); k++) {
            final String t = texts.item(k).getTextContent();
            final Integer oldpoints = scores.put(t, pts);
            if (oldpoints != null) {
                throw new InvalidXMLFormatException("Duplicate string " + t
                        + " found in the file.");
            }
        }
        return scores;
    }
    
    /**
     * This class warns about the invalidness of the XML file supplied to
     * the parser.
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
         * @param message The error message.
         */
        public InvalidXMLFormatException(final String message) {
            super(message);
        }
    }
}
