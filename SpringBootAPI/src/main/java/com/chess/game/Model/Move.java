package com.chess.game.Model;

import com.chess.game.Model.Pieces.Piece;

public class Move {
  private final Piece piece;
  private Piece pieceCaptured;
  private final Position start;
  private final Position end;

  public Move(Piece piece, Position start, Position end) {
    this.piece = piece;
    this.start = start;
    this.end = end;
  }

  public Move(Piece piece, Position start, Position end, Piece pieceCaptured) {
    this(piece, start, end);
    this.pieceCaptured = pieceCaptured;
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

  public boolean isCapture() {
    return pieceCaptured != null;
  }

  @Override
  public String toString() {
    return piece + ": " + start + " to " + end;
  }



}
