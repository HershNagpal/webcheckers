package com.webcheckers.model;

public class Board {

    private final int ROWS = 8;
    private final int COLUMNS = 8;
    private Space[][] spaces;

    public Board(){
        spaces = new Space[ROWS][COLUMNS];
        for(int row = 0; row < ROWS; row++){
            for(int column = 0; column < COLUMNS; column++){
                Space boardSpace = new Space(row, column);
                spaces[row][column] = boardSpace;
            }
        }
    }

}
