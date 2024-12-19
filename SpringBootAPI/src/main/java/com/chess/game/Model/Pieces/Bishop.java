package com.chess.game.Model.Pieces;
import com.chess.game.Model.Color;


public class Bishop extends AbstractStraightPiece {

  public Bishop(Color color, String symbol) {
    super(color, symbol,
          new int[]{-1, -1, 1, 1},
          new int[]{-1, 1, -1, 1});
  }

  @Override
  public PieceType getType() {
    return PieceType.BISHOP;
  }

}
