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
   * @return True if the move is valid, false if it is invalid.
   */
  public boolean validateMove(Move move) {
    // If it is red turn, move is flipped
    if (activeColor.equals(Color.RED)) {
        move = move.flipMove();
    }
    Color movedPieceColor = board.getPieceAtPosition(move.getStart()).getColor();
    // Check valid move conditions
    if( !getActiveColor().equals(movedPieceColor) ) {
      return false;
    }
    if(board.getPieceAtPosition(move.getEnd()) != null) {
      return false;
    }
    if(isNormalMove(move)) {
      lastMoves.add(move);
      lastMove = move;
      makeMove(move);
      return true;
    }
    else if (isJumpMove(move)) {
      lastMoves.add(move);
      lastMove = move;
      makeMove(move);
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
      if(checkDistance(row2,row1,1) && checkDistance(col2,col1,1)) {
        isSingleMove = true;
      }
      else if(checkDistance(row2,row1,1) && checkDistance(col2,col1,-1)) {
        isSingleMove = true;
      }
    }
    if(movingPiece.getColor() == Color.WHITE || movingPiece.getType() == Type.KING) {
      if(checkDistance(row2,row1,-1) && checkDistance(col2,col1,1)) {
        isSingleMove = true;
      }
      else if(checkDistance(row2,row1,-1) && checkDistance(col2,col1,-1)) {
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
  public boolean isJumpMove(Move move) {
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
      if(checkDistance(row2,row1,2) && checkDistance(col2,col1,2)) {
        middlePiece = board.getPieceAtPosition(new Position(row1+1,col1+1));
        // There must also be a piece of the opposite color in between the start and end.
        if(middlePiece != null && middlePiece.getColor() != movingPiece.getColor()) {
            isJumpMove = true;
        }
      }
      // The move must be two down and two to the left
      else if(checkDistance(row2,row1,2) && checkDistance(col2,col1,-2)) {
        middlePiece = board.getPieceAtPosition(new Position(row1+1,col1-1));
        // There must also be a piece of the opposite color in between the start and end.
        if(middlePiece != null && middlePiece.getColor() != movingPiece.getColor()) {
          isJumpMove = true;
        }
      }
    }
    // The piece must either be White or a King to move to the top of the board
    if(movingPiece.getColor() == Color.WHITE || movingPiece.getType() == Type.KING) {
      // The move must be two up and two to the left
      if(checkDistance(row2,row1,-2) && checkDistance(col2,col1,2)) {
        middlePiece = board.getPieceAtPosition(new Position(row1-1,col1+1));
        // There must also be a piece of the opposite color in between the start and end.
        if(middlePiece != null && middlePiece.getColor() != movingPiece.getColor()) {
          isJumpMove = true;
        }
      }
      // The move must be two up and two to the left
      else if(checkDistance(row2,row1,-2) && checkDistance(col2,col1,-2)) {
        middlePiece = board.getPieceAtPosition(new Position(row1-1,col1-1));
        // There must also be a piece of the opposite color in between the start and end.
        if(middlePiece != null && middlePiece.getColor() != movingPiece.getColor()) {
          isJumpMove = true;
        }
      }
    }
    return isJumpMove;
  }

  /**
   *
   * @param val1 col or row value to check distance with another value
   * @param val2 col or row value to check distance with another value.
   * @param expected expected difference between val2 and val1.
   * @return true if the distance between p1 and p2 is equal to the expected value
   */
  public boolean checkDistance(int val2, int val1, int expected){
    return (val2-val1) == expected;
  }

  /**
   * Switches the active Color
   */
  public void switchActiveColor(){
    activeColor = activeColor == Color.RED ? Color.WHITE : Color.RED;
  }

  /**
   * Checks if player is the active player.
   * @param player Player to check
   * @return Is the player is the active player
   */
  public boolean isActivePlayer(Player player) {
    return (player == redPlayer && activeColor == Color.RED)
            || (player == whitePlayer && activeColor == Color.WHITE);
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
      checkIfKinged(move);
    }
    else if (rowDistance == 2 && colDistance == 2) {
      if(move.isBackUpMove()){
        board.makeBackUpJumpMove(move, activeColor);
      }
      else{
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
      
    //reset lastMoves and lastMove
    lastMoves.clear();
    lastMove = null;
    switchActiveColor();
    return true;
  }

  /**
   * "Undo" last move made and update both the list of previous moves,
   * and the lastMove.
   *
   * @return True or false depending on if the back up action was made
   */
  public boolean backUpMove(){
    if (lastMove == null || lastMoves.isEmpty()){
      return false;
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
    int endRow = move.getEnd().getCell();
    Piece currentPiece = board.getPieceAtPosition(move.getEnd());
    Color pieceColor = currentPiece.getColor();

    if (endRow == 7 && pieceColor == Color.RED) {
      return currentPiece.kingPiece();
    }
    else if (endRow == 7 && pieceColor == Color.RED) {
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
  public boolean jumpMoveExists(){
    // for(int ir = 0; ir < Board.ROWS; ir++){
    //     for(int ic = 0; ic < Board.COLUMNS; ic++){
    //         current = pieces[ir][ic];
    //         //make sure the space has a piece
    //         if(!(current.equals(null))){
    //             //see if current is the same color as active player
    //             if(current.getColor().equals(getActiveColor())){
    //             }
    //         }
    //     }
    // }
    Color currentColor = getActiveColor();
    List possiblePiecesToMove = new ArrayList<Position>();
    Position indexPosition;
    Piece indexPiece;
    
    // Iterate through all pieces to see which ones are valid to move this turn.
    for(int row = 0; row < Board.ROWS; row++) {
      for(int col = 0; col < Board.COLUMNS; col++) {
        indexPosition = new Position(row, col);
        indexPiece = board.getPieceAtPosition(indexPosition);

        // Add the possible positions of pieces that are the active color to the array.

      }
    }

    return false;
  }

  /**
  * This method will get a piece and check
  * the jump location
  * @param
  * @return
  */
  public List<Position> checkJumpLocation(Position position) {
    int row = position.getRow();
    int col = position.getCell();
    List<Position> possibleJumps = new ArrayList<>();
    Position upperLeft, upperRight, lowerLeft, lowerRight;
    Piece[][] pieces = board.getPieces();
     upperLeft = new Position(row-2, col-2);
    upperRight = new Position(row - 2, col + 2);
    lowerLeft = new Position(row + 2, col -2);
    lowerRight = new Position(row + 2, col +2);
     possibleJumps.add(upperLeft);
    possibleJumps.add(upperRight);
    possibleJumps.add(lowerLeft);
    possibleJumps.add(lowerRight);
     for(Position p: possibleJumps){
        //make sure positions are on the board
        row = p.getRow();
        col = p.getCell();
        if(row < 0 || col < 0 || row >= Board.ROWS || col >= Board.COLUMNS)
            possibleJumps.remove(p);
        //now check if spaces are empty
        Piece test = pieces[row][ col];
        if(!(test.equals(null))){
            possibleJumps.remove(p);
        }
    }
    return possibleJumps;
  }

  /**
   * Resign the game. This ends the game and makes the current player
   * lose and the other player win.
   * @return True if successful
   */
  public boolean resignGame() {
    // game over
    return true;
  }

}

