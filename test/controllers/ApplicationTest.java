package controllers;

import java.util.Collections;
import java.util.HashMap;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.database.DatabaseConnector;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import play.api.mvc.RequestHeader;
import play.mvc.Http;
import play.mvc.Http.RequestBody;
import play.mvc.Result;
import play.mvc.Results.Status;
import play.test.FakeRequest;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static play.mvc.Http.Status.OK;
import static play.mvc.Http.Status.BAD_REQUEST;
import static play.mvc.Results.ok;
import static play.test.Helpers.callAction;
import static play.test.Helpers.GET;
import static play.test.Helpers.POST;
import static play.test.Helpers.status;

/**
 * Tests the Application class.
 * 
 * @since 18-06-2015
 * @version 18-06-2015
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
     * Tests the splitwaveform method in the application class.
     */
    @Test
    public void testSplitWaveform() {
        /*final Http.Request mockRequest = mock(Http.Request.class);
        final HashMap<String, String> empty = new HashMap<>();
        RequestHeader header = mock(play.api.mvc.RequestHeader.class);
        final Http.Context context = new Http.Context(2L, header,
                mockRequest, empty, empty, Collections.EMPTY_MAP);
        final RequestBody mockBody = mock(RequestBody.class);
        doReturn(null).when(mockBody).asJson();
        doReturn(mockBody).when(mockRequest).body();
        final Http.Context old = Http.Context.current();
        Http.Context.current.set(context);
        final int status = status(Application.splitWaveform());
        assertEquals(BAD_REQUEST, status);
        Http.Context.current.set(old);*/
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
     * Tests the trackrequest method.
     */
    @Test
    public void testTrackRequest() {
        /*ObjectNode json = new ObjectMapper().createObjectNode();
        ObjectNode trackstuff = new ObjectMapper().createObjectNode();
        trackstuff.put("id", 1000214);
        trackstuff.put("duration", 10000);
        json.put("response", trackstuff);
        Result result = callAction(
                routes.ref.Application.trackRequest(),
                new FakeRequest().withJsonBody(json)
        );
        final int status = status(Application.index());
        assertEquals(OK, status);*/
    }
}
