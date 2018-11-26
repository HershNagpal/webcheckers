package com.webcheckers.model;

/**
 * Represents a position on the board.
 *
 * @author Michael Kha
 * @author Hersh Nagpal
 */
public class Position {

    /**
     * A row coordinate
     */
    private int row;

    /**
     * A cell coordinate
     */
    private int cell;

    /**
     * Create a Position object representing a space on the board.
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
     * Returns true if the Position is on the Board.
     * 
     * @return true if the Position is on the Board, false if it is not.
     */
    public boolean isOnBoard() {
        if(this.cell < 0 || this.cell > Board.COLUMNS) {
            return false;
        } else if (this.row < 0 || this.row > Board.ROWS) {
            return false;
        }
        return true;
    }

    /**
     * Is this position on a lower row number than the other?
     * @param other End position
     * @return true if this position higher on the board than this position.
     */
    public boolean isAbove(Position other) {
        return this.row < other.row;
    }

    /**
     * Is this position on a greater column number than the other?
     * @param other End position
     * @return true if this position is to the right of the other position.
     */
    public boolean isRightOf(Position other) {
        return this.cell > other.cell;
    }

    /**
     * Is the other position oriented diagonally to this position?
     * @param other Other position
     * @return If these positions are diagonal to each other
     */
    public boolean isDiagonalTo(Position other) {
        return (Math.abs((double)(this.row - other.row)
                    / (double)(this.cell - other.cell)) == 1);
    }

    /**
     * Returns whether or not the two positions are within simple move distance.
     * @param other The position to be compared to this position.
     * @return True if the positions are diagonal, on the board, and within simple move distance.
     */
    public boolean isDiagonalAdjacentTo(Position other) {
        int x1 = this.getCell();
        int y1 = this.getRow();
        int x2 = other.getCell();
        int y2 = other.getRow();

        // Positions must be diagonal.
        if(!this.isDiagonalTo(other)) return false;
        // Positions must be positive.
        if(x1 < 0 || y1 < 0) return false;
        if(x2 < 0 || y2 < 0) return false;
        // Positions must be within board limits.
        if(x1 > Board.COLUMNS || y1 > Board.ROWS) return false;
        if(x2 > Board.COLUMNS || y2 > Board.ROWS) return false;

        return isDistanceExpectedValue(x1, x2, 1) && isDistanceExpectedValue(y1, y2, 1);
    }

    /**
     * Returns whether or not the two positions are within jump move distance.
     * @param other The position to be compared to this position.
     * @return True if the positions are diagonal, on the board, and within jump move distance.
     */
    public boolean isDiagonalJumpTo(Position other) {
        int x1 = this.getCell();
        int y1 = this.getRow();
        int x2 = other.getCell();
        int y2 = other.getRow();

        // Positions must be diagonal.
        if(!this.isDiagonalTo(other)) return false;
        // Positions must be positive.
        if(x1 < 0 || y1 < 0) return false;
        if(x2 < 0 || y2 < 0) return false;
        // Positions must be within board limits.
        if(x1 > Board.COLUMNS || y1 > Board.ROWS) return false;
        if(x2 > Board.COLUMNS || y2 > Board.ROWS) return false;

        return isDistanceExpectedValue(x1, x2, 2) && isDistanceExpectedValue(y1, y2, 2);
    }

     /**
     * Returns whether or not the distance between the two values is the expected amount.
     * @param val1 col or row value to check distance with another value
     * @param val2 col or row value to check distance with another value.
     * @param expected expected difference between val2 and val1.
     * @return true if the distance between p1 and p2 is equal to the expected value.
     */
    public static boolean isDistanceExpectedValue(int val2, int val1, int expected){
        return Math.abs((val2-val1)) == Math.abs(expected);
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