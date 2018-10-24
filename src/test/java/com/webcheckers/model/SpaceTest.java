package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.webcheckers.model.Space.SpaceColor;

/**
 * Unit test suite for the Space class.
 * @author Hersh Nagpal
 */
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
	* Initialize the object to test
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
	 * Tests the getPiece method
	 */
	@Test
	public void testGetPiece() {
		// @TODO
	}

	/**
	 * Tests the getCellIdx method
	 */
	@Test
	public void testGetCellIdx() {
		assertEquals(0, blackKingSpaceWithoutPiece.getCellIdx());
		assertEquals(7, whiteKingSpaceWithoutPiece.getCellIdx());
	}

	/**
	 * Tests the setPiece method
	 */
	@Test
	public void testSetPiece() {
		// @TODO
	}
}