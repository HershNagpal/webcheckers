package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


/**
 * Unit test suite for the Player class.
 *
 * @author Hersh Nagpal
 */
@Tag("Model-tier")
public class PlayerTest {
	
	private Player playerOrange;
	private Player playerTangerine;
	private static String ORANGE_NAME = "Orange";
	private static String TANGERINE_NAME = "Tangerine";

	/**
	 * Initialize the objects to test
	 */
	@BeforeEach
	public void setup(){
		playerOrange = new Player(ORANGE_NAME);
		playerTangerine = new Player(TANGERINE_NAME);

		Game mockGame = mock(Game.class);
		playerTangerine.setGame(mockGame);
		
	}

	/**
	 * Tests the getName method
	 */
	@Test
	public void testGetName() {
		assertEquals("Orange", playerOrange.getName());
		assertEquals("Tangerine", playerTangerine.getName());
		assertNotEquals("Orange", playerTangerine.getName());
	}

	/**
	 * Tests the getGame method
	 */
	@Test
	public void testGetGame() {
	
	}

	/**
	 * Tests the setGame method
	 */
	@Test
	public void testSetGame() {
	
	}
}