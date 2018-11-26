package com.webcheckers.appl;

import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test suite for the ReplayController class.
 *
 * @author Michael Kha
 */
@Tag("Application-tier")
public class ReplayControllerTest {

    private static final String gameID = "1+2+0";

    /**
     * Component under test
     */
    private ReplayController CuT;

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
        Map<String, Game> finishedGames = new HashMap<>();
        CuT = new ReplayController(finishedGames);
        player = mock(Player.class);
        Player red = new Player("1");
        Player white = new Player("2");
        game = new Game(red, white, 0);
        finishedGames.put(gameID, game);
    }

    /**
     * Test that a replay is retrieved and stored.
     */
    @Test
    public void testGetReplayGame() {
        Replay replay = CuT.getReplayGame(player, gameID);
        // No new replays are created if player has not stopped replaying
        assertEquals(replay, CuT.getReplayGame(player, gameID));
    }

    /**
     * Test that the player is removed from being a replayer.
     */
    @Test
    public void testStopReplaying() {
        Replay replay = CuT.getReplayGame(player, gameID);
        CuT.stopReplaying(player);
        // Replays are different if player stops replaying
        assertNotEquals(replay, CuT.getReplayGame(player, gameID));
    }

    /**
     * Test the message about the next turn is false.
     */
    @Test
    public void testNextTurnFalse() {
        CuT.getReplayGame(player, gameID);
        Message message = CuT.nextTurn(player);
        assertEquals(message.getText(), "false");
        assertEquals(message.getType(), MessageType.info);
    }

    /**
     * Test the message about the previous turn is false.
     */
    @Test
    public void testPreviousTurnFalse() {
        CuT.getReplayGame(player, gameID);
        Message message = CuT.previousTurn(player);
        assertEquals(message.getText(), "false");
        assertEquals(message.getType(), MessageType.info);
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
        CuT.getReplayGame(player, gameID);
        Message message = CuT.nextTurn(player);
        assertEquals(message.getText(), "true");
        assertEquals(message.getType(), MessageType.info);
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
        CuT.getReplayGame(player, gameID);
        // Go to the next turn in replay
        CuT.nextTurn(player);
        // Previous should be true now
        Message message = CuT.previousTurn(player);
        assertEquals(message.getText(), "true");
        assertEquals(message.getType(), MessageType.info);
    }

}
