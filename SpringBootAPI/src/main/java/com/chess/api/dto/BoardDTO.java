package com.chess.api.dto;

public class ChessBoard {
  private final String[][] board;

  public ChessBoard(String[][] board) {
    this.board = board;
  }

  public String[][] getBoard() {
    return board;
  }
}
