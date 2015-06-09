package models.record;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TrackEntryTest {

    TrackEntry trackEntryString;
    TrackEntry trackEntryInt;

    @Before
    public void setUp() {
        trackEntryString = new TrackEntry("key", "value");
        trackEntryInt = new TrackEntry("key", 0);
    }

    @Test
    public void testGetKey() throws Exception {
        assertEquals("key", trackEntryString.getKey());
        assertEquals("key", trackEntryInt.getKey());
    }

    @Test
    public void testGetValue() throws Exception {
        assertEquals("value", trackEntryString.getValue());
        assertEquals(0, trackEntryInt.getValue());
    }

    @Test
    public void testSetValue1() throws Exception {
        trackEntryString.setValue("new value");
        assertEquals("new value", trackEntryString.getValue());
    }

    @Test
    public void testSetValue2() throws Exception {
        trackEntryInt.setValue(1);
        assertEquals(1, trackEntryInt.getValue());
    }

    @Test
    public void testSetValue3() throws Exception {
        assertEquals("value", trackEntryString.setValue("newValue"));
    }
}