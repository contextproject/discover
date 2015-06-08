package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.database.DatabaseConnector;
import models.database.RandomSongSelector;
import models.mix.MixSplitter;
import models.recommender.BasicRecommender;
import models.recommender.FeatureRecommender;
import models.recommender.RecTuple;
import models.record.Track;
import models.utility.TrackList;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

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
        test();
        String url = "w.soundcloud.com/tracks/67016624";
        return ok(index.render(url, getStartTime(67016624)));
    }

    public static void test() {
        FeatureRecommender fr = new FeatureRecommender(new BasicRecommender(RecommenderController.getProfile(), 5));
        TrackList bla = fr.recommend();
        System.out.println(bla);
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
            int trackID = json.get("track").get("id").asInt();
            int duration = json.get("track").get("duration").asInt();
            Track track = new Track();
            track.setId(trackID);
            track.setDuration(duration);
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
        RandomSongSelector selector;
        selector = RandomSongSelector.getRandomSongSelector();
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
            System.out.println("MIX ID: " + trackID);
            MixSplitter splitter = new MixSplitter(json.get("waveform"), trackID);
            List<Integer> list = splitter.split();
            Map<String, List<Integer>> map = new TreeMap<String, List<Integer>>();
            map.put("response", list);
            JsonNode response = mapper.valueToTree(map);
            return ok(response);
        }
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
