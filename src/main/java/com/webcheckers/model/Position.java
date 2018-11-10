package com.webcheckers.model;

/**
 * Represents a position on the board.
 *
 * @author Michael Kha
 */
public class Position {

    /**
     * Coordinates on the board
     */
    private int row;
    private int cell;

    public Position(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    public int getRow() {
        return row;
    }

    public int getCell() {
        return cell;
    }

    /**
     * Is the end position oriented diagonally to this position?
     * @param end End position
     * @return If these positions are diagonal to each other
     */
    public boolean isDiagonalTo(Position end) {
        return true;
    }

    /**
     * Is the end position in front of this position?
     * @param end End position
     * @return If end position is in front of this position
     */
    public boolean isForwardTo(Position end) {
        return true;
    }

    /**
     * Display position's row and cell. Used for testing.
     * @return string representation of Position
     */
    @Override
    public String toString(){
        return "Row: "+row+", Column: "+cell;
    }

    /**
     * Overriding equals() for deep equality between Position Objects.
     * This method is used for testing.
     * @param obj Object being compared to "this" Position
     * @return true if "this" is equal to obj
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        else if(!(obj instanceof Position)){
            return false;
        }

        Position p2 = (Position)obj;

        return this.row == p2.row && this.cell == p2.cell;
    }

}
