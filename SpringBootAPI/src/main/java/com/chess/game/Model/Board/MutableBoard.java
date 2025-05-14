package com.chess.game.Model.Board;

import com.chess.game.Model.Pieces.Piece;
import com.chess.game.Model.Position;

public interface MutableBoard extends ViewableBoard {

  /**
   * Sets the pieces on the board.
   */
  void setPieces();

  /**
   * Places piece on the board
   * @param pos the position to put the piece.
   */
  void placePiece(Piece piece, Position pos);

  /**
   * Removes the piece at the given position
   * @param pos the position of the piece
   * @throws IllegalArgumentException if there is no piece at the position
   */
  void removePiece(Position pos);

  /**
   * Removes the piece at the given position and returns the piece
   * @param pos the position of the piece
   * @return the piece on that position
   * @throws IllegalArgumentException if there is no piece to
   */
  Piece grabPiece(Position pos);

  /**
   * Get the square object at given position.
   * @param pos the position of the square
   * @return the square
   */
  Square getSquare(Position pos);

  /**
   * Clears the board
   */
  void clear();









}
