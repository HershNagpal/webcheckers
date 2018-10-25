package com.webcheckers.model;

/**
 * Represents a space on a checkerboard. A space is located on a row
 * and column. A space may have a piece located on it.
 * @author Luis, Chris
 */
public class Space {

  /**
   * KING_RED - Spaces where Red checkers become king pieces.
   * KING_WHITE - Spaces where White checkers become king pieces.
   * NON_KING - Normal spaces where king pieces cannot be made.
   */
  public enum SpaceType {
    KING_RED, KING_WHITE, NON_KING
  }

  /**
   * Possible colors for spaces
   */
  public enum SpaceColor {BLACK, WHITE}

  /**
   * row - the row coordinate of this space.
   * column - the column coordinate of this space.
   * spaceType - the type of space (whether a piece can be kinged or not).
   * spaceColor - the color associated with this space (black or white).
   * piece - the piece that is currently on this space. Starts as null (no piece) until set up.
   */
  private int row;
  private int column;
  private SpaceType spaceType;
  private SpaceColor spaceColor;
  private Piece piece = null;

  public Space(int row, int column, Piece piece) {
    this.row = row;
    this.column = column;
    this.piece = piece;
    setSpaceType();
    setSpaceColor();

  }

  /**
   * Sets the Space type based on row.
   * Row 0 and 7 are king rows.
   */
  private void setSpaceType() {
    if (row == 0) {
      this.spaceType = SpaceType.KING_WHITE;
    } else if (row == 7) {
      this.spaceType = SpaceType.KING_RED;
    } else {
      this.spaceType = SpaceType.NON_KING;
    }
  }

  /**
   * Sets the Space color based on rows and columns.
   * If both row and column have the same remainder when divided
   * by two, the space .
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
   * @return true if this space is valid, false otherwise.
   */
  public boolean isValid() {
    return this.spaceColor == SpaceColor.BLACK && piece == null;
  }

  /**
   * Does the space have a checker in it?
   * @return true if the space has a checker, false if it doesn't.
   */
  public boolean hasChecker() {
    return this.piece != null;
  }

  public int getCellIdx() {
    return column;
  }

  public Piece getPiece() {
    return piece;
  }

  /**
   * Puts a piece into the space.
   * @param piece the piece to put into the space
   */
  public void setPiece(Piece piece) {
    this.piece = piece;
  }

  /**
   * Overriding equals() for deep equality between Space Objects.
   * This method is used for testing.
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

    return this.row == space2.row && this.column == space2.column
            && this.spaceType.equals(space2.spaceType)
            && this.spaceColor.equals(space2.spaceColor);
  }
}