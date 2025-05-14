package com.chess.game.Model.Board;

import com.chess.game.Model.Color;
import com.chess.game.Model.Pieces.Piece;
import com.chess.game.Model.Position;

public interface ViewableBoard {

  /**
   * Checks if a square is empty.
   * @param pos the position to check
   * @return true if empty, otherwise false
   */
  public boolean isEmpty(Position pos);

  /**
   * Gets the piece at the given position.
   * @param pos the position of the piece
   * @return the piece
   * @throws IllegalArgumentException if there is no piece at the position
   */
  public Piece getPiece(Position pos);


  /**
   * Gets the text grid representation of the board.
   * @return the string 2d array representation
   */
  public String[][] getTextGrid();

  /**
   * Gets the text grid representation of the board.
   * @param color the color pov of the board
   * @return the string 2d representation
   */
  public String[][] getTextGrid(Color color);

  /**
   * Get a deep copy of the board.
   * @return the board
   */
  public Board getCopy();
}
