package com.webcheckers.model;

/**
 *
 * @author Luis Gutierrez
 */
public class Game {
  private Player redPlayer;
  private Player whitePlayer;
  private Board checkerBoard;

  public Game(Player redPlayer, Player whitePlayer, Board board){
    this.redPlayer = redPlayer;
    this.whitePlayer = whitePlayer;
    this.checkerBoard = board;
  }

}
