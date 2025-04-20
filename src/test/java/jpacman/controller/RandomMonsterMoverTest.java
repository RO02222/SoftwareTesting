package jpacman.controller;

import jpacman.model.Engine;
import jpacman.model.Game;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Fairly basic test cases for the image factory.
 * @author Arie van Deursen, TU Delft, created 2007.
 */
public class RandomMonsterMoverTest extends AbstractMonsterControllerTest{

    protected AbstractMonsterController createController(Engine e) {
        return new RandomMonsterMover(e);
    }


    /**
     * The factory under test.
     */
    private Pacman pacman;

    /**
     * Actually create the image factory.
     * @throws IOException if images can't be found.
     */
    @Before public void setUp() throws IOException {
        var map = new String[]
                {"WWWWWWWWW",
                 "W00000WPW",
                 "W00000WWW",
                 "W00M00WWW",
                 "W00000WWW",
                 "W00000WFW",
                 "WWWWWWWWW"};
        pacman = new Pacman(new Engine(new Game(map)));
    }

    @Test
    public void testGenerateAllRandomDirections() throws Exception {
        Field randomizerField = AbstractMonsterController.class.getDeclaredField("randomizer");
        randomizerField.setAccessible(true);
        Random random = (Random) randomizerField.get(null);
        random.setSeed(0L);
        /* Seed specially selected to have all 4 directions within the first 4 doTick() */
        pacman.start();
        var controller = new RandomMonsterMover(pacman.getEngine());
        controller.start();
        var monster = pacman.getEngine().getMonsters().get(0);
        var location = monster.getLocation();
        /* Right, Left, Up, Down */
        for (int i = 0; i < 4; i++) {
            controller.doTick();
        }
        assertEquals(location, monster.getLocation());
    }
}
