package models.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/*import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.thenReturn;*/

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//import java.sql.ResultSet;

/**
 * This class tests the RandomSongSelector class. Due to database
 * constrains this will be highly difficult to achieve properly since a
 * lot of it's structure is implemented in SQL and requires a manual test
 * instead.
 * 
 * @since 13-05-2015
 * @version 14-05-2015
 * 
 * @see RandomSongSelector
 * 
 * @author stefanboodt
 *
 */
public class RandomSongSelectorTest {

	/**
	 * Selector under test.
	 */
	private RandomSongSelector sel;

	/**
	 * Does some set up.
	 * @throws Exception If the set up fails.
	 */
	@Before
	public void setUp() throws Exception {
		setRandomSongSelector(RandomSongSelector.getRandomSongSelector());
	}

	/**
	 * Does some clean up.
	 * @throws Exception If the clean up fails.
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * Sets the selector under test to the given value.
	 * @param tested The selector under test.
	 */
	public void setRandomSongSelector(RandomSongSelector tested) {
		sel = tested;
	}
	
	/**
	 * Returns the selector under test.
	 * @return The selector that is being tested.
	 */
	public RandomSongSelector getRandomSongSelector() {
		return sel;
	}

	/**
	 * Tests the {@link RandomSongSelector#getRandomSongSelector()} method
	 * returns two instances that satisfy the equals method.
	 * For subclasses this test should be overwritten.
	 */
	@Test
	public void testGetSelector() {
		assertEquals(getRandomSongSelector(),
				RandomSongSelector.getRandomSongSelector());
	}
	
	/**
	 * Tests the {@link RandomSongSelector#getRandomSongSelector()} method
	 * returns two instances of the same address. For subclasses this test
	 * should be overwritten.
	 */
	@Test
	public void testGetSelectorAddressCheck() {
		assertTrue(getRandomSongSelector() ==
				RandomSongSelector.getRandomSongSelector());
	}
	
	/**
	 * Tests if the random song selector returns a null value.
	 * Since the song is selected randomly there is no telling on what
	 * the result would be. Therefore a test against null is not entirely
	 * pointless.
	 */
	@Test
	public void testGetRandomSong() {
		//assertNotNull(getRandomSongSelector().getRandomSong());
	}
	
	/**
	 * Tests if the random song selector returns a null value.
	 * Since the song is selected randomly there is no telling on what
	 * the result would be. Therefore a test against null is not entirely
	 * pointless.
	 */
	@Test
	public void testGetRandomSongWithConnector() {
		/*final int expected = 1234182; // Random number.
		final DatabaseConnector dbc = mock(DatabaseConnector.class);
		final ResultSet set = mock(ResultSet.class);
		when(set).getInt("track_id").thenReturn(expected)
		when(dbc).executeQuery(sel.getQuery).thenReturn(set);
		assertEquals(expected,
			getRandomSongSelector().getRandomSong(dbc));*/
	}
	
	/**
	 * Tests if the random song selector returns a null value.
	 * Since the song is selected randomly there is no telling on what
	 * the result would be. Therefore a test against null is not entirely
	 * pointless.
	 */
	@Test
	public void testGetRandomTrackId() {
		//assertNotNull(RandomSongSelector.getRandomSongTrackId());
	}
	
	/**
	 * Tests the {@link RandomSongSelector#getQuery()} method and
	 * checks if it is a select query.
	 */
	@Test
	public void testGetQueryContainsSelect() {
		final String select = "SELECT";
		assertTrue(sel.getQuery().toUpperCase().startsWith(select));
		// The uppercase is because of the SQL syntax which is not picky
		// on capitalization.
	}
	
	/**
	 * Tests the {@link RandomSongSelector#getQuery()} method
	 * on consistency.
	 */
	@Test
	public void testGetQueryConsistent() {
		assertEquals(sel.getQuery(), sel.getQuery());
	}
	
	/**
	 * Tests the {@link RandomSongSelector#getQuery()} method.
	 */
	@Test
	public void testGetQueryNotEmpty() {
		assertNotEquals("", sel.getQuery());
	}
	
	/**
	 * Tests the {@link RandomSongSelector#getQuery()} method.
	 */
	@Test
	public void testGetQueryNotNull() {
		assertNotNull("", sel.getQuery());
	}
}
