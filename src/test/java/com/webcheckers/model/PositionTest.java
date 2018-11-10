package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class to test Position Class and its
 * methods
 * @author Matthew Bollinger
 */
public class PositionTest {

    /**
     * Component under test
     */
    private Position CuT;

    /**
     * Before each test
     * execute this
     */
    @BeforeEach
    public void setup(){
        CuT = new Position(0,1);
    }

    /**
     * This is a test to ensure that
     * getters return correct values
     */
    @Test
    public void testGetters(){
        assertEquals(CuT.getRow(), 0);
        assertEquals(CuT.getCell(), 1);
    }

    /**
     * Test to check if two positions are diagonal to each other
     */
    @Test
    public void testIsDiagonalTo() {
        Position other = new Position(1, 2);
        Position bad = new Position(3, 3);
        assertTrue(CuT.isDiagonalTo(other));
        assertFalse(CuT.isDiagonalTo(bad));
    }

    /**
     * Test to check if one position is forward another position.
     */
    @Test
    public void testIsForwardTo() {
        Position other = new Position(1, 2);
        assertTrue(other.isForwardTo(CuT));
        assertFalse(CuT.isForwardTo(other));
    }

    /**
     * Test the equals method
     */
    @Test
    public void testEquals() {
        Position copy = CuT;
        String fake = "fake";
        assertEquals(copy, CuT);
        assertNotEquals(CuT, fake);
    }
}
