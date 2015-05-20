package controllers;

import models.database.CommentRetriever;
import models.database.DatabaseConnector;
import models.snippet.AlgorithmSelector;
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

        
        AlgorithmSelector as = new AlgorithmSelector(Integer.parseInt(url));
     
        int starttime = as.getStartTime();
        System.out.println("starttime = " + starttime);

        return ok(index.render(url2, (double) starttime));
    }

    public static void setDatabaseConnector(DatabaseConnector dbc) {
        databaseConnector = dbc;
    }

    
    public static DatabaseConnector getDatabaseConnector() {
        return databaseConnector;
    }
}
