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

    /**
     * Test the isDiagonalTo method
     */
    @Test
    public void testIsDiagonalTo() {
        Position valid1 = new Position(1,2);
        Position valid2 = new Position(2,3);
        Position valid3 = new Position(4,5);
        Position valid4 = new Position(1, 0);
        Position invalid1 = new Position(6,2);
        Position invalid2 = new Position(2,1);
        assert(CuT.isDiagonalTo(valid1)); 
        assert(CuT.isDiagonalTo(valid2)); 
        assert(CuT.isDiagonalTo(valid3));
        assert(CuT.isDiagonalTo(valid4));
        assert(!CuT.isDiagonalTo(invalid1)); 
        assert(!CuT.isDiagonalTo(invalid2)); 
    }

    /**
     * Test the isDistanceExpectedValue method
     */
    @Test
    public void testIsDistanceExpectedValue() {
        assert(Position.isDistanceExpectedValue(1, 3, 2));
        assert(Position.isDistanceExpectedValue(7, 4, 3));
        assert(Position.isDistanceExpectedValue(100, 0, 100));
        assert(Position.isDistanceExpectedValue(1, 9, 8));
        assert(!Position.isDistanceExpectedValue(1, 3, 8));
        assert(!Position.isDistanceExpectedValue(8, 3, 2));

    }

    /**
     * Test the isDiagonalAdjacentTo method
     */
    @Test
    public void testIsDiagonalAdjacentTo() {
        Position valid1 = new Position(1, 2);
        Position valid2 = new Position(1, 0);

        Position invalid1 = new Position(2, 3);
        Position invalid2 = new Position(4, 5);
        Position invalid3 = new Position(9, 2);
        Position invalid4 = new Position(2, 1);
        Position invalid5 = new Position(-1, 0);

        assert(CuT.isDiagonalAdjacentTo(valid1)); 
        assert(CuT.isDiagonalAdjacentTo(valid2));

        assert(!CuT.isDiagonalAdjacentTo(invalid1)); 
        assert(!CuT.isDiagonalAdjacentTo(invalid2)); 
        assert(!CuT.isDiagonalAdjacentTo(invalid3)); 
        assert(!CuT.isDiagonalAdjacentTo(invalid4));
        assert(!CuT.isDiagonalAdjacentTo(invalid4));
    }

    /**
     * Test the isDiagonalJumpTo method
     */
    @Test
    public void testIsDiagonalJumpTo() {
        Position valid1 = new Position(2, 3);

        Position invalid1 = new Position(2, 3);
        Position invalid2 = new Position(4, 5);
        Position invalid3 = new Position(9, 2);
        Position invalid4 = new Position(2, 1);
        Position invalid5 = new Position(2, -1);

        assert(CuT.isDiagonalAdjacentTo(valid1));

        assert(!CuT.isDiagonalAdjacentTo(invalid1)); 
        assert(!CuT.isDiagonalAdjacentTo(invalid2)); 
        assert(!CuT.isDiagonalAdjacentTo(invalid3)); 
        assert(!CuT.isDiagonalAdjacentTo(invalid4));
        assert(!CuT.isDiagonalAdjacentTo(invalid4));
        assert(!CuT.isDiagonalAdjacentTo(invalid5));

    }
}
