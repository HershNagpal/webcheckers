package com.webcheckers.model;

/**
 * Represents the movement of pieces.
 */
public class Move {

    private Position start;
    private Position end;

    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    /**
     *
     */
    public void simpleMove() {
        if (canMove()) {
            Piece piece = start.getPiece();
            start.removePiece();
            end.setPiece(piece);
        }
    }

    /**
     *
     */
    public void jumpMove() {

    }

    /**
     * Can move if the following are true:
     * 1. There is a piece on start
     * 2. There is a no piece on end
     * 3. The direction between spaces is diagonal
     * 4. The piece must move forward
     * @return Can a piece move?
     */
    public boolean canMove() {
        return true;
    }

}
