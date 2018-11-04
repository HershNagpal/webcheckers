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
    private GameCenter gameCenter;

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
        dummy = mock(Player.class);
        move = mock(Move.class);

        gameCenter = new GameCenter(messenger);
        game = gameCenter.createGame(player, opponent);
    }

    /**
     * Test that the given player is already in a game or not.
     */
    @Test
    public void testPlayerInGame() {
        assertTrue(gameCenter.playerInGame(player));
        assertFalse(gameCenter.playerInGame(dummy));
    }

    /**
     * Test that a game can be found given a player.
     */
    @Test
    public void testGetGame() {
        assertEquals(game, gameCenter.getGame(player));
        assertNull(gameCenter.getGame(dummy));
    }

    /**
     * Test that a game can be created with two players.
     */
    @Test
    public void testCreateGame() {
        game = gameCenter.createGame(player, opponent);
        assertSame(game.getRedPlayer(), player);
        assertSame(game.getWhitePlayer(), opponent);
        assertTrue(game.isActivePlayer(player));
    }

    /**
     * Test that a game can be removed from the game center.
     */
    @Test
    public void testRemoveGame() {
        gameCenter.removeGame(game);
        assertNull(gameCenter.getGame(player));
        assertNull(gameCenter.getGame(opponent));
    }

    /**
     * Test that the turn status of a player can be checked.
     */
    @Test
    public void testCheckTurn() {
        Message message = new Message("true", MessageType.info);
        when(messenger.checkTurn(game, player)).thenReturn(message);
        Message centerMessage = gameCenter.checkTurn(player);
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
        Message centerMessage = gameCenter.validateMove(player, move);
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
        Message centerMessage = gameCenter.submitTurn(player);
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
        Message centerMessage = gameCenter.backupMove(player);
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
        Message centerMessage = gameCenter.resignGame(player);
        assertEquals(centerMessage.getType(), message.getType());
        assertEquals(centerMessage.getText(), message.getText());
    }


}
