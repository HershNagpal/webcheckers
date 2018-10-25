package com.webcheckers.model;

/**
 * Combines the board and players in order to play the game
 * @author Luis Gutierrez, Christopher Daukshus
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
    public boolean isActivePlayer(Player player){
        if (player == redPlayer){
            if (activeColor == Color.RED)return true;
        }
        else{
            if (player == whitePlayer){
                if (activeColor == Color.WHITE)return true;
            }
        }
        return false;
    }

  public void makeMove(Move move){
    //If normal move
    board.makeNormalMove(move);
    //If jump move
    board.makeJumpMove(move);
  }
}
