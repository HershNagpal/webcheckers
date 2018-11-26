package com.webcheckers.appl;

import com.webcheckers.model.*;

/**
 * Creates the messages for view components to return to ajax calls.
 * Calls the method to update the model and returns the right message from a boolean.
 * Helper class for GameCenter.
 *
 * @author Michael Kha
 */
public class Messenger {

    /**
     * Message to display when a turn is valid.
     */
    static final Message TURN_TRUE = new Message("true", MessageType.info);

    /**
     * Message to display when a turn is not valid.
     */
    static final Message TURN_FALSE = new Message("false", MessageType.info);

    /**
     * Message to display when a move is valid.
     */
    static final Message MOVE_TRUE = new Message("", MessageType.info);

    /**
     * Message to display when a move is not valid.
     */
    static final Message MOVE_FALSE = new Message("Invalid move. Try again.", MessageType.error);

    /**
     * Message to display when a true turn is submitted.
     */
    static final Message SUBMIT_TRUE = new Message("", MessageType.info);

    /**
     * Message to display when a false turn is attempted to be submitted.
     */
    static final Message SUBMIT_FALSE = new Message("Invalid move. Cannot submit turn.", MessageType.error);

    /**
     * Message to display when a move is successfully backed up.
     */
    static final Message BACKUP_TRUE = new Message("", MessageType.info);

    /**
     * Message to display when backup is pressed and no move has been made.
     */
    static final Message BACKUP_FALSE = new Message("Cannot backup, there are no moves to undo.", MessageType.error);

    /**
     * Message to display when a player successfully resigns.
     */
    static final Message RESIGN_TRUE = new Message("", MessageType.info);

    /**
     * Message to display when a player tries to resign after their opponent.
     */
    static final Message RESIGN_FALSE = new Message("Could not resign. Your opponent already resigned.", MessageType.error);

    /**
     * Message to display when the opponent player has resigned.
     */
    static final Message OPP_RESIGN = new Message("Your opponent resigned. You win!", MessageType.info);

    /**
     * Message to display when the player resigns.
     */
    static final Message PLAYER_RESIGN = new Message("You resigned. You lose!", MessageType.info);

    /**
     * Message to display when the player wins.
     */
    static final Message PLAYER_WIN = new Message("You won the game!", MessageType.info);

    /**
     * Message to display when the player loses.
     */
    static final Message PLAYER_LOSE = new Message("You lost the game!", MessageType.info);

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
     * Get the message from the game's response about how the player did in the game.
     *
     * @param game to check whether the player won or not in
     * @param player that may or may not have one
     *
     * @return Message that says whether a player won or not
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
     * @param game to check who won in
     * @return Message that says which player won the game and how
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
     *
     * @param game to check the turn information in
     * @return Message that says how the game changed
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
     * @param game to check the turn information in
     * @param player to check if they have the current turn
     * @return Message that says whether it is the current player's turn or not
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
     * @param game to check for a valid move in
     * @param move to be checked if valid in the game
     * @return Message that says whether the given move is valid or not
     */
    public Message validateMove(Game game, Move move) {
        return game.validateMove(move) ? MOVE_TRUE : MOVE_FALSE;
    }

    /**
     * Get the message from the game's response about submitting a turn.
     *
     * @param game to be checked if the turn is submitted properly
     * @return Message that says whether the turn has been submitted properly
     */
    public Message submitTurn(Game game) {
        return game.submitTurn() ? SUBMIT_TRUE : SUBMIT_FALSE;
    }

    /**
     * Get the message from the game's response about backing up a move.
     *
     * @param game to be checked for backup move status
     * @return Message that says whether the backup move has been submitted successfully
     */
    public Message backupMove(Game game) {
        return game.backUpMove() ? BACKUP_TRUE : BACKUP_FALSE;
    }

    /**
     * Get the message from the game's response about the player resigning.
     *
     * @param game to be checked for resign status
     * @param player that is being checked for resignation status
     * @return Message that says whether the player has resigned or not
     */
    public Message resignGame(Game game, Player player) {
        return game.resignGame(player) ? RESIGN_TRUE : RESIGN_FALSE;
    }

}
