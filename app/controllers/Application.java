package controllers;

import models.database.CommentRetriever;
import models.database.DatabaseConnector;
import models.database.RandomSongSelector;
import models.snippet.Comment;
import models.snippet.CommentIntensitySeeker;
import models.snippet.RandomSnippet;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import java.util.Set;

public class Application extends Controller {

    private static DatabaseConnector databaseConnector;

    private static RandomSnippet snpt;

    public static Result index() {
        String url = "w.soundcloud.com/tracks/202852531";
        snpt = new RandomSnippet();
        return ok(index.render(url, snpt.getStart()));
    }

    public static Result getStartTime(String url) {
        String url2 = "w.soundcloud.com/tracks/" + url;

        CommentRetriever commentRetriever = new CommentRetriever();
        Set<Comment> comments = commentRetriever.getComments(Integer.parseInt(url));
        int starttime = CommentIntensitySeeker.seek(comments).getStartTime();
        System.out.println("starttime = " + starttime);

        return ok(index.render(url2, (double) starttime));
    }
    
    public static Result getRandomSong() {
        RandomSongSelector selector = RandomSongSelector.getRandomSongSelector();
        int track_id = selector.getRandomSong();
        String url = "w.soundcloud.com/tracks/" + track_id;
        
        CommentRetriever commentRetriever = new CommentRetriever();
        Set<Comment> comments = commentRetriever.getComments(track_id);
        int starttime = CommentIntensitySeeker.seek(comments).getStartTime();
        System.out.println("starttime = " + starttime);
        
        return ok(index.render(url, (double) starttime));
    }

    public static void setDatabaseConnector(DatabaseConnector dbc) {
        databaseConnector = dbc;
    }

    public static DatabaseConnector getDatabaseConnector() {
        return databaseConnector;
    }
}
