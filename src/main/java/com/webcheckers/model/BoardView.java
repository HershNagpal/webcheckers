package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The board broken down visually into rows to meet the view
 * @author Luis Gutierrez, Christopher Daukshus
 */
public class BoardView {

    private List<Row> rows;

    public BoardView(Piece[][] pieces){
        rows = new ArrayList<>();
        int numOfRows = pieces.length;
        for(int rowNum = 0; rowNum < numOfRows; rowNum++){
            rows.add(new Row(pieces[rowNum], rowNum));
        }
    }

    public Iterator<Row> iterator(){
        return rows.iterator();
    }
}
