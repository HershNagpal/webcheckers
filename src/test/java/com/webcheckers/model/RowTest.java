package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.model.Piece.Type;

/**
 * Unit test suite for the Row class.
 *
 * @author Hersh Nagpal
 */
@Tag("Model-tier")
public class RowTest {

	/**
	 * Objects being tested
	 */
	private Row row3;
	private Row row7;
	private Piece mockRedPiece;
	private Piece mockWhitePiece;

	/**
	 * Setup for the piece arrays to be put into each Row
	 * pieces3 is 8 mockRedPieces in a row
	 * pieces7 is null, white, red, null twice
	 */
	private Piece pieces3[] = new Piece[]
		{mockRedPiece, mockRedPiece, mockRedPiece, mockRedPiece, 
			mockRedPiece, mockRedPiece, mockRedPiece, mockRedPiece};

	private Piece pieces7[] = new Piece[] 
		{null, mockWhitePiece, mockRedPiece, null,
			null, mockWhitePiece, mockRedPiece, null};
	

	/**
	 * Initialize the objects to test
	 */
	@BeforeEach
	public void setup(){
		mockRedPiece = mock(Piece.class);
		when(mockRedPiece.getColor()).thenReturn(Color.RED);
		when(mockRedPiece.getType()).thenReturn(Type.SINGLE);

		mockWhitePiece = mock(Piece.class);
		when(mockWhitePiece.getColor()).thenReturn(Color.WHITE);
		when(mockWhitePiece.getType()).thenReturn(Type.SINGLE);

		row3 = new Row(pieces3,3);
		row7 = new Row(pieces7, 7);
	}

	/**
	 * Tests the getIndex method
	 */
	@Test
	public void testGetIndex() {
	
	}
}