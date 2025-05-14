package com.chess.game.Model.Game;

import com.chess.game.Model.Pieces.PieceType;
import com.chess.game.Model.Position;

public interface MutableGame extends ViewableGame {

  /**
   * Moves a piece
   * @param from the position of the piece to move
   * @param to the position to move the piece to
   */
  void movePiece(Position from, Position to);

  /**
   * Promotes the pawn
   * @param pieceType the type of the piece to promote pawn to
   * @throws IllegalStateException if there is no pawn to promote
   * @throws IllegalArgumentException if a piece type is pawn or king
   */
  void promotePawn(PieceType pieceType);
}
