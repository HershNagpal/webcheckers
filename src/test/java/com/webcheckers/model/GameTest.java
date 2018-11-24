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
    private Player whitePlayer;
    private Player redPlayer;
    private Board board;
    private Piece RedP;
    private Piece WhtP;

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
    //private Position redPosition1 = new Position(0, 2);
    // The 3 positions the Red piece in position 1 will try to jump to
    //private Position emptyPosition1 = new Position(2, 0);
    private Position emptyPosition2 = new Position(1, 3);

    // Create moves to be tested in validateMove
    //private Move validRedMove1 = new Move(redPosition1, emptyPosition1);
    //private Move validRedMove2 = new Move(redPosition1, emptyPosition2);

    /**
     * Initialize the objects to test
     */
    @BeforeEach
    public void setup(){
        whitePlayer = mock(Player.class);
        redPlayer = mock(Player.class);
        WhtP = new Piece(Color.WHITE, Type.SINGLE);
        RedP = new Piece(Color.RED, Type.SINGLE);

        /*
        Used to test red valid jump move and white single move
         */
        /*customPiecesTestValidateMove = new Piece[][]{
                {null, null, RedP, null, null, null, null, RedP},
                {null, WhtP, null, null, null, null, WhtP, null},
                {null, null, null, null, null, WhtP, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, WhtP, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
        };*/

        /*
        Used to test RedP valid jump move and WhtP valid jump move,
        invalid single moves given that there exists a jump move.
         */
        customPieces = 	new Piece[][]{
                {null, null, RedP, null, null, null, null, null},
                {null, WhtP, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, RedP, null, null, null, null, null},
                {null, null, null, WhtP, null, null, null, null}
        };

        multipleJumpMove1 = new Piece[][]{
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, RedP, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, RedP, null, null, null, null},
                {null, null, WhtP, null, null, null, null, null}
        };

        cpNoRed = new Piece[][]{
                {null, null, null, null, null, null, null, null},
                {null, WhtP, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, WhtP, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
        };

        cpNoWhite = new Piece[][]{
                {null, null, null, null, null, null, null, null},
                {null, null, RedP, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, RedP, null, null},
                {null, null, null, null, null, null, null, null},
                {null, RedP, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
        };

        board = new Board(customPieces);
        CuT = new Game(redPlayer, whitePlayer, board);
    }

    /**
     * Tests the isRedPlayer method
     */
    @Test
    public void testIsRedPlayer() {

        assertFalse(CuT.isRedPlayer(whitePlayer), "" +
                "When passing in white player, this should return false!");
        assertTrue(CuT.isRedPlayer(redPlayer));
    }

    /**
     * Tests the getRedPlayer method
     */
    @Test
    public void testGetRedPlayer() {
        assertEquals(redPlayer, CuT.getRedPlayer(), "" +
                "getRedPlayer does not return the correct object!");
    }

    /**
     * Tests the getWhitePlayer method
     */
    @Test
    public void testGetWhitePlayer() {
        assertEquals(whitePlayer, CuT.getWhitePlayer(), "" +
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
        CuT = new Game(redPlayer, whitePlayer);
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
        assertTrue(CuT.isActivePlayer(redPlayer));
        assertFalse(CuT.isActivePlayer(whitePlayer));
        CuT.switchActiveColor();
        assertTrue(CuT.isActivePlayer(whitePlayer));
        assertFalse(CuT.isActivePlayer(redPlayer));
    }

    /**
     * Tests the ValidateMove and makeMove method with single pieces.
     */
    @Test
    public void testValidateMove(){
        Piece[][] customPiecesTestValidateMove = new Piece[][]{
                {null, RedP, null, null, null, null, null, null},
                {null, null, RedP, null, null, null, RedP, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, WhtP, null, null, null, null, null, null},
                {null, null, null, null, null, WhtP, null, null},
                {null, WhtP, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
        };

        //Setup test
        board = new Board(customPiecesTestValidateMove);
        CuT = new Game(redPlayer, whitePlayer, board);

        //Make red player active
        if(CuT.getActiveColor() == Color.WHITE) {
            CuT.switchActiveColor();
        }

        //Test red single moving piece, moving down and right and active color RED(Valid)
        Move validRedMove = new Move(new Position(1,2), new Position(2,3));
        assertTrue(CuT.validateMove(validRedMove.flipMove()));

        //Test red single moving piece, moving up and right and active color RED(Invalid)
        CuT = new Game(redPlayer, whitePlayer, board);
        Move invalidRedMove01 = new Move(new Position(1, 6), new Position(0, 7));
        assertFalse(CuT.validateMove(invalidRedMove01.flipMove()));

        //Test red single moving piece, moving down and right and active color White(Invalid)
        CuT.switchActiveColor();
        Move invalidRedMove02 = new Move(new Position(0, 1), new Position(1, 0));
        assertFalse(CuT.validateMove(invalidRedMove02));

        //Test white single moving piece, moving up and right and active color WHITE(Valid)
        CuT = new Game(redPlayer, whitePlayer, board);
        CuT.switchActiveColor();
        Move validWhiteMove = new Move(new Position(4,1), new Position(3,2));
        assertTrue(CuT.validateMove(validWhiteMove));

        //Test white single moving piece, moving down and left and active color WHITE(Invalid)
        CuT = new Game(redPlayer, whitePlayer, board);
        CuT.switchActiveColor();
        Move invalidWhiteMove01 = new Move(new Position(6, 1), new Position(7, 0));
        assertFalse(CuT.validateMove(invalidWhiteMove01));

        //Test white single moving piece, moving up and right and active color RED(Invalid)
        CuT.switchActiveColor();
        Move invalidWhiteMove02 = new Move(new Position(5, 5), new Position(4, 6));
        assertFalse(CuT.validateMove(invalidWhiteMove02.flipMove()));
    }

    /**
     * Test for backUpMove
     */
    @Test
    public void testBackUpMove(){
        Piece[][] customPieces = new Piece[][]{
                {null, null, RedP, null, null, null, null, RedP},
                {null, WhtP, null, null, null, null, WhtP, null},
                {null, null, null, null, null, WhtP, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, WhtP, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
        };

        //Setup test
        board = new Board(customPieces);
        CuT = new Game(redPlayer, whitePlayer, board);

        //Test invalid backup move due to empty list of last moves
        assertFalse(CuT.backUpMove());

        //Perform red jump move
        Move validRedMove = new Move(new Position(0, 2), new Position(2, 0));
        CuT.validateMove(validRedMove.flipMove());

        //BackUp red jump move
        assertTrue(CuT.backUpMove());



    }

    /**
     * Test for checking if the player is the winner.
     */
    @Test
    public void testIsWinner() {
        assertFalse(CuT.isWinner(whitePlayer));
        CuT.resignGame(redPlayer);
        assertTrue(CuT.isWinner(whitePlayer));
    }

    /**
     * Test for resignGame
     */
    @Test
    public void testResignGame(){
        //Setup test
        board = new Board(customPiecesTestValidateMove);
        CuT = new Game(redPlayer, whitePlayer, board);

        //Red Player resigns
        CuT.resignGame(redPlayer);

        //Winner should be white player
        assertTrue(CuT.isWinner(whitePlayer));
    }

    /**
     * Test for isGameOver
     */
    @Test
    public void testIsGameOver(){
        //Setup test
        board = new Board(customPieces);
        CuT = new Game(redPlayer, whitePlayer, board);

        //Test game is not over
        assertFalse(CuT.isGameOver());

        //Test game is over by resignation
        //Red player resigns
        CuT.resignGame(redPlayer);
        assertTrue(CuT.isGameOver());

        //Test game is over by all pieces eliminated
        //Red has no more pieces
        board = new Board(cpNoRed);
        CuT = new Game(redPlayer, whitePlayer, board);
        assertTrue(CuT.isGameOver());
        //White has no more pieces
        board = new Board(cpNoWhite);
        CuT = new Game(redPlayer, whitePlayer, board);
        //White to move
        CuT.switchActiveColor();
        assertTrue(CuT.isGameOver());
    }

    /**
     * Test that the resign flag changes
     */
    @Test
    public void testDidPlayerResign(){
        assertFalse(CuT.didPlayerResign());
        CuT.resignGame(whitePlayer);
        assertTrue(CuT.didPlayerResign());
    }

    /**
     * Test for submitTurn()
     */
    @Test
    public void testSubmitTurn(){
        //Setup test
        board = new Board(customPieces);
        CuT = new Game(redPlayer, whitePlayer, board);

        //Test invalid submit turn when no move is made
        assertFalse(CuT.submitTurn());

        //Perform red jump move
        CuT.validateMove(new Move(new Position(0, 2), new Position(2, 0)).flipMove());

        //Test submit red jump move
        assertTrue(CuT.submitTurn());
    }

    /**
     * Test for getBoardView
     */
    @Test
    public void testGetBoardView(){
        //Expected pieces contained in white BoardView
        Piece[][] piecesWhiteGameStart = new Piece[][]{
                {null, RedP, null, RedP, null, RedP, null, RedP},
                {RedP, null, RedP, null, RedP, null, RedP, null},
                {null, RedP, null, RedP, null, RedP, null, RedP},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {WhtP, null, WhtP, null, WhtP, null, WhtP, null},
                {null, WhtP, null, WhtP, null, WhtP, null, WhtP},
                {WhtP, null, WhtP, null, WhtP, null, WhtP, null}
        };

        //Expected pieces contained in red BoardView
        Piece[][] piecesRedGameStart = new Piece[][]{
                {null, WhtP, null, WhtP, null, WhtP, null, WhtP},
                {WhtP, null, WhtP, null, WhtP, null, WhtP, null},
                {null, WhtP, null, WhtP, null, WhtP, null, WhtP},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {RedP, null, RedP, null, RedP, null, RedP, null},
                {null, RedP, null, RedP, null, RedP, null, RedP},
                {RedP, null, RedP, null, RedP, null, RedP, null}
        };

        //Setup test for white Board View
        board = new Board(piecesWhiteGameStart);
        CuT = new Game(redPlayer, whitePlayer, board);

        //Create expected white boardView
        BoardView expectedWhiteBoardView = board.getBoardView();
        //Test that expected board view is the same as actual white board view
        assertEquals(expectedWhiteBoardView,CuT.getBoardView(whitePlayer));

        //Update board for red Board View
        board = new Board(piecesRedGameStart);
        //Create expected red boardView
        BoardView expectedRedBoardView = board.getBoardView();
        //Test that expected board view is the same as actual red board view
        assertEquals(expectedRedBoardView, CuT.getBoardView(redPlayer));
    }

    /**
     * Test for the jumpMoveExists method.
     */
    @Test
    public void testJumpMoveExists() {
        //assertTrue(CuT.jumpMoveExists());
        board = new Board(multipleJumpMove1);
        CuT = new Game(redPlayer, whitePlayer, board);
        assertFalse(CuT.jumpMoveExists());
        //Switch active color to RED
        CuT.switchActiveColor();
        assertTrue(CuT.jumpMoveExists());

        Piece[][] pieces = new Piece[][]{
                {null, RedP, null, RedP, null, null, null, null},
                {null, null, null, null, RedP, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, RedP, null, null, null},
                {null, null, null, null, null, RedP, null, null},
                {null, null, RedP, null, null, null, null, null},
                {null, WhtP, null, null, null, WhtP, null, WhtP},
                {null, null, null, null, null, null, WhtP, null},
        };
        board = new Board(pieces);
        CuT = new Game(redPlayer, whitePlayer, board);
        assertTrue(CuT.jumpMoveExists());

    }


}