package models.json;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import models.record.Track;
import models.utility.TrackList;

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
            int id = jsonNode.get("id").asInt();
            int duration = jsonNode.get("duration").asInt();
            String artist = jsonNode.get("user").get("username").asText();
            String title = jsonNode.get("title").asText();
            String genre = jsonNode.get("genre").asText();
            String url = jsonNode.get("permalink_url").asText();
            new Track();
            return new Track(id, duration, artist, title, genre, url);
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
     * @param json
     *            The JSON object.
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
