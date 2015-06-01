package models.json;

import com.fasterxml.jackson.databind.JsonNode;
import models.record.Track;
import models.utility.TrackList;

/**
 * Processes the json from the web application.
 */
public class Json {

    /**
     * Convert the JsonNode object to a Track object
     *
     * @return A Track object
     */
    public static Track getTrack(JsonNode jsonNode) {
        int trackid = jsonNode.get("id").asInt();
        int duration = jsonNode.get("duration").asInt();
        return new Track(trackid, duration);
    }

    /**
     * Convert the JsonNode object to a TrackList object
     *
     * @return A TrackList object
     */
    public static TrackList getTrackList(JsonNode jsonNode) {
        TrackList trackList = new TrackList();
        for (JsonNode track : jsonNode) {
            trackList.add(getTrack(track));
        }
        return trackList;
    }
}
