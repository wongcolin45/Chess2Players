package com.chess.game.View;

import com.chess.game.Model.Board.ViewableBoard;

import java.util.List;

import com.chess.game.Model.Color;
import com.chess.game.Model.Position;

public class ChessTerminalView implements ChessView {

  private final ViewableBoard board;

  private int iter;

  private int start;

  private final String ranks = "a     b     c     d     e     f     g     h";
  private final String reverseRanks = "h     g     f     e     d     c     b     a";

  public ChessTerminalView(ViewableBoard board) {
    this.board = board;
    this.iter = 1;
    this.start = 0;
  }

  private void addPossibleMoves(String[][] grid) {
    try {
      List<Position> moves = board.getPossibleMoves();
      for (Position p : moves) {
        grid[p.getRow()][p.getCol()] = "◦";
      }
    } catch (Exception ignored) {

    }
  }

  @Override
  public void render() {
    Color turn = board.getTurn();

    System.out.println(this.toString());
  }

  @Override
  public String toString() {
    String[][] grid = board.getTextGrid();
    addPossibleMoves(grid);
    StringBuilder result = new StringBuilder();
    int file = 8 - start;

    result.append("   ╔═════╦═════╦═════╦═════╦═════╦═════╦═════╦═════╗" + "\n");
    for (int r = start; r < 8 && r >= 0; r+=iter) {
      result.append(" " + file + " ");
      file -= iter;
      for (int c = start; c < 8 && c >= 0; c+=iter) {
        result.append("║  " + grid[r][c] + "  ");
      }
      result.append("║"+"\n");
      result.append("   ╠═════╬═════╬═════╬═════╬═════╬═════╬═════╬═════╣");
      result.append("\n");
    }
    result.setLength(result.length() - 53);
    result.append("   ╚═════╩═════╩═════╩═════╩═════╩═════╩═════╩═════╝");
    result.append("\n");

    result.append("      ");
    if (board.getTurn() == Color.WHITE) {
      result.append(ranks);
    } else {
      result.append(reverseRanks);
    }
    result.append("\n");
    // Stack Overflow error
    return result.toString();
  }

  @Override
  public void flip() {
    start = (start == 7) ? 0 : 7;
    iter *= -1;
  }
}
