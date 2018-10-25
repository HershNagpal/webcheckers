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

}
