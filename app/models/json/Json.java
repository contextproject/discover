package models.json;

import com.fasterxml.jackson.databind.JsonNode;
import models.record.Key;
import models.record.Track;
import models.utility.TrackList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
            track.put(new Key<>("id", Integer.class), jsonNode.get("id").asInt());
            track.put(new Key<>("duration", Integer.class), jsonNode.get("duration").asInt());
            track.put(new Key<>("username", String.class), jsonNode.get("user").get("username").asText());
            track.put(new Key<>("title", String.class), jsonNode.get("title").asText());
            track.put(new Key<>("genre", String.class), jsonNode.get("genre").asText().toLowerCase());
            track.put(new Key<>("user_id", Integer.class), jsonNode.get("user_id").asInt());
            track.put(new Key<>("url", String.class), jsonNode.get("uri").asText());
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
        List<Double> data = new ArrayList<Double>();
        while (it.hasNext()) {
            data.add(Math.round(it.next().asDouble() * 10000.00) / 10000.00);
        }
        return data;
    }
}
