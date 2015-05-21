package basic;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

/**
 * A basic test that provides some default tests cases and some hooks
 * that may be useful to implement. 
 * 
 * @since 21-05-2015
 * @version 21-05-2015
 * 
 * @author stefanboodt
 *
 */
public abstract class BasicTest {

	/**
	 * The object under test.
	 */
	private Object tested;
	
	/**
	 * Does some set up for the class.
	 * @throws Exception If the set up fails.
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Does some clean up for the class.
	 * @throws Exception If the clean up fails.
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * Sets the Object under test.
	 * @param tested The Object under test.
	 */
	protected void setObjectUnderTest(final Object tested) {
		this.tested = tested;
	}
	
	/**
	 * Returns the object that is being tested.
	 * @return The object under test.
	 */
	protected Object getObjectUnderTest() {
		return tested;
	}

	/**
	 * Tests the {@link #equals(Object)} method with the tested object
	 * itself.
	 */
	@Test
	public void testEquals() {
		assertEquals(getObjectUnderTest(), getObjectUnderTest());
	}
	
	/**
	 * Tests the {@link #equals(Object)} method with a new Object.
	 */
	@Test
	public void testEqualsWithObject() {
		assertFalse(getObjectUnderTest().equals(new Exception()));
	}

	/**
	 * Tests the {@link #equals(Object)} method with a new exception.
	 */
	@Test
	public void testEqualsWithException() {
		assertNotEquals(getObjectUnderTest(), new Object());
	}
	
	/**
	 * Tests the {@link #hashCode()} method for consistency.
	 */
	@Test
	public void testHashCodeConsistent() {
		assertEquals(getObjectUnderTest().hashCode(), getObjectUnderTest().hashCode());
	}
}
