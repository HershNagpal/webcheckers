package com.webcheckers.model;

/**
 * Class holding all move logic done in Game.
 * @author Luis Gutierrez
 * @author Hersh Nagpal
 */
public class MoveManager {

    /**
     * Returns whether or not the given move is valid according to checkers rules.
     * @param move The move to be checked for validity.
     * @return True if the given move is valid, false otherwise.
     */
    public static boolean isValidMove(Move move, Piece movingPiece) {

        // If there is no piece being moved, return false.
        if(movingPiece == null) {
            return false;
        }

        return isSingleMove(move, movingPiece) || isJumpMove(move, movingPiece);
    }

    /**
     * Returns whether or not this move involves a piece moving towards its own side.
     * @return True if this is a 'King' move, false otherwise.
     */
    public static boolean isKingMove(Move move, Piece piece) {
        Color color = piece.getColor();
        Piece.Type type = piece.getType();

        if(type != Piece.Type.KING) return false;

        if(color == Color.RED && move.isFacingRed()) {
            return true;
        } 
        else if (color == Color.WHITE && !move.isFacingRed()) {
            return true;
        }

        return false;
    }

    /**
     * Checks if the given Move is a valid normal, non-jump move.
     * @param move The Move object that the player is making
     * @return true if the move is a valid normal, non-jump move, false if it is invalid or not a normal move.
     */
    public static boolean isSingleMove(Move move, Piece movingPiece) {
        Position startPosition = move.getStart();
        Position endPosition = move.getEnd();

        Color pieceColor = movingPiece.getColor();

        // Positions must be diagonal adjacent
        if(!startPosition.isDiagonalAdjacentTo(endPosition)) {
            return false;
        }

        // Must either be a King move or moving away from its side.
        if(!isKingMove(move, movingPiece)) {
            if(move.isFacingRed() && pieceColor == Color.RED) {
                return false;
            }
            else if(!move.isFacingRed() && pieceColor == Color.WHITE) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the given Move is a valid jump move
     * @param move The Move object that the player is making.
     * @return true if the move is a valid jump move, false if it is invalid or not a jump move.
     */
    public static boolean isJumpMove(Move move, Board board) {
        Position startPosition = move.getStart();
        Position endPosition = move.getEnd();

        Piece movingPiece = board.getPieceAtPosition(startPosition);
        Piece centerPiece = board.getPieceAtPosition(move.getJumpedPosition());

        Color pieceColor = movingPiece.getColor();
        

        //Positions must be within jump move distance
        if(!startPosition.isDiagonalJumpTo(endPosition)){
            return false;
        }

        // Must either be a King move or moving away from its side.
        if(!isKingMove(move, movingPiece)) {
            if(move.isFacingRed() && pieceColor == Color.RED) {
                return false;
            }
            else if(!move.isFacingRed() && pieceColor == Color.WHITE) {
                return false;
            }
        }

        // Must be a piece of opposite color in the middle of the start and end positions.
        if(centerPiece == null || centerPiece.getColor() == movingPiece.getColor()) {
            return false;
        }

        return true;      
    }



    /**
     * Checks if the given Move is a valid jump move
     * @param move The Move object that the player is making.
     * @return true if the move is a valid jump move, false if it is invalid or not a jump move.
     */
    public static boolean isLastMoveJump(Move move, Piece movingPiece) {
        Position startPosition = move.getStart();
        Position endPosition = move.getEnd();

        Color pieceColor = movingPiece.getColor();

        //Positions must be within jump move distance
        if(!startPosition.isDiagonalJumpTo(endPosition)){
            return false;
        }

        // Must either be a King move or moving away from its side.
        if(!isKingMove(move, movingPiece)) {
            if(move.isFacingRed() && pieceColor == Color.RED) {
                return false;
            }
            else if(!move.isFacingRed() && pieceColor == Color.WHITE) {
                return false;
            }
        }

        return true;
    }
}
