package com.webcheckers.model;

import com.sun.media.sound.AiffFileReader;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.List;

/**
 * This class finds all valid moves
 * that the AI Player can make on the board
 *
 * @author Matthew Bollinger
 */
public class MoveBrain {

    private Piece[][] pieces;
    private Color AIColor;
    private Game game;

    public MoveBrain(Game game, Color AIColor){
        this.game = game;
        this.pieces = game.getBoard().getPieces();
        this.AIColor = AIColor;
    }

    public Move generateAIMove(){

        return null;
    }

    /**
     *
     */
    private List<Move> getAIMoves(){
        List<Move> AIMoves = new ArrayList<>();
        List<Position> AIPieces = game.getMovablePieceLocations();
        Boolean jumpMoveFound = false;

        for(Position position: AIPieces) {
            //first check for jump positions
            List<Position> pieceJumpPoistions = game.getJumpLocations(position);
            if (pieceJumpPoistions.size() != 0){
                for (Position jumpTargerPosition : pieceJumpPoistions) {
                    Move move = new Move(position, jumpTargerPosition);
                    AIMoves.add(move);
                    jumpMoveFound = true;
                }
            }
        }
        //if there are no jump moves found then make a list of simple moves
        if(!(jumpMoveFound)){
            //iterate over AIPieces
            for (Position position: AIPieces){
                List<Position> validPositionList;
                validPositionList = game.getBoard().getvalidNormalMovePositions(position);
                for (Position end: validPositionList){
                    Move move = new Move(position, end);
                    AIMoves.add(move);
                }
            }
        }
        return AIMoves;
    }

}
