package controllers;

import java.util.List;

import models.database.DatabaseConnector;
import models.database.RandomSongSelector;
import models.mix.MixSplitter;
import models.database.retriever.TrackRetriever;
import models.record.Track;
import models.seeker.CommentIntensitySeeker;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * This class is used to control the model and the view parts of the MVC. It
 * renders the view with the values processed by the model classes.
 */
public class Application extends Controller {

    /**
     * The database object of the controller.
     */
    private static DatabaseConnector databaseConnector;

    /**
     * The ObjectMapper used to create JsonNode objects.
     */
    private static ObjectMapper mapper;

    /**
     * The index method is called when the application is started and
     * no other messages have been passed.
     *
     * @return an http ok response with the rendered page.
     */
    public static Result index() {
        String url = "w.soundcloud.com/tracks/67016624";
        return ok(index.render(url, getStartTime(67016624)));
    }

    /**
     * Renders a page with the given track id to load the widget.
     *
     * @param trackId the id of the track we are searching for.
     * @return an http ok response with the new rendered page.
     */
    public static Result getSong(final String trackId) {
        JsonNode json = request().body().asJson();
        if (json == null) {
            String url = "w.soundcloud.com/tracks/" + trackId;
            int starttime = getStartTime(Integer.parseInt(trackId));
            return ok(index.render(url, starttime));
        } else {
            ObjectMapper mapper = new ObjectMapper();
            ObjectNode objNode = mapper.createObjectNode();
            int starttime = getStartTime(Integer.parseInt(trackId));
            JsonNode response = objNode.put("response", starttime);
            return ok(response);
        }

    }

    /**
     * Method is used to receive Json objects containing track information, 
     * pass it on the AlgorithmChooser and return back the new start time.
     *
     * @return An http ok response with the new rendered page.
     */
    public static Result TrackRequest() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest("Object is empty");
        } else {
            ObjectNode objNode = mapper.createObjectNode();
            int trackID = json.get("track").get("id").asInt();
            int duration = json.get("track").get("duration").asInt();
            Track track = new Track();
            track.setTrackid(trackID);
            track.setDuration(duration);
            int starttime2 = getStartTime(track);
            JsonNode response = objNode.put("response", starttime2);
            return ok(response);
        }
    }

    /**
     * Retrieves a start-time calculated by the CommentIntensitySeeker for the
     * given track id.
     *
     * @param trackId The id of the track.
     * @return the start-time of the snippet.
     */
    public static int getStartTime(final int trackId) {
        return new CommentIntensitySeeker(
                new TrackRetriever(trackId).getAll()).seek().getStartTime();
    }

    /**
     * Get the start time calculated by the AlgorithmSelector.
     *
     * @param track The track
     * @return The start time of the snippet.
     */
    public static int getStartTime(final Track track) {
        return AlgorithmSelector.determineStart(track);
    }

    /**
     * Selects a random track from the database.
     *
     * @return an http ok response with a random track id.
     */
    public static Result getRandomSong() {
        RandomSongSelector selector;
        selector = RandomSongSelector.getRandomSongSelector();
        int trackId = selector.getRandomSong();
        String url = "w.soundcloud.com/tracks/" + trackId;
        int starttime = getStartTime(trackId);
        return ok(index.render(url, starttime));
    }

    /**
     * Method used to pass a JsonNode object with track waveform
     * on to the MixSplitter class.
     *
     * @return ok response with the start times for the mix.
     */
    public static Result splitWaveform() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest("Expecting Json data");
        } else {
            //TO-DO: send the iterator to the MixSplitter.
            MixSplitter splitter = new MixSplitter(json.get("waveform"), json.get("track").get("id").asInt());
            //TO-DO: receive the answer from the splitter and send array of start times.
            List<Integer> list = splitter.split();
//            System.out.println(list.size());
//            System.out.println(list.toString());
            
            ObjectNode objNode = mapper.createObjectNode();
            JsonNode response = objNode.put("response", list.toString());
            return ok(response);
        }
    }

    /**
     * Method used to pass a Json object with track meta-data.
     * This will be used in the future to insert non-existing tracks.
     *
     * @return ok response with a
     */
    public static Result trackMetadata() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest("Expecting Json data");
        } else {
            ObjectNode objNode = mapper.createObjectNode();
            JsonNode response = objNode.put("message", "File was transvered successfully");
            return ok(response);
        }
    }

    /**
     * Setter for the ObjectMapper object.
     *
     * @param om the new ObjectMapper object.
     */
    public static void setObjectMapper(final ObjectMapper om) {
        mapper = om;
    }

    /**
     * Getter for the ObjectMapper Object of the controller.
     *
     * @return the ObjectMapper Object.
     */
    public static ObjectMapper getObjectMapper() {
        return mapper;
    }

    /**
     * Setter for the DatabaseConnector object.
     *
     * @param dbc , the new DatabaseConnector object.
     */
    public static void setDatabaseConnector(final DatabaseConnector dbc) {
        databaseConnector = dbc;
    }

    /**
     * Getter for the DatabaseConnecter Object of the controller.
     *
     * @return the DatabaseConnecter Object.
     */
    public static DatabaseConnector getDatabaseConnector() {
        return databaseConnector;
    }

}
