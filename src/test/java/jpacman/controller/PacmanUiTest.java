package jpacman.controller;

import jpacman.model.Engine;
import org.junit.Before;
import org.junit.Test;

import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Fairly basic test cases for the image factory.
 * @author Arie van Deursen, TU Delft, created 2007.
 */
public class PacmanUiTest {

    /**
     * The factory under test.
     */
    private Pacman pacman;
    private PacmanUI pacmanUi;

    private Map<String, KeyEvent> keyMap = new HashMap<String, KeyEvent>();

    /**
     * Actually create the image factory.
     * @throws IOException if images can't be found.
     */
    @Before public void setUp() throws IOException {
        pacman = new Pacman();
        pacmanUi = new PacmanUI(pacman.getEngine(), pacman);

        long when = System.currentTimeMillis();
        int modifiers = 0;
        keyMap.put("VK_UP", new KeyEvent(pacmanUi, KeyEvent.KEY_PRESSED, when, modifiers, KeyEvent.VK_UP, KeyEvent.CHAR_UNDEFINED));
        keyMap.put("VK_DOWN", new KeyEvent(pacmanUi, KeyEvent.KEY_PRESSED, when, modifiers, KeyEvent.VK_DOWN, KeyEvent.CHAR_UNDEFINED));
        keyMap.put("VK_LEFT", new KeyEvent(pacmanUi, KeyEvent.KEY_PRESSED, when, modifiers, KeyEvent.VK_LEFT, KeyEvent.CHAR_UNDEFINED));
        keyMap.put("VK_RIGHT", new KeyEvent(pacmanUi, KeyEvent.KEY_PRESSED, when, modifiers, KeyEvent.VK_RIGHT, KeyEvent.CHAR_UNDEFINED));
        keyMap.put("VK_Q", new KeyEvent(pacmanUi, KeyEvent.KEY_PRESSED, when, modifiers, KeyEvent.VK_Q, 'q'));
        keyMap.put("VK_E", new KeyEvent(pacmanUi, KeyEvent.KEY_PRESSED, when, modifiers, KeyEvent.VK_E, 'e'));
        keyMap.put("VK_S", new KeyEvent(pacmanUi, KeyEvent.KEY_PRESSED, when, modifiers, KeyEvent.VK_S, 's'));
    }

    @Test
    public void testAllKeypressesHandeled() {;
        var engine = pacman.getEngine();
        assertTrue(engine.inStartingState());

        pacmanUi.keyPressed(keyMap.get("VK_S"));
        assertTrue(engine.inPlayingState());
        var location1 = engine.getPlayer().getLocation();

        pacmanUi.keyPressed(keyMap.get("VK_LEFT"));
        var location2 = engine.getPlayer().getLocation();
        assertEquals(location1.getX() - 1, location2.getX());

        pacmanUi.keyPressed(keyMap.get("VK_UP"));
        location1 = engine.getPlayer().getLocation();
        assertEquals(location2.getY(), location1.getY() + 1);

        pacmanUi.keyPressed(keyMap.get("VK_RIGHT"));
        location2 = engine.getPlayer().getLocation();
        assertEquals(location1.getX() + 1, location2.getX());

        pacmanUi.keyPressed(keyMap.get("VK_DOWN"));
        location1 = engine.getPlayer().getLocation();
        assertEquals(location2.getY(), location1.getY() - 1);

        pacmanUi.keyPressed(keyMap.get("VK_E"));
        assertTrue(engine.inHaltedState());

        pacmanUi.keyPressed(keyMap.get("VK_S"));
        assertFalse(engine.inHaltedState());

        pacmanUi.keyPressed(keyMap.get("VK_Q"));
        assertTrue(engine.inHaltedState());
    }

    @Test
    public void testDisplaySize() {
        pacmanUi.display();
        int expectedHeight = pacmanUi.getBoardViewer().windowHeight() + 2 * 40;
        int actualHeight = pacmanUi.getHeight();
        assertEquals(expectedHeight, actualHeight);
    }

    @Test
    public void testSerialVersionUnchanged() {
        assertEquals(-59470379321937183L, PacmanUI.serialVersionUID);
    }
}