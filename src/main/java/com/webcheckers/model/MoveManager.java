package com.webcheckers.model;

/**
 * Class holding all move logic done in Game.
 */
public class MoveManager {

    public static boolean validateMove(Move move) {
        return false;
    }

    /**
     * Returns whether or not this move is a King move.
     * @return True if this is a King move, false otherwise.
     */
    public boolean isKingMove(Move move, Piece piece) {
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
    public static boolean isNormalMove(Move move, Piece movingPiece) {
        Position startPosition = move.getStart();
        Position endPosition = move.getEnd();
        Piece.Type pieceType = movingPiece.getType();
        Color pieceColor = movingPiece.getColor();
        boolean isSingleMove = false;

        // Red pieces move to higher rows, White pieces move to lower rows. Kings can do both.
        if(pieceColor == Color.RED || pieceType == Piece.Type.KING) {
            if(startPosition.isDiagonalAdjacentTo(endPosition)) {
                isSingleMove = true;
            }
            else if(startPosition.isDiagonalAdjacentTo(endPosition)) {
                isSingleMove = true;
            }
        }
        if(pieceColor == Color.WHITE || pieceType == Piece.Type.KING) {
            if(startPosition.isDiagonalAdjacentTo(endPosition)) {
                isSingleMove = true;
            }
            else if(startPosition.isDiagonalAdjacentTo(endPosition)) {
                isSingleMove = true;
            }
        }

        return isSingleMove;
    }

    /**
     * TODO Create helper function in MoveManager containing this method's move logic.
     * Checks if the given Move is a valid jump move
     * @param move The Move object that the player is making.
     * @return true if the move is a valid jump move, false if it is invalid or not a jump move.
     */
    public static boolean isJumpMove(Move move, Piece movingPiece) {
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
     * Checks if the given Move is a valid jump move
     * @param move The Move object that the player is making.
     * @return true if the move is a valid jump move, false if it is invalid or not a jump move.
     */
    public static boolean isLastMoveJump(Move move, Piece movingPiece) {
        int row1 = move.getStart().getRow();
        int col1 = move.getStart().getCell();

        int row2 = move.getEnd().getRow();
        int col2 = move.getEnd().getCell();

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
    }

   
/**
     * Returns whether or not the distance between the two values is the expected amount.
     * @param val1 col or row value to check distance with another value
     * @param val2 col or row value to check distance with another value.
     * @param expected expected difference between val2 and val1.
     * @return true if the distance between p1 and p2 is equal to the expected value.
     */
    public static boolean checkDistance(int val2, int val1, int expected){
        return (val2-val1) == expected;
    }

}
