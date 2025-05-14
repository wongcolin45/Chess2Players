package com.chess.game.Model.Board;

import com.chess.game.Model.Pieces.Piece;
import com.chess.game.Model.Position;

public class BoardSquare implements Square {

  private Piece piece;
  private final Position pos;
  private boolean occupied;

  public BoardSquare(Position pos) {
    this.pos = pos;
    occupied = false;
  }

  @Override
  public boolean isOccupied() {
    return occupied;
  }

  @Override
  public Position getPosition() {
    return pos;
  }

  @Override
  public Piece getPiece() {
    return piece;
  }

  @Override
  public void setPiece(Piece piece) {
    if (piece == null) {
      throw new IllegalArgumentException("Piece cannot be null");
    }
    this.piece = piece;
    occupied = true;
  }

  @Override
  public void clear() {
    piece = null;
    occupied = false;
  }

  @Override
  public Square getCopy() {
    BoardSquare copy = new BoardSquare(pos);
    if (isOccupied()) {
      copy.setPiece(piece);
    }
    return copy;
  }

  @Override
  public String toString() {
    if (occupied) {
      return piece.toString();

    }
    return " ";
  }

}
