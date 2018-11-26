package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;
import com.webcheckers.model.Piece.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the Checkers Board and all the logical operations that need to be done on
 * pieces for the game to function.
 *
 * @author Christopher Daukshus
 * @author Matthew Bollinger
 * @author Hersh Nagpal
 */
public class Board {

    /**
      * The number of rows that this Board contains.
      */
    public final static int ROWS = 8;

    /**
      * The number of columns that this Board contains.
      */
    public final static int COLUMNS = 8;

    /**
     * The array of pieces that this board contains.
     */
    private Piece[][] pieces;

    /**
     * A default single red piece used in debug boards.
     */
    public static Piece red = new Piece(Color.RED, Type.SINGLE);

    /**
     * A default single white piece used in debug boards.
     */
    public static Piece white = new Piece(Color.WHITE, Type.SINGLE);

    /**
     * Various debug boards used for acceptance testing.
     */
    public final static Piece[][] DEBUG_PIECES =
            {   {null, red, null, red, null, null, null, null},
                    {null, null, null, null, red, null, null, null},
                    {null, null, null, null, null, red, null, null},
                    {null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, red, null, null},
                    {null, null, red, null, null, null, null, null},
                    {null, null, null, null, null, red, null, white},
                    {white, null, null, null, null, null, white, null},
            };
    public final static Piece[][] KINGED_NO_JUMP =
            {   {null, red, null, null, null, null, null, null},
                    {null, null, null, null, red, null, null, null},
                    {null, white, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null}
            };
    public final static Piece[][] END_GAME =
            {   {null, red, null, null, null, null, null, null},
                    {null, null, white, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null}
            };
    public final static Piece[][] NO_MOVES_RED =
            {   {null, red, null, null, null, null, null, null},
                    {white, null, white, null, null, null, null, null},
                    {null, null, null, white, null, null, null, null},
                    {null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null, null}
            };


    /**
     * Board constructor that initializes and sets up the 2d Piece array.
     * Creates a BoardView object using the pieces 2d array.
     */
    public Board() {
        pieces = new Piece[ROWS][COLUMNS];
        setUpBoard();
    }

    /**
     * Board constructor that initializes and sets up the 2d Piece array from a given custom configuration.
     * Creates a BoardView object using the pieces 2d array.
     */
    public Board(Piece[][] customPieces) {
        pieces = customPieces;
    }

