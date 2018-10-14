package com.webcheckers.model;

/**
 * Represents a piece on a checkerboard. A piece is associated with
 * the color of its player. A piece may be a single or a king.
 * @author Luis Gutierrez
 * @author Christopher Daukshus
 * @author Hersh Nagpal
 */
public class Piece {


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

    //
    // Constructor
    //
    public Piece(Color color, Type type) {
      this.color = color;
      this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public Type getType() {
        return type;
    }

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
}
