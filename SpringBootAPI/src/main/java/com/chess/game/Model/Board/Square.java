package com.chess.game.Model.Board;

import com.chess.game.Model.Pieces.Piece;
import com.chess.game.Model.Position;

public class Square {

  private Piece piece;
  private final Position pos;
  private boolean occupied;

  public Square(Position pos) {
    this.pos = pos;
    occupied = false;
  }


  public boolean isOccupied() {
    return occupied;
  }


  public Position getPosition() {
    return pos;
  }


  public Piece getPiece() {
    return piece;
  }


  public void setPiece(Piece piece) {
    if (piece == null) {
      throw new IllegalArgumentException("Piece cannot be null");
    }
    this.piece = piece;
    occupied = true;
  }


  public void clear() {
    piece = null;
    occupied = false;
  }


  public Square getCopy() {
    Square copy = new Square(pos);
    if (isOccupied()) {
      copy.setPiece(piece);
    }
    return copy;
  }

  public String toString() {
    if (occupied) {
      return piece.toString();

    }
    return " ";
  }

}
