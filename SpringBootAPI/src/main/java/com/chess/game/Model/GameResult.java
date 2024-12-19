package com.chess.game.Model;

/**
 * This represents the result of a chess game.
 */
public enum GameResult {
  WHITE_CHECKMATE,
  BLACK_CHECKMATE,
  STALEMATE_THREEFOLD_REPETITION,
  STALEMATE_INSUFFICIENT_MATERIAL,
  STALEMATE_FIFTY_MOVE_RULE,
  STALEMATE_FORCED;
}
