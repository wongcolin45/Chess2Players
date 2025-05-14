package com.chess.api.dto;

import com.chess.game.Model.Color;
import com.chess.game.Model.GameStatus;

public class GameStateDTO {
  private Color turn;
  private boolean kingInCheck;
  private boolean gameOver;
  private PositionDTO pawnToPromote;
  private CapturedPiecesDTO capturedPieces;
  private GameStatus gameStatus;
  private String[][] board;
  public GameStateDTO(Color turn,
                      boolean kingInCheck,
                      boolean gameOver,
                      PositionDTO pawnToPromote,
                      CapturedPiecesDTO capturedPieces,
                      GameStatus gameStatus,
                      String[][] board) {
    this.turn = turn;
    this.kingInCheck = kingInCheck;
    this.gameOver = gameOver;
    this.gameStatus = gameStatus;
    this.pawnToPromote = pawnToPromote;
    this.board = board;
    this.capturedPieces = capturedPieces;
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
  public PositionDTO getPawnToPromote() {
    return pawnToPromote;
  }
  public GameStatus getGameResult() {
    return gameStatus;
  }
  public String[][] getBoard() {
    return board;
  }
  public CapturedPiecesDTO getCapturedPieces() {
    return capturedPieces;
  }
}
