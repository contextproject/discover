package models.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.AlgorithmSelector;
import models.record.Track;
import models.snippet.TimedSnippet;
import models.utility.TrackList;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.ok;

/**
 * Processes the json from the web application.
 */
public final class Json {

    /**
     * Utility class.
     */
    private Json() {
    }

    /**
     * Convert the JsonNode object to a Track object.
     *
     * @param jsonNode The JsonNode object
     * @return A Track object
     */
    public static Track getTrack(final JsonNode jsonNode) {
        if (jsonNode != null) {
            Track track = new Track();
            track.put(Track.id, jsonNode.get("id").asInt());
            track.put(Track.duration, jsonNode.get("duration").asInt());
            track.put(Track.username, jsonNode.get("user").get("username").asText());
            track.put(Track.title, jsonNode.get("title").asText());
            track.put(Track.genre, jsonNode.get("genre").asText().toLowerCase());
            track.put(Track.userid, jsonNode.get("user_id").asInt());
            track.put(Track.url, jsonNode.get("uri").asText());
            return track;
        }
        return null;
    }

    /**
     * Convert the JsonNode object to a TrackList object.
     *
     * @param jsonNode The JsonNode object
     * @return A TrackList object
     */
    public static TrackList getTrackList(final JsonNode jsonNode) {
        if (jsonNode != null) {
            TrackList trackList = new TrackList();
            for (JsonNode track : jsonNode) {
                trackList.add(getTrack(track));
            }
            return trackList;
        }
        return null;
    }

    /**
     * Receives a waveform JSON object and converts it into a List object of
     * doubles.
     *
     * @param json The JSON object.
     * @return A list containing the waveform as doubles
     */
    public static List<Double> getWaveform(final JsonNode json) {
        Iterator<JsonNode> it = json.elements();
        List<Double> data = new ArrayList<>();
        while (it.hasNext()) {
            data.add(Math.round(it.next().asDouble() * 10000.00) / 10000.00);
        }
        return data;
    }

    public static Result response(final Track track) {
        if (track == null) {
            return badRequest("Object is empty.");
        } else {
            ObjectNode response = new ObjectMapper().createObjectNode();
            response.put("start", getStartTime(track).getStartTime());
            response.put("window", getStartTime(track).getWindow());
            response.put("url", "w.soundcloud.com/tracks" + track.get(Track.id));
            return ok(response);
        }
    }

    /**
     * Get the start time calculated by the AlgorithmSelector.
     *
     * @param track The track
     * @return The start time of the snippet.
     */
    public static TimedSnippet getStartTime(final Track track) {
        return AlgorithmSelector.determineStart(track);
    }
}
