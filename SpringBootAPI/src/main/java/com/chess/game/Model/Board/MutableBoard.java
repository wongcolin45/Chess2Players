package com.chess.game.Model.Board;

import com.chess.game.Model.Pieces.Piece;
import com.chess.game.Model.Position;

public interface MutableChessBoard extends ViewableChessBoard {

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
   * @return the removed Piece
   * @throws IllegalArgumentException if there is no piece at the position
   */
  Piece removePiece(Position pos);

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
