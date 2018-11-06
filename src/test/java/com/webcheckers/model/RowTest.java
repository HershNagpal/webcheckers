package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.*;

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
	/**
	 * Friendly Objects
	 */
	private Piece mockRedPiece;
	private Piece mockWhitePiece;
	private Piece pieces3[];
	private Piece pieces7[];	

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

		/**
		 * Setup for the piece arrays to be put into each Row
	 	 * pieces3 is 8 mockRedPieces in a row
	 	 * pieces7 is null, white, red, null twice
	 	 */
		pieces3 = new Piece[]
			{mockRedPiece, mockRedPiece, mockRedPiece, mockRedPiece, 
			 mockRedPiece, mockRedPiece, mockRedPiece, mockRedPiece};
		pieces7 = new Piece[] 
			{null, mockWhitePiece, mockRedPiece, null,
			 null, mockWhitePiece, mockRedPiece, null};
	

		row3 = new Row(pieces3, 3);
		row7 = new Row(pieces7, 7);
	}

	/**
	 * Tests the Iterator method and that the Row is set up properly.
	 */
	@Test
	public void testIterator() {
		Iterator<Space> iterator3 = row3.iterator();
		Iterator<Space> iterator7 = row7.iterator();
		Piece currentPiece;

		// Tests that all of the pieces in pieces3 are red pieces.
		while(iterator3.hasNext()) {
			currentPiece = iterator3.next().getPiece();
			assertEquals(mockRedPiece, currentPiece);
			assertNotEquals(mockWhitePiece, currentPiece);
		}
		

		// Tests that the order of pieces in pieces7 matches the pattern given
		// null, white, red, null
		int counter = 0;
		while(iterator7.hasNext()) {
			currentPiece = iterator7.next().getPiece();
			switch(counter%4) {
				case 0:
					assertNull(currentPiece);
					break;
				case 1:
					assertEquals(mockWhitePiece, currentPiece);
					assertNotEquals(mockRedPiece, currentPiece);
					break;
				case 2:
					assertEquals(mockRedPiece, currentPiece);
					assertNotEquals(mockWhitePiece, currentPiece);
					break;
				case 3:
					assertNull(currentPiece);
					break;
			}
			counter++;
		}
	}

	/**
	 * Tests the getIndex method
	 */
	@Test
	public void testGetIndex() {
		assertEquals(3, row3.getIndex());
		assertEquals(7, row7.getIndex());

		assertNotEquals(0, row3.getIndex());
		assertNotEquals(16, row7.getIndex());
	}

	/**
	 * Test the equals method
	 */
	@Test
	public void testEquals() {
		Row copy = row3;
		String fake = "fake";
		assertEquals(copy, row3);
		assertNotEquals(row3, fake);
	}

}