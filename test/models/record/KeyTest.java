package models.record;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test for the Key class.
 */
public class KeyTest {

    @Test
    public void testEquals() throws Exception {
        Key a = new Key<>("id", Integer.class);
        Key b = new Key<>("id", Integer.class);
        assertTrue(a.equals(b));

    }

    @Test
    public void testHashCode() throws Exception {

    }

    @Test
    public void testGetIdentifier() throws Exception {

    }

    @Test
    public void testGetType() throws Exception {

    }
}