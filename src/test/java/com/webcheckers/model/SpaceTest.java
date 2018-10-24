package com.webcheckers.model;

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
	* Setup the object to test
	*/
	public void setup() {
		blackSpaceWithPiece = new Space(1, 2, mock(Piece.class));
		blackSpaceWithoutPiece = new Space(3, 4, null);

		whiteSpaceWithPiece = new Space(6, 6, mock(Piece.class));
		whiteSpaceWithoutPiece = new Space(2, 4, null);

		whiteKingSpaceWithoutPiece = new Space(7, 1, null);
		blackKingSpaceWithoutPiece = new Space();
	}

	/**
	 * Tests the setSpaceColor method
	 */
	@Test
	public void testSetSpaceColor(){
		
	}

	/**
	* Tests the setSpaceType method
	*/
   @Test
   public void testSetSpaceType() {
	   
   }

	/**
	 * Tests the checkColor method
	 */
	@Test
	public void testCheckColor() {
		assertFalse(blackSpaceWithPiece.checkColor());
		assertTrue(whiteSpaceWithoutPiece.checkColor());
	}

	/**
	 * Tests the isValid method
	 */
	@Test
	public void testIsValid() {

	}

	/**
	 * Tests the hasChecker method
	 */
	@Test
	public void testHasChecker() {
		
	}
	
	/**
	 * Tests the getPiece method
	 */
	@Test
	public void testGetPiece() {
		
	}

	/**
	 * Tests the getCellIdx method
	 */
	@Test
	public void testGetCellIdx() {
		
	}

	/**
	 * Tests the setPiece method
	 */
	@Test
	public void testSetPiece() {
		
	}
}