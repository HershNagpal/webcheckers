package com.webcheckers.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockitoSession;
import static org.mockito.Mockito.when;

/**
 * Unit test suite for the Space class.
 * @author Hersh Nagpal
 */
class SpaceTest {

	/**
	 * The objects being tested
	 */
	private Space blackSpaceWithPiece;
	private Space whiteSpaceWithoutPiece;
	private Space whiteKingSpaceWithoutPiece;


	/**
	* Setup the object to test
	*/
	public void setup() {
		blackSpaceWithPiece = new Space(1, 2, mock(Piece.class));
		whiteSpaceWithoutPiece = new Space(2, 4, null);
		whiteKingSpaceWithoutPiece = new Space(6, 2, null);
	}
	
}