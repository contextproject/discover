package models.snippet;

import java.util.ArrayList;

/**
 * Filters the content of a comment.
 *
 */
public class ContentFilter {

  /**
   * List of positive words.
   */
  private final ArrayList<String> positive = new ArrayList<String>() {
    private static final long serialVersionUID = 1L;
    {
      add("great");
      add("brilliant");
      add("beautiful");
      add("awesome");
      add("amazing");
      add("perfect");
      add("good");
      add("like");
      add("love");
      add("best");
      add("nice");
      add("super");
      add("cool");
    }
  };

  /**
   * Checks if a string contains a word from the list above.
   * 
   * @param body
   *          the content of a comment
   * @return true if the content contains a positive message
   */
  public boolean contentFilter(String body) {
    body = body.toLowerCase();
    String[] words = body.split(" ");
    
    for (String p : positive) {
      for(String word : words){
        if (word.contains(p)) {
          return true;
        }
      }
    }

    return findEmoticons(body);
  }

  /**
   * Checks if the content of a comment contains a positive emoticon.
   * 
   * @param body
   *          content of a comment
   * @return true if the content contains a happy emoticon
   */
  public boolean findEmoticons(String body) {
    if (body.contains(":)") || body.contains("<3") || body.contains(":-)") || body.contains("=d")) {
      return true;
    } else {
      return false;
    }
  }
}
