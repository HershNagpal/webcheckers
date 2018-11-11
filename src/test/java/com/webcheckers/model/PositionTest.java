package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
     * Test the equals method
     */
    @Test
    public void testEquals() {
        Position copy = CuT;
        String fake = "fake";
        assertEquals(copy, CuT);
        assertNotEquals(CuT, fake);
    }

    /**
     * Test the isDiagonalTo method
     */
    @Test
    public void testIsDiagonalTo() {

    }

    /**
     * Test the isDistanceExpectedValue method
     */
    @Test
    public void testIsDistanceExpectedValue() {

    }

    /**
     * Test the isDiagonalAdjacentTo method
     */
    @Test
    public void testIsDiagonalAdjacentTo() {

    }
}
