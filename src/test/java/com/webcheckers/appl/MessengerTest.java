package com.webcheckers.appl;

import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.webcheckers.appl.Messenger.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test suite for Messenger
 *
 * @author Michael Kha
 */
@Tag("Application-tier")
public class MessengerTest {

    /**
     * Component under test
     */
    private Messenger CuT;

    /**
     * mock objects
     */
    private Game game;
    private Player player;
    private Move move;

    /**
     * Setup the objects for each test.
     */
    @BeforeEach
    public void setup() {
        game = mock(Game.class);
        player = mock(Player.class);
        move = mock(Move.class);

        CuT = new Messenger();
    }

    /**
     * Test that the winner happened by resign and the opponent
     * did resign.
     */
    @Test
    public void testIsWinnerByResign() {
        Player opponent = mock(Player.class);
        when(game.didPlayerResign()).thenReturn(true);
        when(game.isWinner(player)).thenReturn(true);
        Message message = CuT.isWinner(game, player);
        assertEquals(message.getType(), OPP_RESIGN.getType());
        assertEquals(message.getText(), OPP_RESIGN.getText());
        message = CuT.isWinner(game, opponent);
        assertEquals(message.getType(), PLAYER_RESIGN.getType());
        assertEquals(message.getText(), PLAYER_RESIGN.getText());
    }

    /**
     * Test that the winner happened by an end game condition
     * and the other player lost.
     */
    @Test
    public void testIsWinnerByEndCondition() {
        Player opponent = mock(Player.class);
        when(game.didPlayerResign()).thenReturn(false);
        when(game.isWinner(player)).thenReturn(true);
        when(game.isGameOver()).thenReturn(true);
        Message message = CuT.isWinner(game, player);
        assertEquals(message.getType(), PLAYER_WIN.getType());
        assertEquals(message.getText(), PLAYER_WIN.getText());
        message = CuT.isWinner(game, opponent);
        assertEquals(message.getType(), PLAYER_LOSE.getType());
        assertEquals(message.getText(), PLAYER_LOSE.getText());
    }

    /**
     * Test that there is no winner and the game is still going.
     */
    @Test
    public void testIsWinnerNoWinner() {
        when(game.didPlayerResign()).thenReturn(false);
        when(game.isWinner(player)).thenReturn(true);
        Message message = CuT.isWinner(game, player);
        assertNull(message);
    }

    /**
     * Test that the true message is returned by checking the turn.
     */
    @Test
    public void testCheckTurnTrue() {
        when(game.isActivePlayer(player)).thenReturn(true);
        Message message = CuT.checkTurn(game, player);
        assertEquals(message.getType(), TURN_TRUE.getType());
        assertEquals(message.getText(), TURN_TRUE.getText());
    }

    /**
     * Test that the false message is returned by checking the turn.
     */
    @Test
    public void testCheckTurnFalse() {
        when(game.isActivePlayer(player)).thenReturn(false);
        Message message = CuT.checkTurn(game, player);
        assertEquals(message.getType(), TURN_FALSE.getType());
        assertEquals(message.getText(), TURN_FALSE.getText());
    }

    /**
     * Test that the true message is returned when a player resigned.
     */
    @Test
    public void testCheckTurnResign() {
        when(game.isActivePlayer(player)).thenReturn(true);
        when(game.didPlayerResign()).thenReturn(true);
        Message message = CuT.checkTurn(game, player);
        assertEquals(message.getType(), TURN_TRUE.getType());
        assertEquals(message.getText(), TURN_TRUE.getText());
    }

    /**
     * Test that the true message is returned by validating a move.
     */
    @Test
    public void testValidateMove() {
        when(game.validateMove(move)).thenReturn(true);
        Message message = CuT.validateMove(game, move);
        assertEquals(message.getType(), MOVE_TRUE.getType());
        assertEquals(message.getText(), MOVE_TRUE.getText());

    }

    /**
     * Test that the false message is returned by validating a move.
     */
    @Test
    public void testValidateMoveFalse() {
        when(game.validateMove(move)).thenReturn(false);
        Message message = CuT.validateMove(game, move);
        assertEquals(message.getType(), MOVE_FALSE.getType());
        assertEquals(message.getText(), MOVE_FALSE.getText());

    }


    /**
     * Test that the true message is returned by submitting a move.
     */
    @Test
    public void testSubmitTurnTrue() {
        when(game.submitTurn()).thenReturn(true);
        Message message = CuT.submitTurn(game);
        assertEquals(message.getType(), SUBMIT_TRUE.getType());
        assertEquals(message.getText(), SUBMIT_TRUE.getText());
    }

    /**
     * Test that the true message is returned by submitting a move.
     */
    @Test
    public void testSubmitTurnFalse() {
        when(game.submitTurn()).thenReturn(false);
        Message message = CuT.submitTurn(game);
        assertEquals(message.getType(), SUBMIT_FALSE.getType());
        assertEquals(message.getText(), SUBMIT_FALSE.getText());
    }

    /**
     * Test that the true message is returned by backing up a move.
     */
    @Test
    public void testBackupMoveTrue() {
        when(game.backUpMove()).thenReturn(true);
        Message message = CuT.backupMove(game);
        assertEquals(message.getType(), BACKUP_TRUE.getType());
        assertEquals(message.getText(), BACKUP_TRUE.getText());
    }

    /**
     * Test that the true message is returned by backing up a move.
     */
    @Test
    public void testBackupMoveFalse() {
        when(game.backUpMove()).thenReturn(false);
        Message message = CuT.backupMove(game);
        assertEquals(message.getType(), BACKUP_FALSE.getType());
        assertEquals(message.getText(), BACKUP_FALSE.getText());
    }

    /**
     * Test that the true message is returned by backing up a move.
     */
    @Test
    public void testResignGameTrue() {
        when(game.resignGame(player)).thenReturn(true);
        Message message = CuT.resignGame(game, player);
        assertEquals(message.getType(), RESIGN_TRUE.getType());
        assertEquals(message.getText(), RESIGN_TRUE.getText());
    }

    /**
     * Test that the true message is returned by backing up a move.
     */
    @Test
    public void testResignGameFalse() {
        when(game.resignGame(player)).thenReturn(false);
        Message message = CuT.resignGame(game, player);
        assertEquals(message.getType(), RESIGN_FALSE.getType());
        assertEquals(message.getText(), RESIGN_FALSE.getText());
    }

}
