package com.chess.game.Model.Board;

import com.chess.game.Model.Move;
import com.chess.game.Model.Pieces.Piece;
import com.chess.game.Model.Pieces.PieceType;
import com.chess.game.Model.Position;

/**
 * This represents a chess board.
 */
public interface Board extends ViewableBoard {


  /**
   * Sets the pieces on the board
   */
  void setPieces();


  /**
   * Gets the piece at the position.
   * @param pos the position to check
   * @return the piece on that position
   * @throws IllegalStateException if there is no piece there
   */
  Piece getPiece(Position pos);

  /**
   * Sets the selected piece on the board.
   * @param pos the position of the piece
   * @throws IllegalArgumentException there is no piece at the position / it is an opposing players piece
   */
  void selectedPiece(Position pos);

  /**
   * Unselects the current piece.
   */
  void unselectedPiece();

  /**
   * This moves the piece to the given position.
   * @param pos the position to move too
   * @throws IllegalArgumentException if the position is not a valid move for the piece
   * @throws IllegalStateException if no piece has been selected
   */
  void movePiece(Position pos);

  void move(String start, String end);

  /**
   * Removes the piece at the given position
   * @param pos the position of the piece to remove
   * @throws IllegalArgumentException if there is no piece at the position
   */
  void removePiece(Position pos);

  /**
   * Places a piece on the board.
   * @param piece the piece to place
   * @param pos the pos
   */
  void placePiece(Piece piece, Position pos);

  /**
   * Takes back previous move made.
   */
  void undoMove();

  void promotePawn(PieceType type);

  Board getCopy(boolean check);






}
