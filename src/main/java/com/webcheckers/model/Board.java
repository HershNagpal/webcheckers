package com.webcheckers.model;

public class Board {

    private final int ROWS = 8;
    private final int COLUMNS = 8;
    private Space[][] spaces;

    public Board(){
        spaces = new Space[ROWS][COLUMNS];
        setUpBoard();
    }

    /**
     * Method used to set up spaces and checkers on the board.
     * Red checkers are placed on top three rows and black checkers
     * placed on bottom three rows.
     */
    private void setUpBoard(){
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                Space boardSpace = new Space(row, col);
                spaces[row][col] = boardSpace;
                if ( row % 2 == col % 2 ) {
                    if (row < 3) {
                        Checker checker = new Checker(Checker.CheckerType.RED,row,col);
                        spaces[row][col].setChecker(checker);
                    }
                    else if (row > 4){
                        Checker checker = new Checker(Checker.CheckerType.WHITE,row,col);
                        spaces[row][col].setChecker(checker);
                    }
                }
            }
        }
    }
}
