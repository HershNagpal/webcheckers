package com.webcheckers.model;

public class Piece {

    private enum Type{
      SINGLE,
      KING
    }

    private enum Color{
      RED,
      WHITE
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

    public void makeMove(){

    }
}
