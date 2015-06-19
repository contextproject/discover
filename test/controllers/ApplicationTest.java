package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.database.DatabaseConnector;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import play.mvc.Result;
import play.test.FakeRequest;

import static org.junit.Assert.assertEquals;

import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.test.Helpers.callAction;
import static play.test.Helpers.GET;
import static play.test.Helpers.status;

/**
 * Tests the Application class.
 * 
 * @since 18-06-2015
 * @version 19-06-2015
 * 
 * @see Application
 * 
 * @author stefan boodt
 *
 */
public class ApplicationTest {

    /**
     * Does some set up before the class.
     * @throws Exception If the set up fails.
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        DatabaseConnector databaseConnector = DatabaseConnector.getConnector();
        databaseConnector.loadDrivers();
        databaseConnector.makeConnection("jdbc:mysql://188.166.78.36/contextbase",
                "context", "password");
    }

    /**
     * Does some clean up before the class.
     *
     * @throws Exception If the clean up fails.
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        DatabaseConnector.getConnector().closeConnection();
    }

    /**
     * Creates a JSON element.
     * @return The JSON node.
     */
    protected JsonNode getJSON() {
        ObjectNode json = new ObjectMapper().createObjectNode();
        ObjectNode trackstuff = new ObjectMapper().createObjectNode();
        ObjectNode usermapper = new ObjectMapper().createObjectNode();
        usermapper.put("username", 402104238);
        trackstuff.put("id", 1000214);
        trackstuff.put("duration", 10000);
        trackstuff.put("user_id", 4002123);
        trackstuff.put("title", "The super cool song.");
        trackstuff.put("genre", "cool");
        trackstuff.put("uri", "Super cool URI");
        trackstuff.put("user", usermapper);
        json.put("track", trackstuff);
        json.put("waveform", "[0.4, 0.1, 0.2]");
        json.put("splits", "0");
        return json;
    }

    /**
     * Tests the splitwaveform method in the application class.
     */
    @Test
    public void testSplitWaveformNull() {
        ObjectNode json = null;
        final int status = status(Application.splitWaveform(json));
        assertEquals(BAD_REQUEST, status);
    }

    /**
     * Tests the splitwaveform method in the application class.
     */
    @Test
    public void testSplitWaveformJSON() {
        JsonNode json = getJSON();
        final int status = status(Application.splitWaveform(json));
        assertEquals(OK, status);
    }

    /**
     * Tests the split waveform method.
     */
    @Test
    public void testSplitWaveformRoutes() {
        final JsonNode json = getJSON();
        final Result result = callAction(
                routes.ref.Application.splitWaveform(),
                new FakeRequest().withJsonBody(json)
        );
        final int status = status(result);
        assertEquals(OK, status);
    }

    /**
     * Tests the index method of the application.
     */
    @Test
    public void testIndex() {
        Result result = callAction(
                routes.ref.Application.index(),
                new FakeRequest(GET, "/")
        );
        final int status = status(Application.index());
        assertEquals(OK, status);
    }
    
    /**
     * Tests the get random song method.
     */
    @Test
    public void testGetRandomSong() {
        assertEquals(OK, status(Application.getRandomSong()));
    }

    /**
     * Tests the trackrequest method.
     */
    @Test
    public void testTrackRequest() {
        final JsonNode json = getJSON();
        final Result result = callAction(
                routes.ref.Application.trackRequest(),
                new FakeRequest().withJsonBody(json)
        );
        final int status = status(result);
        assertEquals(OK, status);
    }

    /**
     * Tests the track metadata method.
     */
    @Test
    public void testTrackMetadataRoutes() {
        final JsonNode json = getJSON();
        final Result result = callAction(
                routes.ref.Application.trackMetadata(),
                new FakeRequest().withJsonBody(json)
        );
        final int status = status(result);
        assertEquals(OK, status);
    }

    /**
     * Tests the track metadata method.
     */
    @Test
    public void testTrackMetadata() {
        final JsonNode json = getJSON();
        final Result result = Application.trackMetadata(json);
        final int status = status(result);
        assertEquals(OK, status);
    }

    /**
     * Tests the track metadata method.
     */
    @Test
    public void testTrackMetadataNull() {
        final JsonNode json = null;
        final Result result = Application.trackMetadata(json);
        final int status = status(result);
        assertEquals(BAD_REQUEST, status);
    }

    /**
     * Tests the set preview mode method.
     */
    @Test
    public void testSetPreviewMode() {
        final ObjectNode json = new ObjectMapper().createObjectNode();
        json.put("mode", "RANDOM");
        final Result result = callAction(
                routes.ref.Application.setPreviewMode(),
                new FakeRequest().withJsonBody(json)
        );
        final int status = status(result);
        assertEquals(OK, status);
    }

    /**
     * Tests the set preview mode method.
     */
    @Test
    public void testSetPreviewModeInvalidJSON() {
        final JsonNode json = getJSON();
        final Result result = callAction(
                routes.ref.Application.setPreviewMode(),
                new FakeRequest().withJsonBody(json)
        );
        final int status = status(result);
        assertEquals(BAD_REQUEST, status);
    }
}
