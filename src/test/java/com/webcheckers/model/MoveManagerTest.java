package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test suite for the MoveManager class.
 *
 * @author Luis Gutierrez
 */
public class MoveManagerTest {

    /**
     * Friendly Objects
     */
    private Piece redPiece;
    private Piece whitePiece;
    private Piece redKingPiece;
    private Piece whiteKingPiece;

    /**
     * Initialize the objects to test
     */
    @BeforeEach
    public void setup(){
        redPiece = new Piece(Color.RED, Piece.Type.SINGLE);
        whitePiece = new Piece(Color.WHITE, Piece.Type.SINGLE);
        redKingPiece = new Piece(Color.RED, Piece.Type.KING);
        whiteKingPiece = new Piece(Color.WHITE, Piece.Type.KING);
    }

    /**
     * Test for isNormalMove.
     */
    @Test
    public void testIsNormalMove(){
        //Test red single moving piece, moving down and right (Valid)
        Move validRedSingleMove01 = new Move(new Position(0,0), new Position(1,1));
        assertTrue(MoveManager.isNormalMove(validRedSingleMove01,redPiece));

        //Test red single moving piece, moving down and left (Valid)
        Move validRedSingleMove02 = new Move(new Position(1,1), new Position(2,0));
        assertTrue(MoveManager.isNormalMove(validRedSingleMove02,redPiece));

        //Test white single moving piece, moving up and right (Valid)
        Move validWhiteSingleMove01 = new Move(new Position(5,5), new Position(4,6));
        assertTrue(MoveManager.isNormalMove(validWhiteSingleMove01,whitePiece));

        //Test white single moving piece, moving up and left (Valid)
        Move validWhiteSingleMove02 = new Move(new Position(5,5), new Position(4,4));
        assertTrue(MoveManager.isNormalMove(validWhiteSingleMove02,whitePiece));

        //Test red single moving piece, moving up and right (Invalid)
        Move invalidRedSingleMove01 = new Move(new Position(3,3), new Position(2,4));
        assertFalse(MoveManager.isNormalMove(invalidRedSingleMove01,redPiece));

        //Test red king moving piece, moving up and left (Valid)
        Move validRedKingMove01 = new Move(new Position(3,3), new Position(2,2));
        assertTrue(MoveManager.isNormalMove(validRedKingMove01,redKingPiece));

        //Test white king moving piece, moving down and right (Valid)
        Move validWhiteKingMove01 = new Move(new Position(3,3), new Position(4,4));
        assertTrue(MoveManager.isNormalMove(validWhiteKingMove01,whiteKingPiece));
    }
}
