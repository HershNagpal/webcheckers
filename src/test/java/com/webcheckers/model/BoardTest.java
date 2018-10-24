package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test suite for Board class.
 *
 * @author Luis Gutierrez
 */
@Tag("Model-tier")
public class BoardTest {

  /**
   * Component to test
   */
  private Board CuT;

  /**
   * Friendly Objects
   */
  private Piece redPiece;
  private Piece whitePiece;
  private BoardView expectedBoardView;

  /**
   * Expected piece 2d array
   */
  private Piece[][] expectedPieces = new Piece[][]{
          {null, redPiece, null, redPiece, null, redPiece, null, redPiece},
          {redPiece, null, redPiece, null, redPiece, null, redPiece, null},
          {null, redPiece, null, redPiece, null, redPiece, null, redPiece},
          {null, null, null, null, null, null, null, null},
          {null, null, null, null, null, null, null, null},
          {whitePiece, null, whitePiece, null, whitePiece, null, whitePiece, null},
          {null, whitePiece, null, whitePiece, null, whitePiece, null, whitePiece},
          {whitePiece, null, whitePiece, null, whitePiece, null, whitePiece, null},
  };

  /**
   * Setting up objects before each test
   */
  @BeforeEach
  public void setup(){
    CuT = new Board();
    redPiece = new Piece(Color.RED, Piece.Type.SINGLE);
    whitePiece = new Piece(Color.WHITE,Piece.Type.SINGLE);
    expectedBoardView = new BoardView(expectedPieces);
  }

  /**
   * Test for setUpBoard()
   */
  @Test
  public void testBoardSetUp(){
    Piece[][] actualPieces = CuT.getPieces();

    Piece[][] expectedPieces = new Piece[][]{
            {null, redPiece, null, redPiece, null, redPiece, null, redPiece},
            {redPiece, null, redPiece, null, redPiece, null, redPiece, null},
            {null, redPiece, null, redPiece, null, redPiece, null, redPiece},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {whitePiece, null, whitePiece, null, whitePiece, null, whitePiece, null},
            {null, whitePiece, null, whitePiece, null, whitePiece, null, whitePiece},
            {whitePiece, null, whitePiece, null, whitePiece, null, whitePiece, null},
    };

    for(int row = 0; row < expectedPieces.length; row++){
      assertTrue(Arrays.deepEquals(expectedPieces[row],actualPieces[row]));
    }

  }

  /**
   * Test for getFlippedBoardView()
   */
  @Test
  public void testFlippedBoard(){
    Piece[][] actualPiecesFlipped = CuT.getFlippedPieces();

    Piece[][] expectedPiecesFlipped = new Piece[][]{
            {null, whitePiece, null, whitePiece, null, whitePiece, null, whitePiece},
            {whitePiece, null, whitePiece, null, whitePiece, null, whitePiece, null},
            {null, whitePiece, null, whitePiece, null, whitePiece, null, whitePiece},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {redPiece, null, redPiece, null, redPiece, null, redPiece, null},
            {null, redPiece, null, redPiece, null, redPiece, null, redPiece},
            {redPiece, null, redPiece, null, redPiece, null, redPiece, null},
    };

    for(int row = 0; row < expectedPiecesFlipped.length; row++){
      assertTrue(Arrays.deepEquals(expectedPiecesFlipped[row],actualPiecesFlipped[row]));
    }
  }

  /**
   * Test for equals method.
   */
  @Test
  public void testEquals(){
    //Boards are equal
    Board b1 = new Board();
    Board b2 = new Board();
    assertTrue(b1.equals(b2));

    //Boards are the same object
    assertTrue(b1.equals(b1));

    //Board compared to other object
    assertFalse(b1.equals(redPiece));

    //Boards are not equal
    //TODO add constructor for diff. board setup

  }

  /**
   * Test for Board's board View
   */
  @Test
  public void testBoardView(){
    assertEquals(expectedBoardView,CuT.getBoardView());

  }

}