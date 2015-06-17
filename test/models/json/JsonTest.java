package models.json;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import models.record.Track;
import models.snippet.TimedSnippet;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Unit test class for the java class Json.
 */
public class JsonTest {

    ObjectNode json1, json2, jsonlist;

    JsonNode wave;

    @Before
    public void setUp() throws Exception {
        json1 = new ObjectMapper().createObjectNode();
        jsonlist = new ObjectMapper().createObjectNode();

        ObjectNode user = new ObjectMapper().createObjectNode();
        json1.put("id", 1);
        json1.put("duration", 420);
        json1.put("user", user.put("username", "Bob"));
        json1.put("title", "title");
        json1.put("genre", "Rap");
        json1.put("user_id", 123);
        json1.put("uri", "uri");

        jsonlist.put("a", json1);
        jsonlist.put("b", json1);

        Map<String, List<Integer>> map = new TreeMap<String, List<Integer>>();
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        map.put("response", list);
        wave = new ObjectMapper().createArrayNode().add(1);
    }

    /**
     * Test for the method getTrack.
     */
    @Test
    public void testGetTrack() {
        assertEquals((int) Json.getTrack(json1).get(Track.id), 1);
    }

    /**
     * Test for the method getTrack.
     */
    @Test
    public void testGetTrackNull() {
        assertEquals(Json.getTrack(null), null);
    }

    /**
     * Test for the method getTrackList.
     */
    @Test
    public void testGetTrackList() {
        assertEquals(Json.getTrackList(jsonlist).size(), 2);
    }

    /**
     * Test for the method getTrackList.
     */
    @Test
    public void testGetTrackListNull() {
        assertEquals(Json.getTrackList(null), null);
    }

    /**
     * Test for the method getStartTime.
     */
    @Test
    public void testGetStartTime() {
        assertTrue(Json.getStartTime(Json.getTrack(json1)) instanceof TimedSnippet);
    }

    /**
     * Test for the method response.
     */
    @Test
    public void testResponse() {
        assertTrue(Json.response(Json.getTrack(json1)) != null);
    }

    /**
     * Test for the method response.
     */
    @Test
    public void testResponseNull() {
        assertTrue(Json.response(Json.getTrack(null)) != null);
    }

    /**
     * Test for the method getWaveform.
     */
    @Test
    public void testGetWave() {
        assertTrue(Json.getWaveform(wave) != null);
    }

}
