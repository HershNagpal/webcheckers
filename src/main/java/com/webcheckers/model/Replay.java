package com.webcheckers.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Replay version of the game where turns are simulated
 * as next and previous are selected.
 *
 * @author Michael Kha
 */
public class Replay extends Game {

    /**
     * Current turn being replayed
     */
    private int replayIndex;

    /**
     *
     * @param game
     */
    public Replay(Game game) {
        super(game);
        replayIndex = 0;
    }

    /**
     * Move the game to the next turn.
     *
     * @return If the game successfully went to the next turn
     */
    public boolean nextTurn() {
        lastMoves = allMoves.get(replayIndex);
        for (Move move : lastMoves) {
            makeMove(move);
        }
        // Next color and index
        switchActiveColor();
        replayIndex++;
        return true;

    }

    /**
     * Move the game to the previous turn.
     *
     * @return If the game successfully went to the previous turn
     */
    public boolean previousTurn() {
        // Previous color and index
        switchActiveColor();
        replayIndex--;
        lastMoves = allMoves.get(replayIndex);
        for (Move move : lastMoves) {
            backUpMove(move);
        }
        return true;
    }

    /**
     * Get the mode options component required by the view-model
     *
     * @return Map<String, Object> of the required keys and values.
     */
    public Map<String, Object> getModeOptions() {
        Map<String, Object> modeOptions = new HashMap<>();
        if (replayIndex == 0) {
            modeOptions.put("hasPrevious", false);
        } else {
            modeOptions.put("hasPrevious", true);
        }
        if (replayIndex == allMoves.size()) {
            modeOptions.put("hasNext", false);
        } else {
            modeOptions.put("hasNext", true);
        }
        return modeOptions;
    }

    /**
     * "Undo" last move made
     *
     */
    public void backUpMove(Move move) {
        Move backUpMove = move.createBackUpMove();
        //Undo last move
        makeMove(backUpMove);
    }

}
