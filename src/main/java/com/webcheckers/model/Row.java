package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Row data type that contains spaces
 *
 * @author Michael Kha
 */
public class Row {

    /**
     * The column location of this Row
     */
    private int index;

    /**
     * An list of spaces in this Row
     */
    private List<Space> spaces;

    /**
     * Initialize the row with the given array of pieces. The row is
     * located at the given row index.
     * 
     * @param pieces Array containing pieces and null
     * @param index Column location
     */
    public Row(Piece[] pieces, int index) {
        this.index = index;
        spaces = new ArrayList<>();
        for (int column = 0; column < 8; column++) {
            spaces.add(new Space(index, column, pieces[column]));
        }
    }

    /**
     * Returns the row index of this row.
     * 
     * @return the row index of this row.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Creates an iterable for the Spaces in this Row for the freemarker template to work with.
     * 
     * @return an iterable for all the Spaces in this Row.
     */
    public Iterator<Space> iterator() {
        return spaces.iterator();
    }

    /**
     * Overriding equals() for deep equality between Row Objects.
     * This method is used for testing.
     * 
     * @param obj Object being compared to "this" Piece
     * @return true if "this" is equal to obj
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        else if(!(obj instanceof Row)){
            return false;
        }

        Row row2 = (Row)obj;

        return this.index == row2.index && this.spaces.equals(row2.spaces);
    }

}