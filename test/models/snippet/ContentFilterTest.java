package models.snippet;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;




import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the ContentFilter. It simply checks if content of a comment
 * is positive.
 */
public class ContentFilterTest {

	/**
	 * A contentfilter object.
	 */
	private ContentFilter cf;

	/**
	 * Setting up the class to test with.
	 */
	@Before
	public void initialize() {
		cf = new ContentFilter();
	}

	/**
	 * Test for simple content.
	 */
	@Test
	public void testContentFilter1() {
		String s1 = "great song!";
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
		assertTrue(0 == cf.findEmoticons(s1));
	}

	/**
	 * Test for simple content with a wrong spelled positive word and a
	 * emoticon.
	 */
	@Test
	public void testFindAll() {
		String s1 = "<3 this great song";
		assertTrue(1 == cf.contentFilter(s1));
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
		assertTrue(1 == cf.contentFilter(s1));
		assertTrue(1 == cf.contentFilter(s2));
		assertTrue(1 == cf.contentFilter(s3));
		assertTrue(1 == cf.contentFilter(s4));
		assertTrue(1 == cf.contentFilter(s5));
	}

}
