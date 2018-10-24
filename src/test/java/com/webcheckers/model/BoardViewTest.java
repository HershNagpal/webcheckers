package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Iterator;

import com.webcheckers.model.Piece.Type;


/**
 * Unit test suite for the BoardView class.
 *
 * @author Hersh Nagpal
 */
@Tag("Model-tier")
public class BoardViewTest {

	/**
	 * The objects to be tested
	 */
	private BoardView boardView;
	private Piece mockRedPiece;

	// Board created with mock pieces. Every other row has 8 mock red pieces while the odd rows have none.
	private Piece[][] pieces;

	/**
	 * Initialize the objects to test
	 */
	@BeforeEach
	public void setup(){
		mockRedPiece = mock(Piece.class);
		when(mockRedPiece.getColor()).thenReturn(Color.RED);
		when(mockRedPiece.getType()).thenReturn(Type.SINGLE);

		pieces = new Piece[][]{
			{mockRedPiece, mockRedPiece, mockRedPiece, mockRedPiece, 
				mockRedPiece, mockRedPiece, mockRedPiece, mockRedPiece},
			{null, null, null, null, null, null, null, null},
			{mockRedPiece, mockRedPiece, mockRedPiece, mockRedPiece, 
				mockRedPiece, mockRedPiece, mockRedPiece, mockRedPiece},
			{null, null, null, null, null, null, null, null},
			{mockRedPiece, mockRedPiece, mockRedPiece, mockRedPiece, 
				mockRedPiece, mockRedPiece, mockRedPiece, mockRedPiece},
			{null, null, null, null, null, null, null, null},
			{mockRedPiece, mockRedPiece, mockRedPiece, mockRedPiece, 
				mockRedPiece, mockRedPiece, mockRedPiece, mockRedPiece},
			{null, null, null, null, null, null, null, null}
			};

		boardView = new BoardView(pieces);
	}

	/**
	 * Tests the iterator method and that the BoardView is set up properly.
	 */
	@Test
	public void testIterator() {
		Iterator<Row> rows = boardView.iterator();
		int counter = 0;

		Iterator<Space> spacesFromRow;
		Row currentRow;
		Piece currentPiece;

		
		while(rows.hasNext()){
			currentRow = rows.next();
			switch(counter % 2) {
				case 0:
					// Even rows should have all red pieces.
					spacesFromRow = currentRow.iterator();
					while(spacesFromRow.hasNext()) {
						currentPiece = spacesFromRow.next().getPiece();
						assertEquals(mockRedPiece,currentPiece);
					}
					break;
				case 1:
					// Odd rows should have all nulls.
					spacesFromRow = currentRow.iterator();
					while(spacesFromRow.hasNext()) {
						currentPiece = spacesFromRow.next().getPiece();
						assertNull(currentPiece);
					}
					break;
			}
			counter++;
		}
	}
}