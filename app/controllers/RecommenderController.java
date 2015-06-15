package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.json.Json;
import models.profile.Profile;
import models.recommender.BasicRecommender;
import models.recommender.FeatureRecommender;
import models.recommender.LikesRecommender;
import models.recommender.Recommender;
import models.record.Key;
import models.record.Track2;
import models.utility.TrackList;
import play.mvc.Result;

import java.util.Collections;

import static play.mvc.Controller.request;
import static play.mvc.Results.badRequest;
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
    public static Result favorites() {
        JsonNode json = request().body().asJson();
        if (json == null) {
            System.out.println("json null");
            return badRequest("Json object is null.");
        } else {
            profile.addFavourites(Json.getTrackList(json));
            return ok();
        }
    }

    /**
     * Receives the id of the user from the web page and adds the id to the profile.
     *
     * @return A HTTP ok response
     */
    public static Result user() {
        JsonNode jsonNode = request().body().asJson();
        profile.setUserid(jsonNode.get("id").asInt());
        return ok();
    }

    /**
     * Get tracks to display on the web page.
     * For now it displays the likes from the profile.
     *
     * @return A HTTP ok response with the tracks to display
     */
    public static Result recommend() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = mapper.createObjectNode();
        Recommender rec = new LikesRecommender(new BasicRecommender(profile, 5));
        TrackList recs = rec.recommend();
        Collections.sort(recs);
        System.out.println(recs);
        for (int i = 0; i < recs.size(); i++) {
            Track2 track = recs.get(i);
            ObjectNode jsontrack = mapper.createObjectNode();

            jsontrack.put("id", track.get(new Key<>("id", Integer.class)));
            jsontrack.put("artist", track.get(new Key<>("username", String.class)));
            jsontrack.put("title", track.get(new Key<>("title", String.class)));
            jsontrack.put("url", track.get(new Key<>("url", String.class)));
            jsontrack.put("genre", track.get(new Key<>("genre", String.class)));
            jsontrack.put("score", track.get(new Key<>("score", Double.class)));

            result.put(Integer.toString(i), jsontrack);
        }
        return ok(result);
    }
}
