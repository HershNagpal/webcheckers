package com.webcheckers.model;

import java.util.*;

/**
 * Combines the board and players in order to play the game. Deals with game logic that combines many
 * different classes to calculate something.
 *
 * @author Luis Gutierrez
 * @author Christopher Daukshus
 * @author Hersh Nagpal
 * @author Michael Kha
 * @author Matthew Bollinger
 */
public class Game {

    /**
     * The game number relative to other games created
     */
    private int gameNum;

    /**
     * The game's unique ID
     */
    private String gameID;

    /**
     * Used to prevent player from making successive simple moves
     * and for enforcing the completion of jump moves.
     */
    boolean canContinueMoving = true;

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
    Board board;

    /**
     * The color of the player taking their turn.
     */
    private Color activeColor;

    /**
     * Has a move been made since last checked.
     */
    boolean gameChanged;

    /**
     * Stack of moves made before a move is submitted.
     * Used for backing up a move.
     */
    List<Move> lastMoves = new ArrayList<>();

    /**
     * Stack of all moves submitted in the game for each turn.
     * Used for replaying the game.
     */
    Map<Integer, List<Move>> allMoves;

    /**
     * Current turn number being played
     */
    int turnIndex;

    /**
     * Start a game with a given board state. Used only for testing.
     *
     * @param redPlayer The red player
     * @param whitePlayer The white player
     * @param board The state of the board
     */
    public Game(Player redPlayer, Player whitePlayer, Board board) {
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.board = board;
        activeColor = Color.RED;
        lastMoves = new ArrayList<>();
        allMoves = new HashMap<>();
        turnIndex = 0;
    }

    /**
     * Start a new game with two players.
     *
     * @param redPlayer   The red player
     * @param whitePlayer The white player
     */
    public Game(Player redPlayer, Player whitePlayer, int gameNum) {
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        String whitePlayerName = whitePlayer.getName();
        if(redPlayer.getName().equals("debug")){
            switch(whitePlayerName){
                case "PieceMovement":
                    this.board = new Board(Board.PIECE_MOVEMENT);
                    break;
                case "KingedNoJump":
                    this.board = new Board(Board.KINGED_NO_JUMP);
                    break;
                case "EndGame":
                    this.board = new Board(Board.END_GAME);
                    break;
                case "NoMovesWhite":
                    this.board = new Board(Board.NO_MOVES_WHITE);
                    break;
                case "MultipleJumpRed":
                    this.board = new Board(Board.MULTIPLE_JUMP_RED);
                    break;
                default:
                    this.board = new Board();
            }
        } else {
            this.board = new Board();
        }
        lastMoves = new ArrayList<>();
        allMoves = new HashMap<>();
        turnIndex = 0;
        activeColor = Color.RED;
        // Unique ID
        gameID = redPlayer.getName() + "+" + whitePlayer.getName() + "+" + gameNum;
        this.gameNum = gameNum;
    }

    /**
     * Copy constructor.
     * Copy the finished game for a replay game.
     *
     * @param game Game to copy
     */
    public Game(Game game) {
        this.redPlayer = game.redPlayer;
        this.whitePlayer = game.whitePlayer;
        // reset board to starting state
        this.board = new Board();
        activeColor = Color.RED;
        this.allMoves = new HashMap<>(game.allMoves);
        this.gameID = game.gameID;
        this.gameNum = game.gameNum;
    }

    /**
     * Get the unique game ID.
     *
     * @return The game ID
     */
    public String getGameID() {
        return gameID;
    }

