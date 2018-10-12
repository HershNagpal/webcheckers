package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Row data type that contains spaces
 *
 * @author Michael Kha, Christopher Daukshus
 */
public class Row {

    private int index;
    private List<Space> spaces;

    public Row(Piece[] pieces, int index) {
        this.index = index;
        spaces = new ArrayList<>();
        for (int column = 0; column < 8; column++) {
            spaces.add(new Space(index, column, pieces[column]));
        }
    }

    public int getIndex() {
        return index;
    }

    public Iterator<Space> iterator() {
        return spaces.iterator();
    }

}
