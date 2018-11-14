package com.webcheckers.model;

import java.util.List;

/**
 * This class finds all valid moves
 * that the AI Player can make on the board
 *
 * @author Matthew Bollinger
 */
public class MoveBrain {

    private Piece[][] pieces;

    public MoveBrain(Board board){
        this.pieces = board.getPieces();
    }

    /**
     * this method will go over all of the
     * AI's pieces and add the position
     * of any piece that has a valid move to be made.
     *
     * If there is a jump move then the list will only return jump moves
     * @return a list of piece positions
     * for the ai player
     */
    public List<Position> getMoveableAIPieces(){
        return null;
    }

    /**
     * this is a helper function for getMoveableAIPieces
     * and will return a list of positions who's pieces
     * have a valid jump move
     * @return
     */
    private List<Position> getAIJumpMoves(){
        return null;
    }

    /**
     * this is a helper function for getMoveableAIPieces
     * that goes over all AI pieces and returns a list of
     * piece positions that have valid normal moves
     * @return
     */
    private List<Position> getAINormalMoves(){
        return null;
    }

}
