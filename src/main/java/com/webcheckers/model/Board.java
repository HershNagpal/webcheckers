package com.webcheckers.model;

import com.webcheckers.model.Piece.Type;

/**
 * Represents the Checkers Board and all the logical operations that need to be done on
 * pieces for the game to function.
 * @author Christopher Daukshus
 * @author Matthew Bollinger
 *
 */
public class Board {


    /**
     * ROWS - number of rows on the board
     * COLUMNS - number of columns on the board
     * pieces - 2D array showing where each piece is being kept
     * board - the boardview being sent to the GetGameRoute
     * DEBUG_PIECES - a custom set of pieces to be used for testing purposes.
     */
    public final static int ROWS = 8;
    public final static int COLUMNS = 8;
    private Piece[][] pieces;
    public final static Piece[][] DEBUG_PIECES =
            {   {null, new Piece(Color.WHITE, Type.SINGLE), null, new Piece(Color.WHITE, Type.SINGLE), null, null, null, null},
                {null, null, null, null, new Piece(Color.WHITE, Type.SINGLE), null, null, null},
                {null, null, null, null, null, new Piece(Color.WHITE, Type.SINGLE), null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, new Piece(Color.WHITE, Type.SINGLE), null, null},
                {null, null, new Piece(Color.WHITE, Type.SINGLE), null, null, null, null, null},
                {null, null, null, null, null, new Piece(Color.RED, Type.SINGLE), null, new Piece(Color.RED, Type.SINGLE)},
                {new Piece(Color.RED, Type.SINGLE), null, null, null, null, null, new Piece(Color.RED, Type.SINGLE), null},
            };

    /**
     * Board constructor that initializes and sets up the 2d Piece array.
     * Creates a BoardView object using the pieces 2d array.
     */
    public Board(){
        pieces = new Piece[ROWS][COLUMNS];
        setUpBoard();
    }

    /**
     * Board constructor that initializes and sets up the 2d Piece array from a given custom configuration.
     * Creates a BoardView object using the pieces 2d array.
     */
    public Board(Piece[][] customPieces){
        pieces = customPieces;
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
        return new BoardView(pieces);
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
                if(this.pieces[row][col] != null && b2.pieces[row][col] != null){
                    if(!(this.pieces[row][col].equals(b2.pieces[row][col]))){
                        deepEqual = false;
                    }
                }

            }
        }

        return deepEqual;
    }

    /**
     * Returns the piece at the given Board Position.
     * @param position the Position of the piece
     * @return the Piece at the location. If none, returns null.
     */
    public Piece getPieceAtPosition(Position position){
        int row = position.getRow();
        int col = position.getCell();
        return pieces[row][col];

    }

    public Piece getPieceAtFlippedPosition(Position position) {
        int row = 7 - position.getRow();
        int col = 7 - position.getCell();
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
        pieces[rowEnd][colEnd] = startingPiece;
        pieces[rowStart][colStart] = null;
    }

    /**
     * Performs a jump move by updating the piece moved and removing the
     * jumped piece.
     * @param move Jump move to be performed on board.
     */
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

        //If row or column distance is 2, then the jump is going up/right, so the piece in between is one above the start.
        //Otherwise, the jump is going down/left, so the piece is one below the start.
        int jumpedRow;
        int jumpedCol;

        //jumpMove up
        if(rowDistance == 2){
            jumpedRow = rowStart + 1;
        }
        //jumpMove down
        else{
            jumpedRow = rowStart - 1;
        }

        //jumpMove right
        if(colDistance == 2){
            jumpedCol = colStart + 1;
        }
        //jumpMove left
        else{
            jumpedCol = colStart - 1;
        }

        pieces[jumpedRow][jumpedCol] = null;

    }

    /**
     * Performs a back up for a jump move by updating the piece that jumped
     * and by placing the piece that was jumped back into the board.
     * @param move Jump move to be performed on board.
     */
    public void makeBackUpJumpMove(Move move, Color activeColor){
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

        int jumpedRow;
        int jumpedCol;

        //jumpMove up
        if(rowDistance == 2){
            jumpedRow = rowStart + 1;
        }
        //jumpMove down
        else{
            jumpedRow = rowStart - 1;
        }

        //jumpMove right
        if(colDistance == 2){
            jumpedCol = colStart + 1;
        }
        //jumpMove left
        else{
            jumpedCol = colStart - 1;
        }

        //Place removed piece that got jumped back into board
        if(move.isBackUpMove()){
            if(activeColor.equals(Color.RED)) {
                pieces[jumpedRow][jumpedCol] = new Piece(Color.WHITE, Piece.Type.SINGLE);
            }
            else{
                pieces[jumpedRow][jumpedCol] = new Piece(Color.RED, Piece.Type.SINGLE);
            }
        }

    }

    /**
     * TODO: check and write tests
     * Check the board to see if the pieces of the given color are
     * eliminated.
     * @param color The color of the pieces to check
     * @return If there are no more pieces of the given color
     */
    public boolean checkAllPiecesEliminated(Color color) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece currentPiece = pieces[row][col];
                if(currentPiece != null) {
                    if (currentPiece.getColor() == color) {
                        return false;
                    }
                }
            }
        }
        // Could not find a piece of the given color
        return true;
    }

    /**
     * TODO: check and write tests
     * Check the board to see if the pieces of the given color can still
     * move at any instance.
     * @param color The color of the pieces to check
     * @return If there is a case where there can be a valid move made
     */
    public boolean checkNoMoreValidMoves(Color color) {
        // Get the start position
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Position start = new Position(row, col);
                // Get the end position
                for (int r = 0; r < 8; r++) {
                    for (int c = 0; c < 8; c++) {
                        Position end = new Position(r, c);
                        // Create the move through these positions
                        Move move = new Move(start, end);
                        //Get movingPiece from start Position
                        Piece movingPiece = this.getPieceAtPosition(move.getStart());
                        // Check if move is valid
                        if (MoveManager.isValidMove(move, movingPiece)) {
                            // there is at least one valid move, stop checking
                            return false;
                        }

                    }
                }
            }
        }
        return true;
    }

    /**
     * TODO: check and write tests
     * Helper method for checking no more valid moves. The start position
     * must have a piece and be a piece of the given color.
     * @param start Start position
     * @param color Color of the piece to check
     * @return If conditions are satisfied
     */
    private boolean startChecks(Position start, Color color) {
        Piece startPiece = getPieceAtPosition(start);
        return (getPieceAtPosition(start) == null ||
                startPiece.getColor() != color);
    }

    /**
     * TODO: check and write tests
     * Helper method for checking no more valid moves. The end position
     * must have not have a piece, is diagonal to the start position, and
     * is forward to the start position.
     */
    private boolean endChecks(Position start, Position end) {
        Piece endPiece = getPieceAtPosition(end);
        return (endPiece != null || !end.isDiagonalTo(start) ||
                !end.isForwardTo(start));
    }

}