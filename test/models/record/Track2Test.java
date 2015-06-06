package models.record;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class Track2Test {

    private Track2 track;

    @Before
    public void setUp() {
        track = new Track2();
    }

    @Test
    public void testSize1() {
        assertEquals(0, track.size());
    }

    @Test
    public void testSize2() {
        for (int i = 0; i < 100; i++) {
            track.put(i, i);
        }
        assertEquals(100, track.size());
    }

    @Test
    public void testIsEmpty1() {
        assertTrue(track.isEmpty());
    }

    @Test
    public void testIsEmpty2() {
        track.put("key", "value");
        assertFalse(track.isEmpty());
    }

    @Test
    public void testContainsKey1() {
        assertFalse(track.containsKey("key"));
    }

    @Test
    public void testContainsKey2() {
        assertFalse(track.containsKey("key"));
        track.put("key", "value");
        assertTrue(track.containsKey("key"));
    }

    @Test
    public void testContainsValue1() {
        assertFalse(track.containsValue("value"));
    }

    @Test
    public void testContainsValue2() {
        assertFalse(track.containsValue("value"));
        track.put("key", "value");
        assertTrue(track.containsValue("value"));
    }

    @Test
    public void testGet1() {
        assertNull(track.get("key"));
    }

    @Test
    public void testGet2() {
        track.put("key", "value");
        assertNotNull(track.get("key"));
    }

    @Test
    public void testPut1() {
        assertNull(track.put("key", "value"));
    }

    @Test
    public void testPut2() {
        track.put("key", "old value");
        assertEquals("old value", track.put("key", "new value"));
    }

    @Test
    public void testPut3() {
        track.put("key", "value");
        assertNull(track.put("key", 0));
        assertEquals("value", track.get("key"));
        assertNotEquals(0, track.get("key"));
    }

    @Test
    public void testRemove1() {
        track.put("key", "value");
        assertEquals("value", track.remove("key"));
        assertFalse(track.containsKey("key"));
    }

    @Test
    public void testRemove2() {
        track.put("key", "value");
        assertNull(track.remove("wrong key"));
        assertTrue(track.containsKey("key"));
    }
}