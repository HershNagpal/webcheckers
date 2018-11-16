package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import com.webcheckers.model.Piece.Type;

/**
 * Unit test suite for the Game class.
 *
 * @author Hersh Nagpal
 * @author Matthew Bollinger
 * @author Luis Gutierrez
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
    private Piece redPiece;
    private Piece whitePiece;

    private Piece[][] customPieces;
    private Piece[][] customPiecesTestValidateMove;

    /**
     * Boards to test isGameOver
     */
    private Piece[][] cpNoRed;
    private Piece[][] cpNoWhite;

    // Boards that result from making a move
    private Piece[][] multipleJumpMove1;

    // Positions on the mock board that can be called.
    private Position redPosition1 = new Position(0, 2);
    // The 3 positions the Red piece in position 1 will try to jump to
    private Position emptyPosition1 = new Position(2, 0);
    private Position emptyPosition2 = new Position(1, 3);
    // Create moves to be tested in validateMove
    private Move validRedMove1 = new Move(redPosition1, emptyPosition1);
    private Move validRedMove2 = new Move(redPosition1, emptyPosition2);

    /**
     * Initialize the objects to test
     */
    @BeforeEach
    public void setup(){
        white = mock(Player.class);
        red = mock(Player.class);
        whitePiece = new Piece(Color.WHITE, Type.SINGLE);
        redPiece = new Piece(Color.RED, Type.SINGLE);

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

        cpNoRed = new Piece[][]{
                {null, null, null, null, null, null, null, null},
                {null, whitePiece, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, whitePiece, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
        };

        cpNoWhite = new Piece[][]{
                {null, null, null, null, null, null, null, null},
                {null, null, redPiece, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, redPiece, null, null},
                {null, null, null, null, null, null, null, null},
                {null, redPiece, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
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
     * Tests the isActivePlayer method
     */
    @Test
    public void testIsActivePlayer() {
        assertTrue(CuT.isActivePlayer(red));
        assertFalse(CuT.isActivePlayer(white));
        CuT.switchActiveColor();
        assertTrue(CuT.isActivePlayer(white));
        assertFalse(CuT.isActivePlayer(red));
    }

    /**
     * Tests the ValidateMove and makeMove method with single pieces.
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
     * Test for backUpMove
     */
    @Test
    public void testBackUpMove(){
        //Setup test
        board = new Board(customPiecesTestValidateMove);
        CuT = new Game(red, white, board);

        //Test invalid backup move due to empty list of last moves
        assertFalse(CuT.backUpMove());

        //Perform red jump move
        CuT.validateMove(validRedMove1.flipMove());

        //BackUp red jump move
        assertTrue(CuT.backUpMove());



    }

    /**
     * Test for checking if the player is the winner.
     */
    @Test
    public void testIsWinner() {
        assertFalse(CuT.isWinner(white));
        CuT.resignGame(red);
        assertTrue(CuT.isWinner(white));
    }

    /**
     * Test for resignGame
     */
    @Test
    public void testResignGame(){
        //Setup test
        board = new Board(customPiecesTestValidateMove);
        CuT = new Game(red, white, board);

        //Red Player resigns
        CuT.resignGame(red);

        //Winner should be white player
        /*assertEquals(white,CuT.getWinner());

        assertTrue(CuT.getGameOver());
        assertTrue(CuT.getResigned());*/
    }

    /**
     * Test for isGameOver
     */
    @Test
    public void testIsGameOver(){
        //Setup test
        board = new Board(customPiecesTestValidateMove);
        CuT = new Game(red, white, board);

        //Test game is not over
        assertFalse(CuT.isGameOver());

        //Test game is over by resignation
        //Red player resigns
        CuT.resignGame(red);
        assertTrue(CuT.isGameOver());

        //Test game is over by all pieces eliminated
        //Red has no more pieces
        board = new Board(cpNoRed);
        CuT = new Game(red, white, board);
        assertTrue(CuT.isGameOver());
        //White has no more pieces
        board = new Board(cpNoWhite);
        CuT = new Game(red, white, board);
        assertTrue(CuT.isGameOver());
    }

    /**
     * Test that the resign flag changes
     */
    @Test
    public void testDidPlayerResign(){
        assertFalse(CuT.didPlayerResign());
        CuT.resignGame(white);
        assertTrue(CuT.didPlayerResign());
    }

    /**
     * Test for submitTurn()
     */
    @Test
    public void testSubmitTurn(){
        //Setup test
        board = new Board(customPiecesTestValidateMove);
        CuT = new Game(red, white, board);

        //Test invalid submit turn when no move is made
        assertFalse(CuT.submitTurn());

        //Perform red jump move
        CuT.validateMove(validRedMove1.flipMove());

        //Test submit red jump move
        assertTrue(CuT.submitTurn());
    }

    @Test
    public void testGetBoardView(){
        //Setup test
        board = new Board(customPiecesTestValidateMove);
        CuT = new Game(red, white, board);

        //Create expected boardView
        BoardView expectedBoardView = board.getBoardView();

        //Test that expected board view is the same as actual board view
        assertEquals(expectedBoardView,CuT.getBoardView(white));
    }

    /**
     * Test for the jumpMoveExists method.
     */
    @Test
    public void testJumpMoveExists() {
        //assertTrue(CuT.jumpMoveExists());
        board = new Board(multipleJumpMove1);
        CuT = new Game(red, white, board);
        CuT.switchActiveColor();
        assertTrue(CuT.jumpMoveExists());
    }


}