    /**
     * Get the game name, a form of the game ID, but reader-friendly.
     * Used by the home.ftl through reflection.
     *
     * @return The game name
     */
    public String getGameName() {
        String[] parts = gameID.split("\\+");
        return "Game " + String.valueOf(gameNum) + ": "
                + parts[0] + " vs. " + parts[1];
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
     * Get the winner if any yet.
     *
     * @return The winner or null
     */
    public Player getWinner() {
        return winner;
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
     * If the game has changed since last checked.
     *
     * @return If the game has changed (a move has been submitted).
     */
    public boolean hasGameChanged() {
        if (gameChanged) {
            gameChanged = false;
            return true;
        }
        return false;
    }

    /**
     * Get the status of the game by checking win conditions.
     *
     * @return Is the game over or still going
     */
    public boolean isGameOver() {
        if (resigned) {
            return true;
        }
        if (board.checkNoMoreValidMoves(activeColor) ||
                board.checkAllPiecesEliminated(activeColor)) {
            winner = activeColor == Color.RED ? whitePlayer : redPlayer;
            return true;
        }

        return false;
    }

    /**
     * Check if the player is the winner.
     *
     * @param player Player to check
     * @return If the player won
     */
    public boolean isWinner(Player player) {
        return player == winner;
    }

    /**
     * Resign the game for the given player. The other player is the winner.
     *
     * @param player Player resigning
     * @return True if successful
     */
    public boolean resignGame(Player player) {
        // Resign already occurred
        if (resigned) {
            return false;
        }
        // Game over update state
        winner = player == redPlayer ? whitePlayer : redPlayer;
        if (isActivePlayer(player)) {
            switchActiveColor();
        }
        resigned = true;
        return true;
    }

    /**
     * Has this game been resigned?
     *
     * @return If the game has been resigned by a player.
     */
    public boolean didPlayerResign() {
        return resigned;
    }

    /**
     * Checks if the move being made by a player is valid or not.
     *
     * @param move The Move object that the player is making
     * @return True if the move is valid, false if it is invalid.
     */
    public boolean validateMove(Move move) {
        // If it is red turn, move is flipped
        if (getActiveColor().equals(Color.RED)) {
            move = move.flipMove();
        }

        //Get piece from start position
        Piece movingPiece = board.getPieceAtPosition(move.getStart());

        if (!getActiveColor().equals(movingPiece.getColor())) {
            return false;
        }

        if (MoveManager.isValidMove(move, board) && canContinueMoving) {
            if (MoveManager.isSingleMove(move, movingPiece)) {
                //Force Jump Move
                if (jumpMoveExists()) {
                    return false;
                }
                canContinueMoving = false;
            }
            lastMoves.add(move);
            makeMove(move);

            //Prevent player from performing a single jump after jump move is finished
            if(board.getJumpLocations(move.getEnd()).size() == 0){
                canContinueMoving = false;
            }

            return true;

        } else {
            return false;
        }
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
        if (move.getStart().isDiagonalAdjacentTo(move.getEnd())) {
            board.makeNormalMove(move);
        } else if (move.getStart().isDiagonalJumpTo(move.getEnd())) {
            if (move.isBackUpMove()) {
                board.makeBackUpJumpMove(move, activeColor);
            } else {
                board.makeJumpMove(move);
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
        if (lastMoves.isEmpty()) {
            return false;
        }

        Move lastMove = lastMoves.get(lastMoves.size()-1);
        Piece movingPiece = board.getPieceAtPosition(lastMove.getEnd());
        //Enforce player ending a multiple jump move
        Position lastMoveEndPos = lastMove.getEnd();
        //Multiple jump move has not been completed
        kingPiece(lastMove);
        boolean kinged = board.getPieceAtPosition(lastMoveEndPos).isKing();
        if (MoveManager.isLastMoveJump(lastMove, movingPiece)) {
            if (!kinged && board.getJumpLocations(lastMoveEndPos).size() > 0) {
                return false;
            }
        }

        // Keep track of moves that happened each turn
        List<Move> copy = new ArrayList<>(lastMoves);
        allMoves.put(turnIndex++, copy);

        //reset lastMoves
        lastMoves.clear();
        this.canContinueMoving = true;
        gameChanged = true;
        if (!isGameOver()) {
            switchActiveColor();
        }

        return true;
    }

    /**
     * "Undo" last move made and update both the list of previous moves,
     * and the lastMove.
     *
     * @return True or false depending on if the back up action was made
     */
    public boolean backUpMove() {
        if (lastMoves.isEmpty()) {
            return false;
        }

        //Remove lastMove from list of previous moves
        Move lastMove = lastMoves.remove(lastMoves.size()-1);
        Move backUpMove = lastMove.createBackUpMove();
        //Undo last move
        makeMove(backUpMove);

        canContinueMoving = true;
        return true;
    }

    /**
     * Checks whether or not the last move made a piece is able to be kinged.
     * If able to be kinged, do so.
     * This should only be called once a move has been made.
     *
     * @param move The move that was just made
     */
    void kingPiece(Move move){
        int endRow = move.getEnd().getRow();
        Piece currentPiece = board.getPieceAtPosition(move.getEnd());
        Color pieceColor = currentPiece.getColor();
        if (endRow == 7 && pieceColor == Color.RED) {
            currentPiece.becomeKing();
        } else if (endRow == 0 && pieceColor == Color.WHITE) {
            currentPiece.becomeKing();
        }
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

    /**
     * This method will get the location of a piece and check the jump locations of that piece.
     *
     * @param position location of an active player's piece
     * @return true if piece at position has a position to jump to
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
                        if (!movingPiece.getType().equals(Piece.Type.KING)) {
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

    /**
     * This is a method to set the current board
     * used for testing purposes
     */
    public void setBoard(Board board){
        this.board = board;
    }

}