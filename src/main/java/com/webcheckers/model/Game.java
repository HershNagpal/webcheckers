package com.webcheckers.model;

import java.util.*;

/**
 * Combines the board and players in order to play the game. Deals with game logic that combines many
 * different classes to calculate something.
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
     * Stack of moves made before a move is submitted.
     * Used for backing up a move.
     */
    //private List<Move> lastMoves = new ArrayList<>();
    private Stack<Move> lastMoves = new Stack<>();

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
        this.board = board;
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
        /*
        if(redPlayer.getName().equals("debug") && whitePlayer.getName().equals("test")) {
            this.board = new Board(Board.DEBUG_PIECES);
        } else if (redPlayer.getName().equals("test") && whitePlayer.getName().equals("debug")) {
            this.board = new Board(Board.DEBUG_PIECES);
        } else {*/
            this.board = new Board();
        //}
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
     * Updates the board to implement a move
     *
     * @param move starting position and ending position
     */
    public void makeMove(Move move) {
        if (move.getStart().isDiagonalTo(move.getEnd())) {
            board.makeNormalMove(move);
            checkIfKinged(move);
        } else if (move.getStart().isDiagonalJumpTo(move.getEnd())) {
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

        // @TODO this may not actually work. 
        Piece movingPiece = board.getPieceAtPosition(lastMove.getStart());
        //Enforce player ending a multiple jump move
        Position lastMoveEndPos = lastMove.getEnd();
        //Multiple jump move has not been completed
        if (MoveManager.isLastMoveJump(lastMove, movingPiece) && board.getJumpLocations(lastMoveEndPos).size() > 0) {
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
        List<Position> movablePieceLocations = board.getMovablePieceLocations(getActiveColor());
        for (Position indexPosition : movablePieceLocations) {
            // Check if piece at indexPosition has a position to jump to
            if (board.getJumpLocations(indexPosition).size() > 0) {
                return true;
            }
        }
        return false;
    }
}