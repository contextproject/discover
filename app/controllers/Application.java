package controllers;

import models.database.CommentProcessor;
import models.database.CommentRetriever;
import models.snippet.Comment;
import models.snippet.CommentIntensitySeeker;
import models.snippet.RandomSnippet;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import java.util.Set;

public class Application extends Controller {

    private static RandomSnippet snpt;

    public static Result index() {
        new CommentProcessor().processComments("/Users/daan/Downloads/metadata/without_features/metadata/comments");
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
}
