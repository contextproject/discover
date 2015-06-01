package models.seeker;

import models.database.DatabaseConnector;
import models.database.retriever.CommentRetriever;
import models.database.retriever.TrackRetriever;
import models.record.Comment;
import models.record.Track;
import models.snippet.TimedSnippet;
import models.utility.CommentList;

import org.junit.Before;
import org.junit.Test;

import controllers.Application;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Test for the CommentIntensitySeeker.
 */
public class CommentIntensitySeekerTest {
	/**
	 * CommentIntensitySeeker object.
	 */
	private CommentIntensitySeeker mockedCIS;

	/**
	 * Comment objects.
	 */
	private Comment c1, c3, c4, c5, c6, c7, c8, c9;

	/**
	 * Database object.
	 */
	private DatabaseConnector databaseConnector;

	/**
	 * CommentList object.
	 */
	private CommentList list;

	/**
	 * listting up 2 classes to test with.
	 */
	@Before
	public void makeComments() {
		databaseConnector = new DatabaseConnector();
		databaseConnector.loadDrivers();
		databaseConnector
				.makeConnection("jdbc:mysql://188.166.78.36/contextbase",
						"context", "password");
		Application.setDatabaseConnector(databaseConnector);
		TrackRetriever tr = new TrackRetriever(32097940);
		Track track = tr.getAll();
		CommentRetriever cr = new CommentRetriever(32097940);
		list = cr.getComments();
		mockedCIS = new CommentIntensitySeeker(track);
		c1 = new Comment(1, 1, 5000, "l ");
		c3 = new Comment(1, 3, 15000, "l ");
		c4 = new Comment(1, 4, 16000, "l ");
		c5 = new Comment(1, 5, 18000, "l ");
		c6 = new Comment(1, 6, 21000, "l ");
		c7 = new Comment(1, 7, 50000, "l ");
		c8 = new Comment(1, 8, 41000, "l ");
		c9 = new Comment(1, 9, 42000, "l ");
	}

	/**
	 * Test the seek function with a list of zero comments.
	 */
	@Test
	public void testSeekZero() {
		list.clear();
		mockedCIS.setComments(list);
		TimedSnippet ts = mockedCIS.seek();

		assertEquals(0, ts.getStartTime());
		assertEquals(30000, ts.getWindow());
	}

	/**
	 * Test the seek function with a list of comments with the timestamp
	 * centered at one point.
	 */
	@Test
	public void testSeek1() {

		list.clear();
		list.add(c3);
		list.add(c4);
		list.add(c5);
		list.add(c6);
		mockedCIS.setComments(list);
		TimedSnippet ts = mockedCIS.seek();
		assertEquals(15000, ts.getStartTime());
		assertEquals(30000, ts.getWindow());
	}

	/**
	 * Test the seek function with a list of comments with the timestamp
	 * centered at a other point.
	 */
	@Test
	public void testSeek2() {
		list.clear();
		assertEquals(30000, TimedSnippet.getDefaultDuration());
		assertEquals(5000, Comment.getPeriod());
		list.add(c7);
		list.add(c8);
		list.add(c9);
		mockedCIS.setComments(list);
		TimedSnippet ts = mockedCIS.seek();
		assertEquals(30000, TimedSnippet.getDefaultDuration());
		assertEquals(5000, Comment.getPeriod());
		assertEquals(40000, ts.getStartTime());
		assertEquals(30000, ts.getWindow());
	}

	/**
	 * Test the seek function with only 2 comments.
	 */
	@Test
	public void testSeek3() {
		list.clear();
		list.add(c1);
		list.add(c9);
		mockedCIS.setComments(list);
		TimedSnippet ts = mockedCIS.seek();
		assertEquals(5000, ts.getStartTime());
		assertEquals(30000, ts.getWindow());
	}

	/**
	 * Testing if the functions of the Comment class are used.
	 */
	@Test
	public void testDependency() {
		list.clear();
		Comment com = mock(Comment.class);
		when(com.getTime()).thenReturn(0);
		when(com.getBody()).thenReturn(" ");
		list.add(com);
		mockedCIS.setComments(list);
		TimedSnippet ts = mockedCIS.seek();
		assertEquals(0, ts.getStartTime());
		assertEquals(30000, ts.getWindow());
		verify(com, times(5)).getTime();
		verify(com).hashCode();
	}
}
