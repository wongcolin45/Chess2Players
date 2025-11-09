package com.chess.game.Model;

import com.chess.game.Model.Pieces.Piece;
import java.util.Objects;

/**
 * Stores data for a move
 * State should be immutable
 */
public class Move {
  private final Piece piece;
  private Piece pieceCaptured;
  private final Position start;
  private final Position end;

  public Move(Piece piece, Position start, Position end) {
    this.piece = Objects.requireNonNull(piece, "piece cannot be null");
    this.start = Objects.requireNonNull(start, "start position cannot be null");
    this.end = Objects.requireNonNull(end, "end position cannot be null");
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

  public Move copy() {
    return new Move(piece, start, end, pieceCaptured);
  }

}
