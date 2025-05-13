package com.chess.api;

import com.chess.api.dto.GameStateDTO;
import com.chess.api.dto.MoveDTO;
import com.chess.api.dto.PositionDTO;
import com.chess.api.dto.PossibleMovesDTO;
import com.chess.game.Model.Board.ViewableBoard;
import com.chess.game.Model.Color;
import com.chess.game.Model.Game.ChessGame;
import com.chess.game.Model.GameStatus;
import com.chess.game.Model.Log.ViewableGameLog;
import com.chess.game.Model.Move;
import com.chess.game.Model.Pieces.PieceType;
import com.chess.game.Model.Position;

import java.util.List;

/**
 * Simple Chess service for playing a single game
 */
public class ChessGameService {

  private final ChessGame chessGame;

  public ChessGameService() {
    chessGame = new ChessGame();
  }

  public void movePiece(MoveDTO moveDTO) {
    Position from = getPosition(moveDTO.getFrom());
    Position to = getPosition(moveDTO.getTo());
    chessGame.movePiece(from, to);
  }

  private String getEnPassantSquare() {
    ViewableGameLog log = chessGame.getLog();
    if (log.getMoveCount() == 0) {
      return "-";
    }
    Move lastMove = log.getLastMove();
    boolean isPawn = lastMove.getPiece().getType() == PieceType.PAWN;
    boolean doubleStep = Math.abs(lastMove.getStart().getRow() - lastMove.getEnd().getRow()) == 2;
    if (!isPawn || !doubleStep) {
      return "-";
    }
    int row = lastMove.getEnd().getRow();
    int col = lastMove.getEnd().getCol();
    Position capturePos;
    if (lastMove.getPiece().getColor() == Color.WHITE) {
      capturePos = new Position(row+1, col);
    } else {
      capturePos = new Position(row-1, col);
    }
    ViewableBoard board = chessGame.getViewableBoard();
    Position leftPos = new Position(row, col-1);
    Position rightPos = new Position(row, col+1);
    if ((leftPos.onBoard() && !board.isEmpty(leftPos) &&
        board.getPiece(leftPos).getType() == PieceType.PAWN && chessGame.getPossibleMoves(leftPos).contains(capturePos))
        ||
        (rightPos.onBoard() && !board.isEmpty(rightPos) &&
        board.getPiece(rightPos).getType() == PieceType.PAWN && chessGame.getPossibleMoves(rightPos).contains(capturePos))) {
      return capturePos.toString();
    }
    return "-";
  }

  private String getCastlingAvailability() {
    ViewableGameLog log = chessGame.getLog();
    StringBuilder marks = new StringBuilder();
    // Check white side castles
    if (!log.squareMoved(new Position("e1"))) {
      if (!log.squareMoved(new Position("h1"))) {
        marks.append("K");
      }
      if (!log.squareMoved(new Position("a1"))) {
        marks.append("Q");
      }
    }
    // Check black side castles
    if (!log.squareMoved(new Position("e8"))) {
      if (!log.squareMoved(new Position("g8"))) {
        marks.append("k");
      }
      if (!log.squareMoved(new Position("a8"))) {
        marks.append("q");
      }
    }
    // return final
    if (marks.isEmpty()) {
      return "-";
    }
    return marks.toString();
  }

  private String getPiecePlacement() {
    ViewableBoard board = chessGame.getViewableBoard();
    String[][] grid = board.getTextGrid(Color.WHITE);
    StringBuilder boardString = new StringBuilder();
    for (int i = 0; i < 8; i++) {
      StringBuilder row = new StringBuilder();
      int count = 0;
      for (int j = 0; j < 8; j++) {
        if (grid[i][j].equals(" ")) {
          count += 1;
          continue;
        }
        if (count > 0) {
          row.append(String.valueOf(count));
          count = 0;
        }
        row.append(grid[i][j]);
      }
      if (count > 0) {
        row.append(String.valueOf(count));
      }
      if (i != 7) {
        row.append("/");
      }
      boardString.append(row);
    }
    return boardString.toString();

  }

  public String getFenState() {
    return getPiecePlacement() + " " +
           chessGame.getTurn().toString().toLowerCase().charAt(0) + " " +
           getCastlingAvailability() + " " +
           getEnPassantSquare() + " " +
           chessGame.getLog().getHalfMoves() + " " +
           chessGame.getLog().getFullMoves();
  }

  public GameStateDTO getGameStateDTO() {
    Color turn = chessGame.getTurn();
    boolean kingInCheck = chessGame.kingInCheck(turn);
    boolean gameOver = chessGame.isGameOver();
    GameStatus result = chessGame.getGameStatus();
    String[][] board = chessGame.getViewableBoard().getTextGrid();
    return new GameStateDTO(turn, kingInCheck, gameOver, result, board);
  }

  public PossibleMovesDTO getPossibleMoves(PositionDTO positionDTO) {
    Position pos = getPosition(positionDTO);
    return new PossibleMovesDTO(chessGame.getPossibleMoves(pos));
  }

  private Position getPosition(PositionDTO posDTO) {
    return new Position(posDTO.getRow(), posDTO.getCol());
  }

}
