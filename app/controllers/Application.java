package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.json.Json;
import models.mix.MixSplitter;
import models.record.Track;
import models.seeker.MixSeeker;
import models.snippet.TimedSnippet;
import models.utility.RandomTrackStack;
import models.utility.TrackList;
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
     * The RandomTrackStack object used for returning random tracks.
     */
    private static RandomTrackStack gts = new RandomTrackStack(100, true);
    
    /**
     * The index method is called when the application is started and no other
     * messages have been passed.
     *
     * @return an http ok response with the rendered page.
     */
    public static Result index() {
        int starttime = Json.getStartTime(TrackList.get(
                "SELECT * FROM tracks WHERE track_id = 56772597").get(0)).getStartTime();
        return ok(index.render("w.soundcloud.com/tracks/56772597", starttime));
    }

    /**
     * Method is used to receive Json objects containing track information, pass
     * it on the AlgorithmChooser and return back the new start time.
     *
     * @return An http ok response with the new rendered page.
     */
    public static Result trackRequest() {
        return Json.response(Json.getTrack(request().body().asJson().get("track")));
    }

    /**
     * Selects a random track from the database.
     *
     * @return A HTTP ok response with a random track id.
     */
    public static Result getRandomSong() {
        return Json.response(gts.pop());
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
            int duration = json.get("track").get("duration").asInt();
            final Track track = new Track();
            track.put(Track.ID, trackID);
            track.put(Track.DURATION, duration);
            MixSplitter splitter = new MixSplitter(json.get("waveform"), track);
            List<Integer> splits = splitter.split(json.get("splits").asInt());
            List<Integer> starttimes = getStartTimes(splits, track);
            Map<String, List<Integer>> map = new TreeMap<String, List<Integer>>();
            map.put("response", starttimes);
            JsonNode response = new ObjectMapper().valueToTree(map);
            return ok(response);
        }
    }

    /**
     * Retrieves the starttimes of the pieces of the given song.
     *
     * @param splits The start of all the pieces.
     * @param track  The track to search.
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
     *
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
            ObjectNode objNode = new ObjectMapper().createObjectNode();
            JsonNode response = objNode.put("message", "File was transvered successfully");
            return ok(response);
        }
    }

    /**
     * Set the mode of the preview.
     *
     * @return A HTTP response
     */
    public static Result setPreviewMode() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            return badRequest("Object is empty");
        } else if (json.get("mode") == null) {
            return badRequest("The expected message does not exist.");
        } else {
            AlgorithmSelector.setMode(json.get("mode").asText());
            return ok("");
        }
    }
}
