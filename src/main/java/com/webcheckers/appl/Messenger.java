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
    static final Message TURN_TRUE = new Message("true", MessageType.info);
    static final Message TURN_FALSE = new Message("false", MessageType.info);
    static final Message MOVE_TRUE = new Message("", MessageType.info);
    static final Message MOVE_FALSE = new Message("Invalid move. Try again.", MessageType.error);
    static final Message SUBMIT_TRUE = new Message("", MessageType.info);
    static final Message SUBMIT_FALSE = new Message("Invalid move. Cannot submit turn.", MessageType.error);
    static final Message BACKUP_TRUE = new Message("", MessageType.info);
    static final Message BACKUP_FALSE = new Message("Cannot Backup, there are no moves to undo.", MessageType.error);
    static final Message RESIGN_TRUE = new Message("", MessageType.info);
    static final Message RESIGN_FALSE = new Message("", MessageType.error);

    /**
     * Message to display when the opponent player has resigned.
     */
    static final Message OPP_RESIGN = new Message(
            "Your opponent resigned. You win!", MessageType.gameover);

    /**
     * Message to display when the player resigns.
     */
    static final Message PLAYER_RESIGN = new Message(
            "You resigned. You lose!", MessageType.gameover);

    /**
     * Message to display when the player wins.
     */
    static final Message PLAYER_WIN = new Message(
            "You won the game!", MessageType.gameover);

    /**
     * Message to display when the player loses.
     */
    static final Message PLAYER_LOSE = new Message(
            "You lost the game!", MessageType.gameover);

    /**
     * Message to display when
     */

    /**
     * Get the message from the game's response about the winner and how they won.
     *
     * @return Message
     */
    public Message isWinner(Game game, Player player) {
        if (game.didPlayerResign()) {
            return game.isWinner(player) ? OPP_RESIGN : PLAYER_RESIGN;
        }
        // if all pieces eliminated return the proper message
        if (game.isGameOver()) {
            return game.isWinner(player) ? PLAYER_WIN : PLAYER_LOSE;
        }
        // null: no message will be displayed from freemarker template
        return null;
    }

    /**
     * Get the message from the game's response about whose turn it is.
     *
     * @return Message
     */
    public Message checkTurn(Game game, Player player) {
        // A player resigned
        if (game.didPlayerResign()) {
            return TURN_TRUE;
        }
        return game.isActivePlayer(player) ? TURN_TRUE : TURN_FALSE;
    }

    /**
     * Get the message from the game's response about a valid move.
     *
     * @return Message
     */
    public Message validateMove(Game game, Move move) {
        return game.validateMove(move) ? MOVE_TRUE : MOVE_FALSE;
    }

    /**
     * Get the message from the game's response about submitting a turn.
     *
     * @return Message
     */
    public Message submitTurn(Game game) {
        return game.submitTurn() ? SUBMIT_TRUE : SUBMIT_FALSE;
    }

    /**
     * Get the message from the game's response about backing up a move.
     *
     * @return Message
     */
    public Message backupMove(Game game) {
        return game.backUpMove() ? BACKUP_TRUE : BACKUP_FALSE;
    }

    /**
     * Get the message from the game's response about the player resigning.
     *
     * @return Message
     */
    public Message resignGame(Game game, Player player) {
        return game.resignGame(player) ? RESIGN_TRUE : RESIGN_FALSE;
    }

}
