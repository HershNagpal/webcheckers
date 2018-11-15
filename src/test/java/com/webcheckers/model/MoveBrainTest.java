package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

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


    Game game;
    Player player;
    Player AI;
    Board board;
    Piece[][] pieces;

    /**
     *Run before each test
     */
    @BeforeEach
    public void setup(){
        Piece[][] testBoard00 = new Piece[][]{
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null}
        };

        player = mock(Player.class);
        AI = mock(Player.class);
        board = new Board(pieces);
        game = new Game(player, AI, board);
        CuT = new MoveBrain(game);
    }

    /**
     * Test getAIMoves method
     */
    @Test
    public void testGetAIMoves(){

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
        //run test 5 times
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
