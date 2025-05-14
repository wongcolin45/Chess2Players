package com.chess.game.Model.Pieces;

import java.util.List;

import com.chess.game.Model.Color;
import com.chess.game.Model.Game.ViewableGame;
import com.chess.game.Model.Position;

/**
 * This represents a piece the board.
 * It does one thing, given the board and its position get the pieces possible moves.
 */
public interface Piece {

  /**
   * This gets the list of all the pieces possible moves.
   * @param game the viewable game state
   * @param pos the position of the piece
   * @return the list of positions the piece can move to
   */
  List<Position> getMoves(ViewableGame game, Position pos);

  /**
   * Gets the color of the piece.
   * @return the color
   */
  Color getColor();

  /**
   * Gets the type of the piece.
   * @return the type
   */
  PieceType getType();


}
