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
	 * Initialize the objects to test
	 */
	@BeforeEach
	public void setup(){
	    white = mock(Player.class);
	    red = mock(Player.class);
		board = mock(Board.class);

		redPiece = new Piece(Color.RED, Type.SINGLE);
		whitePiece = new Piece(Color.WHITE, Type.SINGLE);
		
		// Fill the positions on the mock board.
		when(board.getPieceAtPosition(redPosition1)).thenReturn(redPiece);
		when(board.getPieceAtPosition(redPosition2)).thenReturn(redPiece);
		when(board.getPieceAtPosition(whitePosition1)).thenReturn(whitePiece);
		when(board.getPieceAtPosition(whitePosition2)).thenReturn(whitePiece);
		when(board.getPieceAtPosition(emptyPosition1)).thenReturn(null);
		when(board.getPieceAtPosition(emptyPosition2)).thenReturn(null);
		when(board.getPieceAtPosition(emptyPosition3)).thenReturn(null);
		when(board.getPieceAtPosition(emptyPosition4)).thenReturn(null);
		when(board.getPieceAtPosition(emptyPosition5)).thenReturn(null);
		when(board.getPieceAtPosition(emptyPosition6)).thenReturn(null);
	
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
	 * Tests the ValidateMove method.
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
}