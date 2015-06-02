package models.seeker;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * This class tests the ContentFilter. It simply checks if content of a comment
 * is positive.
 * 
 * @since 27-06-2015
 * @version 02-06-2015
 */
public class CommentContentSeekerTest {

    /**
     * A contentfilter object.
     */
    private CommentContentSeeker cf;

    /**
     * Setting up the class to test with.
     * 
     * @throws Exception
     *             If the setUp fails.
     */
    @Before
    public void setUp() throws Exception {
        final URL url = CommentContentSeekerTest.class
                .getResource("CommentContentSeekerTestScores.xml");
        final URI uri = url.toURI();
        cf = new CommentContentSeeker(uri);
    }

    /**
     * Test for simple content.
     */
    @Test
    public void testContentFilter1() {
        System.out.println("The map is " + cf.getScores());
        String s1 = "great song!";
        System.out.println(s1 + " gave score " + cf.contentFilter(s1));
        assertTrue(1 == cf.contentFilter(s1));
        assertTrue(0 == cf.findEmoticons(s1));
    }

    /**
     * Test for simple content with capitals.
     */
    @Test
    public void testContentFilter2() {
        String s1 = "GREAT SONG!";
        assertTrue(1 == cf.contentFilter(s1));
        assertTrue(0 == cf.findEmoticons(s1));
    }

    /**
     * Test for simple content without a positive word.
     */
    @Test
    public void testContentFilter3() {
        String s1 = "this sucks";
        assertTrue(-1 == cf.contentFilter(s1));
        assertTrue(0 == cf.findEmoticons(s1));
    }

    /**
     * Test for simple content with a wrong spelled positive word.
     */
    @Test
    public void testContentFilter4() {
        String s1 = "this is gooddddd";
        assertTrue(1 == cf.contentFilter(s1));
        assertTrue(0 == cf.findEmoticons(s1));
    }

    /**
     * Test for simple content with a negative message .
     */
    @Test
    public void testContentFilter5() {
        String s1 = "I don't like this ";
        assertTrue(-1 == cf.contentFilter(s1));
    }

    /**
     * Test for simple content with a negative message .
     */
    @Test
    public void testContentFilter5Emoticons() {
        String s1 = "I don't like this ";
        assertTrue(0 == cf.findEmoticons(s1));
    }

    /**
     * Test for simple content with a wrong spelled positive word and a
     * emoticon.
     */
    @Test
    public void testFindAllContentFilter() {
        String s1 = "<3 this great song";
        assertTrue(1 == cf.contentFilter(s1));
    }

    /**
     * Test for simple content with a wrong spelled positive word and a
     * emoticon.
     */
    @Test
    public void testFindAllEmoticons() {
        String s1 = "<3 this great song";
        assertTrue(1 == cf.findEmoticons(s1));
    }

    /**
     * Testing all possible positive emoticons.
     */
    @Test
    public void testFindEmoticons() {
        String s1 = "<3";
        String s2 = ":)";
        String s3 = ":-)";
        String s4 = "=D";
        String s5 = ":D";
        String s6 = "Nope";
        assertTrue(1 == cf.contentFilter(s1));
        assertTrue(1 == cf.contentFilter(s2));
        assertTrue(1 == cf.contentFilter(s3));
        assertTrue(1 == cf.contentFilter(s4));
        assertTrue(1 == cf.contentFilter(s5));
        assertTrue(0 == cf.contentFilter(s6));
    }

    /**
     * Tests the {@link CommentContentSeeker#doTheSort(java.util.Set)} method.
     */
    @Test
    public void testDoTheSortEmpty() {
        final TreeSet<Entry<String, Integer>> expected = new TreeSet<>();
        assertEquals(expected,
                cf.doTheSort(new HashMap<String, Integer>().entrySet()));
    }
    
    /**
     * Tests if the default constructor succeeds.
     */
    @Test
    public void testDefaultConstructor() {
        cf = new CommentContentSeeker();
        assertNotNull(cf.getScores());
    }

    /**
     * Tests the {@link CommentContentSeeker#doTheSort(java.util.Set)} method.
     */
    @Test
    public void testDoTheSort() {
        final List<String> expected = new ArrayList<String>();
        expected.add("hi");
        expected.add("boo");
        expected.add("triade");
        final HashMap<String, Integer> map = new HashMap<>();
        map.put("hi", 100);
        map.put("triade", 20);
        map.put("boo", 40);
        final Set<Entry<String, Integer>> sorted = cf.doTheSort(map.entrySet());
        assertEquals(expected.size(), sorted.size());
        int i = 0;
        for (Entry<String, Integer> e : sorted) {
            assertEquals(expected.get(i), e.getKey());
            i++;
        }
    }
}
