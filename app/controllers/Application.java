package controllers;


//import models.snippet.RandomSnippet;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

    public static Result index() {
        //RandomSnippet snpt = new RandomSnippet();
        //return ok(index.render(snpt.getStart()));
        double something = 0.0;
        return ok(index.render(something));
    }
}
