package com.webcheckers.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This class finds all valid moves
 * that the AI Player can make on the board
 *
 * @author Matthew Bollinger
 */
public class MoveBrain extends Game {


    public MoveBrain(Player redPlayer, Player whitePlayer, int gameNum) {
        super(redPlayer, whitePlayer, gameNum);
    }

    /**
     * Submit the red player's last made move. Once the submitted turn is
     * successful, the AI's turn is simulated and the red player begins
     * their turn again.
     * @return True or false depending on if the move was made
     */
    @Override
    public boolean submitTurn() {
        if (lastMove == null) {
            return false;
        }

        //Enforce player ending a multiple jump move
        Position lastMoveEndPos = lastMove.getEnd();
        //Multiple jump move has not been completed
        if (isLastMoveJump(lastMove) && getJumpLocations(lastMoveEndPos).size() > 0) {
            return false;
        }

        //reset lastMoves and lastMove
        lastMoves.clear();
        lastMove = null;
        // Do not switch the turn if the game is over
        // Otherwise invokes PostCheckTurn when not your turn when game is over
        if (!isGameOver()) {
            switchActiveColor();
            // Simulate AI turn
            if (isActivePlayer(getWhitePlayer())) {
                simulateTurn();
                switchActiveColor();
            }
        }
        gameChanged = true;
        this.canContinueMoving = true;
        return true;
    }

    /**
     * Simulate the AI's turn.
     */
    public void simulateTurn() {
        Move move = generateAIMove();
        makeMove(move);
        submitTurn();
    }

    /**
     * this method will use a list of valid
     * moves from getAIMoves and choose a
     * random move from the list to return
     *
     * @return A valid AI Move
     */
    public Move generateAIMove(){
        List<Move> AIMoves = getAIMoves();
        int size = AIMoves.size();
        int rand = getRandomInt(0, size-1);
        return AIMoves.get(rand);
    }

    /**
     * this method will return a list of valid moves for
     * the AI to make. If there is a valid jump move then
     * the list will only contain jump moves
     *
     *@return a list of valid AIMoves.
     */
    public List<Move> getAIMoves(){
        List<Move> AIMoves = new ArrayList<>();
        List<Position> AIPieces = this.getMovablePieceLocations();
        Boolean jumpMoveFound = false;
        for(Position position: AIPieces) {
            Piece piece = this.getBoard().getPieceAtPosition(position);
            if(piece.getColor() == Color.WHITE) {
                //first check for jump positions
                List<Position> pieceJumpPositions = this.getJumpLocations(position);
                if (pieceJumpPositions.size() != 0) {
                    for (Position jumpTargetPosition : pieceJumpPositions) {
                        Move move = new Move(position, jumpTargetPosition);
                        if (isJumpMove(move)) {
                            Piece endPiece = this.getBoard().getPieceAtPosition(move.getEnd());
                            if (endPiece == null)
                            AIMoves.add(move);
                            jumpMoveFound = true;
                        }
                    }
                }
            }
        }
        //if there are no jump moves found then make a list of simple moves
        if(!(jumpMoveFound)){
            //iterate over AIPieces
            for (Position position: AIPieces) {
                Piece piece = this.getBoard().getPieceAtPosition(position);
                if(piece.getColor() == Color.WHITE) {
                    List<Position> validPositionList;
                    validPositionList = this.getBoard().getValidNormalMovePositions(position);
                    for (Position end : validPositionList) {
                        Move move = new Move(position, end);
                        if (isNormalMove(move)) {
                            Piece endPiece = this.getBoard().getPieceAtPosition(move.getEnd());
                            if (endPiece == null)
                            AIMoves.add(move);
                        }
                    }
                }
            }
        }
        return AIMoves;
    }


    /**
     * this method will generate a random int from a given range
     *
     * @param min the min value in desired range
     * @param max the max value in desired range
     * @return a random int from given range
     */
    public int getRandomInt(double min, double max){
        int x = (int) ((Math.random()*((max-min)+1))+min);
        return x;
    }

}
