package models.snippet;

import controllers.AlgorithmSelector;
import controllers.Application;
import models.database.DatabaseConnector;

import org.junit.Before;
import org.junit.Test;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests for the AlgortihmSelector.
 *
 */
public class AlgorithmSelectorTest {

	/**
	 * Needed for the setup.
	 */
	private DatabaseConnector databaseConnector;
	
	/**
	 * Setup up the connection to the database.
	 */
	@Before
	public void setUp() {
		databaseConnector = new DatabaseConnector();
		databaseConnector.loadDrivers();
		databaseConnector
				  .makeConnection("jdbc:mysql://188.166.78.36/contextbase",
						"context", "password");
		Application.setDatabaseConnector(databaseConnector);
	}

	/**
	 * Testing if a song with high amount of comments doesn't return zero.
	 */
	@Test
	public void selectorTest1() {
		// song with a lot of comments
		AlgorithmSelector as = new AlgorithmSelector(114419538);
		TimedSnippet ts = as.getSnippet();

		assertNotEquals(0, ts.getStartTime());
	}

	/**
	 * Testing if a song with high amount of comments does return zero.
	 */
	@Test
	public void selectorTest2() {
		// song with not enough comments
		AlgorithmSelector as = new AlgorithmSelector(1644691);
		TimedSnippet ts = as.getSnippet();

		// must return zero
		assertEquals(0, ts.getStartTime());
	}

}
