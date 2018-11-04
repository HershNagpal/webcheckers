package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.model.Piece.Type;

/**
 * Unit test suite for the Game class.
 *
 * @author Hersh Nagpal
 * @author Matthew Bollinger
 */
@Tag("Model-tier")
public class GameTest {

	/**
	 * The objects to be tested.
	 */
	private Game CuT;

	/**
	 * Friendly Objects
	 */
	private Player white;
	private Player red;
	private Board board;
	private Color color;
	private Piece redPiece;
	private Piece whitePiece;
	Piece[][] customPieces;

	// Boards that result from making a move
	Piece[][] customPiecesRedMove1;
	Piece[][] customPiecesRedMove2;
	Piece[][] customPiecesWhiteMove1;
	Piece[][] customPiecesWhiteMove2;
	Piece[][] customPiecesWhiteMove3;

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
    private Move validRedMove1 = new Move(redPosition1, emptyPosition1);
	private Move validRedMove2 = new Move(redPosition1, emptyPosition2);
	private Move invalidRedMove1 = new Move(redPosition1, whitePosition1);
	private Move invalidRedMove2 = new Move(redPosition1, emptyPosition3);
	private Move validWhiteMove1 = new Move(whitePosition2, emptyPosition4);
	private Move validWhiteMove2 = new Move(whitePosition2, emptyPosition5);
	private Move invalidWhiteMove1 = new Move(whitePosition2, redPosition2);
	private Move invalidWhiteMove2 = new Move(whitePosition2, emptyPosition6);

	 /**
	 * Initialize the objects to test
	 */
	@BeforeEach
	public void setup(){
	    white = mock(Player.class);
	    red = mock(Player.class);
		Piece whitePiece = new Piece(Color.WHITE, Type.SINGLE);
		Piece redPiece = new Piece(Color.RED, Type.SINGLE);

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

        //using this to test jump move
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

        //using this to test jump move
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
        //using this to test jump move
        customPiecesWhiteMove3 = new Piece[][]{
                {null, null, null, null, null, null, null, null},
                {null, whitePiece, null, null, null, null, null, null},
                {null, null, redPiece, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, redPiece, null, null, null, null, null},
                {null, null, null, whitePiece, null, null, null, null}
        };



		board = new Board(customPieces);
	    CuT = new Game(red, white, board);
	}

	/**
	 * Tests the isRedPlayer method
	 */
	@Test
	public void testIsRedPlayer() {

	    assertFalse(CuT.isRedPlayer(white), "" +
                "When passing in white player, this should return false!");
	    assertTrue(CuT.isRedPlayer(red));
	}

	/**
	 * Tests the getRedPlayer method
	 */
	@Test
	public void testGetRedPlayer() {
	assertEquals(red, CuT.getRedPlayer(), "" +
            "getRedPlayer does not return the correct object!");
	}

	/**
	 * Tests the getWhitePlayer method
	 */
	@Test
	public void testGetWhitePlayer() {
	assertEquals(white, CuT.getWhitePlayer(), "" +
            "getWhitePlayer does not return correct object!");
	}

	/**
	 * Tests the getActivePlayer method
	 */
	@Test
	public void testgetActivePlayer() {
	assertEquals(Color.RED, CuT.getActiveColor(), "" +
            "Active player should be red!");
	}

	/**
	 * Tests the getBoard method
	 */
	@Test
	public void testGetBoard() {
	assertSame(board, CuT.getBoard(), "" +
            "The boards are not equal!");
	}

    /**
     * Test new Board should be generated if
     * there is no board supplied
     */
    @Test
    public void testNoBoardConstructor(){
        CuT = new Game(red, white);
        //assertSame();
	}

	/**
     * Tests the switchActiveColor method
     */
    @Test
    public void testSwitchActiveColor() {
		Color originalColor = CuT.getActiveColor();
		CuT.switchActiveColor();
		assertNotEquals(originalColor, CuT.getActiveColor());
		CuT.switchActiveColor();
		assertEquals(originalColor, CuT.getActiveColor());
	}

	/**
	 * Tests the ValidateMove method with single pieces.
	 */
	@Test
	public void testValidateMove(){
		// First the Red pieces will be tested.
		if(CuT.getActiveColor() == Color.WHITE) {
			CuT.switchActiveColor();
		}

		assertTrue(CuT.validateMove(validRedMove1));
		assertTrue(CuT.validateMove(validRedMove2));
		assertFalse(CuT.validateMove(invalidRedMove1));
		assertFalse(CuT.validateMove(invalidRedMove2));
		assertFalse(CuT.validateMove(validWhiteMove1));
		assertFalse(CuT.validateMove(invalidWhiteMove1));

		CuT.switchActiveColor();

		assertTrue(CuT.validateMove(validWhiteMove1));
		assertTrue(CuT.validateMove(validWhiteMove2));
		assertFalse(CuT.validateMove(invalidWhiteMove1));
		assertFalse(CuT.validateMove(invalidWhiteMove2));
		assertFalse(CuT.validateMove(validRedMove1));
		assertFalse(CuT.validateMove(invalidRedMove1));
	}

	/**
	 * Tests if making a move changes the board in the appropriate ways.
	 */
	@Test
	public void testMakeMove(){
	    if(CuT.getActiveColor() == Color.WHITE){
            CuT.switchActiveColor();
        }

        CuT.makeMove(validRedMove2);
        assertEquals(CuT.getBoard().getPieces(),customPiecesRedMove2);

        CuT = new Game(red, white, board);

        CuT.makeMove(validRedMove1);
        assertEquals(CuT.getBoard().getPieces(),customPiecesRedMove1);

        CuT = new Game(red, white, board);

        CuT.makeMove(validWhiteMove1);
        assertEquals(CuT.getBoard().getPieces(),customPiecesWhiteMove1);

        CuT = new Game(red, white, board);

        CuT.makeMove(validWhiteMove2);
        assertEquals(CuT.getBoard().getPieces(),customPiecesWhiteMove2);

    }

    /**
     * If there is a valid jump move to be made
     * then this method should return true
     */
    @Test
    public void testIsJumpMove(){
        //valid jump move
        Position positionStart = new Position(7, 3);
        Position positionEnd = new Position(5, 1);
        Move move = new Move(positionStart, positionEnd);

        assertTrue(CuT.isJumpMove(move));

        //invalid move: trying to move a
        //non red or kinged piece down
        positionStart = new Position(1, 1);
        positionEnd = new Position(2, 3);
        move = new Move(positionStart, positionEnd);
        board = new Board(customPiecesWhiteMove3);
        CuT = new Game(red, white, board);

        assertFalse(CuT.isJumpMove(move));
	}
	
	/**
	 * Test for the checkForValidPieces method.
	 */
	@Test
	public void testCheckForValidPieces() {

	}

	/**
	 * Test for the checkJumpLocation method.
	 */
	@Test
	public void testCheckJumpLocation() {

	}

	/**
	 * Test for the jumpMoveExists method.
	 */
	@Test
	public void testJumpMoveExists() {

	}
}