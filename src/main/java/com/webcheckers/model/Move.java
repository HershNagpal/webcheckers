package com.webcheckers.model;

/**
 * Represents the movement of pieces.
 * 
 * @author Hersh Nagpal
 * @author Luis Gutierrez
 */
public class Move {

    /**
     * The board position where the Move will start.
     */
    private Position start;

    /**
     * The board position where the Move will end.
     */
    private Position end;

    /**
     * Is this move a backup move created to undo another move?
     */
    private boolean isBackUpMove;

    /**
     * Construct a move.
     */
    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
        this.isBackUpMove = false;
    }

    /**
     * Flips a given move as if it was being viewed on the other side of the Board.
     * 
     * @return Flipped move.
     */
    public Move flipMove(){
        Position posStart = this.start;
        Position posEnd = this.end;

        Position posStartFlipped = new Position(7-posStart.getRow(), 7-posStart.getCell());
        Position posEndFlipped = new Position(7-posEnd.getRow(), 7-posEnd.getCell());

        return new Move(posStartFlipped,posEndFlipped);
    }

    /**
     * Returns whether or not the move is facing the RED side of the board.
     * "Facing the red side of the board" means that the move goes up when
     * seen from the white players perspective (True if start row > end row).
     * 
     * @return True if the move is facing the RED side of the board, false otherwise.
     */
    public boolean isFacingRed() {
        return !getStart().isAbove(getEnd());
    }

    /**
     * Returns the piece that is being jumped. Null if none.
     * Should only be called on jump moves or will not work properly.
     * 
     * @return the piece in the middle of a jump move. Returns null if no piece.
     */
    public Position getJumpedPosition() {
        Position jumpedPosition;
        
        if(this.isFacingRed()) {
            // Facing the left side of the board
            if (this.getEnd().getCell() < this.getStart().getCell()) {
                jumpedPosition = new Position(this.getStart().getRow()-1, this.getStart().getCell()-1);
            } else {
                jumpedPosition = new Position(this.getStart().getRow()-1, this.getStart().getCell()+1);
            }
        } 
        else {
            if (this.getEnd().getCell() < this.getStart().getCell()) {
                jumpedPosition = new Position(this.getStart().getRow()+1, this.getStart().getCell()-1);
            } else {
                jumpedPosition = new Position(this.getStart().getRow()+1, this.getStart().getCell()+1);
            }
        }

        return jumpedPosition;
    }

    /**
     * Returns a new Move that is the reverse of the last move.
     * 
     * @return new Move with current's end as start, and current's start as end.
     */
    public Move createBackUpMove() {
        Move backUpMove = new Move(this.end, this.start);
        backUpMove.isBackUpMove = true;
        return backUpMove;
    }

    /**
     * Returns whether or not this move is a backup 
     * move created to undo another move.
     * 
     * @return true if this is a backup move. False otherwise.
     */
    public boolean isBackUpMove() {
        return isBackUpMove;
    }

    /**
     * Returns the start Position of this move.
     * 
     * @return the start Position of this move.
     */
    public Position getStart() {
        return start;
    }

    /**
     * Returns the end Position of this move.
     * 
     * @return the end Position of this move.
     */
    public Position getEnd() {
        return end;
    }

    /**
     * Returns a string that shows this Move's start and end 
     * locations along with whether it is a backup Move or not.
     * 
     * @return String representation of move.
     */
    @Override
    public String toString() {
        return "Start Position: "+this.start+", End Position: "+this.end+", BackUpMove: "+this.isBackUpMove;
    }

    /**
     * Overriding equals() for deep equality between Move Objects.
     * This method is used for testing.
     * 
     * @param obj Object being compared to "this" Move
     * @return true if "this" is equal to obj
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        else if(!(obj instanceof Move)){
            return false;
        }

        Move m2 = (Move)obj;

        return this.start.equals(m2.start) && this.end.equals(m2.end);
    }
}