package jpacman.controller;

import jpacman.model.Engine;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.io.IOException;
import java.lang.reflect.Field;

import static org.junit.Assert.*;

public class BoardViewerTest {
    BoardViewer theBoardViewer;
    ImageFactory theImageFactory;
    JFrame frame;

    @Before public void setUp() throws IOException, NoSuchFieldException, IllegalAccessException {
        Field imageFactoryField = BoardViewer.class.getDeclaredField("imageFactory");
        imageFactoryField.setAccessible(true);
        var theEngine = new Engine();
        var pacman = new Pacman(theEngine);
        var theViewer = new PacmanUI(theEngine, pacman);
        theBoardViewer = theViewer.getBoardViewer();
        theImageFactory = (ImageFactory) imageFactoryField.get(theBoardViewer);

        frame = new JFrame();
        frame.add(theBoardViewer);
        frame.setSize(200, 200);
        frame.setVisible(true);
    }

    @Test
    public void testSerialVersionUnchanged() {
        assertEquals(-4976741292570616918L, BoardViewer.serialVersionUID);
    }

    @Test
    public void testAnimationSequence() {
        int count = theBoardViewer.getAnimationCount();
        while (count != 0) {
            theBoardViewer.nextAnimation();
            count = theBoardViewer.getAnimationCount();
        } /* Start at the beginning of the sequence */

        int count2;
        for (int i = 0; i < theImageFactory.monsterAnimationCount() * theImageFactory.playerAnimationCount() -1; i++) {
            theBoardViewer.nextAnimation();
            count2 = theBoardViewer.getAnimationCount();
            assertEquals(count + 1, count2);
            count = count2;
        }
        theBoardViewer.nextAnimation();
        assertEquals(0, theBoardViewer.getAnimationCount());
    }
}