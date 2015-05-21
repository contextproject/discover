package models.mix;

import basic.BasicTest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the MixSplitter class.
 * 
 * @see MixSplitter
 * @see BasicTest
 * 
 * @since 21-05-2015
 * @version 21-05-2015
 * 
 * @author stefan boodt
 * @author arthur hovenesyan
 *
 */
public class MixSplitterTest extends BasicTest {

	/*
	 * temporary turn CHECKSTYLE:OFF because of the default trackid
	 * that is initialized here.
	 */
	
	/**
	 * Default trackid of the tested mixsplitter.
	 */
	protected static int DEFAULT_TRACKID;
	
	// Turn the CHECKSTYLE:ON again for the rest of the file.
	
	/**
	 * The splitter under test.
	 */
	private MixSplitter splitter;
	
	/**
	 * Does some set up for the class.
	 * @throws Exception If the set up fails.
	 */
	@Override
	public void setUp() throws Exception {
		super.setUp();
		DEFAULT_TRACKID = 2431002;
		setSplitter(new MixSplitter(asList(), DEFAULT_TRACKID));
	}
	
	/**
	 * Returns the given numbers as a list.
	 * @param numbers The numbers to have in the list.
	 * @return The list containing the given numbers.
	 */
	protected List<Float> asList(final float ... numbers) {
		List<Float> floats = new ArrayList<Float>(numbers.length);
		for (float f : numbers) {
			floats.add(f);
		}
		return floats;
	}

	/**
	 * Sets the splitter under test.
	 * @param splitter The new tested splitter.
	 */
	public void setSplitter(final MixSplitter splitter) {
		super.setObjectUnderTest(splitter);
		this.splitter = splitter;
	}
	
	/**
	 * Gets the splitter under test.
	 * @return The splitter that is being tested.
	 */
	public MixSplitter getSplitter() {
		return splitter;
	}

	/**
	 * Tests the {@link MixSplitter#split()} method on an empty dataset.
	 */
	@Test
	public void testSplitEmpty() {
		assertEquals(new ArrayList<Integer>(), getSplitter().split());
	}
	
	/**
	 * Tests the {@link MixSplitter#getTrackID()} method.
	 */
	@Test
	public void testGetTrackID() {
		assertEquals(DEFAULT_TRACKID, getSplitter().getTrackID());
	}
	
	/**
	 * Tests the {@link MixSplitter#getTrackID()} method.
	 */
	@Test
	public void testGetTrackIDAfterSetting() {
		final int id = 2104921042;
		final MixSplitter split = getSplitter();
		split.setTrackID(id);
		assertEquals(id, split.getTrackID());
	}
	
	/**
	 * Tests the {@link MixSplitter#getData()} method.
	 */
	@Test
	public void testDataEmpty() {
		assertEquals(asList(), getSplitter().getData());
	}
	
	/**
	 * Tests the {@link MixSplitter#getData()} method.
	 */
	@Test
	public void testData() {
		final MixSplitter split = getSplitter();
		final List<Float> data = asList(4.0f, 2.1f, 3.9f);
		split.setData(data);
		assertEquals(data, split.getData());
	}
}
