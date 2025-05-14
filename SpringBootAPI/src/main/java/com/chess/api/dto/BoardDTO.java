package com.chess.api.dto;

public class BoardDTO {
  private final String[][] board;

  public BoardDTO(String[][] board) {
    this.board = board;
  }

  public String[][] getBoard() {
    return board;
  }
}
