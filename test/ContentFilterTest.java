import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import models.snippet.Comment;
import models.snippet.CommentIntensitySeeker;
import models.snippet.TimedSnippet;

import models.snippet.ContentFilter;

import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the ContentFilter. It simply checks if content of a comment is positive.
 */
public class ContentFilterTest {

  
  private ContentFilter cf;
  
  /**
   * Setting up the class to test with.
   */
  @Before
  public void initialize() {
    cf = new ContentFilter();
  }
  
  /**
   * Test for simple content.
   */
  @Test
  public void testContentFilter1() {
    String s1 = "great song!";
    assertTrue(cf.contentFilter(s1));
    assertFalse(cf.findEmoticons(s1));
  }
  
  /**
   * Test for simple content with capitals.
   */
  @Test
  public void testContentFilter2() {
    String s1 = "GREAT SONG!";
    assertTrue(cf.contentFilter(s1));
    assertFalse(cf.findEmoticons(s1));
  }
  
  /**
   * Test for simple content without a positive word.
   */
  @Test
  public void testContentFilter3() {
    String s1 = "this sucks";
    assertFalse(cf.contentFilter(s1));
    assertFalse(cf.findEmoticons(s1));
  }
  
  /**
   * Test for simple content with a wrong spelled positive word.
   */
  @Test
  public void testContentFilter4() {
    String s1 = "this is gooddddd";
    assertTrue(cf.contentFilter(s1));
    assertFalse(cf.findEmoticons(s1));
  }
  
  /**
   * Test for simple content with a wrong spelled positive word and a emoticon.
   */
  @Test
  public void testFindAll() {
    String s1 = "<3 this great song";
    assertTrue(cf.contentFilter(s1));
    assertTrue(cf.findEmoticons(s1));
  }
  
  /**
   * Testing all possible positive emoticons.
   */
  @Test
  public void testFindEmoticons() {
    String s1 = "<3";
    String s2 = ":)";
    String s3 = ":-)";
    String s4 = "=D";
    assertTrue(cf.contentFilter(s1));
    assertTrue(cf.contentFilter(s2));
    assertTrue(cf.contentFilter(s3));
    assertTrue(cf.contentFilter(s4));
  }
  
  /**
   * A test in combination with the CommentIntensitySeeker.
   * (Can be moved to another test class)
   */
  @Test
  public void testWithCIS() {
    HashMap<Comment,String> map = new HashMap<Comment,String>();
    Comment c1 = new Comment(1,45000); map.put(c1, "shit part");
    Comment c2 = new Comment(2,80000); map.put(c2, "worthless!!");
    Comment c3 = new Comment(3,45000); map.put(c3, "@user1: you are shit");
    Comment c4 = new Comment(4,21000); map.put(c4, "love it");
    
    Comment c5 = new Comment(5,50000); map.put(c5, "best part");
    Comment c6 = new Comment(6,53000); map.put(c6, "really good");
    Comment c7 = new Comment(7,50000); map.put(c7, "oke");
    Comment c8 = new Comment(8,62000); map.put(c8, "=D");
    HashSet<Comment> goodcom = new HashSet<Comment>(); 
    Set<Comment> allcom =  map.keySet();
    
    for(Comment c:map.keySet()){
      String content = map.get(c);
      if(cf.contentFilter(content)){
        goodcom.add(c);
      }
    }
    
    TimedSnippet tsgood = CommentIntensitySeeker.seek(goodcom);
    TimedSnippet tsall = CommentIntensitySeeker.seek(allcom);

    assertEquals(50000,tsgood.getStartTime());
    assertEquals(45000,tsall.getStartTime());
    
  }
  
  

}
