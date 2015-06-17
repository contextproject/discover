package models.score;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import models.score.XMLScoreParser.InvalidXMLFormatException;

import org.xml.sax.SAXException;

/**
 * This Interface contains methods to parse files that are used to store
 * scores. By creating this interface the Strategy pattern can be used to
 * parse the scores. For Strategy to have any effect you should be able to
 * specify the ScoreParser in each method instead of using (concrete) subclasses
 * of this class as parameters. This then allows for easy transitions between the
 * given implementations on how to parse.
 * 
 * @since 02-06-2015
 * @version 02-06-2015
 * 
 * @author stefan boodt
 *
 */
public interface ScoreParser {

    /**
     * Parses the XML file pointed to by the given URI.
     * 
     * @param uri
     *            The URI of the file.
     * @return The scores that were contained in the file.
     * @throws IOException
     *             If the IO fails.
     * @throws SAXException
     *             If a parse error occurs.
     * @throws ParserConfigurationException
     *             If a DocumentBuilder cannot be created.
     */
    Map<String, Integer> parseCaught(final URI uri) throws SAXException,
            IOException, ParserConfigurationException;

    /**
     * Parses the XML file pointed to by the given URI.
     * 
     * @param uri
     *            The URI of the file.
     * @return The scores that were contained in the file.
     * @throws IOException
     *             If the IO fails.
     * @throws SAXException
     *             If a parse error occurs.
     * @throws ParserConfigurationException
     *             If a DocumentBuilder cannot be created.
     * @throws InvalidXMLFormatException
     *             If the XML is incorrectly formatted.
     */
    Map<String, Integer> parse(final URI uri) throws SAXException,
            IOException, ParserConfigurationException,
            InvalidXMLFormatException;

}
