package com.chess.game.Model.Pieces;
import com.chess.game.Model.Color;


/**
 * This is the implementation of a rook.
 */
public class Rook extends AbstractStraightPiece {

  /**
   * Initializes the color and sends over iters values for up, down, left right.
   * @param color the rook's color
   */
  public Rook(Color color, String symbol) {
    super(color, symbol,
          new int[]{-1, 1, 0, 0},
          new int[]{0, 0, -1, 1});
  }

  @Override
  public PieceType getType() {
    return PieceType.ROOK;
  }

}
