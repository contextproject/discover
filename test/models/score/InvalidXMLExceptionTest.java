package models.score;

import static models.score.XMLScoreParser.InvalidXMLFormatException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the InvalidXMLFormatException class.
 * 
 * @since 04-06-2015
 * @version 04-06-2015
 * 
 * @see InvalidXMLFormatException
 * 
 * @author stefan boodt
 *
 */
public class InvalidXMLExceptionTest {
    
    /**
     * The exception under test.
     */
    private InvalidXMLFormatException exception;
    
    /**
     * The default message.
     */
    private static final String MESSAGE = "An exception";

    /**
     * Does some set up.
     * @throws Exception If the set up fails.
     */
    @Before
    public void setUp() throws Exception {
        exception = new InvalidXMLFormatException(MESSAGE);
    }

    /**
     * Does some clean up.
     * @throws Exception If the clean up fails.
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Tests the empty constructor.
     */
    @Test
    public void testEmptyConstructor() {
        assertNotNull(new InvalidXMLFormatException());
    }
    
    /**
     * Tests the message of the exception.
     */
    @Test
    public void testGetMessage() {
        assertEquals(MESSAGE, exception.getMessage());
    }
}
