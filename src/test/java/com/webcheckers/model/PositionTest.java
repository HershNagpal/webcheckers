package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Class to test Position Class and its
 * methods
 * @author Matthew Bollinger
 */
public class PositionTest {

    /**
     * Componet under test
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
}
