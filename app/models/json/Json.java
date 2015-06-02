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
public class Json {

    /**
     * Convert the JsonNode object to a Track object
     * @param jsonNode JsonNode object containing track 
     * @return A Track object
     */
    public static Track getTrack(final JsonNode jsonNode) {
        if (jsonNode != null) {
            int trackid = jsonNode.get("id").asInt();
            int duration = jsonNode.get("duration").asInt();
            return new Track(trackid, duration);
        }
        return null;
    }

    /**
     * Convert the JsonNode object to a TrackList object
     * @param jsonNode JsonNode object containing tracks 
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
    
    public static List<Double> getWaveform(final JsonNode json) {
		Iterator<JsonNode> it = json.elements();
		List<Double> data = new ArrayList<Double>();
		while (it.hasNext()) {
			data.add(Math.round(it.next().asDouble() * 10000.00) / 10000.00);
		}
		return data;
	}
}
