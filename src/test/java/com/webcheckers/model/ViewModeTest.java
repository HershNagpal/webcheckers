package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * Unit test suite fot ViewMode enum.
 *
 * @author Michael Kha
 */
@Tag("Model-tier")
public class ViewModeTest {

    /**
     * Component under test
     */
    private ViewMode CuT;

    /**
     * Test the PLAY value
     */
    @Test
    public void testPlay() {
        CuT = ViewMode.PLAY;
        assertNotEquals(CuT, ViewMode.REPLAY);
        assertNotEquals(CuT, ViewMode.SPECTATOR);
    }

    /**
     * Test the REPLAY value
     */
    @Test
    public void testReplay() {
        CuT = ViewMode.REPLAY;
        assertNotEquals(CuT, ViewMode.PLAY);
        assertNotEquals(CuT, ViewMode.SPECTATOR);
    }

    /**
     * Test the SPECTATOR value
     */
    @Test
    public void testSpectator() {
        CuT = ViewMode.SPECTATOR;
        assertNotEquals(CuT, ViewMode.PLAY);
        assertNotEquals(CuT, ViewMode.REPLAY);
    }

}
