package models.snippet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import basic.BasicTest;

/**
 * Tests the Timed Snippet class.
 *
 * @author stefan boodt
 * @version 30-05-2015
 * @see TimedSnippet
 * @since 29-04-2015
 */
public class TimedSnippetTest extends BasicTest {

    /**
     * Snippet under test.
     */
    private TimedSnippet snippet;

    /**
     * Does some setup for the test.
     *
     * @throws Exception
     *             If the setup fails.
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        setSnippet(new TimedSnippet(5000));
    }

    /**
     * Tests if the the same snippet has the same hashcode.
     */
    @Test
    public void testEqualsHashCodeSameAddress() {
        assertEquals(snippet.hashCode(), snippet.hashCode());
    }

    /**
     * Returns the snippet under test.
     * 
     * @return The snippet under test.
     */
    public TimedSnippet getSnippet() {
        return snippet;
    }

    /**
     * Sets the snippet under test.
     * 
     * @param snippet
     *            The snippet under test.
     */
    public void setSnippet(final TimedSnippet snippet) {
        setObjectUnderTest(snippet);
        this.snippet = snippet;
    }

    /**
     * Tests if the a snippet with the same values has the same hashcode. It
     * uses copy to copy the snippet.
     */
    @Test
    public void testEqualsHashCodeSameValues() {
        assertEquals(snippet.hashCode(), snippet.copy().hashCode());
    }

    /**
     * Tests if the a snippet has the same address as the copy.
     */
    @Test
    public void testAddressEqualsCopy() {
        assertFalse(snippet == snippet.copy());
    }

    /**
     * Tests if the a snippet without duration has the default duration.
     */
    @Test
    public void testGetDuration() {
        assertEquals(TimedSnippet.getDefaultDuration(), snippet.getWindow());
    }

    /**
     * Tests if the a snippet with duration has the default duration.
     */
    @Test
    public void testGetDurationAgain() {
        final int d = 100;
        final int st = 1000;
        final TimedSnippet s = new TimedSnippet(st, d);
        assertEquals(d, s.getWindow());
    }

    /**
     * Tests if the {@link TimedSnippet#getDefaultDuration()} method.
     */
    @Test
    public void testGetDefaultDuration() {
        final int expected = 30000;
        assertEquals(expected, TimedSnippet.getDefaultDuration());
    }

    /**
     * Tests if the {@link TimedSnippet#getDefaultDuration()} and
     * {@link TimedSnippet#setDefaultDuration(int)} method.
     */
    @Test
    public void testGetAndSetDefaultDuration() {
        final int duration = TimedSnippet.getDefaultDuration();
        final int expected = 10000;
        TimedSnippet.setDefaultDuration(expected);
        assertEquals(expected, TimedSnippet.getDefaultDuration());
        TimedSnippet.setDefaultDuration(duration);
    }

    /**
     * Tests if the {@link TimedSnippet#getDefaultDuration()} and
     * {@link TimedSnippet#setDefaultDuration(int)} method.
     */
    @Test
    public void testGetAndSetDefaultDurationNegativeNumber() {
        final int duration = TimedSnippet.getDefaultDuration();
        final int v = -1100;
        TimedSnippet.setDefaultDuration(v);
        assertEquals(duration, TimedSnippet.getDefaultDuration());
        TimedSnippet.setDefaultDuration(duration);
    }

    /**
     * Tests if the a snippet without duration has the default duration.
     */
    @Test
    public void testCreateSnippet() {
        final int d = 100;
        final int st = 1000;
        final TimedSnippet s = new TimedSnippet(st, d);
        assertEquals(d, s.getWindow());
    }

    /**
     * Tests the {@link TimedSnippet#getStartTime()}.
     */
    @Test
    public void testGetStarttime() {
        final int expected = 5000;
        assertEquals(expected, snippet.getStartTime());
    }

    /**
     * Tests the {@link TimedSnippet#getStartTime()}. This is an on bounds test
     * case.
     */
    @Test
    public void testGetStarttimeZerp() {
        final int newtime = 0;
        final TimedSnippet s = new TimedSnippet(newtime);
        final int expected = 0;
        assertEquals(expected, s.getStartTime());
    }

    /**
     * Tests the {@link TimedSnippet#getStartTime()}.
     */
    @Test
    public void testGetStarttimeNegative() {
        final int newtime = -100;
        final TimedSnippet s = new TimedSnippet(newtime);
        final int expected = 0;
        assertEquals(expected, s.getStartTime());
    }

    /**
     * Tests the {@link TimedSnippet#getWindow()}.
     */
    @Test
    public void testGetDurationZero() {
        final int newtime = 0;
        final int duration = 0;
        final TimedSnippet s = new TimedSnippet(newtime, duration);
        final int expected = TimedSnippet.getDefaultDuration();
        assertEquals(expected, s.getWindow());
    }

    /**
     * Tests the {@link TimedSnippet#getWindow()}.
     */
    @Test
    public void testGetDurationNegative() {
        final int newtime = 0;
        final int duration = -1;
        final TimedSnippet s = TimedSnippet.createSnippet(newtime, duration);
        final int expected = TimedSnippet.getDefaultDuration();
        assertEquals(expected, s.getWindow());
    }

    /**
     * Tests the {@link TimedSnippet#equals(Object)} method and the
     * {@link TimedSnippet#copy()} method.
     */
    @Test
    public void testEqualsAndCopy() {
        assertEquals(snippet, snippet.copy());
    }

    /**
     * Tests the {@link TimedSnippet#equals(Object)} method.
     */
    @Test
    public void testEquals() {
        final TimedSnippet copy = new TimedSnippet(snippet.getStartTime(),
                snippet.getWindow());
        assertEquals(snippet, copy);
    }

    /**
     * Tests the {@link TimedSnippet#equals(Object)} method.
     */
    @Test
    public void testEqualsDifferentWindow() {
        final TimedSnippet copy = new TimedSnippet(snippet.getStartTime(),
                snippet.getWindow() + 1);
        assertNotEquals(snippet, copy);
    }

    /**
     * Tests the {@link TimedSnippet#equals(Object)} method.
     */
    @Test
    public void testEqualsDifferentStarttime() {
        final TimedSnippet copy = new TimedSnippet(snippet.getStartTime() + 1,
                snippet.getWindow());
        assertNotEquals(snippet, copy);
    }

    /**
     * Tests the {@link TimedSnippet#equals(Object)} method.
     */
    @Test
    public void testEqualsBothWindow() {
        final TimedSnippet copy = new TimedSnippet(snippet.getStartTime() - 1,
                snippet.getWindow() + 1);
        assertNotEquals(snippet, copy);
    }

    /**
     * Tests the {@link TimedSnippet#equals(Object)} method.
     */
    @Test
    public void testEqualsAndGetter() {
        assertEquals(snippet, getSnippet().copy());
    }

    /**
     * Tests the {@link TimedSnippet#toString()} method.
     */
    @Test
    public void testToString() {
        final String expected = "TimedSnippet(" + getSnippet().getStartTime()
                + ", " + getSnippet().getWindow() + ")";
        assertEquals(expected, getSnippet().toString());
    }
}
