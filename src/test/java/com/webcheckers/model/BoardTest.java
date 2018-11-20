package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test suite for Board class.
 *
 * @author Luis Gutierrez
 * @author Matthew Bollinger
 */
@Tag("Model-tier")
public class BoardTest {

  /**
   * Component to test
   */
  private Board CuT;
  private Board CuT2;
  private Board CuTNoRed;
  private Board CuTNoWhite;

  /**
   * Friendly Objects
   */
  private Piece redPiece;
  private Piece whitePiece;
  private BoardView expectedBoardView;

  // Boards to test flipping the board
  Piece[][] customFlippedPieces;
  Piece[][] customPiecesFlip01;

  // Boards that result from making a move
  private Piece[][] expectedPieces;
  private Piece[][] customPieces;
  private Piece[][] customPiecesRedMove1;
  private Piece[][] customPiecesRedMove2;
  private Piece[][] customPiecesWhiteMove1;
  private Piece[][] customPiecesWhiteMove2;
  private Piece[][] cpNoRed;
  private Piece[][] cpNoWhite;

  // Positions on the mock board that can be called.
  private Position redPosition1 = new Position(0, 2);
  private Position redPosition2 = new Position(6, 2);
  private Position whitePosition1 = new Position(1, 1);
  private Position whitePosition2 = new Position(7, 3);
  // The 3 positions the Red piece in position 1 will try to jump to
  private Position emptyPosition1 = new Position(2, 0);
  private Position emptyPosition2 = new Position(1, 3);
  private Position emptyPosition3 = new Position(2, 4);
  // The 3 positions the White piece in position 1 will try to jump to
  private Position emptyPosition4 = new Position(5, 1);
  private Position emptyPosition5 = new Position(6, 4);
  private Position emptyPosition6 = new Position(5, 5);
  // Create moves to be tested in validateMove
  Move validRedMove1 = new Move(redPosition1, emptyPosition1);
  Move validRedMove2 = new Move(redPosition1, emptyPosition2);
  Move invalidRedMove1 = new Move(redPosition1, whitePosition1);
  Move invalidRedMove2 = new Move(redPosition1, emptyPosition3);
  Move validWhiteMove1 = new Move(whitePosition2, emptyPosition4);
  Move validWhiteMove2 = new Move(whitePosition2, emptyPosition5);
  Move invalidWhiteMove1 = new Move(whitePosition2, redPosition2);
  Move invalidWhiteMove2 = new Move(whitePosition2, emptyPosition6);




  /**
   * Setting up objects before each test
   */
  @BeforeEach
  public void setup(){
    redPiece = new Piece(Color.RED, Piece.Type.SINGLE);
    whitePiece = new Piece(Color.WHITE,Piece.Type.SINGLE);

    customFlippedPieces = new Piece[][]{
            {redPiece, null, whitePiece},
            {redPiece, null, null},
            {null, null, whitePiece}
    };

    customPiecesFlip01 = new Piece[][]{
            {whitePiece, null, null},
            {null, null, redPiece},
            {whitePiece, null, redPiece}
    };

    expectedPieces = new Piece[][]{
      {null, redPiece, null, redPiece, null, redPiece, null, redPiece},
      {redPiece, null, redPiece, null, redPiece, null, redPiece, null},
      {null, redPiece, null, redPiece, null, redPiece, null, redPiece},
      {null, null, null, null, null, null, null, null},
      {null, null, null, null, null, null, null, null},
      {whitePiece, null, whitePiece, null, whitePiece, null, whitePiece, null},
      {null, whitePiece, null, whitePiece, null, whitePiece, null, whitePiece},
      {whitePiece, null, whitePiece, null, whitePiece, null, whitePiece, null},
      };

    expectedBoardView = new BoardView(expectedPieces);

    customPieces = 	new Piece[][]{
            {null, null, redPiece, null, null, null, null, null},
            {null, whitePiece, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, redPiece, null, null, null, null, null},
            {null, null, null, whitePiece, null, null, null, null}
    };

    customPiecesRedMove2 = 	new Piece[][]{
            {null, null, null, null, null, null, null, null},
            {null, whitePiece, redPiece, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, redPiece, null, null, null, null, null},
            {null, null, null, whitePiece, null, null, null, null}
    };

    customPiecesRedMove1 = new Piece[][]{
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {redPiece, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, redPiece, null, null, null, null, null},
            {null, null, null, whitePiece, null, null, null, null}
    };

    customPiecesWhiteMove1 = new Piece[][]{
            {null, null, redPiece, null, null, null, null, null},
            {null, whitePiece, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, whitePiece, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null}
    };

    customPiecesWhiteMove2 = new Piece[][]{
            {null, null, redPiece, null, null, null, null, null},
            {null, whitePiece, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, redPiece, null, null, null, null, null},
            {null, null, null, whitePiece, null, null, null, null}
    };

    cpNoRed = new Piece[][]{
            {null, null, null, null, null, null, null, null},
            {null, whitePiece, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, whitePiece, null, null, null, null, null},
            {null, null, null, null, null, null, null, null}
    };

    cpNoWhite = new Piece[][]{
            {null, null, null, null, null, null, null, null},
            {null, null, redPiece, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, redPiece, null, null},
            {null, null, null, null, null, null, null, null},
            {null, redPiece, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null}
    };



    CuT = new Board();
    CuT2 = new Board(customPieces);
    CuTNoRed = new Board(cpNoRed);
    CuTNoWhite = new Board(cpNoWhite);

  }

