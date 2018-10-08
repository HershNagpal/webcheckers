package com.webcheckers.model;

/**
 *
 * @author Luis Gutierrez
 */
public class Game {
  private Player redPlayer;
  private Player whitePlayer;
  private Board board;
  private Color activeColor;


  public Game(Player redPlayer, Player whitePlayer, Board board){
    redPlayer.setGame(this);
    whitePlayer.setGame(this);
    this.redPlayer = redPlayer;
    this.whitePlayer = whitePlayer;
    this.board = board;
  }

  public Game(Player redPlayer, Player whitePlayer) {
    redPlayer.setGame(this);
    whitePlayer.setGame(this);
    this.redPlayer = redPlayer;
    this.whitePlayer = whitePlayer;
  }

  public Player getRedPlayer() {
    return redPlayer;
  }

  public Player getWhitePlayer() {
    return whitePlayer;
  }

  public Color getActiveColor() {
    return activeColor;
  }

  public Board getBoard() {
    return board;
  }


}
