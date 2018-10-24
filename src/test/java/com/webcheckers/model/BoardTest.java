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
  Piece redPiece;
  Piece whitePiece;

  /**
   * Setting up objects before each test
   */
  @BeforeEach
  public void setup(){
    CuT = new Board();
    redPiece = new Piece(Color.RED, Piece.Type.SINGLE);
    whitePiece = new Piece(Color.WHITE,Piece.Type.SINGLE);
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
   * Test
   */
  @Test
  public void testBoardView(){

  }

}