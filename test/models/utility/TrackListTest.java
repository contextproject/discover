package models.utility;

import models.database.DatabaseConnector;
import models.record.Key;
import models.record.Track;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Tests for the TrackList class.
 */
public class TrackListTest {

    TrackList a, b;
    Track track1, track2, track3;
    Key id, score;

    /**
     * Set up.
     */
    @Before
    public void setUp() {
        DatabaseConnector.makeConnection("jdbc:mysql://188.166.78.36/contextbase", "context", "password");
        a = new TrackList();
        b = new TrackList();
        track1 = new Track();
        track2 = new Track();
        track3 = new Track();
        id = new Key<>("id", Integer.class);
        score = new Key<>("score", Double.class);
        track1.put(id, 1);
        track2.put(id, 1);
        track3.put(id, 1);
        track2.put(score, 10.0);
        track3.put(score, 5.0);
    }

    /**
     * Test for the constructor.
     */
    @Test
    public void testTrackList() {
        assertNotNull(new TrackList());
    }

    /**
     * Test for the constructor.
     */
    @Test
    public void testTrackList2() {
        TrackList trackList = new TrackList(DatabaseConnector.executeQuery("SELECT * FROM tracks LIMIT 1"));
        assertNotNull(trackList);
        assertEquals(1, trackList.size());
    }

    @Test
    public void testAddDistinctAll1() {
        a.add(track1);
        b.add(track1);
        a.addDistinctAll(b);
        assertEquals(1, a.size());
    }

    @Test
    public void testAddDistinctAll2() {
        a.add(track1);
        b.add(track2);
        a.addDistinctAll(b);
        assertEquals(1, a.size());
        assertEquals(10.0, a.get(0).get(score));
    }

    @Test
    public void testAddDistinctAll3() {
        a.add(track2);
        b.add(track3);
        a.addDistinctAll(b);
        assertEquals(1, a.size());
        assertEquals(15.0, a.get(0).get(score));
    }
}