package models.database.retriever;

import models.record.Track;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TrackRetrieverTest {

    @Test
    public void testRetrieve() throws Exception {
        Track track = new TrackRetriever(100005416).retrieve();
        assertEquals(100005416, track.getTrackID());
    }

    @Test
    public void testGetAll() throws Exception {
        Track track = new TrackRetriever(100005416).getAll();
        assertEquals(100005416, track.getTrackID());
    }

    @Test
    public void testGetTrackID() throws Exception {
        assertEquals(100005416, new TrackRetriever(100005416).getTrackID());
    }
}