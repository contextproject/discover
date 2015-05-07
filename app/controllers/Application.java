package controllers;


import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {

    public static Result index() {
        snippet.RandomSnippet snpt = new snippet.RandomSnippet();
        return ok(index.render(snpt.getStart()));
    }
}
