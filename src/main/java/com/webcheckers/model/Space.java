package com.webcheckers.model;

/**
 * Represents a space on a checkerboard. A space is located on a row
 * and column. A space may have a piece located on it.
 * @author Luis, Chris
 */
public class Space {

  /**
   * KING_RED = Spaces where Red checkers become king pieces.
   * KING_BLACK = Spaces where Black checkers become king pieces.
   */
  public enum SpaceType {
    KING_RED, KING_BLACK, NON_KING
  }

  public enum SpaceColor {BLACK, WHITE}

  /**
   * row - the row coordinate of this space.
   * column - the column coordinate of this space.
   * spaceType - the type of space (whether a piece can be kinged or not).
   * spaceColor - the color associated with this space (black or white).
   */
  private int row;
  private int column;
  private SpaceType spaceType;
  private SpaceColor spaceColor;

  //Space has no piece before setUpBoard() in Board
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
   */
  private void setSpaceType() {
    if (row == 0) {
      this.spaceType = SpaceType.KING_BLACK;
    } else if (row == 7) {
      this.spaceType = SpaceType.KING_RED;
    } else {
      this.spaceType = SpaceType.NON_KING;
    }
  }

  /**
   * Sets the Space color based on rows and columns.
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
}