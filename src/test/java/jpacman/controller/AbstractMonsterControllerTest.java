package jpacman.controller;

import jpacman.model.Engine;
import org.junit.Test;

public abstract class AbstractMonsterControllerTest {

    abstract protected AbstractMonsterController createController(Engine e);

    @Test(expected = AssertionError.class)
    public void testMonsterMoverEngineNull() {
        createController(null);
    }
}
