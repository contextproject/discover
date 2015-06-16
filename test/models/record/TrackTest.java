package models.record;

import models.utility.TrackList;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TrackTest {

    private Track track, a, b;
    private Key<Integer> key1;
    private Key<Double> key2;
    private Key<String> key3;

    @Before
    public void setUp() {
        track = new Track();
        key1 = new Key<>("id", Integer.class);
        key2 = new Key<>("score", Double.class);
        key3 = new Key<>("genre", String.class);

        a = new Track();
        b = new Track();
        a.put(key1, 1);
        b.put(key1, 1);
    }

    @Test
    public void testSize1() {
        assertEquals(0, track.size());
    }

    @Test
    public void testSize2() {
        for (int i = 0; i < 100; i++) {
            track.put(new Key<>(Integer.toString(i), Integer.class), i);
        }
        assertEquals(100, track.size());
    }

    @Test
    public void testIsEmpty1() {
        assertTrue(track.isEmpty());
    }

    @Test
    public void testIsEmpty2() {
        track.put(key1, 1);
        assertFalse(track.isEmpty());
    }

    @Test
    public void testContainsKey1() {
        assertFalse(track.containsKey(key1));
    }

    @Test
    public void testContainsKey2() {
        assertFalse(track.containsKey(key1));
        track.put(key1, 1);
        assertTrue(track.containsKey(key1));
    }

    @Test
    public void testContainsValue1() {
        assertFalse(track.containsValue(1));
    }

    @Test
    public void testContainsValue2() {
        assertFalse(track.containsValue(1));
        track.put(key1, 1);
        assertTrue(track.containsValue(1));
    }

    @Test
    public void testGet1() {
        assertNull(track.get(key3));
    }

    @Test
    public void testGet2a() {
        track.put(key3, "value");
        assertNotNull(track.get(key3));
        assertEquals("value", track.get(key3));
    }

    @Test
    public void testGet2b() {
        track.put(key3, "value");
        Key<String> key = new Key<>("genre", String.class);
        assertNotNull(track.get(key));
        assertEquals("value", track.get(key));
    }

    @Test
    public void testGet3a() {
        track.put(key2, 1.0);
        assertNotNull(track.get(key2));
        assertEquals(1.0, track.get(key2), 0.0);
    }

    @Test
    public void testGet3b() {
        track.put(key2, 1.0);
        Key<Double> key = new Key<>("score", Double.class);
        assertNotNull(track.get(key));
        assertEquals(1.0, track.get(key), 0.0);
    }

    @Test
    public void testGet4a() {
        track.put(key1, 1);
        assertNotNull(track.get(key1));
        assertEquals(new Integer(1), track.get(new Key<>("id", Integer.class)));
    }

    @Test
    public void testGet4b() {
        track.put(key1, 1);
        Key<Integer> key = new Key<>("id", Integer.class);
        assertNotNull(track.get(key));
        assertEquals(new Integer(1), track.get(key));
    }

    @Test
    public void testPut1() {
        track.put(key3, "value");
        assertEquals("value", track.get(key3));
    }

    @Test
    public void testRemove1() {
        track.put(key3, "value");
        assertEquals("value", track.remove(key3));
        assertFalse(track.containsKey(key3));
    }

    @Test
    public void testRemove2() {
        track.put(key3, "value");
        Key wrongkey = new Key<>("wrong key", String.class);
        assertNull(track.remove(wrongkey));
        assertTrue(track.containsKey(key3));
    }

    @Test
    public void testCompareTo1() {
        Key<Double> score = new Key<>("score", Double.class);
        Track a = new Track();
        a.put(score, 1.0);
        Track b = new Track();
        b.put(score, 2.0);
        assertTrue(a.compareTo(a) == 0);
        assertTrue(a.compareTo(b) > 0);
        assertTrue(b.compareTo(a) < 0);
    }

    @Test
    public void testCompareTo2() {
        Key score = new Key<>("score", Double.class);
        TrackList trackList = new TrackList();
        Track a = new Track();
        a.put(score, 1.0);
        trackList.add(a);
        Track b = new Track();
        b.put(score, 2.0);
        trackList.add(b);
        Collections.sort(trackList);
        assertEquals(2.0, trackList.get(0).get(score));
    }

    @Test
    public void testPutAll() throws Exception {
        Track a = new Track();
        a.put(key1, 0);
        a.put(key2, 1.0);
        Track b = new Track();
        b.putAll(a);
        assertEquals(2, b.size());
        assertEquals((Integer) 0, b.get(key1));
        assertEquals((Double) 1.0, b.get(key2));
    }

    @Test
    public void testClear() throws Exception {
        track.put(key1, 0);
        assertEquals(1, track.size());
        track.clear();
        assertEquals(0, track.size());
    }

    @Test
    public void testEntrySet() throws Exception {
        track.put(key1, 0);
        Set<Map.Entry<Key<?>, Object>> entries = track.entrySet();
        assertEquals("id", entries.iterator().next().getKey().getIdentifier());
        assertEquals(0, entries.iterator().next().getValue());
    }

    @Test
    public void testEquals1() throws Exception {
        assertTrue(a.equals(a));
    }

    @Test
    public void testEquals2() throws Exception {
        assertTrue(a.equals(b));
        assertTrue(b.equals(a));
    }

    @Test
    public void testEquals3() throws Exception {
        assertFalse(a.equals(new Track()));
    }

    @Test
    public void testEquals4() throws Exception {
        assertFalse(a.equals(1));
    }

    @Test
    public void testEquals5() throws Exception {
        b.put(key1, 2);
        assertFalse(a.equals(b));
    }

    @Test
    public void testHashcode1() throws Exception {
        assertEquals(0, new Track().hashCode());
    }

    @Test
    public void testHashcode2() throws Exception {
        assertEquals(1, a.hashCode());
    }

    @Test
    public void testToString1() throws Exception {
        assertEquals("[]", new Track().toString());
    }

    @Test
    public void testToString2() throws Exception {
        assertEquals("[id = 1]", a.toString());
    }
}