package models.recommender;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the class RecommendDecorator.
 */
public class RecommendDecoratorTest {

    /**
     * Object under testing
     */
    private RecommendDecorator rec;

    /**
     * Setup the needed objects.
     */
    @Before
    public void setUp() {
        rec = new LikesRecommender(new BasicRecommender(null, 0));
    }

    /**
     * Test the set and get Amount methods.
     */
    @Test
    public void testAmount() {
        rec.setAmount(69);
        assertEquals(rec.getAmount(), 69);
    }

    /**
     * Test the set and get Weight methods.
     */
    @Test
    public void testWeight() {
        rec.setWeight(420.0);
        assertEquals(rec.getWeight(), 420.0, 0);
    }

    /**
     * Test the counting of decorators methods.
     */
    @Test
    public void testDecoratorAmount() {
        rec = new FeatureRecommender(new LikesRecommender(
                new BasicRecommender(null, 0)));
        assertEquals(rec.getDecoratorAmount(), 2);
    }

    @Test
    public void testSetDecorator() {
        rec.setRecommender(new FeatureRecommender(new BasicRecommender(null, 0)));
        assertTrue(rec.getRecommender() instanceof FeatureRecommender);
    }
}
