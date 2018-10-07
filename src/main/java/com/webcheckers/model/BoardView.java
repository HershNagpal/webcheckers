package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BoardView {

  private List<Row> rows;

  public BoardView(){
    rows = new ArrayList<>();
    for(int rowIndex = 0; rowIndex < 8; rowIndex++){
      rows.add(new Row(rowIndex));
    }
  }

  public Iterator<Row> iterator(){ return rows.iterator();}
}
