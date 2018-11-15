package com.webcheckers.model;

import static java.lang.Math.abs;

import com.webcheckers.model.Piece.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Combines the board and players in order to play the game
 * @author Luis Gutierrez
 * @author Christopher Daukshus
 * @author Hersh Nagpal
 * @author Michael Kha
 * @author Matthew Bollinger
 */
public class Game {
    /**
     * Used to prevent player from making successive simple moves
     * and for enforcing the completion of jump moves.
     */
    private boolean canContinueMoving = true;

    /**
     * The resign state of the game.
     */
    private boolean resigned;

    /**
     * The winning player
     */
    private Player winner;

    /**
     * The red player denoting the challenger.
     */
    private Player redPlayer;

    /**
     * The white player denoting the challenged player.
     */
    private Player whitePlayer;

    /**
     * The state of the board and its pieces
     */
    private Board board;

    /**
     * The color of the player taking their turn.
     */
    private Color activeColor;

    /**
     * Last move made before a move is submitted.
     * lastMove is the move at the end of lastMoves.
     */
    private Move lastMove;

    /**
     * List of moves made before a move is submitted.
     * Used for backing up a move.
     */
    private List<Move> lastMoves = new ArrayList<>();

    /**
     * Start a game with a given board state.
     *
     * @param redPlayer   The red player
     * @param whitePlayer The white player
     * @param board       The state of the board
     */
    public Game(Player redPlayer, Player whitePlayer, Board board) {
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        if(redPlayer.getName().equals("debug") && whitePlayer.getName().equals("test")) {
            this.board = new Board(Board.DEBUG_PIECES);
        } else if (redPlayer.getName().equals("test") && whitePlayer.getName().equals("debug")) {
            this.board = new Board(Board.DEBUG_PIECES);

        } else {
            this.board = board;
        }
        activeColor = Color.RED;
    }

    /**
     * Start a new game with two players.
     *
     * @param redPlayer   The red player
     * @param whitePlayer The white player
     */
    public Game(Player redPlayer, Player whitePlayer) {
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        if(redPlayer.getName().equals("debug") && whitePlayer.getName().equals("test")) {
            this.board = new Board(Board.DEBUG_PIECES);
        } else if (redPlayer.getName().equals("test") && whitePlayer.getName().equals("debug")) {
            this.board = new Board(Board.DEBUG_PIECES);
        } else {
            this.board = new Board();
        }
        activeColor = Color.RED;
    }

    public boolean playerInGame(Player player) {
        return player == redPlayer || player == whitePlayer;
    }

    /**
     * Checks if the player is the red player in the game.
     *
     * @param player The player to check
     * @return Is the player the red player
     */
    public boolean isRedPlayer(Player player) {
        return redPlayer == player;
    }

    /**
     * Checks if the player is the white player in the game.
     *
     * @param player The player to check
     * @return Is the player the white player
     */
    public boolean isWhitePlayer(Player player) {
        return whitePlayer == player;
    }

    /**
     * Get the red player.
     *
     * @return The red player
     */
    public Player getRedPlayer() {
        return redPlayer;
    }

    /**
     * Get the white player.
     *
     * @return The white player
     */
    public Player getWhitePlayer() {
        return whitePlayer;
    }

    /**
     * Get the color of the player whose turn it is.
     *
     * @return The color of the current player
     */
    public Color getActiveColor() {
        return activeColor;
    }

    /**
     * Get the state of the board.
     *
     * @return The board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Get the correctly oriented BoardView for the respective player.
     *
     * @param player The red or white player
     * @return The correct BoardView
     */
    public BoardView getBoardView(Player player) {
        if (player == redPlayer) {
            return board.getFlippedBoardView();
        }
        return board.getBoardView();
    }

    /**
     * Get the status of the game by checking win conditions
     * @return Is the game over or still going
     */
    public boolean isGameOver() {
        if (resigned) {
            return true;
        }
        // TODO: make sure activeColor is always the right color needed
        if (board.checkNoMoreValidMoves(activeColor) ||
                board.checkAllPiecesEliminated(activeColor)) {
            winner = activeColor == Color.RED ? whitePlayer : redPlayer;
            return true;
        }
        return false;
    }

    /**
     * Check if the player is the winner
     * @param player Player to check
     * @return If the player won
     */
    public boolean isWinner(Player player) {
        return player == winner;
    }

    /**
     * Resign the game for the given player. The other player is the winner.
     * @param player Player resigning
     * @return True if successful
     */
    public boolean resignGame(Player player) {
        // game over
        winner = player == redPlayer ? whitePlayer : redPlayer;
        if (isActivePlayer(player)) {
            switchActiveColor();
        }
        resigned = true;
        return true;
    }

