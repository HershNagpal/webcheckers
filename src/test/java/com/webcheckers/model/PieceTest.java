package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.webcheckers.model.Piece.Type;

/**
 * Unit test suite for the Piece class.
 *
 * @author Hersh Nagpal
 */
@Tag("Model-tier")
public class PieceTest {

	/**
	 * The objects to be tested.
	 */
	private Piece redSingle;
	private Piece whiteSingle;
	private Piece redKing;
	private Piece whiteKing;
	private Piece redSingleToKing;

	/**
	 * Initialize the objects to test
	 */
	@BeforeEach
	public void setup(){
		redSingle = new Piece(Color.RED, Type.SINGLE);
		whiteSingle = new Piece(Color.WHITE, Type.SINGLE);
		redKing = new Piece(Color.RED, Type.KING);
		whiteKing = new Piece(Color.WHITE, Type.KING);

		redSingleToKing = new Piece(Color.RED, Type.SINGLE);
	}

	/**
	 * Tests the getColor method
	 */
	@Test
	public void testGetColor() {
		assertEquals(Color.RED, redSingle.getColor());
		assertEquals(Color.WHITE, whiteSingle.getColor());
		assertEquals(Color.RED, redKing.getColor());
		assertEquals(Color.WHITE, whiteKing.getColor());
	}

	/**
	 * Tests the getType method
	 */
	@Test
	public void testGetType() {
		assertEquals(Type.SINGLE, redSingle.getType());
		assertEquals(Type.SINGLE, whiteSingle.getType());
		assertEquals(Type.KING, redKing.getType());
		assertEquals(Type.KING, whiteKing.getType());
	}

	/**
	 * Tests the kingPiece method
	 */
	@Test
	public void testKingPiece() {
		assertEquals(Type.SINGLE, redSingleToKing.getType());
		assertTrue(redSingleToKing.kingPiece());
		assertEquals(Type.KING, redSingleToKing.getType());
	}
}