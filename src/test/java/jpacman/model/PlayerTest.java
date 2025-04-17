package jpacman.model;

import org.junit.Test;


/**
 * Test the Player.
 */
public class PlayerTest {
    @Test(expected = AssertionError.class)
    public void testTotalNegativeFood() {
        try {
            Player p = new Player();
            p.eat(-1);
        } catch (Exception e) {
            assert false;
        }
    }
}
