package com.chess.api.dto;

import com.chess.game.Model.Position;

public class PositionDTO {

  private final int row;

  private final int col;

  public PositionDTO(int row, int col) {
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
