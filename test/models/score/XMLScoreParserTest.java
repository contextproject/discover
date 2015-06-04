package models.score;

import basic.BasicTest;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

import static models.score.XMLScoreParser.InvalidXMLFormatException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * This class tests the XMLScoreParser class.
 * 
 * @since 01-06-2015
 * @version 02-06=2015
 * 
 * @see XMLScoreParser
 * 
 * @author stefan boodt
 *
 */
public class XMLScoreParserTest extends BasicTest {

    /**
     * The XML score parser.
     */
    private XMLScoreParser parser;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        setParser(XMLScoreParser.createParser());
    }

    /**
     * Sets the parser under test.
     * 
     * @param parser
     *            The new parser under test.
     */
    protected void setParser(final XMLScoreParser parser) {
        setObjectUnderTest(parser);
        this.parser = parser;
    }

    /**
     * Returns the XML parser.
     * 
     * @return The parser of the xml scores under test.
     */
    protected XMLScoreParser getParser() {
        return parser;
    }

    /**
     * Tests the
     * {@link XMLScoreParser#addScores(Map, org.w3c.dom.NodeList, int)} method.
     * 
     * @throws InvalidXMLFormatException
     *             If the XML is wrongly formatted.
     */
    @Test
    public void testAddScoresDifferentNodes() throws InvalidXMLFormatException {
        Map<String, Integer> expected = new HashMap<String, Integer>();
        expected.put("ok", 20);
        expected.put("drop", 100);
        Map<String, Integer> created = new HashMap<String, Integer>();
        ArrayNodeList<Node> l1 = new ArrayNodeList<Node>();
        Node mocknode1 = mock(Node.class);
        doReturn("ok").when(mocknode1).getTextContent();
        ArrayNodeList<Node> l2 = new ArrayNodeList<Node>();
        Node mocknode2 = mock(Node.class);
        doReturn("drop").when(mocknode2).getTextContent();
        l1.add(mocknode1);
        l2.add(mocknode2);
        created.putAll(getParser().addScores(l1, 20));
        created.putAll(getParser().addScores(l2, 100));
        assertEquals(expected, created);
    }

    /**
     * Tests the
     * {@link XMLScoreParser#addScores(Map, org.w3c.dom.NodeList, int)} method.
     * 
     * @throws InvalidXMLFormatException
     *             If the XML is wrongly formatted.
     */
    @Test
    public void testAddScores() throws InvalidXMLFormatException {
        Map<String, Integer> expected = new HashMap<String, Integer>();
        expected.put("drop", 100);
        ArrayNodeList<Node> l2 = new ArrayNodeList<Node>();
        Node mocknode2 = mock(Node.class);
        doReturn("drop").when(mocknode2).getTextContent();
        l2.add(mocknode2);
        assertEquals(expected, getParser().addScores(l2, 100));
    }

    /**
     * Tests the
     * {@link XMLScoreParser#addScores(Map, org.w3c.dom.NodeList, int)} method.
     * 
     * @throws InvalidXMLFormatException
     *             If the XML is wrongly formatted.
     */
    @Test
    public void testAddScoresSameNode() throws InvalidXMLFormatException {
        Map<String, Integer> expected = new HashMap<String, Integer>();
        expected.put("drop", 100);
        expected.put("superb", 100);
        expected.put("magic", 100);
        Node mocknode2 = mock(Node.class);
        doReturn("drop").when(mocknode2).getTextContent();
        Node mocknode1 = mock(Node.class);
        doReturn("superb").when(mocknode1).getTextContent();
        Node mocknode3 = mock(Node.class);
        doReturn("magic").when(mocknode3).getTextContent();
        NodeList l2 = new ArrayNodeList<Node>(mocknode1, mocknode2, mocknode3);
        assertEquals(expected, getParser().addScores(l2, 100));
    }

    /**
     * Tests the
     * {@link XMLScoreParser#addScores(Map, org.w3c.dom.NodeList, int)} method.
     * 
     * @throws InvalidXMLFormatException
     *             If the XML is wrongly formatted.
     */
    @Test(expected = InvalidXMLFormatException.class)
    public void testAddScoresDuplicate() throws InvalidXMLFormatException {
        Node n = mock(Node.class);
        doReturn("drop").when(n).getTextContent();
        NodeList l2 = new ArrayNodeList<Node>(n, n);
        getParser().addScores(l2, 100);
    }

    /**
     * Tests the
     * {@link XMLScoreParser#addScores(Map, org.w3c.dom.NodeList, int)} method.
     * 
     * @throws InvalidXMLFormatException
     *             If the XML is wrongly formatted.
     */
    @Test
    public void testAddScoresNoText() throws InvalidXMLFormatException {
        NodeList l2 = new ArrayNodeList<Node>();
        assertEquals(new HashMap<String, Integer>(),
                getParser().addScores(l2, 10));
    }

    /**
     * Tests the {@link XMLScoreParser#parseCaught(URI)} method.
     * 
     * @throws IOException
     *             if an IOException occurs.
     * @throws InvalidXMLFormatException
     *             If the XML is not formatted properly.
     * @throws ParserConfigurationException
     *             If the document builder can't be created.
     * @throws SAXException
     *             If a parse error occurs.
     * @throws URISyntaxException
     *             If the URI is wrongly formatted.
     */
    @Test
    public void testParseScoresCaught() throws IOException,
            InvalidXMLFormatException, ParserConfigurationException,
            SAXException, URISyntaxException {
        final URI uri = XMLScoreParserTest.class.getResource("scores1.xml")
                .toURI();
        Map<String, Integer> scores = getParser().parseCaught(uri);
        Map<String, Integer> expected = new HashMap<String, Integer>();
        expected.put("yay!", 20);
        expected.put("hi", 10);
        expected.put("good", 10);
        expected.entrySet();
        assertEquals(expected, scores);
    }

    /**
     * Tests the {@link XMLScoreParser#parse(URI)} method.
     * 
     * @throws IOException
     *             if an IOException occurs.
     * @throws InvalidXMLFormatException
     *             If the XML is not formatted properly.
     * @throws ParserConfigurationException
     *             If the document builder can't be created.
     * @throws SAXException
     *             If a parse error occurs.
     * @throws URISyntaxException
     *             If the URI is wrongly formatted.
     */
    @Test
    public void testParseScores() throws IOException,
            InvalidXMLFormatException, ParserConfigurationException,
            SAXException, URISyntaxException {
        final URI uri = XMLScoreParserTest.class.getResource("scores1.xml")
                .toURI();
        Map<String, Integer> scores = getParser().parse(uri);
        Map<String, Integer> expected = new HashMap<String, Integer>();
        expected.put("yay!", 20);
        expected.put("hi", 10);
        expected.put("good", 10);
        assertEquals(expected, scores);
    }

    /**
     * Tests the {@link XMLScoreParser#parse(URI)} method.
     * 
     * @throws IOException
     *             if an IOException occurs.
     * @throws InvalidXMLFormatException
     *             If the XML is not formatted properly.
     * @throws ParserConfigurationException
     *             If the document builder can't be created.
     * @throws SAXException
     *             If a parse error occurs.
     * @throws URISyntaxException
     *             If the URI is wrongly formatted.
     */
    @Test(expected = InvalidXMLFormatException.class)
    public void testParseScoresDuplicateWord() throws IOException,
            InvalidXMLFormatException, ParserConfigurationException,
            SAXException, URISyntaxException {
        final URI uri = XMLScoreParserTest.class.getResource(
                "scoresDuplicateWords.xml").toURI();
        getParser().parse(uri);
    }

    /**
     * Tests the {@link XMLScoreParser#parse(URI)} method.
     * 
     * @throws IOException
     *             if an IOException occurs.
     * @throws InvalidXMLFormatException
     *             If the XML is not formatted properly.
     * @throws ParserConfigurationException
     *             If the document builder can't be created.
     * @throws SAXException
     *             If a parse error occurs.
     * @throws URISyntaxException
     *             If the URI is wrongly formatted.
     */
    @Test(expected = InvalidXMLFormatException.class)
    public void testParseScoresDoublePoints() throws IOException,
            InvalidXMLFormatException, ParserConfigurationException,
            SAXException, URISyntaxException {
        final URI uri = XMLScoreParserTest.class.getResource(
                "scoresDoublePoints.xml").toURI();
        getParser().parse(uri);
    }

    /**
     * Tests the {@link XMLScoreParser#parse(URI)} method.
     * 
     * @throws IOException
     *             if an IOException occurs.
     * @throws InvalidXMLFormatException
     *             If the XML is not formatted properly.
     * @throws ParserConfigurationException
     *             If the document builder can't be created.
     * @throws SAXException
     *             If a parse error occurs.
     * @throws URISyntaxException
     *             If the URI is wrongly formatted.
     */
    @Test(expected = InvalidXMLFormatException.class)
    public void testParseScoresNoPoints() throws IOException,
            InvalidXMLFormatException, ParserConfigurationException,
            SAXException, URISyntaxException {
        final URI uri = XMLScoreParserTest.class.getResource(
                "scoresNoPoints.xml").toURI();
        getParser().parse(uri);
    }

    /**
     * Tests the {@link XMLScoreParser#parse(URI)} method.
     * 
     * @throws IOException
     *             if an IOException occurs.
     * @throws InvalidXMLFormatException
     *             If the XML is not formatted properly.
     * @throws ParserConfigurationException
     *             If the document builder can't be created.
     * @throws SAXException
     *             If a parse error occurs.
     * @throws URISyntaxException
     *             If the URI is wrongly formatted.
     */
    @Test(expected = InvalidXMLFormatException.class)
    public void testParseScoresNoWords() throws IOException,
            InvalidXMLFormatException, ParserConfigurationException,
            SAXException, URISyntaxException {
        final URI uri = XMLScoreParserTest.class.getResource(
                "scoresNoWords.xml").toURI();
        getParser().parse(uri);
    }

    /**
     * Tests the {@link XMLScoreParser#parse(URI)} method.
     * 
     * @throws IOException
     *             if an IOException occurs.
     * @throws InvalidXMLFormatException
     *             If the XML is not formatted properly.
     * @throws ParserConfigurationException
     *             If the document builder can't be created.
     * @throws SAXException
     *             If a parse error occurs.
     * @throws URISyntaxException
     *             If the URI is wrongly formatted.
     */
    @Test
    public void testParseScoresWithOddSpaces() throws IOException,
            InvalidXMLFormatException, ParserConfigurationException,
            SAXException, URISyntaxException {
        final URI uri = XMLScoreParserTest.class.getResource(
                "scoresWithSpaces.xml").toURI();
        Map<String, Integer> scores = getParser().parse(uri);
        Map<String, Integer> expected = new HashMap<String, Integer>();
        expected.put("yay!", 20);
        expected.put("hi", 10);
        expected.put("good", 10);
        expected.put("drop", 100);
        assertEquals(expected, scores);
    }

    /**
     * Tests the {@link XMLScoreParser#parse(URI)} method.
     * 
     * @throws IOException
     *             if an IOException occurs.
     * @throws InvalidXMLFormatException
     *             If the XML is not formatted properly.
     * @throws ParserConfigurationException
     *             If the document builder can't be created.
     * @throws SAXException
     *             If a parse error occurs.
     * @throws URISyntaxException
     *             If the URI is wrongly formatted.
     */
    @Test(expected = InvalidXMLFormatException.class)
    public void testParseScores2() throws IOException,
            InvalidXMLFormatException, ParserConfigurationException,
            SAXException, URISyntaxException {
        final URI uri = XMLScoreParserTest.class.getResource("scores2.xml")
                .toURI();
        getParser().parse(uri);
    }

    /**
     * Tests the {@link XMLScoreParser#parse(URI)} method.
     * 
     * @throws IOException
     *             if an IOException occurs.
     * @throws InvalidXMLFormatException
     *             If the XML is not formatted properly.
     * @throws ParserConfigurationException
     *             If the document builder can't be created.
     * @throws SAXException
     *             If a parse error occurs.
     * @throws URISyntaxException
     *             If the URI is wrongly formatted.
     */
    @Test(expected = InvalidXMLFormatException.class)
    public void testParseScoresPointsString() throws IOException,
            InvalidXMLFormatException, ParserConfigurationException,
            SAXException, URISyntaxException {
        final URI uri = XMLScoreParserTest.class.getResource("scoresPointsString.xml")
                .toURI();
        getParser().parse(uri);
    }

    /**
     * Tests the {@link XMLScoreParser#getDouble(Map, Map)} method.
     */
    @Test
    public void testGetDouble() {
        final Map<String, Integer> m1 = new HashMap<String, Integer>();
        final Map<String, Integer> m2 = new HashMap<String, Integer>();
        final String k1 = "hi";
        final String k2 = "ppop";
        final String k3 = "poop";
        final String k4 = "boo";
        final String k5 = "key";
        m1.put(k1, 1);
        m1.put(k2, 2);
        m1.put(k3, 3);
        m2.put(k4, 4);
        m2.put(k5, 5);
        assertNull(getParser().getDouble(m1, m2));
    }

    /**
     * Tests the {@link XMLScoreParser#getDouble(Map, Map)} method.
     */
    @Test
    public void testGetDoubleWithDoubles() {
        final Map<String, Integer> m1 = new HashMap<String, Integer>();
        final Map<String, Integer> m2 = new HashMap<String, Integer>();
        final String k1 = "hi";
        final String k2 = "ppop";
        final String k3 = "poop";
        final String k4 = "boo";
        final String k5 = "key";
        m1.put(k1, 1);
        m1.put(k2, 2);
        m1.put(k3, 3);
        m2.put(k4, 4);
        m2.put(k3, 5);
        m2.put(k5, 5);
        assertEquals(k3, getParser().getDouble(m1, m2));
    }

    /**
     * Tests the {@link XMLScoreParser#parseCaught(URI)} method.
     * 
     * @throws IOException
     *             if an IOException occurs.
     * @throws InvalidXMLFormatException
     *             If the XML is not formatted properly.
     * @throws ParserConfigurationException
     *             If the document builder can't be created.
     * @throws SAXException
     *             If a parse error occurs.
     * @throws URISyntaxException
     *             If the URI is wrongly formatted.
     */
    @Test
    public void testParseScoresCaughtWithCapitals() throws IOException,
            InvalidXMLFormatException, ParserConfigurationException,
            SAXException, URISyntaxException {
        final URI uri = XMLScoreParserTest.class.getResource("scoresWithCapitals.xml")
                .toURI();
        Map<String, Integer> scores = getParser().parseCaught(uri);
        Map<String, Integer> expected = new HashMap<String, Integer>();
        expected.put("yay!", 20);
        expected.put("hi", 10);
        expected.put("good", 10);
        expected.entrySet();
        assertEquals(expected, scores);
    }

    /**
     * Tests the {@link XMLScoreParser#parseCaught(URI)} method.
     * 
     * @throws IOException
     *             if an IOException occurs.
     * @throws ParserConfigurationException
     *             If the document builder can't be created.
     * @throws SAXException
     *             If a parse error occurs.
     * @throws URISyntaxException
     *             If the URI is wrongly formatted.
     */
    @Test
    public void testParseCaughtScoresNoWords() throws IOException,
            ParserConfigurationException, SAXException, URISyntaxException {
        Map<String, Integer> expected = new HashMap<String, Integer>();
        final Map<String, Integer> result;
        final PrintStream err = System.err;
        final File dest = new File("PurposeFull error.txt");
        System.setErr(new PrintStream(dest));
        try {
            final URI uri = XMLScoreParserTest.class.getResource(
                    "scoresNoWords.xml").toURI();
            result = getParser().parseCaught(uri);
            
        } catch (IOException | ParserConfigurationException
                | SAXException | URISyntaxException e) {
            throw e;
        } finally {
            System.setErr(err);
        }
        assertEquals(expected, result);
        assertTrue("The error file does not exist.", dest.exists());
        assertTrue(dest.delete());
    }
    
    /**
     * Checks if the two parsers have the same address. This then
     * requires that the Singleton is used properly.
     */
    @Test
    public void testSingleton() {
        final XMLScoreParser parser2 = XMLScoreParser.createParser();
        assertTrue(parser == parser2);
    }

    /**
     * Combination of nodelist and ArrayList to allow easy testing.
     * 
     * <p>
     * We know about Mockito but the getLength method was being impossible when
     * mocking because it threw WrongTypeOfReturnValue since it cannot return
     * {@code int} as a return value since it does not extend {@code Object}. So
     * it bridged to {@code Integer} which in turn was not an int. So that error
     * was thrown.
     * </p>
     * 
     * @since 01-06-2015
     * @version 01-06-2015
     * 
     * @author stefan boodt
     *
     * @param <Node>
     *            The parameter of the list.
     */
    protected static class ArrayNodeList<N extends Node> extends
            ArrayList<Node> implements NodeList {

        /**
         * The serial number.
         */
        private static final long serialVersionUID = 1L;

        /**
         * Creates a new NodeArrayList.
         */
        public ArrayNodeList() {
            super();
        }

        /**
         * Creates a ArrayNodeList from the given nodes.
         * 
         * @param nodes
         *            The nodes in the list.
         */
        public ArrayNodeList(final Node... nodes) {
            super(nodes.length);
            for (Node n : nodes) {
                this.add(n);
            }
        }

        @Override
        public int getLength() {
            return size();
        }

        @Override
        public Node item(final int index) {
            return get(index);
        }
    }
}
