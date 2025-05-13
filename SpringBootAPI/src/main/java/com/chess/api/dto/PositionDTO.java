package com.chess.api.dto;

public class MoveDTO {

  private final int row;

  private final int col;

  public MoveDTO(int row, int col) {
    this.row = row;
    this.col = col;
  }
  public int getRow() {
    return row;
  }
  public int getCol() {
    return col;
  }

}
