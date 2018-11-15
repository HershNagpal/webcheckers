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

    /**
     *
     */
    public List<Move> getAIMoves(){
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
        for(Position position: AIPieces){

        }
        return AIMoves;
    }

    //@TODO
    public List<Position> getvalidNormalMovePositions(Position centerPiece){
        List<Position> movePositions = new ArrayList<>();
        Position upperL =
                new Position(centerPiece.getRow()-1, centerPiece.getCell()-1);
        Position upperR =
                new Position(centerPiece.getRow()-1, centerPiece.getCell()+1);
        Position lowerL =
                new Position(centerPiece.getRow()+1, centerPiece.getCell()-1);
        Position lowerR =
                new Position(centerPiece.getRow()+1, centerPiece.getCell()+1);
        movePositions.add(upperL);
        movePositions.add(upperR);
        movePositions.add(lowerL);
        movePositions.add(lowerR);
        //remove invalid positions based on piece color and type
        if (AIColor.equals(Color.RED) && !(game.getBoard().getPieceAtPosition(centerPiece).getType().equals(Piece.Type.KING))){
            movePositions.remove(upperL);
            movePositions.remove(upperR);
        }
        if (AIColor.equals(Color.WHITE) && !(game.getBoard().getPieceAtPosition(centerPiece).getType().equals(Piece.Type.KING))) {
            movePositions.remove(lowerL);
            movePositions.remove(lowerR);
        }
        //remove positions that are off the board or occupied
        for(Position position: movePositions){
            if (position.getRow() >= Board.ROWS || position.getRow() < 0 ||
                    position.getCell() >= Board.COLUMNS || position.getCell() < 0){
                movePositions.remove(position);
            }
            else{
            }
        }

        return null;
    }

}
