package com.webcheckers.model;

public class Checker {

    public enum CheckerType{
        RED,
        WHITE,
        RED_KING,
        WHITE_KING

        //TODO: Specify Checker Direction
    }

    private CheckerType type;
    private int column;
    private int row;

    public Checker(CheckerType type, int row, int column) {
        this.type = type;
        this.row = row;
        this.column = column;
    }

    public void makeMove(){

    }
}
