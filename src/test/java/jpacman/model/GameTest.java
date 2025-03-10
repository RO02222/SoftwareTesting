package jpacman.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.Vector;

import org.junit.Test;

/**
 * Series of test cases for the game itself.
 * It makes use of the GameTestCase fixture, which
 * contains a simple board.
 * @author Arie van Deursen, 2007
 * @version $Id: GameTest.java,v 1.7 2008/02/10 19:28:20 arie Exp $
 *
 */
public class GameTest extends GameTestCase {

    /**
     * Is each list of monsters a fresh one?
     */
    @Test
    public void testGetMonsters() {
        assertEquals(2, theGame.getMonsters().size());
        // each call to getMonsters should deliver a fresh copy.
        Vector<Monster> ms1 = theGame.getMonsters();
        Vector<Monster> ms2 = theGame.getMonsters();
        assertNotSame(ms1, ms2);
    }

    /**
     * Are the dx/dy in the player correctly set after moving
     * the player around?
     */
    @Test
    public void testDxDyPossibleMove() {
        // start dx/dy should be zero.
        assertEquals(0, theGame.getPlayerLastDx());
        assertEquals(0, theGame.getPlayerLastDy());
        // move to left empty cell -- dx should have beeen adjusted.
        theGame.movePlayer(1, 0);
        assertEquals(1, theGame.getPlayerLastDx());
        assertEquals(0, theGame.getPlayerLastDy());
        // move to up empty cell -- dy should have been adjusted.
        theGame.movePlayer(0, -1);
        assertEquals(0, theGame.getPlayerLastDx());
        assertEquals(-1, theGame.getPlayerLastDy());
    }

    /**
     * Do the player dx/dy remain unaltered if a move fails?
     */
    @Test
    public void testDxDyImpossibleMove() {
        // start dx/dy should be zero.
        assertEquals(0, theGame.getPlayerLastDx());
        assertEquals(0, theGame.getPlayerLastDy());
        // move to a wallcell -- dxdy should have been adjusted.
        theGame.movePlayer(0, -1);
        assertEquals(0, theGame.getPlayerLastDx());
        assertEquals(-1, theGame.getPlayerLastDy());
    }
    

    // // Equivalence partitions for char
    // private char char_1 = 'W';
    // private char char_2 = 'Q';
    // // Equivalence partitions for x
    // private int x_1 = -1;
    // private int x_2 = 0; 
    // private int x_3 = theGame.boardWidth();
    // // Equivalence partitions for x
    // private int y_1 = -1;
    // private int y_2 = 0;
    // private int y_3 = theGame.boardHeight();
    @Test(expected = AssertionError.class)
    public void testAddGuestFromCode111() {
        try {
            theGame.addGuestFromCode('W', -1, -1);
        } catch (Exception e)
        {
            assert false;
        }
    }
    @Test(expected = AssertionError.class)
    public void testAddGuestFromCode211() {
        try {
            theGame.addGuestFromCode('Q', -1, -1);
        } catch (Exception e)
        {
            assert false;
        }
    }
    @Test(expected = AssertionError.class)
    public void testAddGuestFromCode121() {
        try {
            theGame.addGuestFromCode('W', 0, -1);
        } catch (Exception e)
        {
            assert false;
        }
    }
    @Test(expected = AssertionError.class)
    public void testAddGuestFromCode221() {
        try {
            theGame.addGuestFromCode('Q', 0, -1);
        } catch (Exception e)
        {
            assert false;
        }
    }
    @Test(expected = AssertionError.class)
    public void testAddGuestFromCode131() {
        try {
            theGame.addGuestFromCode('W', theGame.boardWidth(), -1);
        } catch (Exception e)
        {
            assert false;
        }
    }
    @Test(expected = AssertionError.class)
    public void testAddGuestFromCode231() {
        try {
            theGame.addGuestFromCode('Q', theGame.boardWidth(), -1);
        } catch (Exception e)
        {
            assert false;
        }
    }

    @Test(expected = AssertionError.class)
    public void testAddGuestFromCode112() {
        try {
            theGame.addGuestFromCode('W', -1, 0);
        } catch (Exception e)
        {
            assert false;
        }
    }
    @Test(expected = AssertionError.class)
    public void testAddGuestFromCode212() {
        try {
            theGame.addGuestFromCode('Q', -1, 0);
        } catch (Exception e)
        {
            assert false;
        }
    }
    @Test(expected = AssertionError.class)
    public void testAddGuestFromCode113() {
        try {
            theGame.addGuestFromCode('W', -1, theGame.boardHeight());
        } catch (Exception e)
        {
            assert false;
        }
    }
    @Test(expected = AssertionError.class)
    public void testAddGuestFromCode213() {
        try {
            theGame.addGuestFromCode('Q', -1, theGame.boardHeight());
        } catch (Exception e)
        {
            assert false;
        }
    }
    @Test
    public void testAddGuestFromCode122() {
        theGame.addGuestFromCode('W', 0, 0);
    }
    @Test(expected = AssertionError.class)
    public void testAddGuestFromCode222() {
        try {
            theGame.addGuestFromCode('Q', 0, 0);
        } catch (Exception e)
        {
            assert false;
        }
    }
    @Test(expected = AssertionError.class)
    public void testAddGuestFromCode123() {
        try {
            theGame.addGuestFromCode('W', 0, theGame.boardHeight());
        } catch (Exception e)
        {
            assert false;
        }
    }
    @Test(expected = AssertionError.class)
    public void testAddGuestFromCode223() {
        try {
            theGame.addGuestFromCode('Q', 0, theGame.boardHeight());
        } catch (Exception e)
        {
            assert false;
        }
    }
    @Test(expected = AssertionError.class)
    public void testAddGuestFromCode132() {
        try {
            theGame.addGuestFromCode('W', theGame.boardWidth(), 0);
        } catch (Exception e)
        {
            assert false;
        }
    }
    @Test(expected = AssertionError.class)
    public void testAddGuestFromCode232() {
        try {
            theGame.addGuestFromCode('Q', theGame.boardWidth(), 0);
        } catch (Exception e)
        {
            assert false;
        }
    }
    @Test(expected = AssertionError.class)
    public void testAddGuestFromCode133() {
        try {
            theGame.addGuestFromCode('W', theGame.boardWidth(), theGame.boardHeight());
        } catch (Exception e)
        {
            assert false;
        }
    }
    @Test(expected = AssertionError.class)
    public void testAddGuestFromCode233() {
        try {
            theGame.addGuestFromCode('Q', theGame.boardWidth(), theGame.boardHeight());
        } catch (Exception e)
        {
            assert false;
        }
    }

    @Test(expected = AssertionError.class)
    public void testAddGuestFromCodeNonEmptyCell() {
        try {
            theGame.addGuestFromCode('F', 0, 1);
        } catch (Exception e)
        {
            assert false;
        }
    }
}