    /**
     * Has this game been resigned?
     * @return If the game has been resigned by a player.
     */
    public boolean didPlayerResign() {
        return resigned;
    }

    /**
     * Checks if the move being made by a player is valid or not.  
     * @param move The Move object that the player is making
     * @return True if the move is valid, false if it is invalid.
     * @TODO debug jumpMoveExists and integrate it here.
     */
    public boolean validateMove(Move move) {
        // If it is red turn, move is flipped
        if (getActiveColor().equals(Color.RED)) {
            move = move.flipMove();
        }

        //Get piece from start position
        Piece movingPiece = board.getPieceAtPosition(move.getStart());
        Color pieceColor = movingPiece.getColor();

        // Destination must be empty
        if(board.getPieceAtPosition(move.getEnd()) == null) {
            return false;
        }

        return pieceColor == getActiveColor() && MoveManager.isValidMove(move, board);
    }

    /**
     * Switches the active Color
     */
    public void switchActiveColor() {
        activeColor = activeColor == Color.RED ? Color.WHITE : Color.RED;
    }

    /**
     * Checks if player is the active player.
     *
     * @param player Player to check
     * @return Is the player is the active player
     */
    public boolean isActivePlayer(Player player) {
        return (player == redPlayer && activeColor == Color.RED)
                || (player == whitePlayer && activeColor == Color.WHITE);
    }

    /**
     * TODO Clean this up.
     * Updates the board to implement a move
     *
     * @param move starting position and ending position
     */
    public void makeMove(Move move) {

        Position moveStart = move.getStart();
        Position moveEnd = move.getEnd();

        int rowStart = moveStart.getRow();
        int colStart = moveStart.getCell();
        int rowEnd = moveEnd.getRow();
        int colEnd = moveEnd.getCell();

        int rowDistance = rowEnd - rowStart;
        rowDistance = abs(rowDistance);
        int colDistance = colEnd - colStart;
        colDistance = abs(colDistance);

        if (rowDistance == 1 && colDistance == 1) {
            board.makeNormalMove(move);
            checkIfKinged(move);
        } else if (rowDistance == 2 && colDistance == 2) {
            if (move.isBackUpMove()) {
                board.makeBackUpJumpMove(move, activeColor);
            } else {
                board.makeJumpMove(move);
                checkIfKinged(move);
            }
        }
    }

    /**
     * Submit the last made move. If there is no last made move an error
     * message is returned. Otherwise, the move is made and the other player's
     * turn now starts.
     *
     * @return True or false depending on if the move was made
     */
    public boolean submitTurn() {
        if (lastMove == null) {
            return false;
        }

        //Enforce player ending a multiple jump move
        Position lastMoveEndPos = lastMove.getEnd();
        //Multiple jump move has not been completed
        if (isLastMoveJump(lastMove) && getJumpLocations(lastMoveEndPos).size() > 0) {
            return false;
        }

        //reset lastMoves and lastMove
        lastMoves.clear();
        lastMove = null;
        switchActiveColor();

        this.canContinueMoving = true;

        return true;
    }

    /**
     * "Undo" last move made and update both the list of previous moves,
     * and the lastMove.
     *
     * @return True or false depending on if the back up action was made
     */
    public boolean backUpMove() {
        if (lastMove == null || lastMoves.isEmpty()) {
            return false;
        }

        //Undo last move
        Move backUpMove = lastMove.createBackUpMove();
        makeMove(backUpMove);

        //Remove lastMove from list of previous moves
        lastMoves.remove(lastMove);

        //Update lastMove
        if (!lastMoves.isEmpty()) {
            lastMove = lastMoves.get(lastMoves.size() - 1);
        } else {
            lastMove = null;
        }

        canContinueMoving = true;
        return true;
    }

    /**
     * Checks whether or not the last move made a piece able to be kinged.
     * If the piece is kinged, returns true. If already kinged or not able to be
     * kinged, returns false.
     * This should only be called once a move has been made.
     *
     * @param move The move that was just made
     * @return Whether or not the piece was kinged.
     */
    private boolean checkIfKinged(Move move) {
        int endRow = move.getEnd().getRow();
        Piece currentPiece = board.getPieceAtPosition(move.getEnd());
        Color pieceColor = currentPiece.getColor();

        if (endRow == 7 && pieceColor == Color.RED) {
            return currentPiece.kingPiece();
        } else if (endRow == 7 && pieceColor == Color.WHITE) {
            return currentPiece.kingPiece();
        }
        return false;
    }

