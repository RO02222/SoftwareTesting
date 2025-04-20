package jpacman;

import jpacman.controller.*;
import jpacman.model.*;
import junit.framework.JUnit4TestAdapter;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


/**
 * Test suite containing all Pacman junit test cases.
 *
 * This class is provided so that, e.g., ant's junit
 * task that is still based on junit 3.8.1. can run the
 * test suite as well.
 *
 * @author Arie van Deursen; Aug 1, 2003
 * @version $Id: TestAll.java,v 1.8 2008/02/04 11:00:38 arie Exp $
 */


/**
 * If you'd like your class to be tested,
 * include it below in the list of suite classes.
 */
@RunWith(Suite.class)
@SuiteClasses({
    BoardTest.class,
    CellTest.class,
    EngineTest.class,
    FoodTest.class,
    GameTest.class,
    GuestTest.class,
    MonsterMoveTest.class,
    ObserverTest.class,
    PlayerMoveTest.class,
    PlayerTest.class,

    BoardViewerTest.class,
    ImageFactoryTest.class,
    PacmanTest.class,
    PacmanUiTest.class,
    RandomMonsterMoverTest.class
})

public final class TestAll  {

    /**
     * Create a JUnit 3.8 Suite object that can be used to exercise
     * the JUnit 4 test suite from the command line or from ant.
     * @return The overall test suite.
     */
    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(TestAll.class);
    }

    /**
     * Convencience method making it easiest to exercise all Pacman test cases.
     * @param args All arguments are ignored.
     */
    public static void main(String[] args) {
        org.junit.runner.JUnitCore.runClasses(jpacman.TestAll.class);
    }

}
