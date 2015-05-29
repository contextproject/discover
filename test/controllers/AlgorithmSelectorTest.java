package controllers;

import controllers.Application;
import models.database.DatabaseConnector;
import models.snippet.TimedSnippet;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

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
	 * Setting up the database connection.
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
		double ts = as.determineStart();

		assertTrue(0 != ts);
	}

	/**
	 * Testing if a song with high amount of comments does return zero.
	 */
	@Test
	public void selectorTest2() {
		// song with not enough comments
		AlgorithmSelector as = new AlgorithmSelector(1644691);
		double ts = as.determineStart();

		// must return zero
		assertTrue(0 == ts);
	}

}