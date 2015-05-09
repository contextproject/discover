package controllers;

import models.database.Database;
import models.snippet.Comment;
import models.snippet.CommentIntensitySeeker;
import models.snippet.RandomSnippet;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class Application extends Controller {

    private static RandomSnippet snpt;

    //TODO: close database after session
    private static Database database = new Database("/Users/daan/Downloads/metadata/without_features/metadata/comments");

    public static Result index() {
        String url = "w.soundcloud.com/tracks/202852531";
        snpt = new RandomSnippet();
        return ok(index.render(url, snpt.getStart()));
    }

    public static Result getStartTime(String url) {
        RandomSnippet snpt = new RandomSnippet();
        String url2 = "w.soundcloud.com/tracks/" + url;

        ResultSet comments = database.getComments(Integer.parseInt(url));
        HashSet<Comment> setofcomments = new HashSet<Comment>();
        try {
            while (comments.next()) {
                Comment current = new Comment(comments.getInt("user_id"), comments.getInt("timestamp"));
                setofcomments.add(current);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int starttime = CommentIntensitySeeker.seek(setofcomments).getStartTime();
        System.out.println("starttime = " + starttime);

        return ok(index.render(url2, (double) starttime));
    }
}
