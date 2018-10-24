package com.webcheckers.model;

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

    //
    // Constructor
    //
    public Board(){
        pieces = new Piece[ROWS][COLUMNS];
        setUpBoard();
        boardView = new BoardView(pieces);
        
    }

    /**
     * Method used to set up spaces and checkers on the board.
     * Red checkers are placed on top three rows and black checkers
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
     * Flips the BoardView to make it so each player sees their pieces closest to them.
     * @return a BoardView that is flipped around (pieces on the far side would appear closer).
     */
    public BoardView getFlippedBoardView() {
        Piece[][] flippedPieces = new Piece[ROWS][COLUMNS];
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                flippedPieces[7 - row][7 - col] = pieces[row][col];
            }
        }
        return new BoardView(flippedPieces);
    }

    /**
     * returns the visual representation of this board.
     * @return the boardview associated with this board.
     */
    public BoardView getBoardView(){
      return boardView;
    }

    public Piece[][] getPieces(){
        return pieces;
    }

}
