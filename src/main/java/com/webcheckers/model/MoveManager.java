package com.webcheckers.model;

/**
 * Class holding all move logic done in Game.
 * @author Luis Gutierrez
 * @author Hersh Nagpal
 */
public class MoveManager {

    /**
     * @TODO Make this compatible with backup moves.
     * Returns whether or not the given move is valid according to checkers rules.
     * @param move The move to be checked for validity.
     * @return True if the given move is valid, false otherwise.
     */
    public static boolean isValidMove(Move move, Board board) {

        // If there is no piece being moved, return false.
        if(board.getPieceAtPosition(move.getStart()) == null) {
            return false;
        }
        // Destination must be empty
        if(board.getPieceAtPosition(move.getEnd()) !=  null) {
            return false;
        }

        if(isSingleMove(move, board)) {
            return true;
        } 
        else if (isJumpMove(move, board)) {
            return true;
        }
        return false;
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
     * @param pieceType The type of the piece making the move (King or Single).
     * @return true if the move is a valid normal, non-jump move, false if it is invalid or not a normal move.
     */
    public static boolean isSingleMove(Move move, Board board) {
        Position startPosition = move.getStart();
        Position endPosition = move.getEnd();

        Piece movingPiece = board.getPieceAtPosition(startPosition);
        Color pieceColor = movingPiece.getColor();

        // Positions must be diagonal adjacent
        if(!startPosition.isDiagonalAdjacentTo(endPosition)) {
            return false;
        }

        // Must either be a King move or moving away from its side.
        if(!isKingMove(move, movingPiece)) {
            if(move.isFacingRed() && pieceColor == Color.RED) {
                return false;
            } else if(!move.isFacingRed() && pieceColor == Color.WHITE) {
                return false;
            }
        }

        return true;
    }

    /**
     * @TODO Implement this.
     * Checks if the given Move is a valid jump move
     * @param move The Move object that the player is making.
     * @return true if the move is a valid jump move, false if it is invalid or not a jump move.
     */
    public static boolean isJumpMove(Move move, Board board) {
        /*
        int row1 = move.getStart().getRow();
        int col1 = move.getStart().getCell();

        int row2 = move.getEnd().getRow();
        int col2 = move.getEnd().getCell();
        Piece middlePiece;

        boolean isJumpMove = false;
        // The piece must either be Red or a King to move towards the bottom of the board.
        if(movingPiece.getColor() == Color.RED || movingPiece.getType() == Type.KING) {
            // The move must be two down and two to the right or..
            if(checkDistance(row2,row1,2) && checkDistance(col2,col1,2)) {
                middlePiece = board.getPieceAtPosition(new Position(row1+1,col1+1));
                // There must also be a piece of the opposite color in between the start and end.
                if(middlePiece != null && middlePiece.getColor() != movingPiece.getColor()) {
                    isJumpMove = true;
                }
            }
            // The move must be two down and two to the left
            else if(checkDistance(row2,row1,2) && checkDistance(col2,col1,-2)) {
                middlePiece = board.getPieceAtPosition(new Position(row1+1,col1-1));
                // There must also be a piece of the opposite color in between the start and end.
                if(middlePiece != null && middlePiece.getColor() != movingPiece.getColor()) {
                    isJumpMove = true;
                }
            }
        }
        // The piece must either be White or a King to move to the top of the board
        if(movingPiece.getColor() == Color.WHITE || movingPiece.getType() == Type.KING) {
            // The move must be two up and two to the left
            if(checkDistance(row2,row1,-2) && checkDistance(col2,col1,2)) {
                middlePiece = board.getPieceAtPosition(new Position(row1-1,col1+1));
                // There must also be a piece of the opposite color in between the start and end.
                if(middlePiece != null && middlePiece.getColor() != movingPiece.getColor()) {
                    isJumpMove = true;
                }
            }
            // The move must be two up and two to the left
            else if(checkDistance(row2,row1,-2) && checkDistance(col2,col1,-2)) {
                middlePiece = board.getPieceAtPosition(new Position(row1-1,col1-1));
                // There must also be a piece of the opposite color in between the start and end.
                if(middlePiece != null && middlePiece.getColor() != movingPiece.getColor()) {
                    isJumpMove = true;
                }
            }
        }
        return isJumpMove;
        */
        return false;
    }



    /**
     * @TODO Implement this.
     * Checks if the given Move is a valid jump move
     * @param move The Move object that the player is making.
     * @return true if the move is a valid jump move, false if it is invalid or not a jump move.
     */
    public static boolean isLastMoveJump(Move move, Piece movingPiece) {
        /*
        Position startPosition = move.getStart();
        Position endPosition = move.getEnd();

        boolean isJumpMove = false;

        // The piece must either be Red or a King to move towards the bottom of the board.
        if (movingPiece.getColor() == Color.RED || movingPiece.getType() == Piece.Type.KING) {
            // The move must be two down and two to the right or..
            if (checkDistance(row2, row1, 2) && checkDistance(col2, col1, 2)) {
                isJumpMove = true;
            }
            // The move must be two down and two to the left
            else if (checkDistance(row2, row1, 2) && checkDistance(col2, col1, -2)) {
                isJumpMove = true;
            }
        }
        // The piece must either be White or a King to move to the top of the board
        if(movingPiece.getColor() == Color.WHITE || movingPiece.getType() == Piece.Type.KING) {
            // The move must be two up and two to the left
            if(checkDistance(row2,row1,-2) && checkDistance(col2,col1,2)) {
                isJumpMove = true;
            }
            // The move must be two up and two to the left
            else if(checkDistance(row2,row1,-2) && checkDistance(col2,col1,-2)) {
                isJumpMove = true;
            }
        }
        return isJumpMove;
        */
        return false;
    }
}
