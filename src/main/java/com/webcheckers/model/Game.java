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
 */
public class Game {
  private static final Message TURN_TRUE = new Message("true", MessageType.info);
  private static final Message TURN_FALSE = new Message("false", MessageType.info);
  private static final Message MOVE_TRUE = new Message("", MessageType.info);
  private static final Message MOVE_FALSE = new Message("Invalid move. Try again.", MessageType.error);
  private static final Message SUBMIT_TRUE = new Message("", MessageType.info);
  private static final Message SUBMIT_FALSE = new Message("Invalid move. Cannot submit turn.", MessageType.error);
  private static final Message BACKUP_TRUE = new Message("", MessageType.info);
  private static final Message BACKUP_FALSE = new Message("Cannot Backup, there are no moves to undo.", MessageType.error);

  private Player redPlayer;
  private Player whitePlayer;
  private Board board;
  private Color activeColor;

  //Last move made before a move is submitted.
  //lastMove is the move at the end of lastMoves.
  private Move lastMove;

  //List of moves made before a move is submitted.
  //Used for backing up a move.
  private List<Move> lastMoves = new ArrayList<>();

  /**
   * Start a game with a given board state.
   * @param redPlayer The red player
   * @param whitePlayer The white player
   * @param board The state of the board
   */
  public Game(Player redPlayer, Player whitePlayer, Board board){
    this.redPlayer = redPlayer;
    this.whitePlayer = whitePlayer;
    this.board = board;
    activeColor = Color.RED;
  }

  /**
   * Start a new game with two players.
   * @param redPlayer The red player
   * @param whitePlayer The white player
   */
  public Game(Player redPlayer, Player whitePlayer) {
    this.redPlayer = redPlayer;
    this.whitePlayer = whitePlayer;
    activeColor = Color.RED;
    board = new Board();
  }

  public boolean playerInGame(Player player) {
    return player == redPlayer || player == whitePlayer;
  }

  /**
   * Checks if the player is the red player in the game.
   * @param player The player to check
   * @return Is the player the red player
   */
  public boolean isRedPlayer(Player player) {
    return redPlayer == player;
  }

  /**
   * Get the red player.
   * @return The red player
   */
  public Player getRedPlayer() {
    return redPlayer;
  }

  /**
   * Get the white player.
   * @return The white player
   */
  public Player getWhitePlayer() {
    return whitePlayer;
  }

  /**
   * Get the color of the player whose turn it is.
   * @return The color of the current player
   */
  public Color getActiveColor() {
    return activeColor;
  }

  /**
   * Get the state of the board.
   * @return The board
   */
  public Board getBoard() {
    return board;
  }

  /**
   * Get the correctly oriented BoardView for the respective player.
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
   * Checks if the move being made by a player is valid or not.
   * First ensures that it is the correct player's turn and that there is no piece at the destination.
   * Checks if the move is a normal diagonal movement.
   * If not, then checks if the move is a jump move over an opponent's piece.
   * @param move The Move object that the player is making
   * @return Info message if the move is valid, error message if it is invalid.
   */
  public Message validateMove(Move move) {
    // If it is red turn, move is flipped
    if (activeColor.equals(Color.RED)) {
        move = move.flipMove();
    }
    Color movedPieceColor = board.getPieceAtPosition(move.getStart()).getColor();
    // Check valid move conditions
    if( !getActiveColor().equals(movedPieceColor) ) {
      return MOVE_FALSE;
    }
    if(board.getPieceAtPosition(move.getEnd()) != null) {
      return MOVE_FALSE;
    }
    if(isNormalMove(move)) {
      lastMoves.add(move);
      lastMove = move;
      makeMove(move);
      return MOVE_TRUE;
    }
    else if (isJumpMove(move)) {
      lastMoves.add(move);
      lastMove = move;
      makeMove(move);
      return MOVE_TRUE;
    }
    else {
      return MOVE_FALSE;
    }
  }

  /**
   * Checks if the given Move is a valid normal, non-jump move.
   * @param move The Move object that the player is making
   * @return true if the move is a valid normal, non-jump move, false if it is invalid or not a normal move.
   */
  private boolean isNormalMove(Move move) {
    int row1 = move.getStart().getRow();
    int col1 = move.getStart().getCell();

    int row2 = move.getEnd().getRow();
    int col2 = move.getEnd().getCell();

    Piece movingPiece = board.getPieceAtPosition(move.getStart());

    boolean isSingleMove = false;
    // Red pieces move to higher rows, White pieces move to lower rows. Kings can do both.
    if(movingPiece.getColor() == Color.RED || movingPiece.getType() == Type.KING) {
      if((row1+1 == row2) && (col1+1 == col2)) {
        isSingleMove = true;
      }
      else if((row1+1 == row2) && (col1-1 == col2)) {
        isSingleMove = true;
      }
    }
    else if(movingPiece.getColor() == Color.WHITE || movingPiece.getType() == Type.KING) {
      if((row1-1 == row2) && (col1+1 == col2)) {
        isSingleMove = true;
      }
      else if((row1-1 == row2) && (col1-1 == col2)) {
        isSingleMove = true;
      }
    }

    return isSingleMove;
  }

