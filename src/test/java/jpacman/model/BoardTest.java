package jpacman.model;

import org.junit.Before;
import org.junit.Test;

import jpacman.TestUtils;

import static org.junit.Assert.*;

/**
 * One of the simpler test classes, so a good point to start understanding
 * how testing JPacman has been setup.
 *
 * @author Arie van Deursen; Jul 29, 2003
 * @version $Id: BoardTest.java,v 1.10 2008/02/03 19:43:38 arie Exp $
 */
public class BoardTest {

    /**
     * The width & height of the board to be used.
     */
    private final int width = 5, height = 10;

    /**
     * The board to be used in the tests.
     */
    private Board theBoard;

    /**
     * Create a simple (empty) board to be used for testing.
     * Note that JUnit invokes such a Before method before every test case
     * so that we start in a fresh situation.
     */
    @Before
    public void setUp() {
        theBoard = new Board(width, height);
    }

    /**
     * Simple test of the width/height getters,
     * to get in the mood for testing.
     */
    @Test
    public void testGettingWidthHeight() {
        assertEquals(width, theBoard.getWidth());
        assertEquals(height, theBoard.getHeight());
    }

    /**
     * Obtain a cell that is on the board at given (x,y) position,
     * and check if the (x,y) coordinates of the Cell obtained are ok.
     * To make the test a little more exciting use a point that is on the
     * border (an "invariant on point" in Binder's terminology).
     */
    @Test
    public void testGettingCellsFromBoard() {
        // the coordinates to be used.
        int x = 0;
        int y = 0;

        // actually get the cell.
        Cell aCell = theBoard.getCell(x, y);

        // compare the coordinates.
        assertEquals(x, aCell.getX());
        assertEquals(y, aCell.getY());
    }

    /**
     * Let a guest occupy one of the Cells on the board, and
     * check if it is actually there.
     * Again, pick a point on the border (but a different one)
     * to (slightly) increase the chances of failure.
     */
    @Test
    public void testOccupy() {
        // place to put the guest.
        int x = width - 1;
        int y = height - 1;
        Cell aCell = theBoard.getCell(x, y);

        // guest to be put on the board.
        Food food = new Food();

        // put the guest on the cell.
        food.occupy(aCell);

        // verify its presence.
        assertEquals(food, theBoard.getGuest(x, y));
        assertEquals(Guest.FOOD_TYPE, theBoard.guestCode(x, y));
    }

    /**
     * Try to create an illegal board (e.g., negative sizes),
     * and verify that this generates an assertion failure.
     * This test also serves to illustrate how to test whether a method
     * generates an assertion failure if a precondition is violated.
     */
    @Test
    public void testFailingBoardCreation() {
        if (TestUtils.assertionsEnabled()) {
            boolean failureGenerated;
            try {
                new Board(-1, -1);
                failureGenerated = false;
            } catch (AssertionError ae) {
                failureGenerated = true;
            }
            assertTrue(failureGenerated);
        }
        // else: nothing to test -- no guarantees what so ever!
    }


//     // Equivalence partitions for x
//     private int x_1 = -1;
//     private int x_2 = 0; // width -1
//     private int x_3 = width;
//     // Equivalence partitions for y
//     private int y_1 = -1;
//     private int y_2 = 0; // height -1
//     private int y_3 = theBoard.getHeight();

    @Test
    public void testWithinBoarders22() {
        assertTrue(theBoard.withinBorders(0,0));
        assertTrue(theBoard.withinBorders(width -1,0));
        assertTrue(theBoard.withinBorders(0,height - 1));
        assertTrue(theBoard.withinBorders(width -1,height -1));
    }

    @Test
    public void testWithinBoarders12() {
        assertFalse(theBoard.withinBorders(-1,0));
        assertFalse(theBoard.withinBorders(-1,height -1));
    }

    @Test
    public void testWithinBoarders32() {
        assertFalse(theBoard.withinBorders(width,0));
        assertFalse(theBoard.withinBorders(width,height -1));
    }

    @Test
    public void testWithinBoarders21() {
        assertFalse(theBoard.withinBorders(0,-1));
        assertFalse(theBoard.withinBorders(width -1,-1));
    }


    @Test
    public void testWithinBoarders23() {
        assertFalse(theBoard.withinBorders(0,height));
        assertFalse(theBoard.withinBorders(width -1,height));
    }

    @Test
    public void testWithinBoarders11() {
        assertFalse(theBoard.withinBorders(-1,-1));
    }

    @Test
    public void testWithinBoarders13() {
        assertFalse(theBoard.withinBorders(-1,height));
    }

    @Test
    public void testWithinBoarders31() {
        assertFalse(theBoard.withinBorders(width,-1));
    }

    @Test
    public void testWithinBoarders33() {
        assertFalse(theBoard.withinBorders(width,height));
    }
}
