package com.webcheckers.model;

/**
 * Represents a space on a checkerboard. A space is located on a row
 * and column. A space may have a piece located on it.
 * 
 * @author Luis Gutierrez
 * @author Chris Daukshus
 * @author Hersh Nagpal
 */
public class Space {
    /**
     * The color of the space, either black or white
     */
    public enum SpaceColor {BLACK, WHITE}

    /**
     * The row number of this space
     */
    private int row;

    /**
     * The column number of this space
     */
    private int column;

    /**
     * The color of this space (Black or white)
     */
    private SpaceColor spaceColor;

    /**
     * The piece that is on this space
     */
    private Piece piece;

    /**
     * Construct a Space
     * 
     * @param row The row number of the space
     * @param column The column number of the space
     * @param piece The piece to place on this space
     */
    public Space(int row, int column, Piece piece) {
        this.row = row;
        this.column = column;
        this.piece = piece;
        setSpaceColor();

    }

    /**
     * Sets the Space color based on rows and columns.
     * If both row and column have the same remainder when divided
     * by two, the space is white. Otherwise, it is black.
     */
    private void setSpaceColor() {
        if (row % 2 == column % 2) {
            spaceColor = SpaceColor.WHITE;
        } else {
            spaceColor = SpaceColor.BLACK;
        }
    }

    /**
     * Checks the color of the space.
     * Returns false for dark square and true for light square.
     *
     * @return indicator of color in boolean
     */
    public boolean checkColor() {
        if (row % 2 == 0) {
            return column % 2 == 0;
        } else {
            return column % 2 == 1;
        }
    }

    /**
     * Checks if the space is a valid space to move to.
     * 
     * @return true if this space is valid, false otherwise.
     */
    public boolean isValid() {
        return this.spaceColor == SpaceColor.BLACK && piece == null;
    }

    /**
     * Does the space have a checker in it?
     * 
     * @return true if the space has a checker, false if it doesn't.
     */
    public boolean hasChecker() {
        return this.piece != null;
    }

    /**
     * Get the column
     * 
     * @return The column number of this Space.
     */
    public int getCellIdx() {
        return column;
    }

    /**
     * Get the Piece located at this Space
     * 
     * @return The Piece on this Space. Returns null if none.
     */
    public Piece getPiece() {
        return piece;
    }

    /**
     * Puts a Piece onto this Space.
     * 
     * @param piece the Piece to put onto the Space
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * Overriding equals() for deep equality between Space Objects.
     * This method is used for testing.
     * 
     * @param obj Object being compared to "this" Piece
     * @return true if "this" is equal to obj
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        else if(!(obj instanceof Space)){
            return false;
        }

        Space space2 = (Space)obj;

        if(this.piece == null || space2.piece == null){
            return this.row == space2.row && this.column == space2.column
                    && this.spaceColor.equals(space2.spaceColor)
                    && this.piece==(space2.piece);
        }

        return this.row == space2.row && this.column == space2.column
                && this.spaceColor.equals(space2.spaceColor)
                && this.piece.equals(space2.piece);
    }
}