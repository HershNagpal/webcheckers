package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
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
    private Piece RedP;
    private Piece WhtP;
    private Piece redKingPiece;
    private Piece whiteKingPiece;

    /**
     * Initialize the objects to test
     */
    @BeforeEach
    public void setup(){
        RedP = new Piece(Color.RED, Piece.Type.SINGLE);
        WhtP = new Piece(Color.WHITE, Piece.Type.SINGLE);
        redKingPiece = new Piece(Color.RED, Piece.Type.KING);
        whiteKingPiece = new Piece(Color.WHITE, Piece.Type.KING);
    }

    /**
     * Test for isNormalMove.
     */
    @Test
    public void testIsSingleMove(){
        //Test red single moving piece, moving down and right (Valid)
        Move validRedSingleMove01 = new Move(new Position(0,0), new Position(1,1));
        assertTrue(MoveManager.isSingleMove(validRedSingleMove01,RedP));

        //Test red single moving piece, moving down and left (Valid)
        Move validRedSingleMove02 = new Move(new Position(1,1), new Position(2,0));
        assertTrue(MoveManager.isSingleMove(validRedSingleMove02,RedP));

        //Test white single moving piece, moving up and right (Valid)
        Move validWhiteSingleMove01 = new Move(new Position(5,5), new Position(4,6));
        assertTrue(MoveManager.isSingleMove(validWhiteSingleMove01,WhtP));

        //Test white single moving piece, moving up and left (Valid)
        Move validWhiteSingleMove02 = new Move(new Position(5,5), new Position(4,4));
        assertTrue(MoveManager.isSingleMove(validWhiteSingleMove02,WhtP));

        //Test red single moving piece, moving up and right (Invalid)
        Move invalidRedSingleMove01 = new Move(new Position(3,3), new Position(2,4));
        assertFalse(MoveManager.isSingleMove(invalidRedSingleMove01,RedP));

        //Test white single moving piece, moving down and right (Invalid)
        Move invalidWhiteSingleMove01 = new Move(new Position(3,3), new Position(4,4));
        assertFalse(MoveManager.isSingleMove(invalidWhiteSingleMove01,WhtP));

        //Test red king moving piece, moving up and left (Valid)
        Move validRedKingMove01 = new Move(new Position(3,3), new Position(2,2));
        assertTrue(MoveManager.isSingleMove(validRedKingMove01,redKingPiece));

        //Test white king moving piece, moving down and right (Valid)
        Move validWhiteKingMove01 = new Move(new Position(3,3), new Position(4,4));
        assertTrue(MoveManager.isSingleMove(validWhiteKingMove01,whiteKingPiece));

        //Test piece not moving diagonally
        Move invalidDiagonalMove = new Move(new Position(3,3), new Position(4,3));
        assertFalse(MoveManager.isSingleMove(invalidDiagonalMove,RedP));
    }

    /**
     * Test for isJumpMove.
     * @TODO ensure this test can run properly with a board.
     */
    @Test
    public void testIsJumpMove() {
        Piece[][] pieces = new Piece[][]{
                {null, null, null, null, null, RedP, null, null},
                {null, RedP, null, null, null, null, WhtP, null},
                {null, null, WhtP, WhtP, null, RedP, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, WhtP, null, RedP, null, null, WhtP},
                {null, null, null, WhtP, null, null, RedP, null},
                {RedP, null, null, null, null, null, null, RedP}
        };
        Board board = new Board(pieces);

        //Test red jump moving piece, moving down and right (Valid)
        Move validRedJumpMove01 = new Move(new Position(1,1), new Position(3,3));

        assertTrue(MoveManager.isJumpMove(validRedJumpMove01, board));
    }

    /**
     * Test for isLastMoveJump
     */
    @Test
    public void testIsLastMoveJump(){
        //test lastMoveJump true when white piece jumps "up/left" -2 col, -2 rows
        Position positionStart = new Position(4, 2);
        Position positionEnd = new Position(2, 4);
        Move move = new Move(positionStart, positionEnd);
        assertTrue(MoveManager.isLastMoveJump(move, WhtP));

        //test last move valid when white piece jumps "up/right" +2 col, +2 rows
        positionStart = new Position(4, 6);
        positionEnd = new Position(2, 4);
        move = new Move(positionStart, positionEnd);
        assertTrue(MoveManager.isLastMoveJump(move, WhtP));

        //test last move valid when red piece jumps "down/left" -2 col, +2 rows
        positionStart = new Position(3, 4);
        positionEnd = new Position(5, 2);
        move = new Move(positionStart, positionEnd);
        assertTrue(MoveManager.isLastMoveJump(move, RedP));

        //test last move valid when red piece jumps "down/right" +2 col, +2 rows
        positionStart = new Position(3, 0);
        positionEnd = new Position(5, 2);
        move = new Move(positionStart, positionEnd);
        assertTrue(MoveManager.isLastMoveJump(move, RedP));

        //test lastMoveJump false when white piece makes a non jump move
        positionStart = new Position(3, 3);
        positionEnd = new Position(2, 4);
        move = new Move(positionStart, positionEnd);
        assertFalse(MoveManager.isLastMoveJump(move, WhtP));

        //test lastMoveJump false when red piece makes a non jump move
        positionStart = new Position(4, 1);
        positionEnd = new Position(5, 2);
        move = new Move(positionStart, positionEnd);
        assertFalse(MoveManager.isLastMoveJump(move, WhtP));
    }

    /**
     * Test for isKingMove
     */
    @Test
    public void testIsKingMove() {
        Move whiteKingMove1 = new Move(new Position(1,6), new Position(2,5));
        Move whiteKingMove2 = new Move(new Position(1,2), new Position(2,3));
        Move redKingMove1 = new Move(new Position(5,2), new Position(3,0));
        Move redKingMove2 = new Move(new Position(6,3), new Position(4,5));

        Move normalWhiteMove1 = new Move(new Position(1,6), new Position(0,7));
        Move normalRedMove1 = new Move(new Position(5,1), new Position(6,0));

        assertTrue(MoveManager.isKingMove(whiteKingMove1, whiteKingPiece));
        assertTrue(MoveManager.isKingMove(whiteKingMove2, whiteKingPiece));
        assertTrue(MoveManager.isKingMove(redKingMove1, redKingPiece));
        assertTrue(MoveManager.isKingMove(redKingMove2, redKingPiece));

        assertFalse(MoveManager.isKingMove(redKingMove2, RedP));
        assertFalse(MoveManager.isKingMove(normalWhiteMove1, whiteKingPiece));
        assertFalse(MoveManager.isKingMove(normalRedMove1, redKingPiece));
    }
}
