package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.json.Json;
import models.profile.Profile;
import models.record.Track;
import models.utility.TrackList;
import play.mvc.Result;

import static play.mvc.Controller.request;
import static play.mvc.Results.ok;

/**
 * Controller for the recommendation system.
 */
public final class UserActionController {

    /**
     * The profile of this session.
     */
    private static Profile profile;

    /**
     * Constructor.
     */
    private UserActionController() {
    }

    /**
     * Creates a new profile for this session.
     */
    public static void initialize() {
        profile = new Profile();
    }

    /**
     * Adds the current track to the likes collection.
     *
     * @return A HTPP ok response
     */
    public static Result like() {
        Track track = Json.getTrack(request().body().asJson());
        profile.addLike(track);
        return ok();
    }

    /**
     * Adds the current track to the likes collection.
     *
     * @return A HTPP ok response
     */
    public static Result dislike() {
        Track track = Json.getTrack(request().body().asJson());
        profile.addDislike(track);
        return ok();
    }

    /**
     * Adds the collection of likes from the current user to the like collection
     * of the profile of the current session.
     *
     * @return A HTTP ok response
     */
    public static Result collection() {
        JsonNode jsonNode = request().body().asJson();
        TrackList trackList = Json.getTrackList(jsonNode);
        for (Track track : trackList) {
            profile.addLike(track);
        }
        return ok();
    }

    /**
     * Get tracks to display on the web page.
     * For now it displays the likes from the profile.
     *
     * @return A HTTP ok response with the tracks to display
     */
    public static Result tracks() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = mapper.createObjectNode();
        TrackList likes = profile.getLikes();
        for (int i = 0; i < likes.size(); i++) {
            Track track = likes.get(i);
            ObjectNode jsontrack = mapper.createObjectNode();
            jsontrack.put("id", track.getId());
            jsontrack.put("artist", track.getArtist());
            jsontrack.put("title", track.getTitle());
            jsontrack.put("url", track.getUrl());
            result.put(Integer.toString(i), jsontrack);
        }
        return ok(result);
    }

}
