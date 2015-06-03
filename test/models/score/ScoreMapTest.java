package models.score;

import static org.junit.Assert.*;

import org.junit.Test;
/**
 * Tests the ScoreMap class.
 * 
 * @since 03-06-2015
 * @version 03-06-2015
 * 
 * @see ScoreMap
 * 
 * @author stefan boodt
 *
 */
public class ScoreMapTest extends ScoreStorageTest {

    @Override
    public void setUp() throws Exception {
        super.setUp();
        setStorage(new ScoreMap());
    }
}
