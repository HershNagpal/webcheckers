package com.webcheckers.model;

public class Checker {

    public enum PieceType{
        RED,
        WHITE,
        RED_KING,
        WHITE_KING

        //TODO: Specify Checker Direction
    }

    private PieceType type;
    private int column;
    private int row;

    public Checker(PieceType type, int column, int row) {
        this.type = type;
        this.column = column;
        this.row = row;
    }

    public void makeMove(){

    }
}
