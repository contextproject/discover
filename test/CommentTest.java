package snippet;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CommentTest {

  private Comment c1;
  private Comment c2;

  /**
   * Setting up 2 comments to test with.
   */
  @Before
  public void makeComments() {
    c1 = new Comment(1, 5000);
    c2 = new Comment(2, 12321);

  }

  /**
   * Testing the getUser method.
   */
  @Test
  public void testGetUser() {

    assertEquals(1, c1.getUser());
    assertEquals(2, c2.getUser());

  }

  /**
   * Testing the getTime method.
   */
  @Test
  public void testGetTime() {

    assertEquals(5000, c1.getTime());
    assertEquals(10000, c2.getTime());

  }

  /**
   * Testing the setPeriod. The timestamp can change after setting a new period
   */
  @Test
  public void testSetPeriod() {
    // before
    assertEquals(10000, c2.getTime());
    assertEquals(5000, c1.getTime());

    Comment.setPeriod(1000);

    // after
    assertEquals(12000, c2.getTime());
    assertEquals(5000, c1.getTime());
  }

  /**
   * Testing if get period gives the right number back after change it.
   */
  @Test
  public void testGetPeriod() {
    // default period should be 5000
    assertEquals(5000, Comment.getPeriod());

    Comment.setPeriod(1000);

    // after
    assertEquals(1000, Comment.getPeriod());
  }

  /**
   * Nothing to do after.
   */
  @After
  public void after() {
    Comment.setPeriod(5000);
  }

}
