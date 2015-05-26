package controllers;

import models.database.DatabaseConnector;
import models.database.RandomSongSelector;
import models.snippet.TimedSnippet;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

/**
 * This class is used to control the model and the view parts of the MVC. It
 * renders the view with the values processed by the model classes.
 */
public class Application extends Controller {

	/** The database object of the controller. */
	private static DatabaseConnector databaseConnector;

	/**
	 * The index method is called when the application is started and no other
	 * messages have been passed.
	 * 
	 * @return an http ok response with the rendered page.
	 */
	public static Result index() {
		String url = "w.soundcloud.com/tracks/67016624";
		Double starttime = getStartTime(67016624);
		return ok(index.render(url, starttime));
	}

	/**
	 * Renders a page with the given track id to load the widget.
	 * 
	 * @param trackId
	 *            , the id of the track we are searching for.
	 * @return an http ok response with the new rendered page.
	 */
	public static Result getSong(final String trackId) {
		String url = "w.soundcloud.com/tracks/" + trackId;
		double starttime = getStartTime(Integer.parseInt(trackId));
		return ok(index.render(url, starttime));
	}

	/**
	 * Retrieves a start-time calculated by the CommentIntensitySeeker for the
	 * given track id.
	 * 
	 * @param trackId
	 *            , the id of the track.
	 * @return the start-time of the snippet.
	 */
	public static double getStartTime(final int trackId) {
		AlgorithmSelector as = new AlgorithmSelector(trackId);
		TimedSnippet ts = as.getSnippet();
		return ts.getStartTime();
	}

	/**
	 * Selects a random track from the database.
	 * 
	 * @return an http ok response with a random track id.
	 */
	public static Result getRandomSong() {
		RandomSongSelector selector;
		selector = RandomSongSelector.getRandomSongSelector();
		int trackId = selector.getRandomSong();
		String url = "w.soundcloud.com/tracks/" + trackId;
		double starttime = getStartTime(trackId);
		return ok(index.render(url, starttime));
	}

	/**
	 * Setter for the DatabaseConnector object.
	 * 
	 * @param dbc
	 *            , the new DatabaseConnector object.
	 */
	public static void setDatabaseConnector(final DatabaseConnector dbc) {
		databaseConnector = dbc;
	}

	/**
	 * Getter for the DatabaseConnecter Object of the controller.
	 * 
	 * @return the DatabaseConnecter Object.
	 */
	public static DatabaseConnector getDatabaseConnector() {
		return databaseConnector;
	}
}
