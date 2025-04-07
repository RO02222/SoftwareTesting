package jpacman.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 * Test the Food.
 */
public class FoodTest {
    @Test(expected = AssertionError.class)
    public void testCreateInvalidFood() {
        try {
            Food food = new Food(-1);
        } catch (Exception e) {
            assert false;
        }
    }
}
