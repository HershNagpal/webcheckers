package com.webcheckers.model;

public class Space {

    /**
     * KING_RED = Spaces where Red checkers become king pieces.
     * KING_BLACK = Spaces where Black checkers become king pieces.
     */
    public enum SpaceType {KING_RED, KING_BLACK, NON_KING}

    private int row;
    private int column;
    private SpaceType thisSpaceType;

    //Space has no piece before setUpBoard() in Board
    private Piece piece = null;

    public Space(int row, int column){
        this.row = row;
        this.column = column;
        if(row == 0){
            thisSpaceType = SpaceType.KING_BLACK;
        }
        else if (row == 7){
            thisSpaceType = SpaceType.KING_RED;
        }
        else{
            thisSpaceType = SpaceType.NON_KING;
        }
    }

    /**
     * Checks the color of the space. Returns false for dark square and true for light square.
     *
     * @return indicator of color in boolean
     */
    public boolean checkColor(){
        if(row%2==0) {
            return column % 2 == 0;
        }
        else{
            return column % 2 == 1;
        }
    }

    public boolean hasChecker(){
        return this.piece != null;
    }

    public Piece getPiece(){
        return piece;
    }

    public void setPiece(Piece piece){
        this.piece = piece;
    }
}