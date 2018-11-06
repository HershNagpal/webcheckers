package com.webcheckers.model;

/**
 * Represents the movement of pieces.
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
