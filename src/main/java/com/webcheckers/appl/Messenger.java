package com.webcheckers.appl;

import com.webcheckers.model.*;

/**
 * Creates the messages for view components to return to ajax calls.
 * Calls the method to update the model and returns the right message
 * from a boolean.
 * Helper class for GameCenter.
 *
 * @author Michael Kha
 */
public class Messenger {

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
     * Get the message from the game about whose turn it is.
     *
     * @return Message
     */
    public Message checkTurn(Game game, Player player) {
        return game.isActivePlayer(player) ? TURN_TRUE : TURN_FALSE;
    }

    /**
     * Get the message from the game about a valid move.
     *
     * @return Message
     */
    public Message validateMove(Game game, Move move) {
        return game.validateMove(move) ? MOVE_TRUE : MOVE_FALSE;
    }

    /**
     * Get the message from the game about submitting a turn.
     *
     * @return Message
     */
    public Message submitTurn(Game game) {
        return game.submitTurn() ? SUBMIT_TRUE : SUBMIT_FALSE;
    }

    /**
     * Get the message from the game about backing up a move.
     *
     * @return Message
     */
    public Message backupMove(Game game) {
        return game.backUpMove() ? BACKUP_TRUE : BACKUP_FALSE;
    }

}
