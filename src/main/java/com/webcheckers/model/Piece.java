package com.webcheckers.model;

/**
 *
 * @author Luis Gutierrez, Christopher Daukshus
 */
public class Piece {


    public enum Type{
      SINGLE,
      KING
    }

    private Color color;
    private Type type;

    public Piece(Color color, Type type) {
      this.color = color;
      this.type = type;
    }

    public void makeMove(){

    }
}
