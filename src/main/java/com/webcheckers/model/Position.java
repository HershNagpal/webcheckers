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

    /**
     * Create a position on the board.
     * @param row Row location
     * @param cell Cell location
     */
    public Position(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    /**
     * Get the row of this position.
     * @return Row
     */
    public int getRow() {
        return row;
    }

    /**
     * Get the cell of this position.
     * @return Cell
     */
    public int getCell() {
        return cell;
    }

    /**
     * Is the other position oriented diagonally to this position?
     * @param other Other position
     * @return If these positions are diagonal to each other
     */
    public boolean isDiagonalTo(Position other) {
        return ((double)(this.row - other.row)
                / (double)(this.cell - other.cell) == 1);
    }

    /**
     * Is this position in front of the other position?
     * @param other End position
     * @return If other position is in front of this position
     */
    public boolean isForwardTo(Position other) {
        return this.row > other.row;
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
