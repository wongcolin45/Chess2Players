package com.chess.game.Model.Game;

import com.chess.game.Model.Pieces.Piece;
import com.chess.game.Model.Position;


/**
 * Interface for a Chess Game where rules can be broken.
 */
public interface SandboxChessGame extends MutableChessGame {

  /**
   * Make this move with no regard for rules.
   * @param start the start position
   * @param end the end position
   */
  void forceMove(Position start, Position end);

  /**
   * Clear out any piece on this square.
   * @param pos the position of the square
   */
  void clearSquare(Position pos);

  /**
   * Place the piece at the given position.
   * @param piece the piece to place
   * @param pos the position to put the piece
   */
  void placePiece(Piece piece, Position pos);

  /**
   * Clear the chess board of all pieces.
   */
  void clearBoard();
}
