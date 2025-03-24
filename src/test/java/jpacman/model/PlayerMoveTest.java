package jpacman.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Specialize the general MoveTest test suite to one
 * that is tailored to PlayerMoves.
 * Thanks to inheritance, all test cases from MoveTest
 * are also methods in PlayerMoveTest, thus helping us
 * to test conformance with Liskov's Substitution Principle (LSP)
 * of the Move hierarchy.
 * <p>
 * @author Arie van Deursen; August 21, 2003.
 * @version $Id: PlayerMoveTest.java,v 1.8 2008/02/10 19:51:11 arie Exp $
 */
public class PlayerMoveTest extends MoveTest {

    /**
     * The move the player would like to make.
     */
    private PlayerMove aPlayerMove;

    /**
     * Simple test of a few getters.
     */
    @Test
    public void testSimpleGetters() {
        PlayerMove playerMove = new PlayerMove(thePlayer, foodCell);
        assertEquals(thePlayer, playerMove.getPlayer());
        assertTrue(playerMove.movePossible());
        assertFalse(playerMove.playerDies());
        assertEquals(1, playerMove.getFoodEaten());
        assertTrue(playerMove.invariant());
    }


    /**
     * Create a move object that will be tested.
     *
     * @param target The cell to be occupied by the move.
     * @return The move to be tested.
     * @see jpacman.model.MoveTest#createMove(jpacman.model.Cell)
     */
    @Override
    protected PlayerMove createMove(Cell target) {
        aPlayerMove = new PlayerMove(thePlayer, target);
        return aPlayerMove;
    }

    @Test
    public void testPlayerMoveToPlayer() {
        var playerMove = createMove(playerCell);
        assertTrue(playerMove.initialized());

        assertFalse(playerMove.movePossible());
        assertFalse(playerMove.playerDies());
        assertTrue(playerMove.invariant());
    }

    @Test
    public void testPlayerMoveToMonster() {
        var playerMove = createMove(monsterCell);
        assertTrue(playerMove.initialized());

        assertFalse(playerMove.movePossible());
        assertTrue(playerMove.playerDies());
        assertTrue(playerMove.invariant());
    }

    @Test
    public void testPlayerMoveToFood() {
        var oldFoodEaten = thePlayer.getPointsEaten();
        var playerMove = createMove(foodCell);
        assertTrue(playerMove.initialized());
        var foodAmount = playerMove.getFoodEaten();

        assertTrue(playerMove.movePossible());
        assertTrue(playerMove.invariant());
        playerMove.apply();

        assertEquals(thePlayer.getLocation().getX(), foodCell.getX());
        assertEquals(thePlayer.getLocation().getY(), foodCell.getY());
        assertEquals(thePlayer.getPointsEaten(), oldFoodEaten + foodAmount);
        assertFalse(playerMove.playerDies());
        assertTrue(playerMove.moveDone());
        assertTrue(playerMove.invariant());
    }

    @Test
    public void testPlayerMoveToWall() {
        var playerMove = createMove(wallCell);
        assertTrue(playerMove.initialized());

        assertFalse(playerMove.movePossible());
        assertFalse(playerMove.playerDies());
        assertTrue(playerMove.invariant());
    }

    @Test
    public void testPlayerMoveToEmpty() {
        var oldFoodEaten = thePlayer.getPointsEaten();
        var playerMove = createMove(emptyCell);
        assertTrue(playerMove.initialized());
        assertTrue(playerMove.movePossible());
        assertTrue(playerMove.invariant());
        playerMove.apply();

        assertEquals(thePlayer.getLocation().getX(), emptyCell.getX());
        assertEquals(thePlayer.getLocation().getY(), emptyCell.getY());
        assertEquals(thePlayer.getPointsEaten(), oldFoodEaten);
        assertFalse(playerMove.playerDies());
        assertTrue(playerMove.moveDone());
        assertTrue(playerMove.invariant());
    }

    @Test
    public void testPlayerMoveUndoToEmpty() {
        var playerMove = createMove(emptyCell);
        var originalLocation = thePlayer.getLocation();
        var originalPoints = thePlayer.getPointsEaten();
        var originalInhabitant = emptyCell.getInhabitant();
        assertTrue(playerMove.movePossible());
        playerMove.apply();
        assertTrue(playerMove.invariant());
        assertTrue(thePlayer.playerInvariant());
        assertTrue(playerMove.moveDone());

        playerMove.undo();

        assertEquals(thePlayer.getLocation(), originalLocation);
        assertEquals(thePlayer.getPointsEaten(), originalPoints);
        assertEquals(emptyCell.getInhabitant(), originalInhabitant);
        assertFalse(theGame.playerDied());
        assertTrue(playerMove.movePossible());
        assertTrue(playerMove.invariant());
        assertTrue(thePlayer.playerInvariant());
    }

    @Test
    public void testPlayerMoveUndoToFood() {
        var playerMove = createMove(foodCell);
        var originalLocation = thePlayer.getLocation();
        var originalPoints = thePlayer.getPointsEaten();
        var originalInhabitant = foodCell.getInhabitant();
        assertTrue(playerMove.movePossible());
        playerMove.apply();
        assertTrue(playerMove.invariant());
        assertTrue(thePlayer.playerInvariant());
        assertTrue(playerMove.moveDone());

        playerMove.undo();

        assertEquals(thePlayer.getLocation(), originalLocation);
        assertEquals(thePlayer.getPointsEaten(), originalPoints);
        assertEquals(foodCell.getInhabitant(), originalInhabitant);
        assertFalse(theGame.playerDied());
        assertTrue(playerMove.movePossible());
        assertTrue(playerMove.invariant());
        assertTrue(thePlayer.playerInvariant());
    }
}