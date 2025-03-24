package jpacman.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


/**
 * Systematic testing of the game state transitions.
 *
 * The test makes use of the simple map and its containing monsters
 * and players, as defined in the GameTestCase.
 * <p>
 *
 * @author Arie van Deursen; Aug 5, 2003
 * @version $Id: EngineTest.java,v 1.6 2008/02/04 23:00:12 arie Exp $
 */
public class EngineTest extends GameTestCase {

    /**
     * The engine that we'll push along every possible transition.
     */
    private Engine theEngine;


    /**
     * Set up an Engine, making use of the Game object
     * (with a small map containing all sorts of guests)
     * created in the superclass.
     */
    @Before public void setUp() {
        theEngine = new Engine(theGame);
        assertTrue(theEngine.inStartingState());
    }

    @Test
    public void StartGameTakeBreakDie()
    {
        assertTrue(theEngine.inStartingState());
        assertTrue(theEngine.invariant());
        theEngine.start();
        assertTrue(theEngine.inPlayingState());
        assertTrue(theEngine.invariant());
        theEngine.quit();
        assertTrue(theEngine.inHaltedState());
        assertTrue(theEngine.invariant());
        theEngine.start();
        assertTrue(theEngine.inPlayingState());
        assertTrue(theEngine.invariant());
        theEngine.movePlayer(0, 1);
        assertTrue(theEngine.inDiedState());
        assertTrue(theEngine.inGameOverState());
        assertTrue(theEngine.invariant());
    }

    @Test
    public void StartGameKillRestartEatRoamWin()
    {
        assertTrue(theEngine.inStartingState());
        assertTrue(theEngine.invariant());
        theEngine.start();
        assertTrue(theEngine.inPlayingState());
        assertTrue(theEngine.invariant());
        theEngine.moveMonster(theMonster, 0, -1);
        assertTrue(theEngine.inDiedState());
        assertTrue(theEngine.inGameOverState());
        assertTrue(theEngine.invariant());
        theEngine.start();
        assertTrue(theEngine.inPlayingState());
        assertTrue(theEngine.invariant());
        theEngine.movePlayer(-1, 0);
        assertTrue(theEngine.inPlayingState());
        assertTrue(theEngine.invariant());
        theEngine.moveMonster(theMonster, 1, 0);
        assertTrue(theEngine.inPlayingState());
        assertTrue(theEngine.invariant());
        theEngine.movePlayer(0, 1);
        assertTrue(theEngine.inGameOverState());
        assertTrue(theEngine.inWonState());
        assertTrue(theEngine.invariant());
    }

    @Test
    public void StartingSneakPath()
    {
        assertTrue(theEngine.inStartingState());
        assertTrue(theEngine.invariant());
        theEngine.quit(); // e_quit
        assertTrue(theEngine.inStartingState());
        assertTrue(theEngine.invariant());
        var playerLocation = thePlayer.getLocation();
        theEngine.movePlayer(-1, 0); // e_moveP_live
        assertTrue(theEngine.inStartingState());
        assertTrue(theEngine.invariant());
        assertEquals(playerLocation, thePlayer.getLocation());
        theEngine.movePlayer(0, 1); // e_moveP_die
        assertTrue(theEngine.inStartingState());
        assertTrue(theEngine.invariant());
        assertEquals(playerLocation, thePlayer.getLocation());
        var monsterLocation = theMonster.getLocation();
        theEngine.moveMonster(theMonster, 0, 1); // e_moveM_kill
        assertTrue(theEngine.inStartingState());
        assertTrue(theEngine.invariant());
        assertEquals(monsterLocation, theMonster.getLocation());
        // GameTestCase Simple map contains two pieces of food
        // Delete one of them and try to move to last food piece
        theGame.addGuestFromCode(Guest.EMPTY_TYPE, 0, 2);
        theEngine.movePlayer(-1, 0); // e_moveP_win
        assertTrue(theEngine.inStartingState());
        assertTrue(theEngine.invariant());
        assertEquals(playerLocation, thePlayer.getLocation());
        theEngine.moveMonster(theMonster, 1, 0); // e_moveM_roam
        assertTrue(theEngine.inStartingState());
        assertTrue(theEngine.invariant());
        assertEquals(monsterLocation, theMonster.getLocation());
    }

