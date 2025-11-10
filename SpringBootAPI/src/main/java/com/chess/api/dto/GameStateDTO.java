package com.chess.api.dto;

import com.chess.game.Model.Color;
import com.chess.game.Model.GameStatus;

import java.util.Optional;

public class GameStateDTO {
  private final Color turn;
  private final boolean kingInCheck;
  private final boolean gameOver;
  private final PositionDTO pawnToPromote;
  private final CapturedPiecesDTO capturedPieces;
  private final GameStatus gameStatus;
  private final String[][] board;
  private final MoveDTO lastMove;
  private final PositionDTO kingPosition;
  public GameStateDTO(Color turn,
                      boolean kingInCheck,
                      Optional<MoveDTO> lastMove,
                      PositionDTO kingPosition,
                      boolean gameOver,
                      PositionDTO pawnToPromote,
                      CapturedPiecesDTO capturedPieces,
                      GameStatus gameStatus,
                      String[][] board) {
    this.turn = turn;
    this.kingPosition = kingPosition;
    this.kingInCheck = kingInCheck;
    this.lastMove = lastMove.orElse(null);
    this.gameOver = gameOver;
    this.gameStatus = gameStatus;
    this.pawnToPromote = pawnToPromote;
    this.board = board;
    this.capturedPieces = capturedPieces;
  }
  public Color getTurn() {
    return turn;
  }
  public MoveDTO getLastMove() {
    return lastMove;
  }
  public PositionDTO getKingPosition() {
    return kingPosition;
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
