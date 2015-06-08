package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.json.Json;
import models.profile.Profile;
import models.recommender.BasicRecommender;
import models.recommender.LikesRecommender;
import models.record.Track2;
import models.utility.TrackList;
import play.mvc.Result;

import static play.mvc.Controller.request;
import static play.mvc.Results.ok;

/**
 * Controller for the recommendation system.
 */
public final class RecommenderController {

    /**
     * The profile of this session.
     */
    private static Profile profile;

    /**
     * Constructor.
     */
    private RecommenderController() {
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
        Track2 track = Json.getTrack2(request().body().asJson());
        profile.addLike(track);
        return ok();
    }

    /**
     * Adds the current track to the likes collection.
     *
     * @return A HTPP ok response
     */
    public static Result dislike() {
        Track2 track = Json.getTrack2(request().body().asJson());
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
        for (Track2 track : trackList) {
            profile.addLike(track);
        }
        return ok();
    }

    public static Result recommend() {
        System.out.println();
        LikesRecommender rec = new LikesRecommender(new BasicRecommender(profile, 10));
        TrackList recs = rec.suggest();
        System.out.println(recs.size());
        System.out.println(recs.toString());
        return ok(recs.toString());
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
//        Recommender rec = new LikesRecommender( new BasicRecommender(profile, 10));
//        System.out.println(likes.size());
//        List<RecTuple> rtl = rec.recommend();
//        System.out.println(rtl.size());
        LikesRecommender rec = new LikesRecommender(new BasicRecommender(profile, 10));
        TrackList recs = rec.suggest();
        System.out.println(recs.size());
        for (int i = 0; i < recs.size(); i++) {
            Track2 track = recs.get(i);
            ObjectNode jsontrack = mapper.createObjectNode();

            jsontrack.put("id", (Integer) track.get("id"));
            jsontrack.put("artist", (String) track.get("username"));
            jsontrack.put("title", (String) track.get("title"));
            jsontrack.put("url", (String) track.get("url"));
            jsontrack.put("genre", (String) track.get("genre"));

            result.put(Integer.toString(i), jsontrack);
        }
        return ok(result);
    }

    public static Profile getProfile() {
        return profile;
    }
}
