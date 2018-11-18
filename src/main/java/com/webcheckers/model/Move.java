package com.webcheckers.model;

/**
 * Represents the movement of pieces.
 * @author Hersh Nagpal
 * @author Luis Gutierrez
 */
public class Move {

    private Position start;
    private Position end;

    //Flag to check if move is a backUpMove
    private boolean isBackUpMove;

    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
        this.isBackUpMove = false;
    }

    /**
     * Flips a given move. Used in PostValidateTurnRoute and PostSubmitTurnRoute.
     * @return Flipped move.
     */
    public Move flipMove(){
        Position posStart = this.start;
        Position posEnd = this.end;

        Position posStartFlipped = new Position(7-posStart.getRow(), 7-posStart.getCell());
        Position posEndFlipped = new Position(7-posEnd.getRow(), 7-posEnd.getCell());

        Move flippedMove = new Move(posStartFlipped,posEndFlipped);
        return flippedMove;
    }

    /**
     * Returns whether or not the move is facing the RED side of the board.
     * "Facing the red side of the board" means that the move goes up when
     * seen from the white players perspective.
     * @return True if the move is facing the RED side of the board, false otherwise.
     */
    public boolean isFacingRed() {
        //True if start row > end row
        return !getStart().isAbove(getEnd());
    }

    /**
     * Returns the piece that is being jumped. Null if none.
     * Should only be called on jump moves or will not work properly.
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
     * Returns a new Move that "deletes" the last move.
     * @return new Move with current's end as start, and current's start as end
     */
    public Move createBackUpMove(){
        Move backUpMove = new Move(this.end, this.start);
        backUpMove.isBackUpMove = true;
        return backUpMove;
    }

    public boolean isBackUpMove(){
        return isBackUpMove;
    }

    public Position getStart() {
        return start;
    }

    public Position getEnd() {
        return end;
    }

    /**
     * Used for testing moves.
     * @return String representation of move.
     */
    @Override
    public String toString(){
        return "Start Position: "+this.start+", End Position: "+this.end+", BackUpMove: "+this.isBackUpMove;
    }

    /**
     * Overriding equals() for deep equality between Move Objects.
     * This method is used for testing.
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
