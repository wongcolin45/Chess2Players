package com.chess.game.Model.Game;

import com.chess.game.Model.Board.ViewableChessBoard;
import com.chess.game.Model.Color;
import com.chess.game.Model.GameStatus;
import com.chess.game.Model.Log.ViewableChessGameLog;
import com.chess.game.Model.Position;

import java.util.List;


public interface ViewableChessGame {


  boolean kingInCheck(Color color);

  /**
   * Get the turn.
   * @return the color of the turn
   */
  Color getTurn();

  /**
   * Get the possible moves for the position.
   * @param start the starting position of the piece
   * @return the possible moves
   */
  List<Position> getPossibleMoves(Position start);

  /**
   * Checks if the game is over
   * @return true if game over, otherwise false
   */
  boolean isGameOver();

  /**
   * Gets the result of the game.
   * @return the game result enum
   */
  GameStatus getGameStatus();


  /**
   * Get the chess log object
   * @return the log
   */
  ViewableChessGameLog getLog();

  /**
   * Get a viewable board.
   * @return the viewable chess board
   */
  ViewableChessBoard getViewableBoard();


  /**
   * Gets a mutable copy of the game.
   * @return the mutable chess game
   */
  SandboxChessGame getCopy();


}
