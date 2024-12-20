package com.chess.game.Model.Log;

import com.chess.game.Model.Color;
import com.chess.game.Model.Log.ChessLog;
import com.chess.game.Model.Board.Board;
import com.chess.game.Model.Move;
import com.chess.game.Model.Pieces.Piece;
import com.chess.game.Model.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This keep track of moves made.
 */
public class ChessGameLog implements ChessLog {
  private Stack<Move> moves;
  private final Board board;

  private final ConcurrentHashMap<Piece, Position> whiteLocations;
  private final ConcurrentHashMap<Piece, Position> blackLocations;

  public ChessGameLog(Board board) {
    if (board == null) {
      throw new IllegalArgumentException("Cannot pass null board to log!");
    }
    moves = new Stack<>();
    this.board = board;
    whiteLocations = new ConcurrentHashMap<>();
    blackLocations = new ConcurrentHashMap<>();
    setPieceLocations();
  }

  @Override
  public void setPieceLocations() {

    whiteLocations.clear();
    blackLocations.clear();
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        Position pos = new Position(i, j);
        if (!board.isSquareEmpty(pos)) {
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
  }

  @Override
  public Move getLastMove() {
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
    setPieceLocations();
    ConcurrentHashMap<Piece, Position> map = (color == Color.WHITE) ? whiteLocations : blackLocations;
    List<Position> positions = new ArrayList<>();
    for (Position pos : map.values()) {
      positions.add(pos);
    }
    return positions;

  }

  @Override
  public void setMoves(Stack<Move> moves) {
    this.moves = moves;
  }

  @Override
  public Stack<Move> getMoves() {
    return moves;
  }

  @Override
  public int getSize() {
    return moves.size();
  }


}
