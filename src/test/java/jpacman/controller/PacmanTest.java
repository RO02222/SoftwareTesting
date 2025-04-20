package jpacman.controller;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class PacmanTest {

    private Pacman pacman;

    @Before public void setUp() throws IOException {
        pacman = new Pacman();
    }

    @FunctionalInterface
    interface Runnable {
        void run();
    }

    private void testMove(Runnable move, int dx, int dy) {
        pacman.start();
        var old_location = pacman.getEngine().getPlayer().getLocation();
        move.run();
        assertEquals(dx, pacman.getEngine().getPlayerLastDx());
        assertEquals(dy, pacman.getEngine().getPlayerLastDy());
        var location = pacman.getEngine().getPlayer().getLocation();
        assertEquals(old_location.getX() + dx, location.getX());
        assertEquals(old_location.getY() + dy, location.getY());
    }

    @Test public void testPlayerMoveLeft() {
        testMove(() -> pacman.left(),-1, 0);
    }

    @Test public void testPlayerMoveRight() {
        testMove(() -> pacman.right(),1, 0);
    }

    @Test public void testPlayerMoveUp() {
        testMove(() -> pacman.up(),0, -1);
    }

    @Test public void testPlayerMoveDown() {
        testMove(() -> pacman.down(),0, 1);
    }

    @Test
    public void testMainWithArgsPrintsWarning() {
        PrintStream originalErr = System.err;
        ByteArrayOutputStream errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));
        try {
            Pacman.main(new String[]{"ignored-argument"});
            String output = errContent.toString();
            assertFalse(output.isEmpty());
        } catch (IOException e) {
            assertTrue(false);
        } finally {
            System.setErr(originalErr);
        }
    }
}
