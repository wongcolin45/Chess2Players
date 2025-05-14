package com.chess.api.dto;

import com.chess.game.Model.Color;
import com.chess.game.Model.GameStatus;

public class GameStateDTO {
  private Color turn;
  private boolean kingInCheck;
  private boolean gameOver;
  private GameStatus gameStatus;
  private String[][] board;
  public GameStateDTO(Color turn, boolean kingInCheck, boolean gameOver, GameStatus gameStatus, String[][] board) {
    this.turn = turn;
    this.kingInCheck = kingInCheck;
    this.gameOver = gameOver;
    this.gameStatus = gameStatus;
    this.board = board;
  }
  public Color getTurn() {
    return turn;
  }
  public boolean isKingInCheck() {
    return kingInCheck;
  }
  public boolean isGameOver() {
    return gameOver;
  }
  public GameStatus getGameResult() {
    return gameStatus;
  }
  public String[][] getBoard() {
    return board;
  }
}
