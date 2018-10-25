package com.webcheckers.model;

import static java.lang.Math.abs;

/**
 * Represents the Checkers Board and all the logical operations that need to be done on 
 * pieces for the game to function.
 * @author Christopher Daukshus
 *
 */
public class Board {

  /**
   * ROWS - number of rows on the board
   * COLUMNS - number of columns on the board
   * pieces - 2D array showing where each piece is being kept
   * board - the boardview being sent to the GetGameRoute
   *
   */
  private static int ROWS = 8;
  private static int COLUMNS = 8;
  private Piece[][] pieces;
  private BoardView boardView;

  /**
   * Board constructor that initializes and sets up the 2d Piece array.
   * Creates a BoardView object using the pieces 2d array.
   */
  public Board(){
      pieces = new Piece[ROWS][COLUMNS];
      setUpBoard();
      boardView = new BoardView(pieces);
  }

  /**
   * Method used to set up spaces and checkers on the board.
   * Red checkers are placed on top three rows and white checkers
   * placed on bottom three rows.
   */
  private void setUpBoard(){
      for (int row = 0; row < ROWS; row++) {
          for (int col = 0; col < COLUMNS; col++) {
              if ( row % 2 != col % 2 ) {
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

  /**
   * Flips the pieces in the Board's 2d Array of pieces.
   * @return 2d array of pieces.
   */
  public Piece[][] getFlippedPieces(){
    Piece[][] flippedPieces = new Piece[ROWS][COLUMNS];
    for (int row = 0; row < ROWS; row++) {
      for (int col = 0; col < COLUMNS; col++) {
        flippedPieces[7 - row][7 - col] = pieces[row][col];
      }
    }
    return flippedPieces;
  }

  /**
   * Flips the BoardView to make it so each player sees their pieces closest to them.
   * @return a BoardView that is flipped around (pieces on the far side would appear closer).
   */
  public BoardView getFlippedBoardView() {
      Piece[][] flippedPieces = getFlippedPieces();
      return new BoardView(flippedPieces);
  }

  /**
   * returns the visual representation of this board.
   * @return the boardview associated with this board.
   */
  public BoardView getBoardView(){
    return boardView;
  }

  /**
   * Accessor method for the board pieces.
   * This method is used to test Board.
   * @return 2d array of Pieces
   */
  public Piece[][] getPieces(){
      return pieces;
  }

  /**
   * Overriding equals() for deep equality between Board Objects.
   * This method is used for testing.
   * @param obj Object being compared to "this" Board
   * @return true if "this" is equal to obj
   */
  public boolean equals(Object obj){
    if(this == obj){
      return true;
    }
    else if(!(obj instanceof Board)){
      return false;
    }

    Board b2 = (Board)obj;
    boolean deepEqual = true;

    for(int row = 0; row < this.pieces.length; row++){
      for(int col = 0; col < this.pieces[row].length; col++){
        if(this.pieces[row][col] == null && b2.pieces[row][col] == null){
          //continue
        }
        else if(!(this.pieces[row][col].equals(b2.pieces[row][col]))){
          deepEqual = false;
        }

      }
    }

    return deepEqual;
  }

  public Piece checkPiece(Position position){
      int row = position.getRow();
      int col = position.getCell();
      return pieces[row][col];
  }


  public void makeNormalMove(Move move){
      Position startingPosition = move.getStart();
      Position endingPosition = move.getEnd();

      int rowStart = startingPosition.getRow();
      int colStart = startingPosition.getCell();
      int rowEnd = endingPosition.getRow();
      int colEnd = endingPosition.getCell();

      Piece startingPiece = pieces[rowStart][colStart];
      pieces[rowStart][colStart] = null;
      pieces[rowEnd][colEnd] = startingPiece;

  }

  public void makeJumpMove(Move move){
      Position startingPosition = move.getStart();
      Position endingPosition = move.getEnd();

      int rowStart = startingPosition.getRow();
      int colStart = startingPosition.getCell();
      int rowEnd = endingPosition.getRow();
      int colEnd = endingPosition.getCell();

      Piece startingPiece = pieces[rowStart][colStart];
      pieces[rowStart][colStart] = null;
      pieces[rowEnd][colEnd] = startingPiece;

      int rowDistance = rowEnd - rowStart;
      int colDistance = colEnd - colStart;

      int jumpedPieceRow = rowStart + rowDistance;

  }
}
