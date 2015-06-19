package models;

import basic.BasicTest;
import models.database.DatabaseConnector;

import org.junit.Test;

import play.Application;
import static play.test.Helpers.fakeApplication;

/**
 * Tests the global class.
 * 
 * @since 19-06-2015
 * @version 19-06-2015
 * 
 * @see Global
 * 
 * @author stefanboodt
 *
 */
public class GlobalTest extends BasicTest {
    
    /**
     * The global under test.
     */
    private Global global;
    
    @Override
    public void setUp() throws Exception {
        super.setUp();
        setGlobal(new Global());
    }
    
    /**
     * Sets the global under test.
     * @param global The global under test.
     */
    protected void setGlobal(final Global global) {
        setObjectUnderTest(global);
        this.global = global;
    }

    /**
     * Tests if the global onStart method fails.
     */
    @Test
    public void testOnStart() {
        final Application app = null;
        //final Application app = new Application((play.api.test.Application) fakeApplication());
        global.onStart(app);
        DatabaseConnector.getConnector().closeConnection();
    }

    /**
     * Tests if the global onStop method fails.
     */
    @Test
    public void testOnStop() {
        final Application app = null;
        global.onStart(app);
        global.onStop(app);
    }

}
