package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Luis Gutierrez
 */
public class BoardView {

  private Board board;
  private Space[][] spaces;

  private List<Row> rows;

  public BoardView(Board board) {
    this.board = board;
    spaces = board.getSpaces();
    rows = board.getRows();
  }

  public Iterator<Row> iterator(){
    return rows.iterator();
  }
}
