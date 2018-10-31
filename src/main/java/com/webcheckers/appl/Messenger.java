package com.webcheckers.appl;

import com.webcheckers.model.*;

/**
 * Creates the messages for view components to return to ajax calls.
 * Helper class for GameCenter.
 *
 * @author Michael Kha
 */
public class Messenger {

    /**
     * Game to retrieve messages from.
     */
    private Game game;

    /**
     * Create a messenger for the game.
     * @param game Game
     */
    public Messenger(Game game) {
        this.game = game;
    }

    /**
     * Get the message from the game about whose turn it is.
     *
     * @return Message
     */
    public Message checkTurn(Player player) {
        return game.isActivePlayer(player);
    }

    /**
     * Get the message from the game about a valid move.
     *
     * @return Message
     */
    public Message validateMove(Move move) {
        return game.validateMove(move);
    }

    /**
     * Get the message from the game about submitting a turn.
     *
     * @return Message
     */
    public Message submitTurn() {
        return game.submitTurn();
    }

    /**
     * Get the message from the game about backing up a move.
     *
     * @return Message
     */
    public Message backupMove() {
        return game.backUpMove();
    }

}
