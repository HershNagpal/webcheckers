package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * represents the board broken down visually into rows. This class is the physical structure of the board
 * and holds all of the subunits (rows, spaces, and pieces).
 * @author Luis Gutierrez, Christopher Daukshus
 */
public class BoardView {

    private List<Row> rows;

    //
    // Constructor
    //
    public BoardView(Piece[][] pieces){
        rows = new ArrayList<>();
        int numOfRows = pieces.length;
        for(int rowNum = 0; rowNum < numOfRows; rowNum++){
            rows.add(new Row(pieces[rowNum], rowNum));
        }
    }

    /**
     * Creates an iterable for the Rows on the Board for the freemarker template to work with.
     * @return an iterable for all the Rows on the board.
     */
    public Iterator<Row> iterator(){
        return rows.iterator();
    }

    /**
     * Overriding equals() for deep equality between BoardView Objects.
     * This method is used for testing.
     * @param obj Object being compared to "this" Piece
     * @return true if "this" is equal to obj
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        else if(!(obj instanceof BoardView)){
            return false;
        }

        BoardView bv2 = (BoardView)obj;

        return this.rows.equals(bv2.rows);
    }
}