  /**
   * Checks if the given Move is a valid jump move
   * @param move The Move object that the player is making.
   * @return true if the move is a valid jump move, false if it is invalid or not a jump move.
   */
  private boolean isJumpMove(Move move) {
    int row1 = move.getStart().getRow();
    int col1 = move.getStart().getCell();

    int row2 = move.getEnd().getRow();
    int col2 = move.getEnd().getCell();

    Piece movingPiece = board.getPieceAtPosition(move.getStart());
    Piece middlePiece;

    boolean isJumpMove = false;
    // The piece must either be Red or a King to move towards the bottom of the board.
    if(movingPiece.getColor() == Color.RED || movingPiece.getType() == Type.KING) {
      // The move must be two down and two to the right or..
      if((row1+2 == row2) && (col1+2 == col2)) {
        middlePiece = board.getPieceAtPosition(new Position(row1+1,col1+1));
        if (middlePiece == null) {
          return false;
        }
        // There must also be a piece of the opposite color in between the start and end.
        if(middlePiece.getColor() != movingPiece.getColor()) {
          isJumpMove = true;
        }
      }
      // The move must be two down and two to the left
      else if((row1+2 == row2) && (col1-2 == col2)) {
        middlePiece = board.getPieceAtPosition(new Position(row1+1,col1-1));
        if (middlePiece == null) {
          return false;
        }
        // There must also be a piece of the opposite color in between the start and end.
        if(middlePiece.getColor() != movingPiece.getColor()) {
          isJumpMove = true;
        }
      }
    }
    // The piece must either be White or a King to move to the top of the board
    else if(movingPiece.getColor() == Color.WHITE || movingPiece.getType() == Type.KING) {
      // The move must be two up and two to the left
      if((row1-2 == row2) && (col1+2 == col2)) {
        middlePiece = board.getPieceAtPosition(new Position(row1-1,col1+1));
        if (middlePiece == null) {
          return false;
        }
        // There must also be a piece of the opposite color in between the start and end.
        if(middlePiece.getColor() != movingPiece.getColor()) {
          isJumpMove = true;
        }
      }
      // The move must be two up and two to the left
      else if((row1-2 == row2) && (col1-2 == col2)) {
        middlePiece = board.getPieceAtPosition(new Position(row1-1,col1-1));
        if (middlePiece == null) {
          return false;
        }
        // There must also be a piece of the opposite color in between the start and end.
        if(middlePiece.getColor() != movingPiece.getColor()) {
          isJumpMove = true;
        }
      }
    }
    return isJumpMove;
  }

  /**
   * Switches the active Color
   */
  public void switchActiveColor(){
    if (activeColor == Color.RED) {
      activeColor = Color.WHITE;
    } else {
      activeColor = Color.RED;
    }
  }

  /**
   * Checks if player is the active player.
   * @param player Player to check
   * @return Message indicating if the player is the active player
   */
  public Message isActivePlayer(Player player) {
    if ((player == redPlayer && activeColor == Color.RED)
            || (player == whitePlayer && activeColor == Color.WHITE)) {
      return TURN_TRUE;
    }
    return TURN_FALSE;
  }

  /**
   * Updates the board to implement a move
   * @param move starting position and ending position
   */
  public void makeMove(Move move){

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

      if(rowDistance == 1 && colDistance == 1) {
          board.makeNormalMove(move);
      }
      else if (rowDistance == 2 && colDistance == 2) {
          if(move.isBackUpMove()){
            board.makeBackUpJumpMove(move, activeColor);
          }
          else{
            board.makeJumpMove(move);
          }
      }

  }

    /**
     * Submit the last made move. If there is no last made move an error
     * message is returned. Otherwise, the move is made and the other player's
     * turn now starts.
     *
     * @return Error or info message depending on if the move was made
     */
  public Message submitTurn() {
      if (lastMove == null) {
          return SUBMIT_FALSE;
      }
      
      //reset lastMoves and lastMove
      lastMoves.clear();
      lastMove = null;
      switchActiveColor();
      return SUBMIT_TRUE;
  }

  /**
   * "Undo" last move made and update both the list of previous moves,
   * and the lastMove.
   *
   * @return Error or info message depending on if the back up action was made
   */
  public Message backUpMove(){
    if (lastMove == null || lastMoves.isEmpty()){
      return BACKUP_FALSE;
    }

    //Undo last move
    Move backUpMove = lastMove.createBackUpMove();
    makeMove(backUpMove);

    //Remove lastMove from list of previous moves
    lastMoves.remove(lastMove);

    //Update lastMove
    if(!lastMoves.isEmpty()) {
      lastMove = lastMoves.get(lastMoves.size() - 1);
    }
    else{
      lastMove = null;
    }

    return BACKUP_TRUE;
  }
}
