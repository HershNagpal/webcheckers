package com.webcheckers.model;

/**
 * Represents a piece on a checkerboard. A piece is associated with
 * the color of its player. A piece may be a single or a king.
 * @author Luis Gutierrez
 * @author Christopher Daukshus
 * @author Hersh Nagpal
 */
public class Piece {

  /**
   * Enum holding the different possible Piece types.
   */
  public enum Type{
    SINGLE,
    KING
  }

  /**
   * color - the color of the piece (red or white).
   * type - the type of piece (kinged or normal).
   */
  private Color color;
  private Type type;

  /**
   * Constructor for a Piece.
   * Sets the color and type of the piece
   * @param color The piece color (Red or White)
   * @param type The piece type (Single or King)
   */
  public Piece(Color color, Type type) {
    this.color = color;
    this.type = type;
  }

  /**
   * Accessor method for the piece's color
   * @return Piece color
   */
  public Color getColor() {
      return color;
  }

  /**
   * Accessor method for the piece's type
   * @return Piece type
   */
  public Type getType() { return type; }

  /**
   * Changes a piece to the king type and returns true if the piece was successfully kinged.
   * @return true if the piece was changed from the SINGLE type to KING type and false otherwise.
   */
  public boolean kingPiece() {
    if(this.type == Type.SINGLE) {
      this.type = Type.KING;
      return true;
    } else return false;
  }

/**
 * Overriding equals() for deep equality between Piece Objects.
 * This method is used for testing.
 * @param obj Object being compared to "this" Piece
 * @return true if "this" is equal to obj
 */
  @Override
  public boolean equals(Object obj){
    if(this == obj){
      return true;
    }
    else if(!(obj instanceof Piece)){
      return false;
    }

    Piece p2 = (Piece)obj;

    return this.color.equals(p2.color) && this.type.equals(p2.type);
  }
}
