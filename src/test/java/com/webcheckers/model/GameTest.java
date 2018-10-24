package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
	private Player white;
	private Player red;
	private Board board;
	private Color color;

	private Game CuT;


	 /**
	 * Initialize the objects to test
	 */
	@BeforeEach
	public void setup(){
	    white = mock(Player.class);
	    red = mock(Player.class);
	    board = mock(Board.class);

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
	assertEquals(board, CuT.getBoard(), "" +
            "The boards are not equal!");
	}
}