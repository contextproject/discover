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
        System.out.println("profile.getLikes().size() = " + profile.getLikes().size());
        System.out.println("likes: " + profile.getLikes().toString());
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
    public static Result favourites() {
        JsonNode json = request().body().asJson();
        // something goes wrong here, json node is apparently null,
        // but when logging it in javascript, is isn't null
        if (json == null) {
            System.out.println("json null");
        } else {
            int userid = json.get("id").asInt();
            profile.setUserid(userid);
            TrackList favourites = Json.getTrackList(json);
            profile.addFavourites(favourites);
        }
        return ok();
    }

//    public static Result recommend() {
//        LikesRecommender rec = new LikesRecommender(new BasicRecommender(profile, 10));
//        TrackList recs = rec.recommend();
//        System.out.println(recs.size());
//        System.out.println(recs.toString());
//        return ok(recs.toString());
//    }

    /**
     * Get tracks to display on the web page.
     * For now it displays the likes from the profile.
     *
     * @return A HTTP ok response with the tracks to display
     */
    public static Result recommend() {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode result = mapper.createObjectNode();
        
        LikesRecommender rec = new LikesRecommender(new BasicRecommender(profile, 5));
        TrackList recs = rec.recommend();
        System.out.println("Artists: " + rec.getArtistBoard().toString());
        System.out.println("Genre: " + rec.getGenreBoard().toString());
//        System.out.println(recs.toString());
        
        for (int i = 0; i < recs.size(); i++) {
            Track2 track = recs.get(i);
            ObjectNode jsontrack = mapper.createObjectNode();

            jsontrack.put("id", (Integer) track.get("id"));
            jsontrack.put("artist", (String) track.get("username"));
            jsontrack.put("title", (String) track.get("title"));
            jsontrack.put("url", (String) track.get("url"));
            jsontrack.put("genre", (String) track.get("genre"));
            jsontrack.put("score", (Double) track.get("score"));

            result.put(Integer.toString(i), jsontrack);
        }
        return ok(result);
    }

    public static Profile getProfile() {
        return profile;
    }
}
