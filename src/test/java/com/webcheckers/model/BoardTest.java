package com.webcheckers.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Model-tier")
public class BoardTest {

  @Test
  public void testBoardSetUp(){
    Board board = new Board();
    Piece[][] actualPieces = board.getPieces();

    Piece redPiece = new Piece(Color.RED, Piece.Type.SINGLE);
    Piece whitePiece = new Piece(Color.WHITE,Piece.Type.SINGLE);
    Piece[][] expectedPieces = new Piece[][]{
            {null, redPiece, null, redPiece, null, redPiece, null, redPiece},
            {redPiece, null, redPiece, null, redPiece, null, redPiece, null},
            {null, redPiece, null, redPiece, null, redPiece, null, redPiece},
            {null,null,null,null,null,null,null,null},
            {null,null,null,null,null,null,null,null},
            {whitePiece, null, whitePiece, null, whitePiece, null, whitePiece, null},
            {null, whitePiece, null, whitePiece, null, whitePiece, null, whitePiece},
            {whitePiece, null, whitePiece, null, whitePiece, null, whitePiece, null},
    };
    System.out.println(expectedPieces.toString());
    System.out.println(actualPieces.toString());

    for(int row = 0; row < expectedPieces.length; row++){
      assertTrue(Arrays.deepEquals(expectedPieces[row],actualPieces[row]));
    }
    //assertTrue(Arrays.deepEquals(expectedPieces, actualPieces));
  }

  @Test
  public void testBoardView(){

  }

  @Test
  public void testFlippedBoard(){

  }

}