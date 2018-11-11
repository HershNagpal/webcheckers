package com.webcheckers.appl;

import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test suite for GameCenter
 *
 * @author Michael Kha
 */
@Tag("Application-tier")
public class GameCenterTest {

    /**
     * Component under test
     */
    private GameCenter CuT;

    /**
     * friendly objects
     */
    private Game game;

    /**
     * mock objects
     */
    private Messenger messenger;
    private Player player;
    private Player opponent;
    private Player dummy;
    private Move move;

    /**
     * Setup the objects for each test.
     */
    @BeforeEach
    public void setup() {
        messenger = mock(Messenger.class);
        player = mock(Player.class);
        opponent = mock(Player.class);
        //Added to avoid nullPointer in Game constructor
        when(player.getName()).thenReturn("Player1");
        when(opponent.getName()).thenReturn("Player2");
        dummy = mock(Player.class);
        move = mock(Move.class);

        CuT = new GameCenter(messenger);
        game = CuT.createGame(player, opponent);
    }

    /**
     * Test that the given player is already in a game or not.
     */
    @Test
    public void testPlayerInGame() {
        assertTrue(CuT.playerInGame(player));
        assertFalse(CuT.playerInGame(dummy));
    }

    /**
     * Test that the given player was challenged to a game or not.
     */
    @Test
    public void testWasChallenged() {
        assertTrue(CuT.wasChallenged(opponent));
        assertFalse(CuT.wasChallenged(dummy));
    }

    /**
     * Test that a game can be found given a player.
     */
    @Test
    public void testGetGame() {
        assertEquals(game, CuT.getGame(player));
        assertNull(CuT.getGame(dummy));
    }

    /**
     * Test that a game can be created with two players.
     */
    @Test
    public void testCreateGame() {
        game = CuT.createGame(player, opponent);
        assertSame(game.getRedPlayer(), player);
        assertSame(game.getWhitePlayer(), opponent);
        assertTrue(game.isActivePlayer(player));
    }

    /**
     * Test that a game can be removed from the game center.
     */
    @Test
    public void testRemoveGame() {
        CuT.removeGame(game);
        assertNull(CuT.getGame(player));
        assertNull(CuT.getGame(opponent));
    }

    /**
     * Test that to check if the given game is over.
     */
    @Test
    public void testIsGameOver() {
        assertFalse(CuT.isGameOver(game));
        when(messenger.resignGame(game, player)).thenReturn(new Message("", MessageType.info));
        CuT.resignGame(player);
        assertTrue(CuT.isGameOver(game));
    }

    /**
     * Test that to check if the given player is a winner.
     */
    @Test
    public void testIsWinner() {
        assertFalse(CuT.isWinner(game, player));
    }

    /**
     * Test that the turn status of a player can be checked.
     */
    @Test
    public void testCheckTurn() {
        Message message = new Message("true", MessageType.info);
        when(messenger.checkTurn(game, player)).thenReturn(message);
        Message centerMessage = CuT.checkTurn(player);
        assertEquals(centerMessage.getType(), messenger.checkTurn(game, player).getType());
        assertEquals(centerMessage.getText(), messenger.checkTurn(game, player).getText());
    }

    /**
     * Test that a move made by a player is valid or invalid.
     */
    @Test
    public void testValidateMove() {
        Message message = new Message("", MessageType.info);
        when(messenger.validateMove(game, move)).thenReturn(message);
        Message centerMessage = CuT.validateMove(player, move);
        assertEquals(centerMessage.getType(), messenger.validateMove(game, move).getType());
        assertEquals(centerMessage.getText(), messenger.validateMove(game, move).getText());
    }

    /**
     * Test that submitting a turn was successful or not.
     */
    @Test
    public void testSubmitTurn() {
        Message message = new Message("", MessageType.info);
        when(messenger.submitTurn(game)).thenReturn(message);
        Message centerMessage = CuT.submitTurn(player);
        assertEquals(centerMessage.getType(), message.getType());
        assertEquals(centerMessage.getText(), message.getText());
    }

    /**
     * Test that backing up a move made this turn is successful or not.
     */
    @Test
    public void testBackupMove() {
        Message message = new Message("", MessageType.info);
        when(messenger.backupMove(game)).thenReturn(message);
        Message centerMessage = CuT.backupMove(player);
        assertEquals(centerMessage.getType(), message.getType());
        assertEquals(centerMessage.getText(), message.getText());
    }

    /**
     * Test that the player resigning was successful or not.
     */
    @Test
    public void testResignGame() {
        Message message = new Message("", MessageType.info);
        when(messenger.resignGame(game, player)).thenReturn(message);
        Message centerMessage = CuT.resignGame(player);
        assertEquals(centerMessage.getType(), message.getType());
        assertEquals(centerMessage.getText(), message.getText());
    }


}
