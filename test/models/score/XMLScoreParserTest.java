package score;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import basic.BasicTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import score.XMLScoreParser.InvalidXMLFormatException;

/**
 * This class tests the XMLScoreParser class.
 * 
 * @since 01-06-2015
 * @version 01-06=2015
 * 
 * @see XMLScoreParser
 * 
 * @author stefanboodt
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
        setParser(new XMLScoreParser());
    }
    
    /**
     * Sets the parser under test.
     * @param parser The new parser under test.
     */
    protected void setParser(final XMLScoreParser parser) {
        setObjectUnderTest(parser);
        this.parser = parser;
    }
    
    /**
     * Returns the XML parser.
     * @return The parser of the xml scores under test.
     */
    protected XMLScoreParser getParser() {
        return parser;
    }

    /**
     * Tests the {@link XMLScoreParser#addScores(Map, org.w3c.dom.NodeList, int)}
     * method.
     * @throws InvalidXMLFormatException If the XML is wrongly formatted.
     */
    @Test
    public void testAddScoresDifferentNodes() throws InvalidXMLFormatException {
        Map<String, Integer> expected = new HashMap<String, Integer>();
        expected.put("ok", 20);
        expected.put("drop", 100);
        Map<String, Integer> created = new HashMap<String, Integer>();
        ArrayNodeList l1 = new ArrayNodeList<Node>();
        Node mocknode1 = mock(Node.class);
        doReturn("ok").when(mocknode1).getTextContent();
        ArrayNodeList l2 = new ArrayNodeList<Node>();
        Node mocknode2 = mock(Node.class);
        doReturn("drop").when(mocknode2).getTextContent();
        l1.add(mocknode1);
        l2.add(mocknode2);
        created.putAll(getParser().addScores(l1, 20));
        created.putAll(getParser().addScores(l2, 100));
        assertEquals(expected, created);
    }

    /**
     * Tests the {@link XMLScoreParser#addScores(Map, org.w3c.dom.NodeList, int)}
     * method.
     * @throws InvalidXMLFormatException If the XML is wrongly formatted.
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
     * Tests the {@link XMLScoreParser#addScores(Map, org.w3c.dom.NodeList, int)}
     * method.
     * @throws InvalidXMLFormatException If the XML is wrongly formatted.
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
     * Tests the {@link XMLScoreParser#addScores(Map, org.w3c.dom.NodeList, int)}
     * method.
     * @throws InvalidXMLFormatException If the XML is wrongly formatted.
     */
    @Test
    public void testAddScoresNoText() throws InvalidXMLFormatException {
        NodeList l2 = new ArrayNodeList<Node>();
        assertEquals(new HashMap<String, Integer>(), getParser().addScores(l2, 10));
    }
    
    /**
     * Combination of nodelist and ArrayList to allow easy testing.
     * 
     * <p>
     * Yes, we know about Mockito but the getLength method was being impossible when
     * mocking because it threw WrongTypeOfReturnValue since it cannot return {@code int} as
     * a return value since it does not extend {@code Object}. So it bridged to {@code Integer}
     * which in turn was not an int. So that error was thrown.
     * </p>
     * 
     * @since 01-06-2015
     * @version 01-06-2015
     * 
     * @author stefan boodt
     *
     * @param <Node> The parameter of the list.
     */
    protected static class ArrayNodeList<N extends Node> extends ArrayList<Node>
        implements NodeList {

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
         * @param nodes The nodes in the list.
         */
        public ArrayNodeList(final Node ...nodes) {
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
