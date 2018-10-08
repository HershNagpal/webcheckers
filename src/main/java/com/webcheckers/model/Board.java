package com.webcheckers.model;

/**
 * Represents the Checkers Board
 * @author
 */
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
              Piece piece = new Piece(Piece.Color.RED, Piece.Type.SINGLE,row,col);
              spaces[row][col].setPiece(piece);
            }
              else if (row > 4){
                Piece piece = new Piece(Piece.Color.WHITE,Piece.Type.SINGLE,row,col);
                spaces[row][col].setPiece(piece);
            }
          }
        }
      }
    }
}
