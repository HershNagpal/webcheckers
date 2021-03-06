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
     * Current turn being replayed.
     */
    private int replayIndex;

    /**
     * Map of moves that king a piece on the given turn.
     */
    private Map<Integer, Move> kingMoves;

    /**
     * Create a replay version of the given Game.
     * @param game Original finished game to copy
     */
    public Replay(Game game) {
        super(game);
        replayIndex = 0;
        kingMoves = new HashMap<>();
    }

    /**
     * Move the replay game to the next turn. Returns true if 
     * successful, false otherwise.
     *
     * @return If the game successfully went to the next turn
     */
    public boolean nextTurn() {
        lastMoves = allMoves.get(replayIndex);
        if (lastMoves == null) {
            return false;
        }
        for (Move move : lastMoves) {
            makeMove(move);
            if (checkIfKinged(move)) {
                kingMoves.put(replayIndex, move);
            }
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
        if (lastMoves == null) {
            return false;
        }
        if (kingMoves.containsKey(replayIndex)) {
            Move revertKing = kingMoves.get(replayIndex);
            board.getPieceAtFlippedPosition(revertKing.flipMove().getEnd()).becomeSingle();
        }
        for (int i = lastMoves.size() - 1; i >= 0; i--) {
            Move move = lastMoves.get(i);
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
     * Undo the last move made.
     * 
     * @param move the move to be undone.
     */
    public void backUpMove(Move move) {
        Move backUpMove = move.createBackUpMove();
        makeMove(backUpMove);
    }

}