package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.database.DatabaseConnector;
import models.database.RandomSongSelector;
import models.mix.MixSplitter;
import models.record.Track;
import models.seeker.MixSeeker;
import models.snippet.TimedSnippet;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class is used to control the model and the view parts of the MVC. It
 * renders the view with the values processed by the model classes.
 */
public final class Application extends Controller {

    /**
     * The database object of the controller.
     */
    private static DatabaseConnector databaseConnector;

    /**
     * The ObjectMapper used to create JsonNode objects.
     */
    private static ObjectMapper mapper;

    /**
     * The index method is called when the application is started and no other
     * messages have been passed.
     *
     * @return an http ok response with the rendered page.
     */
    public static Result index() {
        String url = "w.soundcloud.com/tracks/56772597";
        Track track = new Track(56772597, 5054685);
        return ok(index.render(url, getStartTime(track)));
    }

    /**
     * Method is used to receive Json objects containing track information, pass
     * it on the AlgorithmChooser and return back the new start time.
     *
     * @return An http ok response with the new rendered page.
     */
    public static Result trackRequest() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest("Object is empty");
        } else if (json.get("track") == null) {
            return badRequest("Object does not contain a 'track' subset.");
        } else {
            int id = json.get("track").get("id").asInt();
            int duration = json.get("track").get("duration").asInt();
            Track track = new Track(id, duration);
            int starttime2 = getStartTime(track);
            ObjectNode objNode = mapper.createObjectNode();
            JsonNode response = objNode.put("response", starttime2);
            return ok(response);
        }
    }

    /**
     * Receives a Json object containing information about a track that the user
     * has liked. This information is passed to the recommender for processing.
     *
     * @return an http ok response.
     */
    public static Result userLike() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest("Object is empty");
        } else {
            System.out
                    .println("Information about a Liked song has been received.");
            return ok("");
        }
    }

    /**
     * Receives a Json object containing information about a track that the user
     * has disliked. This information is passed to the recommender for
     * processing.
     *
     * @return an http ok response.
     */
    public static Result userDislike() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest("Object is empty");
        } else {
            System.out
                    .println("Information about a disliked song has been received.");
            return ok("");
        }
    }

    /**
     * Selects a random track from the database.
     *
     * @return an http ok response with a random track id.
     */
    public static Result getRandomSong() {
        RandomSongSelector selector = RandomSongSelector.getRandomSongSelector();
        int trackId = selector.getRandomSong();
        String widgetUrl = "w.soundcloud.com/tracks/" + trackId;
        int starttime = getStartTime(trackId);
        Map<String, String> map = new TreeMap<String, String>();
        map.put("url", widgetUrl);
        map.put("start", Integer.toString(starttime));
        JsonNode response = mapper.valueToTree(map);
        return ok(response);
    }

    /**
     * Method used to pass a JsonNode object with track waveform on to the
     * MixSplitter class.
     *
     * @return ok response with the start times for the mix.
     */
    public static Result splitWaveform() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest("Expecting Json data");
        } else {
            int trackID = json.get("track").get("id").asInt();
            final Track track = new Track(trackID, 300000);
            MixSplitter splitter = new MixSplitter(json.get("waveform"), track);
            List<Integer> splits = splitter.split();
            List<Integer> starttimes = getStartTimes(splits, track);
            Map<String, List<Integer>> map = new TreeMap<String, List<Integer>>();
            map.put("response", starttimes);
            JsonNode response = mapper.valueToTree(map);
            return ok(response);
        }
    }
    
    /**
     * Retrieves the starttimes of the pieces of the given song.
     * @param splits The start of all the pieces.
     * @param track The track to search.
     * @return The starttimes of the snippets.
     */
    protected static List<Integer> getStartTimes(final List<Integer> splits, final Track track) {
        MixSeeker ms = new MixSeeker(splits, track);
        // Half of the timedsnippet default duration for mixsnippets.
        final List<TimedSnippet> snippets = ms.getSnippets(TimedSnippet.getDefaultDuration() / 2);
        return getStartTimes(snippets);
    }

    /**
     * Lists the starttimes of all the snippets.
     * @param snippets The snippets to list the starttimes of.
     * @return The starttimes of all the snippets.
     */
    private static List<Integer> getStartTimes(final List<TimedSnippet> snippets) {
        final List<Integer> starttimes = new ArrayList<Integer>(snippets.size());
        for (TimedSnippet snippet : snippets) {
            starttimes.add(snippet.getStartTime());
        }
        return starttimes;
    }

    /**
     * Method used to pass a Json object with track meta-data. This will be used
     * in the future to insert non-existing tracks.
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
     * Retrieves a start-time calculated by the CommentIntensitySeeker for the
     * given track id.
     *
     * @param trackId The id of the track.
     * @return the start-time of the snippet.
     */
    public static int getStartTime(final int trackId) {
        Track track = new Track();
        track.setDuration(-1);
        track.setId(trackId);
        return getStartTime(track);
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
     * Getter for the ObjectMapper Object of the controller.
     *
     * @return the ObjectMapper Object.
     */
    public static ObjectMapper getObjectMapper() {
        return mapper;
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
     * Getter for the DatabaseConnecter Object of the controller.
     *
     * @return the DatabaseConnecter Object.
     */
    public static DatabaseConnector getDatabaseConnector() {
        return databaseConnector;
    }

    /**
     * Setter for the DatabaseConnector object.
     *
     * @param dbc the new DatabaseConnector object.
     */
    public static void setDatabaseConnector(final DatabaseConnector dbc) {
        databaseConnector = dbc;
    }

}
