package com.chess.test.Model;

import com.chess.game.Model.Pieces.Piece;
import com.chess.game.Model.Position;

public class Move {
  private final Piece piece;
  private final Position start;
  private final Position end;

  public Move(Piece piece, Position start, Position end) {
    this.piece = piece;
    this.start = start;
    this.end = end;
  }

  public Piece getPiece() {
    return piece;
  }

  public Position getStart() {
    return start;
  }

  public Position getEnd() {
    return end;
  }

}
