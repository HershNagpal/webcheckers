package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test suite for the Message class.
 *
 * @author Hersh Nagpal
 */
@Tag("Model-tier")
public class MessageTest {

	/**
	 * The objects to be tested.
	 */
	private Message errorMessage;
	private Message infoMessage;
	/**
	 * Friendly Objects
	 */
	private final String ERROR_TEXT = "This is an error; you suck.";
	private final String INFO_TEXT = "This just in: you suck.";

	/**
	 * Initialize the objects to test
	 */
	@BeforeEach
	public void setup(){
		errorMessage = new Message(ERROR_TEXT, MessageType.ERROR);
		infoMessage = new Message(INFO_TEXT, MessageType.INFO);
	}

	/**
	 * Tests the getText method
	 */
	@Test
	public void testGetText() {
		assertEquals("This is an error; you suck.", errorMessage.getText());
		assertEquals("This just in: you suck.", infoMessage.getText());
	}

	/**
	 * Tests the getType method
	 */
	@Test
	public void testGetType() {
		assertEquals(MessageType.ERROR, errorMessage.getType());
		assertEquals(MessageType.INFO, infoMessage.getType());
	}
}