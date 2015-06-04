package controllers;


import static play.mvc.Controller.request;
import static play.mvc.Results.ok;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import models.json.Json;
import models.mix.MixSplitter;
import models.profile.Profile;
import models.recommender.BasicRecommender;
import models.recommender.LikesRecommender;
import models.recommender.RecTuple;
import models.recommender.Recommender;
import models.record.Track;
import models.utility.TrackList;
import play.mvc.Result;

import com.fasterxml.jackson.databind.JsonNode;

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
    
    public static Result recommend() {
        System.out.println();
        Recommender rec = new LikesRecommender(new BasicRecommender(profile, 10));
        List<RecTuple> recs = rec.recommend();
        System.out.println(recs.size());
        System.out.println(recs.toString());
        return ok(recs.toString());
    }
}
