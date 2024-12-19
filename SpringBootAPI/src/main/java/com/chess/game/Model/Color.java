package com.chess.game.Model;

/**
 * This represents the color of a chess piece.
 */
public enum Color {
  BLACK,
  WHITE;

  public Color opposing() {
    return (this == WHITE) ? BLACK : WHITE;
  }



  @Override
  public String toString() {
    return (this == WHITE) ? "white" : "black";
  }
}
