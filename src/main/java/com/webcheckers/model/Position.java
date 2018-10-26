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
