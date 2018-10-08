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

    private int index;
    private List<Space> spaces;

    public Row(int index) {
        this.index = index;
        spaces = new ArrayList<>();
    }

    public void addSpace(Space space){spaces.add(space);}

    public int getIndex() {
        return index;
    }

    public Iterator<Space> iterator() {
        return spaces.iterator();
    }

}
