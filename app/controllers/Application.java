package controllers;

import java.util.Iterator;
import java.util.Set;

import models.database.CommentRetriever;
import models.database.DatabaseConnector;
import models.database.RandomSongSelector;
import models.snippet.Comment;
import models.snippet.CommentIntensitySeeker;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * This class is used to control the model and the view parts of the MVC. It
 * renders the view with the values processed by the model classes.
 */
public class Application extends Controller {

	/** The database object of the controller. */
	private static DatabaseConnector databaseConnector;

	/**
	 * The index method is called when the application is started and
	 * no other messages have been passed.
	 * @return an http ok response with the rendered page.
	 */
	public static Result index() {
		String url = "w.soundcloud.com/tracks/67016624";
		return ok(index.render(url, getStartTime(67016624)));
	}

	/**
	 * Renders a page with the given track id to load the widget.
	 * @param trackId the id of the track we are searching for.
	 * @return an http ok response with the new rendered page.
	 */
	public static Result getSong(final String trackId) {
		String url = "w.soundcloud.com/tracks/" + trackId;
        double[] results = new double[3];
		double starttime = getStartTime(Integer.parseInt(trackId));
        results[0] = starttime;
		return ok(index.render(url, starttime));
	}

	/**
	 * Retrieves a start-time calculated by the CommentIntensitySeeker
	 * for the given track id.
	 * @param trackId the id of the track.
	 * @return the start-time of the snippet.
	 */
	public static double getStartTime(final int trackId) {
		CommentRetriever commentRetriever = new CommentRetriever();
		Set<Comment> coms = commentRetriever.getComments(trackId);
		int start = CommentIntensitySeeker.seek(coms).getStartTime();
		return start;
	}

	/**
	 * Selects a random track from the database.
	 * @return an http ok response with a random track id.
	 */
	public static Result getRandomSong() {
		RandomSongSelector selector;
		selector = RandomSongSelector.getRandomSongSelector();
		int trackId = selector.getRandomSong();
		String url = "w.soundcloud.com/tracks/" + trackId;
		double starttime = getStartTime(trackId);
        double[] results = new double[3];
        results[0] = starttime;
		return ok(index.render(url, starttime));
	}
	
	/**
	 * Method used to pass a Iterator object with track waveform 
	 * on to the MixSplitter class.
	 * @return ok response with the start times for the mix.
	 */
	public static Result splitWaveform() {
		JsonNode json = request().body().asJson();
		if (json == null) {
			return badRequest("Expecting Json data");
		} else {
			Iterator<JsonNode> it = json.elements();
			//TO-DO: send the iterator to the MixSplitter.
			//TO-DO: receive the answer from the splitter and make a proper success message.
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode objNode = mapper.createObjectNode();
			JsonNode response = objNode.put("message", "File was transvered successfully");
			return ok(response);
		}
	}
	
	/**
	 * Method used to pass a Json object with track meta-data.
	 * This will be used in the future to insert non-existing 
	 * Example: {id:123, genre: "Ambient"}
	 * @return ok response with a 
	 */
	public static Result trackMetadata() {
		JsonNode json = request().body().asJson();
		if (json == null) {
			return badRequest("Expecting Json data");
		} else {
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode objNode = mapper.createObjectNode();
			JsonNode response = objNode.put("message", "File was transvered successfully");
			return ok(response);
		}
	}

	/**
	 * Setter for the DatabaseConnector object.
	 * @param dbc the new DatabaseConnector object.
	 */
	public static void setDatabaseConnector(final DatabaseConnector dbc) {
		databaseConnector = dbc;
	}

	/**
	 * Getter for the DatabaseConnecter Object of the controller.
	 * @return the DatabaseConnecter Object.
	 */
	public static DatabaseConnector getDatabaseConnector() {
		return databaseConnector;
	}
}