    /**
     * Set the engine to s_playing, making use of the Game object
     * (with a small map containing all sorts of guests)
     * created in the superclass.
     */
    private void SetupPlayingState()
    {
        assert theEngine.inStartingState();
        theEngine.start();
    }

    @Test
    public void PlayingSneakPath()
    {
        SetupPlayingState();
        assertTrue(theEngine.inPlayingState());
        assertTrue(theEngine.invariant());
        theEngine.start();
        assertTrue(theEngine.inPlayingState());
        assertTrue(theEngine.invariant());
    }

    /**
     * Set the engine to s_halted, making use of the Game object
     * (with a small map containing all sorts of guests)
     * created in the superclass.
     */
    private void SetupHaltedState()
    {
        assert theEngine.inStartingState();
        SetupPlayingState();
        theEngine.quit();
    }

    @Test
    public void HaltedSneakPath()
    {
        SetupHaltedState();
        assertTrue(theEngine.inHaltedState());
        assertTrue(theEngine.invariant());
        theEngine.quit(); // e_quit
        assertTrue(theEngine.inHaltedState());
        assertTrue(theEngine.invariant());
        var playerLocation = thePlayer.getLocation();
        theEngine.movePlayer(-1, 0); // e_moveP_live
        assertTrue(theEngine.inHaltedState());
        assertTrue(theEngine.invariant());
        assertEquals(playerLocation, thePlayer.getLocation());
        theEngine.movePlayer(0, 1); // e_moveP_die
        assertTrue(theEngine.inHaltedState());
        assertTrue(theEngine.invariant());
        assertEquals(playerLocation, thePlayer.getLocation());
        var monsterLocation = theMonster.getLocation();
        theEngine.moveMonster(theMonster, 0, 1); // e_moveM_kill
        assertTrue(theEngine.inHaltedState());
        assertTrue(theEngine.invariant());
        assertEquals(monsterLocation, theMonster.getLocation());
        // GameTestCase Simple map contains two pieces of food
        // Delete one of them and try to move to last food piece
        theGame.addGuestFromCode(Guest.EMPTY_TYPE, 0, 2);
        theEngine.movePlayer(-1, 0); // e_moveP_win
        assertTrue(theEngine.inHaltedState());
        assertTrue(theEngine.invariant());
        assertEquals(playerLocation, thePlayer.getLocation());
        theEngine.moveMonster(theMonster, 1, 0); // e_moveM_roam
        assertTrue(theEngine.inHaltedState());
        assertTrue(theEngine.invariant());
        assertEquals(monsterLocation, theMonster.getLocation());
    }

    /**
     * Set the engine to s_gameover.died, making use of the Game object
     * (with a small map containing all sorts of guests)
     * created in the superclass.
     */
    private void SetupGamoveOverDiedState()
    {
        assert theEngine.inStartingState();
        SetupPlayingState();
        theEngine.movePlayer(0, 1);
    }