  /**
   * Test for setUpBoard()
   */
  @Test
  public void testBoardSetUp(){

    Piece[][] actualPieces = CuT.getPieces();

    for (int row = 0; row < 8; row++) {
      for (int col = 0; col < 8; col++) {
          if (actualPieces[row][col] != null) {
              assertEquals(actualPieces[row][col].getColor(), (expectedPieces[row][col].getColor()));
          }
      }
    }
  }

  /**
   * Test for makeBackUpJumpMove
   */
  @Test
  public void testMakeBackUpJumpMove(){

    CuT.makeJumpMove(validRedMove1);
    assertNull(CuT.getPieceAtPosition(whitePosition1));
    assertNotEquals(redPiece, CuT.getPieceAtPosition(emptyPosition1));
    CuT.makeBackUpJumpMove(validRedMove1, Color.RED);
    //assertEquals(whitePiece, CuT.getPieceAtPosition(whitePosition1));

  }

  /**
   * Test for getPieceAtFlippedPosition
   */
  @Test
  public void testGetPieceAtFlippedPosition(){
    /*
    From CuT(expectedPieces)
    Original position at (7,4) contains whitePiece
    Flipped position at (0,3) contains redPiece
    */

    Position originalPosition = new Position(7,4);
    //expected = redPiece, actual = CuT.getPieceAtFlippedPosition(7,4)
    assertEquals(redPiece,CuT.getPieceAtFlippedPosition(originalPosition));

  }

  /**
   * Test for getFlippedBoardView()
   */
  @Test
  public void testFlippedBoard(){


    Piece[][] actualPiecesFlipped = CuT.getFlippedPieces();
    BoardView actualFlippedBoardView = CuT.getFlippedBoardView();


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
    BoardView expectedFlippedBoardView = new BoardView(CuT.getFlippedPieces());

    for(int row = 0; row < expectedPiecesFlipped.length; row++){
      assertTrue(Arrays.deepEquals(expectedPiecesFlipped[row],actualPiecesFlipped[row]));
    }
    assertTrue(actualFlippedBoardView.equals(expectedFlippedBoardView));

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
    /*
    CuT.makeNormalMove(validRedMove2);
    assertEquals(CuT.getPieces(),customPiecesRedMove2);

    CuT = new Board(customPieces);

    CuT.makeNormalMove(validWhiteMove2);
    assertEquals(CuT.getPieces(),customPiecesWhiteMove2);
    */
  }

  /**
   * Test if a jump move changes the board in the proper way
   */
  @Test
  public void testJumpMove(){

    CuT2.makeJumpMove(validRedMove1);
  //  assertEquals(CuT2.getPieces(), customPiecesRedMove1);
    Piece[][] pieces = CuT2.getPieces();
    for (int row = 0; row < 8; row++) {
        for (int col = 0; col < 8; col++) {
            if (pieces[row][col] != null) {
                assertEquals(pieces[row][col].getColor(), customPiecesRedMove1[row][col].getColor());
                assertEquals(pieces[row][col].getType(), customPiecesRedMove1[row][col].getType());
            }
        }
    }
  }

  /**
   * Test if the check for all pieces of a color being eliminated is correct
   */
  @Test
  public void testCheckAllPiecesEliminated(){
      assertEquals(CuT.checkAllPiecesEliminated(),null);
      assertEquals(CuT2.checkAllPiecesEliminated(), null);
      assertEquals(CuTNoRed.checkAllPiecesEliminated(), Color.RED);
      assertEquals(CuTNoWhite.checkAllPiecesEliminated(), Color.WHITE);
  }

  /**
   * Test that getValidNormalMovePositions returns
   * a list of valid normal move positions
   */
  @Test
  public void testGetValidNormalMovePositions(){
      List<Position> testPositions;
      Position centerPiece = new Position(0,2);
      testPositions = CuT2.getValidNormalMovePositions(centerPiece);
      assert(testPositions.size() == 1);

      centerPiece = new Position(1, 1);
      testPositions = CuT2.getValidNormalMovePositions(centerPiece);
      assert(testPositions.size() == 1);

      //test kinged white piece
      Piece piece = CuT2.getPieceAtPosition(centerPiece);
      piece.kingPiece();
      testPositions = CuT2.getValidNormalMovePositions(centerPiece);
      assert(testPositions.size() == 3);

      //test kinged red piece
      centerPiece = new Position(6, 2);
      piece = CuT2.getPieceAtPosition(centerPiece);
      piece.kingPiece();
      testPositions = CuT2.getValidNormalMovePositions(centerPiece);
      assert(testPositions.size() == 3);


  }
}