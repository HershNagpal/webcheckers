package com.webcheckers.appl;

import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Set;

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

        CuT = new GameCenter(messenger, new HashMap<>());
        game = CuT.createGame(player, opponent);
    }

    /**
     * Test to check if there are ongoing games
     */
    @Test
    public void testGamesOngoing() {
        assertTrue(CuT.gamesOngoing());
        game.resignGame(player);
        CuT.updateGames(game);
        assertFalse(CuT.gamesOngoing());
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
        assertFalse(CuT.wasChallenged(player));
        game.resignGame(player);
        assertFalse(CuT.wasChallenged(opponent));
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
     * Test that the set of games is retrieved.
     */
    @Test
    public void testGetGames() {
        assertNotNull(CuT.getGames());
    }

    /**
     * Test that the size of the set of games is correct
     */
    @Test
    public void testSize() {
        assertEquals(1, CuT.size());
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
     * Test that to check if the given game is over.
     */
    @Test
    public void testIsGameOver() {
        Message message = new Message("", MessageType.info);
        assertFalse(CuT.isGameOver(game));
        when(messenger.resignGame(game, player)).thenReturn(message);
        game.resignGame(player);
        assertTrue(CuT.isGameOver(game));
    }

    @Test
    public void testUpdateGames() {
        Game test = null;
        // Defensive check test
        Set<Game> temp = CuT.getGames();
        CuT.updateGames(test);
        assertEquals(temp, CuT.getGames());
        // Game not over test
        CuT.updateGames(game);
        assertEquals(temp, CuT.getGames());
        // Game is over test
        game.resignGame(player);
        CuT.updateGames(game);
        assertNotEquals(temp, CuT.getGames());
    }

    /**
     * Test that to check if the given player is a winner.
     */
    @Test
    public void testIsWinner() {
        Message message = new Message("", MessageType.info);
        when(messenger.isWinner(game, player)).thenReturn(message);
        Message centerMessage = CuT.isWinner(game, player);
        assertEquals(centerMessage.getType(), messenger.isWinner(game, player).getType());
        assertEquals(centerMessage.getText(), messenger.isWinner(game, player).getText());
    }

    /**
     * Test that to check who won the game
     */
    @Test
    public void testWhoWon() {
        Message message = new Message("", MessageType.info);
        when(messenger.whoWon(game)).thenReturn(message);
        Message centerMessage = CuT.whoWon(game);
        assertEquals(centerMessage.getType(), messenger.whoWon(game).getType());
        assertEquals(centerMessage.getText(), messenger.whoWon(game).getText());
    }

    /**
     * Test that the turn status of a game has changed
     */
    @Test
    public void testCheckTurnGameID() {
        Message message = new Message("true", MessageType.info);
        Game game = CuT.createGame(new Player("1"), new Player("2"));
        when(messenger.checkTurn(game)).thenReturn(message);
        Message centerMessage = CuT.checkTurn("1+2+2");
        assertEquals(centerMessage.getType(), messenger.checkTurn(game).getType());
        assertEquals(centerMessage.getText(), messenger.checkTurn(game).getText());
    }

    /**
     * Test that the turn status of a player can be checked using a game and a player.
     */
    @Test
    public void testCheckTurnPlayer() {
        Message message = new Message("true", MessageType.info);
        when(messenger.checkTurn(game, player)).thenReturn(message);
        Message centerMessage = CuT.checkTurn(player);
        assertEquals(centerMessage.getType(), messenger.checkTurn(game, player).getType());
        assertEquals(centerMessage.getText(), messenger.checkTurn(game, player).getText());
        // Case where game ended and therefore removed from list of games
        game.resignGame(player);
        CuT.resignGame(player);
        CuT.updateGames(game);
        assertNull(CuT.checkTurn(player));
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
