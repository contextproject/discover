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
        a = new Key<>("id", Integer.class);
        b = new Key<>("id", Integer.class);
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
        assertEquals(3355, a.hashCode());
    }

    @Test
    public void testGetIdentifier() throws Exception {
        assertEquals("id", a.getIdentifier());
    }

    @Test
    public void testGetType() throws Exception {
        assertEquals(Integer.class, a.getType());
    }
}