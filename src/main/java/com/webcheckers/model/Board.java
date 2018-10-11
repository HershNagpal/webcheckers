package com.webcheckers.model;

/**
 * Represents the Checkers Board
 * @author
 */
public class Board {

    private static int ROWS = 8;
    private static int COLUMNS = 8;
    private Piece[][] pieces;
    private BoardView board;

    public Board(){
        pieces = new Piece[ROWS][COLUMNS];
        board = new BoardView();
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
          if ( row % 2 == col % 2 ) {
            if (row < 3) {
              Piece piece = new Piece(Color.RED, Piece.Type.SINGLE);
              pieces[row][col] = piece;
            }
              else if (row > 4){
                Piece piece = new Piece(Color.WHITE,Piece.Type.SINGLE);
                pieces[row][col] = piece;
            }
          }
        }
      }
    }
}
