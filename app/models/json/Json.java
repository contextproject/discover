package models.json;

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
            String url = jsonNode.get("permalink_url").asText();
            return new Track(id, duration, artist, title, url);
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
}
