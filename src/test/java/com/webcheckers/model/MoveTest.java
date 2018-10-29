package com.webcheckers.model;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test suite for Move class.
 *
 * @author Luis Gutierrez
 * @author Matthew Bollinger
 */
@Tag("Model-tier")
public class MoveTest {

  /**
   * Component to test
   */
  private Move CuT;


  private Position position1;
  private Position position2;

  /**
   * Setting up objects before each test
   */
  @BeforeEach
  public void setup(){
      position1 = new Position(0,1);
      position2 = new Position(1,2);
      CuT = new Move(position1, position2);

  }

    /**
     *Test that when flipMove is given
     * a move it will return a flipped
     * version of the move
     */
  @Test
  public void flipMoveTest(){
      Position initialPos = new Position(5,2);
      Position endPos = new Position(4,3);

      Move move = new Move(initialPos,endPos);

      Position flippedInitialPos = new Position(2,5);
      Position flippedEndPos = new Position(3,4);

      Move expectedFlippedMove = new Move(flippedInitialPos,flippedEndPos);

      assertEquals(move, expectedFlippedMove.flipMove());
      assertEquals(expectedFlippedMove, move.flipMove());
      System.out.println(expectedFlippedMove);
      System.out.println(move.flipMove());
      assertTrue(expectedFlippedMove.equals(move.flipMove()));
  }

    /**
     * Ensures that createBackupMove will
     * siwtch the start and end position and set
     * isBackUpMove to true
     */
  @Test
  public void createBackupMoveTest(){
      Move test;
      test = CuT.createBackUpMove();

      assertTrue(test.getStart().equals(CuT.getEnd()));

      assertTrue(test.isBackUpMove());
  }

    /**
     * Test that Move's getters
     * return the correct values
     */
    @Test
    public void gettersTest(){
      assertEquals(CuT.getStart(), position1);
      assertEquals(CuT.getEnd(), position2);
    }

}
