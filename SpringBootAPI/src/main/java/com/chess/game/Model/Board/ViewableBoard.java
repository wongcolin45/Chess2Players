package com.chess.game.Model.Board;

import java.util.List;

import com.chess.game.Model.Color;
import com.chess.game.Model.GameResult;
import com.chess.game.Model.Move;
import com.chess.game.Model.Position;

public interface ViewableBoard {


  /**
   * Gets the current turn of the player.
   * @return the color turn
   */
  Color getTurn();

  /**
   * Checks is position is empty;
   * @param pos the position to check
   * @return true if empty, otherwise false
   */
  boolean isEmpty(Position pos);


  /**
   * Check if the king is in check.
   * @param color the color of the king
   * @return true if the king is in check, otherwise false
   */
  boolean kingInCheck(Color color);


  /**
   * Gets all the possible moves for the selected piece at that position.
   * If no piece is selected an empty list is given.
   */
  List<Position> getPossibleMoves();

  /**
   * Gets the String grid of the board.
   * @return the grid
   */
  String[][] getTextGrid();

  String[][] getTextGrid(Color color);


  boolean isGameOver();

  /**
   * Gets the results of the game.
   * @return the result, either checkmate for white/black or stalemate
   * @throws IllegalStateException if the game is not over
   */
  GameResult getGameResult();


  /**
   * Checks if the piece is pinned.
   * @return true if the piece is pinned
   */
  boolean isPiecePinned();

  boolean isSquareEmpty(Position pos);

  boolean squareNotMoved(Position pos);

  boolean squareNotMoved(int r, int c);

  Move getLastMove();

  boolean isPawnPromotion();

  int movesMade();
}
