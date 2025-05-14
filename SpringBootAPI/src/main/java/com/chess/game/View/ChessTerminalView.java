package com.chess.game.View;

import com.chess.game.Model.Board.ViewableBoard;
import com.chess.game.Model.Color;
import com.chess.game.Model.Game.ViewableGame;

public class ChessTerminalView implements ChessView {

  private final ViewableGame game;

  private int iter;

  private int start;

  private final String ranks = "a     b     c     d     e     f     g     h";
  private final String reverseRanks = "h     g     f     e     d     c     b     a";

  public ChessTerminalView(ViewableGame game) {
    this.game = game;
    this.iter = 1;
    this.start = 0;
  }



  @Override
  public void render() {
    System.out.println(this);
  }

  @Override
  public String toString() {
    ViewableBoard board = game.getViewableBoard();
    String[][] grid = board.getTextGrid();
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
    if (game.getTurn() == Color.WHITE) {
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
