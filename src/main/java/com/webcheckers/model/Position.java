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
     * Display position's row and cell. Used for testing.
     * @return string representation of Position
     */
    @Override
    public String toString(){
        return "Row: "+row+", Column: "+cell;
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
     * 
     * @param other
     * @return
     */
    public boolean isDiagonalAdjacentTo(Position other) {
        if(!this.isDiagonalTo(other)) return false;

        int x1 = this.cell;
        int y1 = this.row;
        int x2 = other.cell;
        int y2 = other.row;

        boolean xIsDiagonalAdjacent = false;
        boolean yIsDiagonalAdjacent = false;



        return true;
    }

     /**
     * Returns whether or not the distance between the two values is the expected amount.
     * @param val1 col or row value to check distance with another value
     * @param val2 col or row value to check distance with another value.
     * @param expected expected difference between val2 and val1.
     * @return true if the distance between p1 and p2 is equal to the expected value.
     */
    public boolean checkDistance(int val2, int val1, int expected){
        return (val2-val1) == expected;
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
