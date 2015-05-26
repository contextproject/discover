package models.database.retriever;

import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;

import static org.junit.Assert.*;


public class TrackRetrieverTest {

    Retriever retriever;

    @Before
    public void startUp() {
        retriever = new TrackRetriever(100005416);
    }

    @Test
    public void testGetAll() throws Exception {
        ResultSet resultSet = retriever.retrieve();
        assertEquals(100005416, resultSet.getInt("track_id"));
    }
}