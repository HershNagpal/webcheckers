package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

/**
 * this class contains test for
 * MoveBrain and all of its methods
 *
 * @author Matthew Bollinger
 */
@Tag("Model-tier")
public class MoveBrainTest {

    /**
     * Component under test
     */
    MoveBrain CuT;


    Piece red, white, rKP, wKP;
    Player player;
    Player AI;
    Board board;

    /**
     *Run before each test
     */
    @BeforeEach
    public void setup(){

        red = new Piece(Color.RED, Piece.Type.SINGLE);
        rKP = new Piece(Color.RED, Piece.Type.KING);
        white = new Piece(Color.RED, Piece.Type.SINGLE);
        wKP = new Piece(Color.RED, Piece.Type.KING);

        Piece[][] testBoard00 = new Piece[][]{
            {null, red, null, null, null, null, null, null},
            {wKP, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, rKP},
            {white, null, null, null, null, null, null, null}
        };

        player = new Player("player");
        AI = new Player("AI");
        board = new Board(testBoard00);
        CuT = new MoveBrain(player, AI, 0);
        CuT.setBoard(board);
    }

    /**
     * Test getAIMoves method
     */
    @Test
    public void testGetAIMoves() {
        //red is active player first
        Move move0 = new Move(new Position(0, 1), new Position(1,2));
        Move move1 = new Move(new Position(6, 7), new Position(7,6));
        Move move2 = new Move(new Position(6, 7), new Position(5,6));
        List<Move> expectedMoves = new ArrayList<>();
        List<Move> actualMoves;
        expectedMoves.add(move0);
        expectedMoves.add(move1);
        expectedMoves.add(move2);
        actualMoves = CuT.getAIMoves();
        int total = 3;

        for(int i = 0; i < actualMoves.size(); i++){
            Move actual = actualMoves.get(i);
            for (int x = 0; x < expectedMoves.size(); x++){
                Move expected = expectedMoves.get(x);
                if(expected.equals(actual))
                    total--;
            }
        }
        System.out.println(total);
        assert(total == 0);
    }

    /**
     * Test generateAIMove method
     */
    @Test
    public void testGenerateAIMove(){

    }

    /**
     * Test getRandomInt method
     */
    @Test
    public void testGetRandomInt(){
        int max = 10;
        int min = 0;
        //run test 10 times
        for(int i=0; i<10; i++) {
            int test = CuT.getRandomInt(min, max);
            assert (test >= min && test <= max);
        }
        //test for if range is 0-0
        max = 0;
        min = 0;
        int test = CuT.getRandomInt(min, max);
        assert(test == 0);
    }
}
