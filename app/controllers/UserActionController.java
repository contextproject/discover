package controllers;

import com.fasterxml.jackson.databind.JsonNode;
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
}
