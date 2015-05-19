package models.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
/*import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;*/

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controllers.Application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * This class tests the RandomSongSelector class. Due to database
 * constrains this will be highly difficult to achieve properly since a
 * lot of it's structure is implemented in SQL and requires a manual test
 * instead.
 * 
 * @since 13-05-2015
 * @version 19-05-2015
 * 
 * @see RandomSongSelector
 * 
 * @author stefan boodt
 *
 */
public class RandomSongSelectorTest {

	/**
	 * Selector under test.
	 */
	private RandomSongSelector sel;
	
	/**
     * DatabaseConnector object to the database
     */
    private static DatabaseConnector databaseConnector;
	
    /**
     * Does some static set up for the class. Such as database connections.
     * @throws Exception If the set up fails.
     */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		databaseConnector = new DatabaseConnector();
        databaseConnector.loadDrivers();
        databaseConnector.makeConnection("jdbc:mysql://188.166.78.36/contextbase", "context", "password");
        Application.setDatabaseConnector(databaseConnector);
	}
	
	/**
     * Does some static clean up for the class. Such as database connections.
     * @throws Exception If the clean up fails.
     */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		databaseConnector.closeConnection();
	}

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
		assertNotNull(getRandomSongSelector().getRandomSong());
	}
	
	/**
	 * Tests if the random song selector returns a the expected
	 * value. Since the connection is mocked the result is verifiable.
	 * @throws SQLException If the request on the mocked object fails.
	 */
	/* @Test
	public void testGetRandomSongWithMockedConnector()
			throws SQLException {
		final int expected = 1234182; // Random number.
		final DatabaseConnector dbc = mock(DatabaseConnector.class);
		final ResultSet set = mock(ResultSet.class);
		dbc.setStatement(mock(Statement.class));
		when(set.getInt("track_id")).thenReturn(expected);
		doReturn(set).when(dbc.executeQuery(sel.getQuery()));
		assertEquals(expected,
			getRandomSongSelector().getRandomSong(dbc));
	}/* Removed because the mocking is not working.
	DatabaseConnector is to final.*/
	
	/**
	 * Tests if the random song selector returns a null value.
	 * Since the song is selected randomly there is no telling on what
	 * the result would be. Therefore a test against null is not entirely
	 * pointless.
	 */
	@Test
	public void testGetRandomSongWithConnector() {
		assertNotNull(getRandomSongSelector().getRandomSong(databaseConnector));
	}
	
	/**
	 * Tests if the random song selector returns a null value.
	 * Since the song is selected randomly there is no telling on what
	 * the result would be. Therefore a test against null is not entirely
	 * pointless.
	 */
	@Test
	public void testGetRandomTrackId() {
		assertNotNull(RandomSongSelector.getRandomSongTrackId());
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
