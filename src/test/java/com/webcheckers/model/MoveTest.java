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
  private Move CuT2;
  private Move CuTUL;
  private Move CuTUR;
  private Move CuTLL;
  private Move CuTLR;
  

  /**
   * Friendly Objects
   */
  private Position position1;
  private Position position2;

  private Position position3;
  private Position position4;

  private Position position5;

  private Position position6;
  private Position position7;
  private Position position8;
  private Position position9;

  /**
   * Setting up objects before each test
   */
  @BeforeEach
  public void setup(){
      position1 = new Position(0,1);
      position2 = new Position(1,2);
      CuT = new Move(position1, position2);

      position3 = new Position(3,2);
      position4 = new Position(2,1);
      CuT2 = new Move(position3, position4);

      // These will be jump moves
      position5 = new Position(3,3);

      position6 = new Position(1,1);
      position7 = new Position(1,5);
      position8 = new Position(5,1);
      position9 = new Position(5,5);

      // UL, UR, LL, LR
      CuTUL = new Move(position5, position6);
      CuTUR = new Move(position5, position7);
      CuTLL = new Move(position5, position8);
      CuTLR = new Move(position5, position9);
  }

    /**
     * Test for the isFacingRed method.
     */
    @Test
    public void testIsFacingRed() {
        //Testing move done going up from white players point of view
        assertFalse(CuT.isFacingRed());
        //Testing move done going down from white players point of view
        assertTrue(CuT2.isFacingRed());
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

    /**
     * Test the equals method
     */
    @Test
    public void testEquals() {
        Move copy = CuT;
        Move a = new Move(position2, position1);
        String fake = "fake";
        assertEquals(copy, CuT);
        assertNotEquals(CuT, fake);
        assertNotEquals(a, CuT);
    }

    /**
     * Test for getJumpedPosition
     */
    @Test
    public void testGetJumpedPosition() {
        Position expectedUL = new Position(2,2);
        Position expectedUR = new Position(2,4);
        Position expectedLL = new Position(4,2);
        Position expectedLR = new Position(4,4);

        assertEquals(expectedUL, CuTUL.getJumpedPosition());
        assertEquals(expectedLL, CuTLL.getJumpedPosition());
        assertEquals(expectedLR, CuTLR.getJumpedPosition());
        assertEquals(expectedUR, CuTUR.getJumpedPosition());
    }
}
