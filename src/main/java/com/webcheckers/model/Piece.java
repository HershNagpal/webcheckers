package com.webcheckers.model;

/**
 * Represents a piece on a checkerboard. A piece is associated with
 * the color of its player. A piece may be a single or a king.
 * 
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
     * The color of this piece (Red or White).
     */
    private Color color;

    /**
     * This piece's type (Single or King).
     */
    private Type type;

    /**
     * Construct a piece.
     * 
     * @param color The piece color (Red or White)
     * @param type The piece type (Single or King)
     */
    public Piece(Color color, Type type) {
        this.color = color;
        this.type = type;
    }

    /**
     * Copy a piece for storage in board when it is removed
     * @param piece Piece to copy based on color and type
     */
    public Piece(Piece piece) {
        this.color = piece.color;
        this.type = piece.type;
    }

    /**
     * Get this piece's color.
     * 
     * @return Piece color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Get this piece's type.
     * 
     * @return Piece type
     */
    public Type getType() { return type; }

    /**
     * Is the current piece a king?
     * 
     * @return true if piece is a king
     */
    public boolean isKing() {
        return type == Type.KING;
    }

    /**
     * Change this piece to a king piece.
     */
    public void becomeKing() {
        type = Type.KING;
    }

    /**
     * Change this piece to a single piece.
     */
    public void becomeSingle() {
        type = Type.SINGLE;
    }

    /**
     * Return piece's color and type as a String. Used for testing.
     * 
     * @return string representation of Piece.
     */
    @Override
    public String toString(){
        return "Color: "+this.color+", Type: "+this.type;
    }

    /**
     * Overriding equals() for deep equality between Piece Objects.
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
        else if(!(obj instanceof Piece)){
            return false;
        }
        Piece p2 = (Piece)obj;

        return this.color.equals(p2.color) && this.type.equals(p2.type);
    }
}