package jpacman.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

/**
 * Fairly basic test cases for the image factory.
 * @author Arie van Deursen, TU Delft, created 2007.
 */
public class ImageFactoryTest {

    /**
     * The factory under test.
     */
    private ImageFactory imf;

    /**
     * Actually create the image factory.
     * @throws IOException if images can't be found.
     */
    @Before public void setUp() throws IOException {
        imf = new ImageFactory();
    }

    /**
     * Are images for player properly loaded?
     */
    @Test public void testPlayer() {
        Image up = imf.player(0, -1, 1);
        Image down = imf.player(0, 1, 1);
        assertNotSame(up, down);
    }

    /**
     * Are monster images properly loaded?
     */
    @Test public void testMonster() {
        Image m1 = imf.monster(0);
        Image m2 = imf.monster(0);
        assertEquals(m1, m2);
    }

    @Test
    public void testPlayerDefault() {
        assertNotNull(imf.player(0,0,0));
    }

    @Test
    public void testMonsterAnimation() {
        Image m1 = imf.monster(0);
        Image m2 = imf.monster(1);
        Image m3 = imf.monster(2);
        assertNotSame(m1, m2);
        assertNotSame(m2, m3);
    }
}
