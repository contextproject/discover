package models.snippet;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the TimedSnippetFactory class.
 * 
 * @since 09-06-2015
 * @version 09-06-2015
 * 
 * @see TimedSnippet
 * @see TimedSnippetFactory
 * 
 * @author stefan boodt
 *
 */
public class TimedSnippetFactoryTest {

    /**
     * Does some set up.
     * @throws Exception If the set up fails.
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * Does some clean up.
     * @throws Exception If the clean up fails.
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Tests the {@link TimedSnippetFactory#createSnippet(int)} method.
     */
    @Test
    public void testCreateSnippetInt() {
        final TimedSnippet expected = new TimedSnippet(20, TimedSnippet.getDefaultDuration());
        assertEquals(expected, TimedSnippetFactory.createSnippet(20));
    }

    /**
     * Tests the {@link TimedSnippetFactory#createSnippet(int, int)} method.
     */
    @Test
    public void testCreateSnippetTwoInt() {
        final TimedSnippet expected = new TimedSnippet(20, 40);
        assertEquals(expected, TimedSnippetFactory.createSnippet(20, 40));
    }

    /**
     * Tests the {@link TimedSnippetFactory#createSnippetWindowInSeconds(int, int)} method.
     */
    @Test
    public void testCreateSnippetDurationSeconds() {
        final TimedSnippet expected = new TimedSnippet(20, 40000);
        assertEquals(expected, TimedSnippetFactory.createSnippetWindowInSeconds(20, 40));
    }

}
