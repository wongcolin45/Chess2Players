package com.chess.game.Model.Pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.game.Model.Board.ViewableBoard;
import com.chess.game.Model.Color;
import com.chess.game.Model.Game.ViewableGame;
import com.chess.game.Model.Position;

public abstract class AbstractStraightPiece extends AbstractPiece {

  private final int[] rowIters;
  private final int[] colIters;

  protected AbstractStraightPiece(Color color, String symbol, int[] rowIters, int [] colIters) {
    super(color, symbol);
    this.rowIters = rowIters;
    this.colIters = colIters;
  }

  @Override
  public List<Position> getMoves(ViewableGame game, Position pos) {
    ViewableBoard board = game.getViewableBoard();
    List<Position> moves = new ArrayList<>();
    int i = 0;
    int r = pos.getRow();
    int c = pos.getCol();
    while (i < 4) {
      r += rowIters[i];
      c += colIters[i];
      checkValidMove(board, moves, pos, r , c);
      if (!onBoard(r, c) || !board.isEmpty(new Position(r , c))) {
        i++;
        r = pos.getRow();
        c = pos.getCol();
      }
    }
    return moves;
  }




}
