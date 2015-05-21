package models.snippet;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;



/**
 * Tests for the AlgortihmSelector.
 *
 */
public class AlgorithmSelectorTest {

	/**
	 * Testing if a song with high amount of comments doesn't return zero.
	 */
	@Test
	public void selectorTest1() {
		// song with a lot of comments
		AlgorithmSelector as = new AlgorithmSelector(114419538);
		int start = as.getStartTime();

		assertNotEquals(0, start);
	}

	/**
	 * Testing if a song with high amount of comments does return zero.
	 */
	@Test
	public void selectorTest2() {
		// song with not enough comments
		AlgorithmSelector as = new AlgorithmSelector(1644691);
		int start = as.getStartTime();

		// must return zero
		assertEquals(0, start);
	}

}
