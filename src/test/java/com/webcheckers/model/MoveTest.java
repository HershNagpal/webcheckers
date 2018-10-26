package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import net.bytebuddy.dynamic.scaffold.TypeWriter.MethodPool.Record.AccessBridgeWrapper;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test suite for Move class.
 *
 * @author Luis Gutierrez
 */
@Tag("Model-tier")
public class MoveTest {

  /**
   * Component to test
   */
  private Move CuT;

  private Move expectedMove;

  /**
   * Setting up objects before each test
   */
  @BeforeEach
  public void setup(){
    //CuT = new Move();
  }

  @Test
  public void flipMoveTest(){
    Position initialPos = new Position(5,2);
    Position endPos = new Position(4,3);

    Move move = new Move(initialPos,endPos);

    Position flippedInitialPos = new Position(2,5);
    Position flippedEndPos = new Position(3,4);

    Move expectedFlippedMove = new Move(flippedInitialPos,flippedEndPos);

<<<<<<< HEAD
    assertEquals(move, expectedFlippedMove.flipMove());
    assertEquals(expectedFlippedMove, move.flipMove());
=======
    System.out.println(expectedFlippedMove);
    System.out.println(move.flipMove());
    assertTrue(expectedFlippedMove.equals(move.flipMove()));
>>>>>>> 32322f138152cd8dde981069a5694c887105987e
  }

}
