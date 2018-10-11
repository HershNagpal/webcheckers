package com.webcheckers.model;

/**
 *
 * @author Luis Gutierrez
 * @author Hersh Nagpal
 */
public class Piece {

    public enum Type{
      SINGLE,
      KING
    }

    private Color color;
    private Type type;
    private int column;
    private int row;

    public Piece(Color color, Type type, int row, int column) {
      this.color = color;
      this.type = type;
      this.row = row;
      this.column = column;
    }

    /**
     * Changes a piece into a Kinged piece and returns true if the piece was successfully kinged.
     * @return if the piece was successfully Kinged, false otherwise.
     */
    public boolean kingPiece() {
      if (this.type == Type.SINGLE) {
        this.type = Type.KING;
        return true;
      } 
      else return false;
    }

    

}
