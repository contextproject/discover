package models.record;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for the Key class.
 */
public class KeyTest {

    private Key a, b, c, d;

    @Before
    public void setUp() {
        a = new Key<>("ID", Integer.class);
        b = new Key<>("ID", Integer.class);
        c = new Key<>("something else", Integer.class);
        d = new Key<>("something else", String.class);
    }

    @Test
    public void testKey() throws Exception {
        assertNotNull(a);
    }

    @Test
    public void testEquals() throws Exception {
        assertTrue(a.equals(a));
        assertTrue(a.equals(b));
        assertFalse(a.equals(c));
        assertFalse(c.equals(d));
        assertFalse(a.equals(new Integer(1)));
    }

    @Test
    public void testHashCode() throws Exception {
        assertEquals(2331, a.hashCode());
    }

    @Test
    public void testGetIdentifier() throws Exception {
        assertEquals("ID", a.getIdentifier());
    }

    @Test
    public void testGetType() throws Exception {
        assertEquals(Integer.class, a.getType());
    }
}