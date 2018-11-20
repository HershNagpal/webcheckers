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
    static final Message BACKUP_FALSE = new Message("Cannot backup, there are no moves to undo.", MessageType.error);
    static final Message RESIGN_TRUE = new Message("", MessageType.info);
    static final Message RESIGN_FALSE = new Message("Could not resign. Your opponent already resigned.", MessageType.error);

    /**
     * Message to display when the opponent player has resigned.
     */
    static final Message OPP_RESIGN = new Message(
            "Your opponent resigned. You win!", MessageType.info);

    /**
     * Message to display when the player resigns.
     */
    static final Message PLAYER_RESIGN = new Message(
            "You resigned. You lose!", MessageType.info);

    /**
     * Message to display when the player wins.
     */
    static final Message PLAYER_WIN = new Message(
            "You won the game!", MessageType.info);

    /**
     * Message to display when the player loses.
     */
    static final Message PLAYER_LOSE = new Message(
            "You lost the game!", MessageType.info);

    /**
     * Format messages using String.format
     * @param text Text with %s to fill in
     * @param addToText Text to replace %s with
     * @param type Type of message to return
     * @return New message with the formatted parameters
     */
    private Message formatStringMessage(String text, String addToText, MessageType type) {
        return new Message(String.format(text, addToText), type);
    }

    /**
     * Get the message from the game's response about how the player
     * did in the game.
     *
     * @return Message indicating how the player did in the game
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
     * Get the message from the game's response about who won and how.
     *
     * @return Message telling who won the game and how
     */
    public Message whoWon(Game game) {
        Player winner = game.getWinner();
        if (winner != null) {
            String name = winner.getName();
            // Message depending on how game ended
            return game.didPlayerResign() ? formatStringMessage(
                    "%s won the game by resignation",
                    name, MessageType.info) :
                    formatStringMessage("%s won the game by " +
                            "an end-game condition.", name, MessageType.info);
        }
        return null;
    }

    /**
     * Get the message from the game's response about if the game has
     * changed at all since last checked. If a game is over the message
     * acts as those the game did change turns.
     * @return Message indicating if game has changed or is over
     */
    public Message checkTurn(Game game) {
        if (game.isGameOver()) {
            return TURN_TRUE;
        }
        return game.hasGameChanged() ? TURN_TRUE : TURN_FALSE;
    }

    /**
     * Get the message from the game's response about whose turn it is.
     *
     * @return Message indicating if it is now the player's turn
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
     * @return Message informing player if the move was valid
     */
    public Message validateMove(Game game, Move move) {
        return game.validateMove(move) ? MOVE_TRUE : MOVE_FALSE;
    }

    /**
     * Get the message from the game's response about submitting a turn.
     *
     * @return Message informing the player if the turn was submitted
     */
    public Message submitTurn(Game game) {
        return game.submitTurn() ? SUBMIT_TRUE : SUBMIT_FALSE;
    }

    /**
     * Get the message from the game's response about backing up a move.
     *
     * @return Message informing the player if the move was backed up
     */
    public Message backupMove(Game game) {
        return game.backUpMove() ? BACKUP_TRUE : BACKUP_FALSE;
    }

    /**
     * Get the message from the game's response about the player resigning.
     *
     * @return Message informing the player if they successfully resigned
     */
    public Message resignGame(Game game, Player player) {
        return game.resignGame(player) ? RESIGN_TRUE : RESIGN_FALSE;
    }

}
