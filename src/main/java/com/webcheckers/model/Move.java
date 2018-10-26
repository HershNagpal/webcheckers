package com.webcheckers.model;

/**
 * Represents the movement of pieces.
 */
public class Move {

  private Position start;
  private Position end;

  public Move(Position start, Position end) {
      this.start = start;
      this.end = end;
  }
  
  public Move flipMove(Move initial){
      Position pos_start = initial.start;
      Position pos_end = initial.end;

      Position pos_start_flipped = new Position(7-pos_start.getRow(), 7-pos_end.getCell());
      Position pos_end_flipped = new Position(7-pos_end.getRow(), 7-pos_end.getCell());

      Move flippedMove = new Move(pos_start_flipped,pos_end_flipped);
      return flippedMove;
  }

  public Position getStart() {
      return start;
  }

  public Position getEnd() {
      return end;
  }
}
