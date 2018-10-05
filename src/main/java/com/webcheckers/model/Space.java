package com.webcheckers.model;

public class Space {


    public enum SpaceType {KING_RED, KING_BLACK, NON_KING}

    private int row;
    private int column;
    private SpaceType thisSpaceType;

    public Space(int row, int column){
        this.row = row;
        this.column = column;
        if(row == 0){
            thisSpaceType = SpaceType.KING_BLACK;
        }
        else if (row == 7){
            thisSpaceType = SpaceType.KING_RED;
        }
        else{
            thisSpaceType = SpaceType.NON_KING;
        }
    }
}
