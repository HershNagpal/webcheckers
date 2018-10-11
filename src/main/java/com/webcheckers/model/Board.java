package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Checkers Board
 * @author
 */
public class Board {

    private static int ROWS = 8;
    private static int COLUMNS = 8;
    private Space[][] spaces;
    private List<Row> r;

    public Board(){
        spaces = new Space[ROWS][COLUMNS];
        r = new ArrayList<>();
        setUpBoard();
    }

    /**
     * Method used to set up spaces and checkers on the board.
     * Red checkers are placed on top three rows and black checkers
     * placed on bottom three rows.
     */
    private void setUpBoard(){
      for (int row = 0; row < ROWS; row++) {
          Row currentRow = new Row(row);
          r.add(currentRow);
        for (int col = 0; col < COLUMNS; col++) {
          Space boardSpace = new Space(row, col);
          spaces[row][col] = boardSpace;
          currentRow.addSpace(boardSpace);
          if ( row % 2 == col % 2 ) {
            if (row < 3) {
              Piece piece = new Piece(Color.RED, Piece.Type.SINGLE,row,col);
              spaces[row][col].setPiece(piece);
            }
              else if (row > 4){
                Piece piece = new Piece(Color.WHITE,Piece.Type.SINGLE,row,col);
                spaces[row][col].setPiece(piece);
            }
          }
        }
      }
    }

    public Space[][] getSpaces(){return spaces;}

    public List<Row> getRows(){return r;}
}
