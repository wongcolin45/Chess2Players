package com.chess.game.Model;

import com.chess.game.Model.Pieces.Piece;

public class Move {
  private final Piece piece;
  private final Position start;
  private  final Position end;

  public Move(Piece piece, Position start, Position end) {
    this.piece = piece;
    this.start = start;
    this.end = end;
  }

  public Position getStart() {
    return start;
  }

  public Position getEnd() {
    return end;
  }

  public Piece getPiece() {
    return piece;
  }

  @Override
  public String toString() {
    return piece + ": " + start + " to " + end;
  }

}