    /**
     * This function will return true if there is a possible jump move for the
     * current player to make. This method should only be called when a move is
     * being validated.
     *
     * @return whether or not the current player can make a jump move.
     */
    public boolean jumpMoveExists() {
        List<Position> movablePieceLocations = getMovablePieceLocations();
        for (Position indexPosition : movablePieceLocations) {
            // Check if piece at indexPosition has a position to jump to
            //System.out.println("JUMP LOCATIONS: "+getJumpLocations(indexPosition));
            if (getJumpLocations(indexPosition).size() > 0) {
                //System.out.println("************************");
                return true;
            }
        }
        //System.out.println("************************");
        return false;
    }

    /**
     * This method will get the location of a piece and check the jump locations of that piece.
     *
     * @param position location of an active player's piece
     * @return true if piece at position has a position to jump to
     * @TODO make this so that it only checks kinged jump moves if the piece is a king.
     */
    public List<Position> getJumpLocations(Position position) {
        Piece movingPiece = board.getPieceAtPosition(position);

        int row = position.getRow();
        int col = position.getCell();
        List<Position> possibleJumpPos = new ArrayList<>();
        Position upperLeft, upperRight, lowerLeft, lowerRight;

        upperLeft = new Position(row - 2, col - 2);
        upperRight = new Position(row - 2, col + 2);
        lowerLeft = new Position(row + 2, col - 2);
        lowerRight = new Position(row + 2, col + 2);

        possibleJumpPos.add(upperLeft);
        possibleJumpPos.add(upperRight);
        possibleJumpPos.add(lowerLeft);
        possibleJumpPos.add(lowerRight);

        List<Position> jumpedPositions = new ArrayList<>();
        jumpedPositions.add(new Position(row - 1, col - 1));
        jumpedPositions.add(new Position(row - 1, col + 1));
        jumpedPositions.add(new Position(row + 1, col - 1));
        jumpedPositions.add(new Position(row + 1, col + 1));

        List<Position> validJumpPositions = new ArrayList<>();

        for (int i = 0; i < possibleJumpPos.size(); i++) {
            Position pos = possibleJumpPos.get(i);

            //make sure positions are on the board
            if (pos.getRow() < 0 || pos.getCell() < 0 || pos.getRow() >= Board.ROWS || pos.getCell() >= Board.COLUMNS) {
                //continue
            }

            // Check if position jumping into is not empty
            else if (board.getPieceAtPosition(pos) == null) {
                // Check if there is a piece being jumped
                Position positionJumped = new Position(pos.getRow(), pos.getCell());

                // Checking if its equal to null because you cannot call .equals on null
                if (board.getPieceAtPosition(jumpedPositions.get(i)) == null) {
                    //continue
                } else {
                    Piece jumpedPiece = board.getPieceAtPosition(jumpedPositions.get(i));
                    //Valid jump if the piece jumped is the opposite color of the active color.
                    if (!jumpedPiece.getColor().equals(activeColor)) {
                        System.out.println(jumpedPiece);
                        //Invalid jump if the piece is type SINGLE and going in the wrong direction
                        if (!movingPiece.getType().equals(Type.KING)) {
                            //SINGLE red piece cant jump up
                            if (activeColor.equals(Color.RED)) {
                                if (i != 0 && i != 1) {
                                    validJumpPositions.add(pos);
                                }
                            }
                            //SINGLE white piece cant jump down
                            else {
                                if (i != 2 && i != 3) {
                                    validJumpPositions.add(pos);
                                }
                            }
                        } else {
                            validJumpPositions.add(pos);
                        }
                    }
                }
            }
        }

        return validJumpPositions;
    }

    /**
     * Helper method that returns all of the locations of pieces that can make moves.
     *
     * @return a list of positions that have pieces that can move.
     */
    public List<Position> getMovablePieceLocations() {
        List<Position> movablePieceLocations = new ArrayList<>();
        Position indexPosition;
        Piece indexPiece;

        // Iterate through all pieces to see which ones are valid to move this turn.
        for (int row = 0; row < Board.ROWS; row++) {
            for (int col = 0; col < Board.COLUMNS; col++) {
                indexPosition = new Position(row, col);
                if (board.getPieceAtPosition(indexPosition) != null) {
                    indexPiece = board.getPieceAtPosition(indexPosition);
                    // Add the possible positions of pieces that are the active color to the array.
                    if (indexPiece.getColor() == getActiveColor()) {
                        movablePieceLocations.add(indexPosition);
                    }
                }
            }
        }
        return movablePieceLocations;
    }

}