    /**
     * Method used to set up spaces and checkers on the board.
     * Red checkers are placed on top three rows and white checkers
     * placed on bottom three rows.
     */
    private void setUpBoard() {
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
     * Flips the pieces in the Board's 2D Array of pieces as if
     * they were viewed by someone on the other side of the board.
     *
     * @return a flipped 2D array of pieces.
     */
    public Piece[][] getFlippedPieces() {
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
     *
     * @return a BoardView that is flipped around (pieces on the far side would appear closer).
     */
    public BoardView getFlippedBoardView() {
        Piece[][] flippedPieces = getFlippedPieces();
        return new BoardView(flippedPieces);
    }

    /**
     * returns the visual representation of this board.
     *
     * @return the boardview associated with this board.
     */
    public BoardView getBoardView() {
        return new BoardView(pieces);
    }

    /**
     * Get the array of pieces on this board.
     *
     * @return 2d array of Pieces
     */
    public Piece[][] getPieces() {
        return pieces;
    }

    /**
     * Overriding equals() for deep equality between Board Objects.
     * This method is used for testing.
     *
     * @param obj Object being compared to "this" Board
     * @return true if "this" is equal to obj
     */
    public boolean equals(Object obj) {
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
     *
     * @param position the Position of the piece
     * @return the Piece at the location. If none, returns null.
     */
    public Piece getPieceAtPosition(Position position) {
        int row = position.getRow();
        int col = position.getCell();
        return pieces[row][col];

    }

    /**
     * Gets the Piece at a given Position, but from the perspective
     * of a player on the other side of the Board.
     *
     * @param position The position of the Piece on the Board.
     * @return
     */
    public Piece getPieceAtFlippedPosition(Position position) {
        int row = 7 - position.getRow();
        int col = 7 - position.getCell();
        return pieces[row][col];
    }

    /**
     * Perform a normal single move on the Board.
     *
     * @param move The move to be made.
     */
    public void makeNormalMove(Move move) {
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
     *
     * @param move Jump move to be performed on board.
     */
    public void makeJumpMove(Move move) {
        int rowStart = move.getStart().getRow();
        int colStart = move.getStart().getCell();
        int rowEnd = move.getEnd().getRow();
        int colEnd = move.getEnd().getCell();

        Piece startingPiece = pieces[rowStart][colStart];
        pieces[rowStart][colStart] = null;
        pieces[rowEnd][colEnd] = startingPiece;

        Position jumpedPosition = move.getJumpedPosition();

        this.pieces[jumpedPosition.getRow()][jumpedPosition.getCell()] = null;
    }

    /**
     * Performs a back up for a jump move by updating the piece that jumped
     * and by placing the piece that was jumped back into the board.
     *
     * @param move Jump move to be performed on board.
     */
    public void makeBackUpJumpMove(Move move, Color activeColor) {
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
        if(move.isBackUpMove()) {
            if(activeColor.equals(Color.RED)) {
                pieces[jumpedRow][jumpedCol] = new Piece(Color.WHITE, Piece.Type.SINGLE);
            }
            else{
                pieces[jumpedRow][jumpedCol] = new Piece(Color.RED, Piece.Type.SINGLE);
            }
        }
    }

    /**
     * Check the board to see if the pieces of the given color are
     * eliminated.
     *
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

        return true;
    }

    /**
     * Check the board to see if the pieces of the given color can still
     * move at any instance.
     *
     * @param color The color of the pieces to check
     * @return If there is a case where there can be a valid move made
     */
    public boolean checkNoMoreValidMoves(Color color) {
        // Get the start position
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Position start = new Position(row, col);
                // Is this a bad start position?
                Piece startPiece = getPieceAtPosition(start);
                if (startPiece == null || startPiece.getColor() != color) {
                    continue;
                }
                // Get the end position
                for (int r = 0; r < 8; r++) {
                    for (int c = 0; c < 8; c++) {
                        Position end = new Position(r, c);
                        // Create the move through these positions
                        Move move = new Move(start, end);
                        // Check if move is valid
                        if (MoveManager.isValidMove(move, this)) {
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
     * Takes in the position of a piece and returns all locations that it cn make a valid jump move.
     * Must be a piece at given location.
     *
     * @param start the position of a piece.
     * @return a list of the possible valid jump moves this piece can make.
     */
    public List<Position> getJumpLocations(Position start) {
        List<Position> validJumpPositions = new ArrayList<>();

        List<Position> possibleJumpPositions = this.findPossibleJumpPositions(start);

        Move possibleJumpMove;
        for (Position currentEnd : possibleJumpPositions) {
            possibleJumpMove = new Move(start, currentEnd);
            if(MoveManager.isJumpMove(possibleJumpMove, this)) {
                validJumpPositions.add(currentEnd);
            }
        }

        return validJumpPositions;
    }

    /**
     * Returns a list of positions to be jumped to on the board from a given position.
     *
     * @param position the position of the piece to be checked for possible jump locations.
     * @return a list of the possible locations to be jumped to from this position.
     */
    public List<Position> findPossibleJumpPositions(Position position) {
        List<Position> possibleJumpPositions = new ArrayList<>();
        int row = position.getRow();
        int col = position.getCell();

        // Upper
        if(row - 2 >= 0) {
            if (col - 2 >= 0) {
                // Left
                possibleJumpPositions.add((new Position(row - 2, col - 2)));
            }
            if (col + 2 < Board.COLUMNS) {
                // Right
                possibleJumpPositions.add((new Position(row - 2, col + 2)));
            }
        }
        // Lower
        if(row + 2 < Board.ROWS) {
            if (col - 2 >= 0) {
                // Left
                possibleJumpPositions.add((new Position(row + 2, col - 2)));
            }
            if (col + 2 < Board.COLUMNS) {
                // Right
                possibleJumpPositions.add((new Position(row + 2, col + 2)));
            }
        }

        return possibleJumpPositions;
    }

    /**
     * Returns all of the locations of pieces that can make moves.
     *
     * @return a list of positions that have pieces that can move.
     */
    public List<Position> getMovablePieceLocations(Color activeColor) {
        List<Position> movablePieceLocations = new ArrayList<>();
        Position indexPosition;
        Piece indexPiece;

        // Iterate through all pieces to see which ones are valid to move this turn.
        for (int row = 0; row < Board.ROWS; row++) {
            for (int col = 0; col < Board.COLUMNS; col++) {
                indexPosition = new Position(row, col);
                if (this.getPieceAtPosition(indexPosition) != null) {
                    indexPiece = this.getPieceAtPosition(indexPosition);
                    // Add the possible positions of pieces that are the active color to the array.
                    if (indexPiece.getColor() == activeColor) {
                        movablePieceLocations.add(indexPosition);
                    }
                }
            }
        }
        return movablePieceLocations;
    }
}