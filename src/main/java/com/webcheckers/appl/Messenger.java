package com.webcheckers.appl;

import com.webcheckers.model.Message;

/**
 * Creates the messages for view components to return to ajax calls
 *
 * @author Michael Kha
 */
public class Messenger {

    /**
     * Get the message from the game about whose turn it is.
     *
     * @return Message
     */
    public Message checkTurn() {
        return null;
    }

    /**
     * Get the message from the game about a valid move.
     *
     * @return Message
     */
    public Message validateMove() {
        return null;
    }

    /**
     * Get the message from the game about submitting a turn.
     *
     * @return Message
     */
    public Message submitTurn() {
        return null;
    }

    /**
     * Get the message from the game about backing up a move.
     *
     * @return Message
     */
    public Message backupMove() {
        return null;
    }

}
