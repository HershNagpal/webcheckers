package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.webcheckers.model.Piece.Type;

import java.util.ArrayList;
import java.util.List;

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
    private Game CuT;

    /**
     * Friendly Objects
     */
    private Player white;
    private Player red;
    private Board board;
    private Color color;
    private Piece redPiece;
    private Piece whitePiece;

    private Piece[][] customPieces;
    private Piece[][] customPiecesTestMovable;
    private Piece[][] customPiecesTestLastMoveJump;
    private Piece[][] customPiecesTestValidateMove;

    // Boards that result from making a move
    private Piece[][] customPiecesRedMove1;
    private Piece[][] customPiecesRedMove2;
    private Piece[][] customPiecesWhiteMove1;
    private Piece[][] customPiecesWhiteMove2;
    private Piece[][] customPiecesWhiteMove3;
    private Piece[][] multipleJumpMove1;
    private Piece[][] multipleJumpMove2;

    // Positions on the mock board that can be called.
    private Position redPosition1 = new Position(0, 2);
    private Position redPosition2 = new Position(6, 2);
    private Position whitePosition1 = new Position(1, 1);
    private Position whitePosition2 = new Position(7, 3);
    // The 3 positions the Red piece in position 1 will try to jump to
    private Position emptyPosition1 = new Position(2, 0);
    private Position emptyPosition2 = new Position(1, 3);
    private Position emptyPosition3 = new Position(2, 4);
    // The 3 positions the White piece in position 1 will try to jump to
    private Position emptyPosition4 = new Position(5, 1);
    private Position emptyPosition5 = new Position(6, 4);
    private Position emptyPosition6 = new Position(5, 5);
    // Create moves to be tested in validateMove
    private Move validRedMove1 = new Move(redPosition1, emptyPosition1);
    private Move validRedMove2 = new Move(redPosition1, emptyPosition2);
    private Move invalidRedMove1 = new Move(redPosition1, whitePosition1);
    private Move invalidRedMove2 = new Move(redPosition1, emptyPosition3);
    private Move validWhiteMove1 = new Move(whitePosition2, emptyPosition4);
    private Move validWhiteMove2 = new Move(whitePosition2, emptyPosition5);
    private Move invalidWhiteMove1 = new Move(whitePosition2, redPosition2);
    private Move invalidWhiteMove2 = new Move(whitePosition2, emptyPosition6);

    /**
     * Initialize the objects to test
     */
    @BeforeEach
    public void setup(){
        white = mock(Player.class);
        red = mock(Player.class);
        whitePiece = new Piece(Color.WHITE, Type.SINGLE);
        redPiece = new Piece(Color.RED, Type.SINGLE);

        customPiecesTestMovable = new Piece[][]{
                {null, null, null, null, null, null, null, null},
                {null, redPiece, null, null, null, null, null, null},
                {null, null, whitePiece, null, null, null, null, null},
                {null, redPiece, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
        };

        customPiecesTestLastMoveJump = 	new Piece[][]{
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, whitePiece, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, redPiece, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
        };

        /*
        Used to test red valid jump move and white single move
         */
        customPiecesTestValidateMove = new Piece[][]{
                {null, null, redPiece, null, null, null, null, redPiece},
                {null, whitePiece, null, null, null, null, whitePiece, null},
                {null, null, null, null, null, whitePiece, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, whitePiece, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
        };

        /*
        Used to test redPiece valid jump move and whitePiece valid jump move,
        invalid single moves given that there exists a jump move.
         */
        customPieces = 	new Piece[][]{
                {null, null, redPiece, null, null, null, null, null},
                {null, whitePiece, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, redPiece, null, null, null, null, null},
                {null, null, null, whitePiece, null, null, null, null}
        };

        customPiecesRedMove2 = 	new Piece[][]{
                {null, null, null, null, null, null, null, null},
                {null, whitePiece, redPiece, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, redPiece, null, null, null, null, null},
                {null, null, null, whitePiece, null, null, null, null}
        };

        customPiecesRedMove1 = new Piece[][]{
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {redPiece, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, redPiece, null, null, null, null, null},
                {null, null, null, whitePiece, null, null, null, null}
        };

        //using this to test jump move
        customPiecesWhiteMove1 = new Piece[][]{
                {null, null, redPiece, null, null, null, null, null},
                {null, whitePiece, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, whitePiece, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
        };

        //using this to test jump move
        customPiecesWhiteMove2 = new Piece[][]{
                {null, null, redPiece, null, null, null, null, null},
                {null, whitePiece, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, redPiece, null, null, null, null, null},
                {null, null, null, whitePiece, null, null, null, null}
        };
        //using this to test jump move
        customPiecesWhiteMove3 = new Piece[][]{
                {null, null, null, null, null, null, null, null},
                {null, whitePiece, null, null, null, null, null, null},
                {null, null, redPiece, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, redPiece, null, null, null, null, null},
                {null, null, null, whitePiece, null, null, null, null}
        };
        multipleJumpMove1 = new Piece[][]{
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, redPiece, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, redPiece, null, null, null, null},
                {null, null, whitePiece, null, null, null, null, null}
        };
        //this is a case where the white player can
        //either choose to take a double or single jump
        multipleJumpMove2 = new Piece[][]{
                {null, null, null, null, null, null, null, null},
                {null, null, whitePiece, null, null, null, null, null},
                {null, redPiece, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, redPiece, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, redPiece, null, null, null, null, null, null},
                {whitePiece, null, null, null, null, null, null, null}
        };




        board = new Board(customPieces);
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
        assertSame(board, CuT.getBoard(), "" +
                "The boards are not equal!");
    }

    /**
     * Test new Board should be generated if
     * there is no board supplied
     */
    @Test
    public void testNoBoardConstructor(){
        CuT = new Game(red, white);
        //assertSame();
    }

    /**
     * Tests the switchActiveColor method
     */
    @Test
    public void testSwitchActiveColor() {
        Color originalColor = CuT.getActiveColor();
        CuT.switchActiveColor();
        assertNotEquals(originalColor, CuT.getActiveColor());
        CuT.switchActiveColor();
        assertEquals(originalColor, CuT.getActiveColor());
    }

    /**
     * Tests the ValidateMove method with single pieces.
     */
    @Test
    public void testValidateMove(){
        //Setup test
        board = new Board(customPiecesTestValidateMove);
        CuT = new Game(red, white, board);

        //Make red player active
        if(CuT.getActiveColor() == Color.WHITE) {
            CuT.switchActiveColor();
        }

        //Flip moves for red moves because moves are flipped in validateMove()

        //Test invalid red single move due to a jump move being available
        assertFalse(CuT.validateMove(validRedMove2.flipMove()));

        //Test valid red jump move
        assertTrue(CuT.validateMove(validRedMove1.flipMove()));

        //Test invalid move due to active color (piece moved is white, active color is red)
        Move invalidMoveActiveColor = new Move(new Position(5,3),new Position(4,2)).flipMove();
        assertFalse(CuT.validateMove(invalidMoveActiveColor));

        //Test invalid move due to space moving into not being empty
        Move invalidMoveSpaceNotEmpty = new Move(new Position(0, 7), new Position(2, 5)).flipMove();
        assertFalse(CuT.validateMove(invalidMoveSpaceNotEmpty));

        //Change active color to white
        CuT.switchActiveColor();

        //Test valid white single move
        Move validWhiteSingleMove = new Move(new Position(5,3), new Position(4,4));
        assertTrue(CuT.validateMove(validWhiteSingleMove));
    }

    /**
     * Tests if making a move changes the board in the appropriate ways.
     */
    @Test
    public void testMakeMove(){
        if(CuT.getActiveColor() == Color.WHITE){
            CuT.switchActiveColor();
        }

        CuT.makeMove(validRedMove2.flipMove());
        assertEquals(CuT.getBoard().getPieces(),customPiecesRedMove2);

        CuT = new Game(red, white, board);

        CuT.makeMove(validRedMove1.flipMove());
        assertEquals(CuT.getBoard().getPieces(),customPiecesRedMove1);

        CuT = new Game(red, white, board);

        CuT.makeMove(validWhiteMove1);
        assertEquals(CuT.getBoard().getPieces(),customPiecesWhiteMove1);

        CuT = new Game(red, white, board);

        CuT.makeMove(validWhiteMove2);
        assertEquals(CuT.getBoard().getPieces(),customPiecesWhiteMove2);

    }

    /**
     * If there is a valid jump move to be made
     * then this method should return true
     */
    @Test
    public void testIsJumpMove(){
        //valid jump move
        Position positionStart = new Position(7, 3);
        Position positionEnd = new Position(5, 1);
        Move move = new Move(positionStart, positionEnd);

        assertTrue(CuT.isJumpMove(move));

        //invalid move: trying to move a
        //non red or kinged piece down
        positionStart = new Position(1, 1);
        positionEnd = new Position(2, 3);
        move = new Move(positionStart, positionEnd);
        board = new Board(customPiecesWhiteMove3);
        CuT = new Game(red, white, board);

        assertFalse(CuT.isJumpMove(move));
    }

    /**
     * Test for the jumpMoveExists method.
     */
    @Test
    public void testJumpMoveExists() {
        assertTrue(CuT.jumpMoveExists());
        board = new Board(multipleJumpMove1);
        CuT = new Game(red, white, board);
        CuT.switchActiveColor();
        assertTrue(CuT.jumpMoveExists());
    }

    /**
     * Test getMovablePieceLocations
     */
    @Test
    public void testGetMovablePieceLocations(){
        board = new Board(customPiecesTestMovable);
        CuT = new Game(red, white, board);

        //set red player as active
        if(!CuT.isActivePlayer(red)){
            CuT.switchActiveColor();
        }

        //Test red player movablePieceLocations
        List<Position> expectedMovablePieceLocations = new ArrayList<>();
        expectedMovablePieceLocations.add(new Position(1,1));
        expectedMovablePieceLocations.add(new Position(3, 1));

        List<Position> actualMovablePieceLocations = CuT.getMovablePieceLocations();

        assertEquals(expectedMovablePieceLocations,actualMovablePieceLocations);
    }

    /**
     * Test jumpPositionExists
     */
    @Test
    public void testGetJumpLocations() {
        board = new Board(customPiecesTestMovable);
        CuT = new Game(red, white, board);

        //set red player as active
        if(!CuT.isActivePlayer(red)){
            CuT.switchActiveColor();
        }

        //Test jumpPositions for red piece on row 1, col 1
        List<Position> expectedJumpPositions = new ArrayList<>();
        expectedJumpPositions.add(new Position(3,3));

        List<Position> actualJumpPositions = CuT.getJumpLocations(new Position(1,1));
        System.out.println("Expected: "+expectedJumpPositions);
        System.out.println("Actual: "+actualJumpPositions);

        assertEquals(expectedJumpPositions,actualJumpPositions);

        //set white player as active
        CuT.switchActiveColor();

        //Test jumpPositions for white piece on row 2, col 2
        expectedJumpPositions.clear();
        expectedJumpPositions.add(new Position(0, 0));

        actualJumpPositions = CuT.getJumpLocations(new Position(2,2));
        System.out.println("Expected: "+expectedJumpPositions);
        System.out.println("Actual: "+actualJumpPositions);

        assertEquals(expectedJumpPositions,actualJumpPositions);
    }

    @Test
    public void testIsLastMoveJump(){

        //Setup Test
        board = new Board(customPiecesTestLastMoveJump);
        CuT = new Game(red, white, board);

        //test lastMoveJump true when white piece jumps "up/left" -2 col, -2 rows
        Position positionStart = new Position(4, 2);
        Position positionEnd = new Position(2, 4);
        Move move = new Move(positionStart, positionEnd);
        assertTrue(CuT.isLastMoveJump(move));

        //test last move valid when white piece jumps "up/right" +2 col, +2 rows
        positionStart = new Position(4, 6);
        positionEnd = new Position(2, 4);
        move = new Move(positionStart, positionEnd);
        assertTrue(CuT.isLastMoveJump(move));

        //test last move valid when red piece jumps "down/left" -2 col, +2 rows
        positionStart = new Position(3, 4);
        positionEnd = new Position(5, 2);
        move = new Move(positionStart, positionEnd);
        assertTrue(CuT.isLastMoveJump(move));

        //test last move valid when red piece jumps "down/right" +2 col, +2 rows
        positionStart = new Position(3, 0);
        positionEnd = new Position(5, 2);
        move = new Move(positionStart, positionEnd);
        assertTrue(CuT.isLastMoveJump(move));

        //test lastMoveJump false when white piece makes a non jump move
        positionStart = new Position(3, 3);
        positionEnd = new Position(2, 4);
        move = new Move(positionStart, positionEnd);
        assertFalse(CuT.isLastMoveJump(move));

        //test lastMoveJump false when red piece makes a non jump move
        positionStart = new Position(4, 1);
        positionEnd = new Position(5, 2);
        move = new Move(positionStart, positionEnd);
        assertFalse(CuT.isLastMoveJump(move));
    }
}