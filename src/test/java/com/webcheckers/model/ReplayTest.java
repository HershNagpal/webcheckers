package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Unit test suite for the ReplayController class.
 *
 * @author Michael Kha
 */
@Tag("Model-tier")
public class ReplayTest {

    private static final String gameID = "1+2+0";

    /**
     * Component under test
     */
    private Replay CuT;

    /**
     * friendly objects
     */
    private Game game;

    /**
     * mock objects
     */
    private Player player;

    /**
     * Setup the objects for each test
     */
    @BeforeEach
    public void setup() {
        player = mock(Player.class);
        Player red = new Player("1");
        Player white = new Player("2");
        game = new Game(red, white, 0);
        CuT = new Replay(game);
    }

    /**
     * Test the message about the next turn is false.
     */
    @Test
    public void testNextTurnFalse() {
        assertFalse(CuT.nextTurn());
    }

    /**
     * Test the message about the previous turn is false.
     */
    @Test
    public void testPreviousTurnFalse() {
        assertFalse(CuT.previousTurn());
    }

    /**
     * Test the message about the next turn is true.
     */
    @Test
    public void testNextTurnTrue() {
        // Simulate a move
        game.validateMove(new Move(new Position(2, 5),
                new Position(3, 4)).flipMove());
        game.submitTurn();
        CuT = new Replay(game);
        assertTrue(CuT.nextTurn());
    }

    /**
     * Test the message about the previous turn is true.
     */
    @Test
    public void testPreviousTurnTrue() {
        // Simulate a move
        game.validateMove(new Move(new Position(2, 5),
                new Position(3, 4)).flipMove());
        game.submitTurn();
        CuT = new Replay(game);
        // Go to the next turn in replay
        CuT.nextTurn();
        // Previous should be true now
        assertTrue(CuT.previousTurn());
    }

    /**
     * Test that the modeOptions map is populated correctly.
     */
    @Test
    public void testGetModeOptions() {
        // When no moves have been made
        Map<String, Object> modeOptions = CuT.getModeOptions();
        assertEquals(modeOptions.get("hasPrevious"), false);
        assertEquals(modeOptions.get("hasNext"), false);

        // When moves have been made
        game.validateMove(new Move(new Position(2, 5),
                new Position(3, 4)).flipMove());
        game.submitTurn();
        CuT = new Replay(game);
        modeOptions = CuT.getModeOptions();
        assertEquals(modeOptions.get("hasPrevious"), false);
        assertEquals(modeOptions.get("hasNext"), true);

        // Last move made
        CuT.nextTurn();
        modeOptions = CuT.getModeOptions();
        assertEquals(modeOptions.get("hasPrevious"), true);
        assertEquals(modeOptions.get("hasNext"), false);
    }

}
