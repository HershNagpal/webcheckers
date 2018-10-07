package com.webcheckers.model;

public class CheckersGame {
  private Player redPlayer;
  private Player whitePlayer;
  private Board checkerBoard;

  public CheckersGame(Player redPlayer, Player whitePlayer, Board board){
    this.redPlayer = redPlayer;
    this.whitePlayer = whitePlayer;
    this.checkerBoard = board;
  }

}
