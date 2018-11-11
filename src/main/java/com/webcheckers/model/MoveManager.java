package com.webcheckers.model;

/**
 * Class holding all move logic done in Game.
 */
public class MoveManager {

    /**
     * Checks if the given Move is a valid normal, non-jump move.
     * @param move The Move object that the player is making
     * @return true if the move is a valid normal, non-jump move, false if it is invalid or not a normal move.
     */
    public static boolean isNormalMove(Move move, Piece movingPiece) {
        int row1 = move.getStart().getRow();
        int col1 = move.getStart().getCell();

        int row2 = move.getEnd().getRow();
        int col2 = move.getEnd().getCell();

        boolean isSingleMove = false;
        // Red pieces move to higher rows, White pieces move to lower rows. Kings can do both.
        if(movingPiece.getColor() == Color.RED || movingPiece.getType() == Piece.Type.KING) {
            if(checkDistance(row2,row1,1) && checkDistance(col2,col1,1)) {
                isSingleMove = true;
            }
            else if(checkDistance(row2,row1,1) && checkDistance(col2,col1,-1)) {
                isSingleMove = true;
            }
        }
        if(movingPiece.getColor() == Color.WHITE || movingPiece.getType() == Piece.Type.KING) {
            if(checkDistance(row2,row1,-1) && checkDistance(col2,col1,1)) {
                isSingleMove = true;
            }
            else if(checkDistance(row2,row1,-1) && checkDistance(col2,col1,-1)) {
                isSingleMove = true;
            }
        }

        return isSingleMove;
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
