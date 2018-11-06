package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.webcheckers.model.Piece.Type;

/**
 * Unit test suite for the Space class.
 * @author Hersh Nagpal
 */
@Tag("Model-tier")
class SpaceTest {

	/**
	 * The objects being tested
	 */
	private Space blackSpaceWithPiece;
	private Space blackSpaceWithoutPiece;

	private Space whiteSpaceWithPiece;
	private Space whiteSpaceWithoutPiece;

	private Space whiteKingSpaceWithoutPiece;
	private Space blackKingSpaceWithoutPiece;


	/**
	* Initialize the objects to test
	*/
	@BeforeEach
	public void setup() {
		blackSpaceWithPiece = new Space(1, 2, mock(Piece.class));
		blackSpaceWithoutPiece = new Space(3, 4, null);

		whiteSpaceWithPiece = new Space(6, 6, mock(Piece.class));
		whiteSpaceWithoutPiece = new Space(2, 4, null);

		whiteKingSpaceWithoutPiece = new Space(1, 7, null);
		blackKingSpaceWithoutPiece = new Space(0, 0, null);
	}

	/**
	 * Tests the checkColor method
	 */
	@Test
	public void testCheckColor() {
		// Black Spaces are false
		assertFalse(blackSpaceWithPiece.checkColor());
		assertFalse(blackSpaceWithPiece.checkColor());
		// White Spaces are true
		assertTrue(whiteSpaceWithoutPiece.checkColor());
		assertTrue(whiteSpaceWithPiece.checkColor());
	}

	/**
	 * Tests the isValid method
	 */
	@Test
	public void testIsValid() {
		// Space is valid because it has no Piece and is Black.
		assertTrue(blackSpaceWithoutPiece.isValid());
		// These should all be false because they either have Pieces or are not Black spaces.
		assertFalse(blackSpaceWithPiece.isValid());
		assertFalse(whiteSpaceWithoutPiece.isValid());
		assertFalse(whiteSpaceWithPiece.isValid());
	}

	/**
	 * Tests the hasChecker method
	 */
	@Test
	public void testHasChecker() {
		assertTrue(blackSpaceWithPiece.hasChecker());
		assertTrue(whiteSpaceWithPiece.hasChecker());

		assertFalse(blackSpaceWithoutPiece.hasChecker());
		assertFalse(whiteSpaceWithoutPiece.hasChecker());
	}

	/**
	 * Tests the getCellIdx method
	 */
	@Test
	public void testGetCellIdx() {
		assertEquals(0, blackKingSpaceWithoutPiece.getCellIdx());
		assertEquals(7, whiteKingSpaceWithoutPiece.getCellIdx());

		assertNotEquals(0, whiteKingSpaceWithoutPiece.getCellIdx());
		assertNotEquals(7, blackKingSpaceWithoutPiece.getCellIdx());
	}

	/**
	 * Tests the setPiece and getPiece method
	 */
	@Test
	public void testSetAndGetPiece() {
		Piece testPiece = new Piece(Color.RED, Type.SINGLE);
		blackSpaceWithoutPiece.setPiece(testPiece);

		assertEquals(new Piece(Color.RED, Type.SINGLE), blackSpaceWithoutPiece.getPiece());
		assertNotEquals(new Piece(Color.WHITE, Type.SINGLE), blackSpaceWithoutPiece.getPiece());
		assertNotEquals(new Piece(Color.WHITE, Type.KING), blackSpaceWithoutPiece.getPiece());
		assertNotEquals(new Piece(Color.RED, Type.KING), blackSpaceWithoutPiece.getPiece());
	}

	/**
	 * Test the equals method
	 */
	@Test
	public void testEquals() {
		Space copy = blackSpaceWithoutPiece;
		String fake = "fake";
		assertEquals(copy, blackSpaceWithoutPiece);
		assertNotEquals(blackSpaceWithoutPiece, fake);
	}
}