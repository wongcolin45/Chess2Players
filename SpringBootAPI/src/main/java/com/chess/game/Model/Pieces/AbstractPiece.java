package com.chess.game.Model.Pieces;

import java.util.List;

import com.chess.game.Model.Board.ViewableBoard;
import com.chess.game.Model.Color;
import com.chess.game.Model.Game.ViewableGame;
import com.chess.game.Model.Position;

public abstract class AbstractPiece implements Piece {

  protected final Color color;

  protected final String symbol;

  protected AbstractPiece(Color color, String symbol) {
    this.color = color;
    this.symbol = symbol;
  }

  protected boolean onBoard(int r, int c) {
    return 0 <= r && r <= 7 && 0 <= c && c <= 7;
  }

  protected void checkValidMove(ViewableBoard board, List<Position> moves, Position pos, int r, int c) {
    if (!onBoard(r, c)) {
      return;
    }

    Position check = new Position(r, c);

    boolean captureAvailable = !board.isEmpty(check) && board.getPiece(check).getColor() != color;
    if (board.isEmpty(check) || captureAvailable) {
      moves.add(check);
    }
  }

  @Override
  public abstract List<Position> getMoves(ViewableGame game, Position pos);

  @Override
  public Color getColor() {
    return this.color;
  }

  @Override
  public String toString() {
    return symbol;
  }


}
