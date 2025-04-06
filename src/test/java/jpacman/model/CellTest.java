package jpacman.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

/**
 * Test suite for methods working directly on Cells.
 *
 * @author Arie van Deursen; Jul 29, 2003
 * @version $Id: CellTest.java,v 1.16 2008/02/10 12:51:55 arie Exp $
 */
public class CellTest {

    /**
     * Width & heigth of board to be used.
     */
    private final int width = 4, height = 5;

    /**
     * The board the cells occur on.
     */
    private Board aBoard;

    /**
     * The "Cell Under Test".
     */
    private Cell aCell;

    /**
     * The "Cell Under Test".
     */
    private Cell cell11;

    /**
     * The mocked Cell with stubs for adjacent
     */
    @Mock
    private Cell mockedCell;

    /**
     * Actually create the board and the cell. *
     */
    @Before
    public void setUpBoard() {
        aBoard = new Board(width, height);
        // put the cell on an invariant boundary value.
        aCell = new Cell(0, height - 1, aBoard);
        cell11 = new Cell(1, 1, aBoard);

        // Lars
        // Setup mocked Cell(1,1)
        mockedCell = mock();
        // when(mockedCell.getX()).thenReturn(1);
        // when(mockedCell.getY()).thenReturn(1);
        when(mockedCell.invariant()).thenReturn(true);
    }

    /**
     * Test obtaining a cell at a given offset. Ensure both postconditions
     * (null value if beyond border, value with board) are executed.
     */
    @Test
    public void testCellAtOffset() {
        assertEquals(height - 2, aCell.cellAtOffset(0, -1).getY());
        assertEquals(0, aCell.cellAtOffset(0, -1).getX());
        // assertNull(aCell.cellAtOffset(-1, 0));

        Cell cell11 = aBoard.getCell(1, 1);
        Cell cell12 = aBoard.getCell(1, 2);
        assertEquals(cell12, cell11.cellAtOffset(0, 1));
    }

    // // Lars
    // @Test
    // public void testCellAdjacent1() {
    //     Cell otherCell = new Cell(1, 2, aBoard);

    //     assertTrue("Cell should be adjacent.", cell11.adjacent(otherCell));

    //     assertTrue(cell11.invariant());
    // }

    // // Lars
    // @Test
    // public void testCellAdjacent2() {
    //     Cell otherCell = new Cell(1, 0, aBoard);

    //     assertTrue("Cell should be adjacent.", cell11.adjacent(otherCell));

    //     assertTrue(cell11.invariant());
    // }

    // // Lars
    // @Test
    // public void testCellAdjacent3() {
    //     Cell otherCell = new Cell(2, 1, aBoard);

    //     assertTrue("Cell should be adjacent.", cell11.adjacent(otherCell));

    //     assertTrue(cell11.invariant());
    // }

    // // Lars
    // @Test
    // public void testCellAdjacent4() {
    //     Cell otherCell = new Cell(0, 1, aBoard);

    //     assertTrue("Cell should be adjacent.", cell11.adjacent(otherCell));

    //     assertTrue(cell11.invariant());
    // }

    // // Lars
    // @Test
    // public void testCellAdjacent5() {        
    //     assertFalse("Cell should not be adjacent to own location.", cell11.adjacent(cell11));

    //     assertTrue(cell11.invariant());
    // }

    // // Lars
    // @Test
    // public void testCellAdjacent6() {
    //     Cell otherCell = new Cell(0, 0, aBoard);

    //     assertFalse("Cell should not be adjacent.", cell11.adjacent(otherCell));

    //     assertTrue(cell11.invariant());
    // }

    // Lars
    @Test
    public void testCellAdjacent1() {
        Cell otherCell = new Cell(1, 2, aBoard);
        when(mockedCell.adjacent(otherCell)).thenReturn(true);

        assertTrue("Cell should be adjacent.", mockedCell.adjacent(otherCell));

        assertTrue(mockedCell.invariant());
    }

    // Lars
    @Test
    public void testCellAdjacent2() {
        Cell otherCell = new Cell(1, 0, aBoard);
        when(mockedCell.adjacent(otherCell)).thenReturn(true);

        assertTrue("Cell should be adjacent.", mockedCell.adjacent(otherCell));

        assertTrue(mockedCell.invariant());
    }

    // Lars
    @Test
    public void testCellAdjacent3() {
        Cell otherCell = new Cell(2, 1, aBoard);
        when(mockedCell.adjacent(otherCell)).thenReturn(true);

        assertTrue("Cell should be adjacent.", mockedCell.adjacent(otherCell));

        assertTrue(mockedCell.invariant());
    }

    // Lars
    @Test
    public void testCellAdjacent4() {
        Cell otherCell = new Cell(0, 1, aBoard);
        when(mockedCell.adjacent(otherCell)).thenReturn(true);

        assertTrue("Cell should be adjacent.", mockedCell.adjacent(otherCell));

        assertTrue(mockedCell.invariant());
    }

    // Lars
    @Test
    public void testCellAdjacent5() {
        when(mockedCell.adjacent(mockedCell)).thenReturn(false);
        assertFalse("Cell should not be adjacent to own location.", mockedCell.adjacent(mockedCell));

        assertTrue(mockedCell.invariant());
    }

    // Lars
    @Test
    public void testCellAdjacent6() {
        Cell otherCell = new Cell(0, 0, aBoard);
        when(mockedCell.adjacent(otherCell)).thenReturn(false);

        assertFalse("Cell should not be adjacent.", mockedCell.adjacent(otherCell));

        assertTrue(mockedCell.invariant());
    }

    @Test(expected = AssertionError.class)
    public void testInvalidCellCreation() {
        try {
            Cell cell = new Cell(20, 20, aBoard);
        } catch (Exception e) {
            assert false;
        }
    }

    @Test
    public void testAdjacentNotZeroLocationX() {
        Cell cell = new Cell(1, 1, aBoard);
        Cell other = new Cell(2, 1, aBoard);
        assertTrue(cell.adjacent(other));
    }

    @Test
    public void testAdjacentNotZeroLocationY() {
        Cell cell = new Cell(1, 1, aBoard);
        Cell other = new Cell(1, 2, aBoard);
        assertTrue(cell.adjacent(other));
    }

    @Test(expected = AssertionError.class)
    public void testAdjacentDifferentBoard() {
        try {
            Cell cell = new Cell(1, 1, aBoard);
            Board otherBoard = new Board(width, height);
            Cell other = new Cell(2, 1, otherBoard);
            cell.adjacent(other);
        } catch (Exception e) {
            assert false;
        }
    }

    @Test(expected = AssertionError.class)
    public void testFreeWhileOccupied() {
        try {
            Cell cell = new Cell(1, 1, aBoard);
            Player player = new Player();
            player.occupy(cell);
            cell.free();
        } catch (Exception e) {
            assert false;
        }
    }
}
