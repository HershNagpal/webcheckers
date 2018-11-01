package com.webcheckers.appl;

import com.webcheckers.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
     * All possible messages
     */
    private static final Message TURN_TRUE = new Message("true", MessageType.info);
    private static final Message TURN_FALSE = new Message("false", MessageType.info);
    private static final Message MOVE_TRUE = new Message("", MessageType.info);
    private static final Message MOVE_FALSE = new Message("Invalid move. Try again.", MessageType.error);
    private static final Message SUBMIT_TRUE = new Message("", MessageType.info);
    private static final Message SUBMIT_FALSE = new Message("Invalid move. Cannot submit turn.", MessageType.error);
    private static final Message BACKUP_TRUE = new Message("", MessageType.info);
    private static final Message BACKUP_FALSE = new Message("Cannot Backup, there are no moves to undo.", MessageType.error);

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

}
