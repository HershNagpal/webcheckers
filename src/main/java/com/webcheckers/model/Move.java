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

  /**
   * Flips a given move. Used in PostValidateTurnRoute and PostSubmitTurnRoute.
   * @return Flipped move.
   */
  public Move flipMove(){
      Position posStart = this.start;
      Position posEnd = this.end;

      Position posStartFlipped = new Position(7-posStart.getRow(), 7-posEnd.getCell());
      Position posEndFlipped = new Position(7-posEnd.getRow(), 7-posEnd.getCell());

      Move flippedMove = new Move(posStartFlipped,posEndFlipped);
      return flippedMove;
  }

  public Position getStart() {
      return start;
  }

  public Position getEnd() {
      return end;
  }

  /**
   * Overriding equals() for deep equality between Move Objects.
   * This method is used for testing.
   * @param obj Object being compared to "this" Move
   * @return true if "this" is equal to obj
   */
  @Override
  public boolean equals(Object obj){
    if(this == obj){
      return true;
    }
    else if(!(obj instanceof Move)){
      return false;
    }

    Move m2 = (Move)obj;

    return this.start.equals(m2.start) && this.end.equals(m2.end);
  }
}
