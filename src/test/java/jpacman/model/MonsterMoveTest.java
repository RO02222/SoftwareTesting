package jpacman.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Specialize the general MoveTest test suite to one
 * that is tailored to MonsterMoves.
 * Thanks to inheritance, all test cases from MoveTest
 * are also methods in MonsterMoveTest, thus helping us
 * to test conformance with Liskov's Substitution Principle (LSP)
 * of the Move hierarchy.
 */
public class MonsterMoveTest extends MoveTest {

    /**
     * The move the monster would like to make.
     */
    private MonsterMove aMonsterMove;

    /**
     * Simple test of a few getters.
     */
    @Test
    public void testSimpleGetters() {
        MonsterMove monsterMove = new MonsterMove(theMonster, emptyCell);
        assertEquals(theMonster, monsterMove.getMonster());
        assertTrue(monsterMove.movePossible());
        assertFalse(monsterMove.playerDies());
        assertTrue(monsterMove.invariant());
    }


    /**
     * Create a move object that will be tested.
     *  @see MoveTest#createMove(Cell)
     *  @param target The cell to be occupied by the move.
     *  @return The move to be tested.
     */
    @Override
    protected MonsterMove createMove(Cell target) {
        aMonsterMove = new MonsterMove(theMonster, target);
        return aMonsterMove;
    }

    @Test
    public void testMonsterMoveToPlayer() {
        var monsterMove = createMove(playerCell);
        assertTrue(monsterMove.initialized());

        assertFalse(monsterMove.movePossible());
        assertTrue(monsterMove.playerDies());
        assertTrue(monsterMove.invariant());
    }

    @Test
    public void testMonsterMoveToMonster() {
        var monsterMove = createMove(monsterCell);
        assertTrue(monsterMove.initialized());

        assertFalse(monsterMove.movePossible());
        assertFalse(monsterMove.playerDies());
        assertTrue(monsterMove.invariant());
    }

    @Test
    public void testMonsterMoveToFood() {
        var monsterMove = createMove(foodCell);
        assertTrue(monsterMove.initialized());

        assertFalse(monsterMove.movePossible());
        assertFalse(monsterMove.playerDies());
        assertTrue(monsterMove.invariant());
    }

    @Test
    public void testMonsterMoveToWall() {
        var monsterMove = createMove(wallCell);
        assertTrue(monsterMove.initialized());

        assertFalse(monsterMove.movePossible());
        assertFalse(monsterMove.playerDies());
        assertTrue(monsterMove.invariant());
    }

    @Test
    public void testMonsterMoveToEmpty() {
        var monsterMove = createMove(emptyCell);
        assertTrue(monsterMove.initialized());

        assertTrue(monsterMove.movePossible());
        assertTrue(monsterMove.invariant());
        monsterMove.apply();

        assertEquals(theMonster.getLocation().getX(), emptyCell.getX());
        assertEquals(theMonster.getLocation().getY(), emptyCell.getY());

        assertFalse(monsterMove.playerDies());
        assertTrue(monsterMove.invariant());
    }
}