    @Test
    public void GameOverDiedSneakPath()
    {
        SetupGamoveOverDiedState();
        assertTrue(theEngine.inGameOverState());
        assertTrue(theEngine.inDiedState());
        assertTrue(theEngine.invariant());
        theEngine.quit(); // e_quit
        assertTrue(theEngine.inGameOverState());
        assertTrue(theEngine.inDiedState());
        assertTrue(theEngine.invariant());
        var playerLocation = thePlayer.getLocation();
        theEngine.movePlayer(-1, 0); // e_moveP_live
        assertTrue(theEngine.inGameOverState());
        assertTrue(theEngine.inDiedState());
        assertTrue(theEngine.invariant());
        assertEquals(playerLocation, thePlayer.getLocation());
        theEngine.movePlayer(0, 1); // e_moveP_die
        assertTrue(theEngine.inGameOverState());
        assertTrue(theEngine.inDiedState());
        assertTrue(theEngine.invariant());
        assertEquals(playerLocation, thePlayer.getLocation());
        var monsterLocation = theMonster.getLocation();
        theEngine.moveMonster(theMonster, 0, 1); // e_moveM_kill
        assertTrue(theEngine.inGameOverState());
        assertTrue(theEngine.inDiedState());
        assertTrue(theEngine.invariant());
        assertEquals(monsterLocation, theMonster.getLocation());
        // GameTestCase Simple map contains two pieces of food
        // Delete one of them and try to move to last food piece
        theGame.addGuestFromCode(Guest.EMPTY_TYPE, 0, 2);
        theEngine.movePlayer(-1, 0); // e_moveP_win
        assertTrue(theEngine.inGameOverState());
        assertTrue(theEngine.inDiedState());
        assertTrue(theEngine.invariant());
        assertEquals(playerLocation, thePlayer.getLocation());
        theEngine.moveMonster(theMonster, 1, 0); // e_moveM_roam
        assertTrue(theEngine.inGameOverState());
        assertTrue(theEngine.inDiedState());
        assertTrue(theEngine.invariant());
        assertEquals(monsterLocation, theMonster.getLocation());
    }

    /**
     * Set the engine to s_gameover.won, making use of the Game object
     * (with a small map containing all sorts of guests)
     * created in the superclass.
     */
    private void SetupGamoveOverWonState()
    {
        assert theEngine.inStartingState();
        SetupPlayingState();
        theEngine.movePlayer(-1, 0);
        theEngine.movePlayer(0, 1);
    }

    @Test
    public void GameOverWonSneakPath()
    {
        SetupGamoveOverWonState();
        assertTrue(theEngine.inGameOverState());
        assertTrue(theEngine.inWonState());
        assertTrue(theEngine.invariant());
        theEngine.quit(); // e_quit
        assertTrue(theEngine.inGameOverState());
        assertTrue(theEngine.inWonState());
        assertTrue(theEngine.invariant());
        var playerLocation = thePlayer.getLocation();
        theEngine.movePlayer(0, 1); // e_moveP_live
        assertTrue(theEngine.inGameOverState());
        assertTrue(theEngine.inWonState());
        assertTrue(theEngine.invariant());
        assertEquals(playerLocation, thePlayer.getLocation());
        theEngine.movePlayer(1, 0); // e_moveP_die
        assertTrue(theEngine.inGameOverState());
        assertTrue(theEngine.inWonState());
        assertTrue(theEngine.invariant());
        assertEquals(playerLocation, thePlayer.getLocation());
        var monsterLocation = theMonster.getLocation();
        theEngine.moveMonster(theMonster, 0, 1); // e_moveM_kill
        assertTrue(theEngine.inGameOverState());
        assertTrue(theEngine.inWonState());
        assertTrue(theEngine.invariant());
        assertEquals(monsterLocation, theMonster.getLocation());
        // GameTestCase Simple map contains two pieces of food
        // Add one of them and try to move to last food piece
        // this event does not make sense to test
        // theGame.addGuestFromCode(Guest.FOOD_TYPE, 0, 1);
        // theEngine.movePlayer(0, -1); // e_moveP_win
        // assertTrue(theEngine.inGameOverState());
        // assertTrue(theEngine.inWonState());
        // assertTrue(theEngine.invariant());
        // assertEquals(playerLocation, thePlayer.getLocation());
        theEngine.moveMonster(theMonster, 1, 0); // e_moveM_roam
        assertTrue(theEngine.inGameOverState());
        assertTrue(theEngine.inWonState());
        assertTrue(theEngine.invariant());
        assertEquals(monsterLocation, theMonster.getLocation());
    }
}
