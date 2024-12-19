package com.chess.game.Model.Pieces;

import com.chess.game.Model.Board.Board;

import java.util.ArrayList;
import java.util.List;

import com.chess.game.Model.Position;
import com.chess.game.Model.Color;

/**
 * This is the implementation of a knight.
 */
public class Knight extends AbstractPiece {

  private final int[] rowIters = {-1, -2, -2, -1, 1, 2, 2, 1};
  private final int[] colIters = {-2, -1, 1, 2, -2, -1, 1, 2};

  /**
   * Initializes the knights color.
   * @param color the color of the knight;
   */
  public Knight(Color color, String symbol) {
    super(color, symbol);

  }

  @Override
  public List<Position> getMoves(Board board, Position pos) {
    List<Position> moves = new ArrayList<>();
    int r = pos.getRow();
    int c = pos.getCol();
    for (int i = 0; i < 8; i++) {
      checkValidMove(board, moves, pos, r + rowIters[i], c + colIters[i]);
    }
    return moves;
  }

  @Override
  public PieceType getType() {
    return PieceType.KNIGHT;
  }


}
