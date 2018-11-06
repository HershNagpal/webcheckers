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
 */
public class Game {

  /**
   * Used to prevent player from making successive simple moves
   * and for enforcing the completion of jump moves.
   */
  private boolean canContinueMoving = true;

  /**
   * The game completion state
   */
  private boolean gameOver;

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
   * @param redPlayer The red player
   * @param whitePlayer The white player
   * @param board The state of the board
   */
  public Game(Player redPlayer, Player whitePlayer, Board board){
    this.redPlayer = redPlayer;
    this.whitePlayer = whitePlayer;
    this.board = board;
    gameOver = false;
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
    gameOver = false;
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
   * Checks if the player is the white player in the game.
   * @param player The player to check
   * @return Is the player the white player
   */
  public boolean isWhitePlayer(Player player) {
    return whitePlayer == player;
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
   * @TODO debug jumpMoveExists and integrate it here.
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
    // Check space piece is moving into is empty
    if(board.getPieceAtPosition(move.getEnd()) != null) {
      return false;
    }

    if(isNormalMove(move) && canContinueMoving) {
      // Forcing jump move
      if(jumpMoveExists()){
        System.out.println("JUMP MOVE EXISTS");
        return false;
      }
      lastMoves.add(move);
      lastMove = move;
      canContinueMoving = false;
      makeMove(move);
      return true;
    }
    else if (isJumpMove(move) && canContinueMoving) {
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
  public boolean isLastMoveJump(Move move) {
    int row1 = move.getStart().getRow();
    int col1 = move.getStart().getCell();

    int row2 = move.getEnd().getRow();
    int col2 = move.getEnd().getCell();

    //Piece is now at the end position of the move
    Piece movingPiece = board.getPieceAtPosition(move.getEnd());

    boolean isJumpMove = false;

    // The piece must either be Red or a King to move towards the bottom of the board.
    if(movingPiece.getColor() == Color.RED || movingPiece.getType() == Type.KING) {
      // The move must be two down and two to the right or..
      if(checkDistance(row2,row1,2) && checkDistance(col2,col1,2)) {
        isJumpMove = true;
      }
      // The move must be two down and two to the left
      else if(checkDistance(row2,row1,2) && checkDistance(col2,col1,-2)) {
        isJumpMove = true;
      }
    }
    // The piece must either be White or a King to move to the top of the board
    if(movingPiece.getColor() == Color.WHITE || movingPiece.getType() == Type.KING) {
      // The move must be two up and two to the left
      if(checkDistance(row2,row1,-2) && checkDistance(col2,col1,2)) {
          isJumpMove = true;
      }
      // The move must be two up and two to the left
      else if(checkDistance(row2,row1,-2) && checkDistance(col2,col1,-2)) {
        isJumpMove = true;
      }
    }
    return isJumpMove;
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

    //Enforce player ending a multiple jump move
    Position lastMoveEndPos = lastMove.getEnd();
    //Multiple jump move has not been completed
    if (isLastMoveJump(lastMove) && getJumpLocations(lastMoveEndPos).size()>0){
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
    }
    else if (endRow == 7 && pieceColor == Color.WHITE) {
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
    List<Position> movablePieceLocations = getMovablePieceLocations();
    for (Position indexPosition : movablePieceLocations) {
      // Check if piece at indexPosition has a position to jump to
      //System.out.println("JUMP LOCATIONS: "+getJumpLocations(indexPosition));
      if(getJumpLocations(indexPosition).size()>0) {
        //System.out.println("************************");
        return true;
      }
    }
    //System.out.println("************************");
    return false;
  }

  /**
   * This method will get the location of a piece and check the jump locations of that piece.
   * @TODO make this so that it only checks kinged jump moves if the piece is a king.
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
    lowerLeft = new Position(row + 2, col -2);
    lowerRight = new Position(row + 2, col +2);

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

    for(int i = 0; i<possibleJumpPos.size(); i++){
      Position pos = possibleJumpPos.get(i);

      //make sure positions are on the board
      if(pos.getRow() < 0 || pos.getCell() < 0 || pos.getRow() >= Board.ROWS || pos.getCell() >= Board.COLUMNS){
        //continue
      }

      // Check if position jumping into is not empty
      else if(board.getPieceAtPosition(pos)==null){
        // Check if there is a piece being jumped
        Position positionJumped = new Position(pos.getRow(),pos.getCell());

        // Checking if its equal to null because you cannot call .equals on null
        if(board.getPieceAtPosition(jumpedPositions.get(i))==null) {
          //continue
        }
        else{
          Piece jumpedPiece = board.getPieceAtPosition(jumpedPositions.get(i));
          //Valid jump if the piece jumped is the opposite color of the active color.
          if(!jumpedPiece.getColor().equals(activeColor)){
            System.out.println(jumpedPiece);
            //Invalid jump if the piece is type SINGLE and going in the wrong direction
            if(!movingPiece.getType().equals(Type.KING)){
              //SINGLE red piece cant jump up
              if(activeColor.equals(Color.RED)){
                if(i==0 || i==1){
                  continue;
                }
                else{
                  validJumpPositions.add(pos);
                }
              }
              //SINGLE white piece cant jump down
              else{
                if(i==2 || i==3){
                  continue;
                }
                else{
                  validJumpPositions.add(pos);
                }
              }
            }
            else{
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
    for(int row = 0; row < Board.ROWS; row++) {
      for(int col = 0; col < Board.COLUMNS; col++) {
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
   * Get the status of the game
   * @return Is the game over or still going
   */
  public boolean isGameOver() {
    return gameOver;
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
    gameOver = true;
    return true;
  }

  public boolean getResigned(){
    return resigned;
  }

  public boolean getGameOver(){
    return gameOver;
  }

  public Player getWinner(){
    return winner;
  }

  /**
   * Has this game been resigned?
   * @return If the game has been resigned by a player.
   */
  public boolean didPlayerResign() {
    return resigned;
  }

}
