package models.score;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import basic.BasicTest;

/**
 * Abstract test for all the score storages.
 * 
 * @since 03-06-2015
 * @version 03-06-2015
 * 
 * @see ScoreStorage
 * @see BasicTest
 * 
 * @author stefan boodt
 *
 */
public abstract class ScoreStorageTest extends BasicTest {

    /**
     * The storage used for testing.
     */
    private ScoreStorage storage;
    
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }
    
    /**
     * REturns the storage under test.
     * @return The storage under test.
     */
    protected ScoreStorage getStorage() {
        return storage;
    }
    
    /**
     * Sets the storage under test.
     * @param storage The new storage under test.
     */
    protected void setStorage(final ScoreStorage storage) {
        setObjectUnderTest(storage);
        this.storage = storage;
    }

    /**
     * Tests the {@link ScoreStorage#isEmpty()} method.
     */
    @Test
    public void testIsEmpty() {
        getStorage().isEmpty();
    }

}
