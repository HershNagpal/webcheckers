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
  private Board CuT2;

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
   * Boards for custom move tests
   */
  private Piece[][] customPieces = 	new Piece[][]{
    {null, null, redPiece, null, null, null, null, null},
    {null, whitePiece, null, null, null, null, null, null},
    {null, null, null, null, null, null, null, null},
    {null, null, null, null, null, null, null, null},
    {null, null, null, null, null, null, null, null},
    {null, null, null, null, null, null, null, null},
    {null, null, redPiece, null, null, null, null, null},
    {null, null, null, whitePiece, null, null, null, null}
  };

  private Piece[][] customPiecesRedMove2 = 	new Piece[][]{
    {null, null, null, null, null, null, null, null},
    {null, whitePiece, redPiece, null, null, null, null, null},
    {null, null, null, null, null, null, null, null},
    {null, null, null, null, null, null, null, null},
    {null, null, null, null, null, null, null, null},
    {null, null, null, null, null, null, null, null},
    {null, null, redPiece, null, null, null, null, null},
    {null, null, null, whitePiece, null, null, null, null}
  };

  private Piece[][] customPiecesRedMove1 = new Piece[][]{
    {null, null, null, null, null, null, null, null},
    {null, null, null, null, null, null, null, null},
    {redPiece, null, null, null, null, null, null, null},
    {null, null, null, null, null, null, null, null},
    {null, null, null, null, null, null, null, null},
    {null, null, null, null, null, null, null, null},
    {null, null, redPiece, null, null, null, null, null},
    {null, null, null, whitePiece, null, null, null, null}
  };

  private Piece[][] customPiecesWhiteMove1 = new Piece[][]{
    {null, null, redPiece, null, null, null, null, null},
    {null, whitePiece, null, null, null, null, null, null},
    {null, null, null, null, null, null, null, null},
    {null, null, null, null, null, null, null, null},
    {null, null, null, null, null, null, null, null},
    {null, whitePiece, null, null, null, null, null, null},
    {null, null, null, null, null, null, null, null},
    {null, null, null, null, null, null, null, null}
  };

  private Piece[][] customPiecesWhiteMove2 = new Piece[][]{
    {null, null, redPiece, null, null, null, null, null},
    {null, whitePiece, null, null, null, null, null, null},
    {null, null, null, null, null, null, null, null},
    {null, null, null, null, null, null, null, null},
    {null, null, null, null, null, null, null, null},
    {null, null, null, null, null, null, null, null},
    {null, null, redPiece, null, null, null, null, null},
    {null, null, null, whitePiece, null, null, null, null}
  };

  // Positions on the mock board that can be called.
  private Position redPosition1 = new Position(0, 2);
  private Position redPosition2 = new Position(6, 2);
  private Position whitePosition1 = new Position(1, 1);
  private Position whitePosition2 = new Position(7, 3);
  // The 2 positions the Red piece in position 1 will try to jump to
  private Position emptyPosition1 = new Position(2, 0);
  private Position emptyPosition2 = new Position(1, 3);
  // The 2 positions the White piece in position 1 will try to jump to
  private Position emptyPosition4 = new Position(5, 1);
  private Position emptyPosition5 = new Position(6, 4);
  // Create moves to be tested in validateMove
  Move validRedMove1 = new Move(redPosition1, emptyPosition1);
  Move validRedMove2 = new Move(redPosition1, emptyPosition2);
  Move validWhiteMove1 = new Move(whitePosition2, emptyPosition4);
  Move validWhiteMove2 = new Move(whitePosition2, emptyPosition5);




  /**
   * Setting up objects before each test
   */
  @BeforeEach
  public void setup(){
    redPiece = new Piece(Color.RED, Piece.Type.SINGLE);
    whitePiece = new Piece(Color.WHITE,Piece.Type.SINGLE);
    expectedBoardView = new BoardView(expectedPieces);

    CuT = new Board(expectedPieces);
    CuT2 = new Board(customPieces);

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

  /**
   * Test if a normal move changes the board in the proper way.
   */
  @Test
  public void testMakeNormalMove(){

    CuT2.makeNormalMove(validRedMove2);
    assertEquals(CuT2.getPieces(),customPiecesRedMove2);

    CuT2 = new Board(customPieces);

    CuT2.makeNormalMove(validWhiteMove2);
    assertEquals(CuT2.getPieces(),customPiecesWhiteMove2);

  }

  /**
   * Test if a jump move changes the board in the proper way
   */
  @Test
  public void testJumpMove(){

    CuT2.makeJumpMove(validRedMove1);
    assertEquals(CuT2.getPieces(),customPiecesRedMove1);

    CuT2 = new Board(customPieces);

    CuT2.makeNormalMove(validWhiteMove1);
    assertEquals(CuT2.getPieces(),customPiecesWhiteMove1);

  }
}