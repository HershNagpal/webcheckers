package com.webcheckers.model;

import static java.lang.Math.abs;

import com.webcheckers.model.Piece.Type;

/**
 * Combines the board and players in order to play the game
 * @author Luis Gutierrez
 * @author Christopher Daukshus
 * @author Hersh Nagpal
 */
public class Game {
  private Player redPlayer;
  private Player whitePlayer;
  private Board board;
  private Color activeColor;

  /**
   * Start a game with a given board state.
   * @param redPlayer The red player
   * @param whitePlayer The white player
   * @param board The state of the board
   */
  public Game(Player redPlayer, Player whitePlayer, Board board){
    redPlayer.setGame(this);
    whitePlayer.setGame(this);
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
    redPlayer.setGame(this);
    whitePlayer.setGame(this);
    this.redPlayer = redPlayer;
    this.whitePlayer = whitePlayer;
    activeColor = Color.RED;
    board = new Board();
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
   * Checks if the move being made by a player is valid or not.
   * First ensures that it is the correct player's turn and that there is no piece at the destination.
   * Checks if the move is a normal diagonal movement. If so, returns true.
   * If not, then checks if the move is a jump move over an opponent's piece. If so, returns true.
   * Returns false if neither.
   * @param move The Move object that the player is making
   * @return true if the move is valid, false if it is invalid.
   */
  public boolean validateMove(Move move) {
    // Color movedPieceColor;
    // if (activeColor.equals(Color.RED)) {
    //   movedPieceColor = board.getPieceAtFlippedPosition(move.getStart()).getColor();
    // } else {
    //   movedPieceColor = board.getPieceAtPosition(move.getStart()).getColor();
    // }

    Color movedPieceColor = board.getPieceAtPosition(move.getStart()).getColor();

    if( !getActiveColor().equals(movedPieceColor) ) {
      return false;
    }

    if(board.getPieceAtPosition(move.getEnd()) != null) {
      return false;
    }

    if(isNormalMove(move)) {
      return true;
    }
    else if (isJumpMove(move)) {
      return true;
    }
    else {
      return false;
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
   * @TODO make sure that there is a piece in between the start and end.
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
  * Checks if player is the active player
  */
  public boolean isActivePlayer(Player player) {
      if (player == redPlayer) {
        if (activeColor == Color.RED) {
          return true;
        }
      }
      else{
        if (player == whitePlayer) {
          if (activeColor == Color.WHITE) {
            return true;
          }
        }
    }
    return false;
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
      int rowEnd = moveStart.getRow();
      int colEnd = moveStart.getCell();

      int rowDistance = rowEnd - rowStart;
      rowDistance = abs(rowDistance);
      int colDistance = colEnd - colStart;
      colDistance = abs(colDistance);

      if(rowDistance == 1 && colDistance == 1){ board.makeNormalMove(move); }
      else if (rowDistance == 2 && colDistance == 2) { board.makeJumpMove(move); }

  }
}
