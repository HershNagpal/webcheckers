package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test suite for the Player class.
 *
 * @author Hersh Nagpal
 */
@Tag("Model-tier")
public class PlayerTest {
	
	/**
	 * Objects being tested
	 */
	private Player playerOrange;
	private Player playerTangerine;
	/**
	 * Friendly Objects
	 */
	private final String ORANGE_NAME = "Orange";
	private final String TANGERINE_NAME = "Tangerine";

	/**
	 * Initialize the objects to test
	 */
	@BeforeEach
	public void setup(){
		playerOrange = new Player(ORANGE_NAME);
		playerTangerine = new Player(TANGERINE_NAME);
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
	 * Tests the equals method
	 */
	@Test
	public void testEquals() {
		Player copy = playerOrange;
		String fake = "fake";
		assertEquals(copy, playerOrange);
		assertNotEquals(playerOrange, fake);
	}
}