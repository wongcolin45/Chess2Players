package com.chess.game.Model.Log;

import com.chess.game.Model.Board.ViewableBoard;
import com.chess.game.Model.Color;
import com.chess.game.Model.Move;
import com.chess.game.Model.Pieces.Piece;
import com.chess.game.Model.Pieces.PieceType;
import com.chess.game.Model.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This keeps track of moves made.
 */
public class GameLog implements MutableGameLog {
  private final Stack<Move> moves;

  private int fullMoveNumber;
  private int halfMoveClock;

  private final ConcurrentHashMap<Piece, Position> whiteLocations;
  private final ConcurrentHashMap<Piece, Position> blackLocations;

  public GameLog() {
    moves = new Stack<>();
    whiteLocations = new ConcurrentHashMap<>();
    blackLocations = new ConcurrentHashMap<>();
    fullMoveNumber = 0;
    halfMoveClock = 0;
  }


  @Override
  public void setPieceLocations(ViewableBoard board) {
    if (board == null) {
      throw new IllegalArgumentException("board cannot be null");
    }
    whiteLocations.clear();
    blackLocations.clear();
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        Position pos = new Position(i, j);
        if (!board.isEmpty(pos)) {
          Piece piece = board.getPiece(pos);
          if (piece.getColor() == Color.WHITE) {
            whiteLocations.put(piece, pos);
          } else {
            blackLocations.put(piece, pos);
          }
        }
      }
    }
  }

  @Override
  public void addMove(Move move) {
    moves.push(move);
    Color color = move.getPiece().getColor();
    ConcurrentHashMap<Piece, Position> locations = (color == Color.WHITE) ? whiteLocations : blackLocations;
    for (Map.Entry<Piece, Position> entry : locations.entrySet()) {
      Position pos = entry.getValue();
      if (move.getStart().equals(pos)) {
        entry.setValue(move.getEnd());
        break;
      }
    }
    if (color == Color.BLACK) {
      fullMoveNumber++;
    }
    if (move.isCapture() || move.getPiece().getType() == PieceType.PAWN) {
      halfMoveClock = 0;
    } else {
      halfMoveClock++;
    }
  }

  @Override
  public Move getLastMove() {
    if (moves.isEmpty()) {
      throw new IllegalStateException("No moves record yet cannot get last move");
    }
    return moves.peek();
  }

  @Override
  public void removeLastMove() {
    moves.pop();
  }

  @Override
  public boolean squareMoved(Position pos) {
    for (Move move : moves) {
      if (move.getStart().equals(pos) || move.getEnd().equals(pos)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public List<Position> getLocations(Color color) {
    if (color == Color.WHITE) {
      return new ArrayList<>(whiteLocations.values());
    }
    return new ArrayList<>(blackLocations.values());
  }

  @Override
  public Stack<Move> getMoves() {
    return moves;
  }

  @Override
  public int getHalfMoves() {
    return halfMoveClock;
  }

  @Override
  public int getFullMoves() {
    return fullMoveNumber;
  }

  @Override
  public int getMoveCount() {
    return moves.size();
  }


  private GameLog(Stack<Move> moves) {
    this();
    for (Move move : moves) {
      addMove(move);
    }
  }

  @Override
  public GameLog getCopy() {
    return new GameLog(getMoves());
  }


}
